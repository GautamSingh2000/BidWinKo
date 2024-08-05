package com.bidwinko.screens.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.RemoteException
import android.provider.Settings
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.bidwinko.MainActivity
import com.bidwinko.R
import com.bidwinko.databinding.ActivityLoginBinding
import com.bidwinko.model.RequestModels.UserSignUpRequest
import com.bidwinko.model.RequestModels.AppOpenRequest
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.net.URLDecoder

class GoogleLoginActivity : AppCompatActivity() , View.OnClickListener , InstallReferrerStateListener{

    private var utmSource: String = ""
    private var utmMedium: String = ""
    private var utmTerm: String = ""
    private var utmContent: String = ""
    private var utmCompaign: String = ""

    private var referrerLink: String = ""

    var TAG = "GoogleLoginACtivity"
    var progressDialog: ProgressDialog? = null
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: mainViewModel
    lateinit var sessionManager: SessionManager
    private var mAuth: FirebaseAuth? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var context : Context
    private lateinit var mReferrerClient: InstallReferrerClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvPrivacy.setOnClickListener(this)
        binding.txtTermcondition.setOnClickListener(this)
        binding.googleLogin.setOnClickListener(this)
        viewModel = mainViewModel(this)
        sessionManager = SessionManager(this)
        context = this

        findViewById<View>(R.id.google_login).setOnClickListener(this)
        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance(FirebaseApp.getInstance())
        setUtm()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("276302943649-ce98hsrs7vl775dch6gjd06o1rplvvu6.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = completedTask.getResult(ApiException::class.java)
           //            firebaseAuthWithGoogle(account);

            val devicename = sessionManager.GetValue(Constants.DEVICE_NAME)
            val version = sessionManager.GetValue(Constants.VERSION_NAME)
            val versioncode = sessionManager.GetValue(Constants.VERSION_CODE)

            userSignUp(
                devicename = devicename,
                socialtype = "google",
                id = account.id.toString(),
                email = account.email.toString(),
                displayName = account.displayName.toString(),
                photoUrl = account.photoUrl.toString(),
                adId = sessionManager.GetValue(Constants.ADVERTISING_ID),
                acct = account,
                versionCode = versioncode,
                versionName = version
            )
            Log.e(TAG, "onActivityResult: " + account.displayName)
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Toast.makeText(this, "Signin Fails!!", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Google sign in failed", e)
        }
    }


