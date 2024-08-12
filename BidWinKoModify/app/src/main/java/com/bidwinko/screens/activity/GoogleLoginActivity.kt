package com.bidwinko.screens.activity

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.RemoteException
import android.provider.Settings
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
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

        binding.googleLogin.setOnClickListener(this)
        viewModel = mainViewModel(this)
        sessionManager = SessionManager(this)
        context = this
       binding.anim.let{
            it.playAnimation()
            it.addAnimatorListener(object : AnimatorListener{
                override fun onAnimationStart(animation: Animator) {
                    
                }

                override fun onAnimationEnd(animation: Animator) {
             binding.anim.playAnimation()
                }

                override fun onAnimationCancel(animation: Animator) {
             
                }

                override fun onAnimationRepeat(animation: Animator) {
             
                }

            })
        }

        val text = ContextCompat.getString(this@GoogleLoginActivity,R.string.textprviacy)

        val spannableString = SpannableString(text)

        // Set color and clickable span for "Privacy Policy"
        val privacyPolicyText = "Privacy Policy"
        val privacyPolicyStartIndex = text.indexOf(privacyPolicyText)
        val privacyPolicyEndIndex = privacyPolicyStartIndex + privacyPolicyText.length

        val privacyPolicyClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val url = "http://192.168.1.31:5000/privacy.html"
                // Open Privacy Policy webpage
                openTab(url)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false // Remove underline
            }
        }

        val blue = ContextCompat.getColor(this@GoogleLoginActivity,R.color.blue)

        spannableString.setSpan(
            ForegroundColorSpan(blue),
            privacyPolicyStartIndex, privacyPolicyEndIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            privacyPolicyClickableSpan,
            privacyPolicyStartIndex, privacyPolicyEndIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Set color and clickable span for "Terms Of Services"
        val termsOfServicesText = "Terms Of Services"
        val termsOfServicesStartIndex = text.indexOf(termsOfServicesText)
        val termsOfServicesEndIndex = termsOfServicesStartIndex + termsOfServicesText.length

        val termsOfServicesClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val urlcondition = "http://192.168.1.31:5000/terms.html"
                // Open Terms Of Services webpage
                openTab(urlcondition)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false // Remove underline
            }
        }

        spannableString.setSpan(
            ForegroundColorSpan(blue),
            termsOfServicesStartIndex, termsOfServicesEndIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            termsOfServicesClickableSpan,
            termsOfServicesStartIndex, termsOfServicesEndIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvPrivacy.text = spannableString
        binding.tvPrivacy.movementMethod = android.text.method.LinkMovementMethod.getInstance()

        findViewById<View>(R.id.google_login).setOnClickListener(this)
        FirebaseApp.initializeApp(this)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance(FirebaseApp.getInstance())
        setUtm()
        binding.image.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fast_fade_in))

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
                SessionManager(this).InitializeValue(Constants.TOTAL_BIDS,it.bids)
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
                    overridePendingTransition(R.anim.fast_right_slide_in, R.anim.fast_left_slide_out)
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
                sessionManager.InitializeValue(
                    Constants.SECURITY_TOKEN,
                    it.securityToken.toString()
                )
                openApp()
            } else {
                Toast.makeText(this, "Try again later ${it.message}", Toast.LENGTH_LONG).show()
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
    override fun onClick(v: View) {
        when (v.id) {
            R.id.google_login -> signIn()
        }
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

    fun openTab(url: String) {
        try {
            val builder = CustomTabsIntent.Builder()

            // to set the toolbar color use CustomTabColorSchemeParams
            // since CustomTabsIntent.Builder().setToolBarColor() is deprecated

            val params = CustomTabColorSchemeParams.Builder()
            params.setToolbarColor(ContextCompat.getColor(context!!, R.color.black))
            builder.setDefaultColorSchemeParams(params.build())
            // shows the title of web-page in toolbar
            builder.setShowTitle(true)

            // setShareState(CustomTabsIntent.SHARE_STATE_ON) will add a menu to share the web-page
            builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)

            // To modify the close button, use
            // builder.setCloseButtonIcon(bitmap)

            // to set weather instant apps is enabled for the custom tab or not, use
            builder.setInstantAppsEnabled(true)

            //  To use animations use -
            //  builder.setStartAnimations(this, android.R.anim.start_in_anim, android.R.anim.start_out_anim)
            //  builder.setExitAnimations(this, android.R.anim.exit_in_anim, android.R.anim.exit_out_anim)
            val customBuilder = builder.build()
            customBuilder.intent.setPackage("com.android.chrome")
            customBuilder.launchUrl(context!!, Uri.parse(url))
        }
        catch (e:Exception)
        {
            Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
        }
    }
}