    private fun openApp() {

        if (this@GoogleLoginActivity.isFinishing) {
            val progressDialog = ProgressDialog(this@GoogleLoginActivity)
            progressDialog.setMessage(getString(R.string.loadingwait))
            progressDialog.show()
            progressDialog.setCancelable(false)
        }

        val versionname = sessionManager.GetValue(Constants.VERSION_NAME)
        val versionCode = sessionManager.GetValue(Constants.VERSION_CODE)
        val UserId = sessionManager.GetValue(Constants.USER_ID)
        val securityToken = sessionManager.GetValue(Constants.SECURITY_TOKEN)

        val appOpenRequest = AppOpenRequest(
            userId = UserId,
            securityToken = securityToken,
            versionName = versionname,
            versionCode = versionCode
        )

        viewModel.appOpen(appOpenRequest).observe(this) {
            progressDialog?.dismiss()
            if (it.status == 200) {

                if (it.forceUpdate) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                        )
                    )
                    finish()
                } else {

                    Toast.makeText(this, "Signin Complete!!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(
                    this@GoogleLoginActivity,
                    "Somthing Went Wrong Try Again Later!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun setUtm() {
        mReferrerClient = InstallReferrerClient.newBuilder(this).build()
        mReferrerClient.startConnection(this@GoogleLoginActivity)
    }

    private fun userSignUp(
        devicename: String,
        socialtype: String,
        id: String,
        email: String,
        displayName: String,
        photoUrl: String,
        adId: String,
        acct: GoogleSignInAccount,
        versionCode: String,
        versionName: String,
    ) {
        if (this@GoogleLoginActivity.isFinishing) {
            val progressDialog = ProgressDialog(this@GoogleLoginActivity)
            progressDialog.setMessage(getString(R.string.loadingwait))
            progressDialog.show()
            progressDialog.setCancelable(false)
        }

        val request = UserSignUpRequest(
            deviceName = devicename,
            deviceType = sessionManager.GetValue(Constants.DEVICE_TYPE),
            deviceId = sessionManager.GetValue(Constants.DEVICE_ID),
            socialType = socialtype,
            socialId = id,
            socialEmail = email,
            socialName = displayName,
            advertisingId = adId,
            versionCode = versionCode,
            versionName = versionName,
            utmSource = sessionManager.GetValue(Constants.UTM_SOURCE),
            utmCampaign = sessionManager.GetValue(Constants.UTM_Campaign),
            utmContent = sessionManager.GetValue(Constants.UTM_Content),
            utmMedium = sessionManager.GetValue(Constants.UTM_Medium),
            utmTerm = sessionManager.GetValue(Constants.UTM_Term),
            socialImgurl = photoUrl,
            socialToken = acct.idToken.toString(),
            referalUrl = sessionManager.GetValue(Constants.UTM_ReferalLink)
        )

        viewModel.Signup(request).observe(this) {
            progressDialog?.dismiss()
            if (it.status == 200) {
                sessionManager.InitializeValue(Constants.USER_ID, it.userId.toString())
                sessionManager.InitializeValue(Constants.SECURITY_TOKEN, it.securityToken.toString())
                openApp()
            } else {
                Toast.makeText(this, "Try again later ${it.message}", Toast.LENGTH_LONG).show()
            }
        }
    }


    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        // [START_EXCLUDE silent]
//        showProgressDialog();
        // [END_EXCLUDE]
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //                            Log.d(TAG, "signInWithCredential:success");
                    val user = mAuth!!.currentUser
                    //                            Log.e(TAG, "onComplete: "+user.getDisplayName());
                    //                            Log.e(TAG, "onComplete: "+user.getEmail());

                    //                            Log.e(TAG, "onComplete:pho "+user.getPhotoUrl());
                    val apilevel = Build.VERSION.SDK_INT.toString()
                    val android_id = Settings.Secure.getString(
                        contentResolver, Settings.Secure.ANDROID_ID
                    )
                    val devicename = Build.MODEL
                    var version = ""
                    Log.d(TAG, "task success: " + task.isSuccessful)
                    var versioncode = 0
                    try {
                        val pInfo = packageManager.getPackageInfo(
                            packageName, 0
                        )
                        version = pInfo.versionName
                        versioncode = pInfo.versionCode
                    } catch (e: PackageManager.NameNotFoundException) {
                        e.printStackTrace()
                    }
                    val userFrom = "bidwinko"
//                    Constants.setSharedPreferenceString(
//                        this@GoogleLoginActivity,
//                        "email",
//                        acct.email
//                    )
//                    Constants.setSharedPreferenceString(
//                        this@GoogleLoginActivity,
//                        "displayName",
//                        acct.displayName
//                    )
//                    Constants.setSharedPreferenceInt(
//                        this@GoogleLoginActivity,
//                        "versionCode",
//                        versioncode
//                    )
//                    Constants.setSharedPreferenceString(
//                        this@GoogleLoginActivity,
//                        "versionName",
//                        version
//                    )
//                    Constants.setSharedPreferenceString(
//                        this@GoogleLoginActivity,
//                        "userFrom",
//                        userFrom
//                    )

                    //                            userSignUp(apilevel, android_id, devicename, "google", acct.getId(),
                    //                                    Constants.getSharedPreferenceString(GoogleLoginActivity.this, "email", ""), acct.getDisplayName(),
                    //                                    acct.getPhotoUrl(), Constants.getSharedPreferenceString(GoogleLoginActivity.this, "adverId", ""),
                    //                                    acct);
                } else {
                }
            }
    }
    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        signInLauncher.launch(signInIntent)
    }


    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    // [END signin]
    private fun signOut() {
        // Firebase sign out
        mAuth!!.signOut()

        // Google sign out
        mGoogleSignInClient!!.signOut().addOnCompleteListener(
            this
        ) {
            //                        updateUI(null);
        }
    }

    private fun revokeAccess() {
        // Firebase sign out
        mAuth!!.signOut()

        // Google revoke access
        mGoogleSignInClient!!.revokeAccess().addOnCompleteListener(
            this
        ) {
            //                        updateUI(null);
        }
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.google_login -> signIn()
            R.id.txt_privacy -> {
                val url = "https://bidwinzo.app/privacy-policy.html"
                webViewLoad(url, "Privacy Policy")
            }

            R.id.txt_termcondition -> {
                val urlcondition = "https://bidwinzo.app/terms-conditions.html"
                webViewLoad(urlcondition, "Terms Of Services")
            }
        }
    }

    private fun webViewLoad(url: String, title: String) {
        val alert = AlertDialog.Builder(this@GoogleLoginActivity)
        alert.setTitle(title)
        val wv = WebView(this@GoogleLoginActivity)
        wv.loadUrl(url)
        wv.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        })
        alert.setView(wv)
        alert.setNegativeButton(
            "Accept"
        ) { dialog, id -> dialog.dismiss() }
        alert.show()
    }

    companion object {
        private const val RC_SIGN_IN = 101
    }
    override fun onInstallReferrerSetupFinished(p0: Int) {
        when (p0) {
            InstallReferrerClient.InstallReferrerResponse.OK ->                 // Connection established
                try {
                    getReferralUser()
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }

            InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {}
            InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {}
            InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR -> {}
            InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED -> {}
        }
    }

    override fun onInstallReferrerServiceDisconnected() {

    }


    private fun getReferralUser() {
        try {
            val response: ReferrerDetails = mReferrerClient.installReferrer
            val referrerData = response.installReferrer
            referrerLink = referrerData.toString()
            Log.d("LINK_", referrerLink)
            Log.d("TAG12122", "Install referrer: $referrerData")
            Log.d("TAG1212", "Install referrer: $referrerLink")

            // Parse referrer data for UTM parameters
            val values = HashMap<String, String>()
            referrerData?.split("&")?.forEach { referrerValue ->
                val keyValue = referrerValue.split("=").toTypedArray()
                if (keyValue.size == 2) {
                    values[URLDecoder.decode(keyValue[0], "UTF-8")] =
                        URLDecoder.decode(keyValue[1], "UTF-8")
                }
            }

            utmSource = values["utm_source"].toString()
            utmMedium = values["utm_medium"].toString()
            utmTerm = values["utm_term"].toString()
            utmCompaign = values["utm_campaign"].toString()
            utmContent = values["utm_content"].toString()

            sessionManager.InitializeValue(Constants.UTM_SOURCE, utmSource)
            sessionManager.InitializeValue(Constants.UTM_Medium, utmMedium)
            sessionManager.InitializeValue(Constants.UTM_Term, utmTerm)
            sessionManager.InitializeValue(Constants.UTM_Content, utmCompaign)
            sessionManager.InitializeValue(Constants.UTM_Campaign, utmContent)
            sessionManager.InitializeValue(Constants.UTM_ReferalLink, referrerLink)

            Log.d(
                "API_UTM",
                "utmSource: $utmSource, utmMedium: $utmMedium, utmTerm: $utmTerm, utmCompaign: $utmCompaign, utmContent: $utmContent"
            )
        } catch (e: Exception) {
            Log.e("TAG1", "Failed to get install referrer: ${e.message}")
        }
    }
}
