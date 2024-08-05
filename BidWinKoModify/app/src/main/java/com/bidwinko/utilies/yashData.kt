//package com.bidwinko.utilies
//
//import android.app.Activity
//import android.app.Dialog
//import android.content.Intent
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.net.Uri
//import android.os.Build
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.os.RemoteException
//import android.provider.Settings
//import android.util.Log
//import android.view.View
//import android.widget.LinearLayout
//import android.widget.Toast
//import androidx.activity.result.ActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.browser.customtabs.CustomTabColorSchemeParams
//import androidx.browser.customtabs.CustomTabsIntent
//import androidx.core.content.ContextCompat
//import com.android.installreferrer.api.InstallReferrerClient
//import com.android.installreferrer.api.InstallReferrerStateListener
//import com.android.installreferrer.api.ReferrerDetails
//import com.apps.couponstudio.BuildConfig
//import com.apps.couponstudio.R
//import com.apps.couponstudio.api.ApiInterface
//import com.apps.couponstudio.api.RestClient
//import com.apps.couponstudio.databinding.ActivityLoginBinding
//import com.apps.couponstudio.databinding.EmailLoginLayoutBinding
//import com.apps.couponstudio.model.AppOpenRes
//import com.apps.couponstudio.model.DefaultUserRes
//import com.apps.couponstudio.model.UserSignUpRes
//import com.apps.couponstudio.utill.MyPreference
//import com.apps.couponstudio.utill.NetworkConfig
//import com.google.android.gms.ads.identifier.AdvertisingIdClient
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException
//import com.google.android.gms.tasks.Task
//import com.google.firebase.FirebaseApp
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.GoogleAuthProvider
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.create
//import java.net.URLDecoder
//
//class LoginActivity : AppCompatActivity(), InstallReferrerStateListener {
//
//    private lateinit var binding: ActivityLoginBinding
//    private lateinit var auth: FirebaseAuth
//    private var mGoogleSignInClient: GoogleSignInClient? = null
//
//    private lateinit var mReferrerClient: InstallReferrerClient
//    private lateinit var myGid: String
//
//    //we need this for passing in singUpApi
//    private lateinit var deviceId: String
//    private lateinit var deviceType: String
//    private lateinit var deviceName: String
//
//    //user's social ID
//    private lateinit var socialType: String
//
//    //    private lateinit var socialId: String
//    private lateinit var socialToken: String
//
//    //user's name,email,image
//    private lateinit var socialEmail: String
//    private lateinit var socialName: String
//    private var socialImgUrl: String = ""
//
//    //user's advertisingId , versionName, versionCode
////    private  var advertisingId: String?=""
//    private lateinit var versionName: String
//    private lateinit var versionCode: String
//
//    private var userId: Int = -1
//    private var securityToken: String? = null
//
//    //user's utm
//    private var utmSource: String = ""
//    private var utmMedium: String = ""
//    private var utmTerm: String = ""
//    private var utmContent: String = ""
//    private var utmCompaign: String = ""
//
//    private var referrerLink: String = ""
//
//    companion object {
//        private const val RC_SIGN_IN = 101
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
////      Hide the status bar
//        getWindow().setFlags(
//            android.R.attr.windowFullscreen,
//            android.R.attr.windowFullscreen
//        )
//
//        setUtm()
//        getAdvertisingId()
//
//        //firebase setup
//        FirebaseApp.initializeApp(this)
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
//        auth = FirebaseAuth.getInstance()
//
//        //privacy policy
//        binding.tvPrivacyPolicy.setOnClickListener {
//            openTab("https://couponstudio.app/privacy.html")
//        }
//
//        //terms of services that are used to to define tha data
//        binding.tvTermsOfServices.setOnClickListener {
//            openTab("https://couponstudio.app/terms_n_conditions.html")
//        }
//
//        //google button
//        binding.btnLoginWithGoogle.setOnClickListener {
////            var intent = Intent(this, MainActivity::class.java)
////            startActivity(intent)
////            finish()
//
//            if (NetworkConfig().networkIsConnected(this)) {
//                binding.progressbar.visibility = View.VISIBLE
//                signIn()
//            } else {
//                Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//
//        //gmail button
//        binding.btnLoginWithGmail.setOnClickListener {
//            emailLoginDialog()
//        }
//    }
//
//    fun emailLoginDialog() {
//        val dialog = Dialog(this)
//        dialog.setCancelable(true)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        var binding = EmailLoginLayoutBinding.inflate(dialog.layoutInflater)
//        dialog.setContentView(binding.root)
//
//        //cancel
//        binding.remove.setOnClickListener {
//            dialog.dismiss()
//        }
//
//        //save
//        binding.btnLoginWithEmail.setOnClickListener {
//            checkValidation(binding, dialog)
//
////            dialog.dismiss()
////            var intent = Intent(this, MainActivity::class.java)
////            startActivity(intent)
////            finish()
//        }
//
//        //terms of services
//        binding.tvTermsOfServices.setOnClickListener {
//            dialog.dismiss()
//            openTab("https://couponstudio.app/terms_n_conditions.html")
//        }
//
//        //privacy policy
//        binding.tvPrivacyPolicy.setOnClickListener {
//            dialog.dismiss()
//            openTab("https://couponstudio.app/privacy.html")
//        }
//
//        dialog.window?.setLayout(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.MATCH_PARENT
//        )
//        dialog.show()
//    }
//
//    private fun signIn() {
//        val signInIntent = mGoogleSignInClient!!.signInIntent
//        startForResult.launch(signInIntent)
//    }
//
//    val startForResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//                handleSignInResult(task)
//            } else {
//                binding.progressbar.visibility = View.GONE
//            }
//        }
//
//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(ApiException::class.java)
//            if (account != null) {
//                firebaseAuthWithGoogle(account)
//            }
//        } catch (e: ApiException) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            binding.progressbar.visibility = View.GONE
//            Toast.makeText(this, "signInResult:failed code=" + e.statusCode, Toast.LENGTH_SHORT)
//                .show()
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
//        val firebaseCredential = GoogleAuthProvider.getCredential(account.idToken, null)
//        auth.signInWithCredential(firebaseCredential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    updateUI(account)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    binding.progressbar.visibility = View.GONE
//                    Toast.makeText(
//                        this,
//                        "Something went wrong please try again.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//    }
//
//    private fun updateUI(account: GoogleSignInAccount) {
//        if (account != null) {
//            signUpApi(account)
//        }
////        var intent=Intent(this,MainActivity::class.java)
////        startActivity(intent)
////        finish()
//    }
//
//    private fun signUpApi(account: GoogleSignInAccount) {
//        deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID).toString()
//        deviceType = Build.MODEL.toString()
//        deviceName = Build.MANUFACTURER + " " + Build.MODEL.toString()
//        socialType = "Google"
//        socialToken = account.idToken.toString()
//        socialName = account.displayName.toString()
//        socialEmail = account.email.toString()
//        socialImgUrl = account.photoUrl.toString()
//        versionName = BuildConfig.VERSION_NAME
//        versionCode = BuildConfig.VERSION_CODE.toString()
//
//        val call = RestClient.retrofit.create(ApiInterface::class.java)
//        call.signUp(
//            deviceId,
//            deviceType,
//            deviceName,
//            socialType,
//            account.id.toString(),
//            socialToken,
//            socialEmail,
//            socialName,
//            socialImgUrl,
//            myGid,
//            versionName,
//            versionCode,
//            utmSource,
//            utmMedium,
//            utmTerm,
//            utmContent,
//            utmCompaign,
//            referrerLink
//        ).enqueue(object : Callback<UserSignUpRes> {
//            override fun onResponse(call: Call<UserSignUpRes>, response: Response<UserSignUpRes>) {
//                if (response.body() != null) {
//                    if (response.isSuccessful && response.body()?.status == 200) {
//
//                        MyPreference.saveIdToken(
//                            this@LoginActivity,
//                            response.body()!!.userId,
//                            response.body()!!.securityToken
//                        )
//
//                        //hit appOpen api
//                        appOpen(
//                            response.body()!!.userId,
//                            response.body()!!.securityToken,
//                            null,
//                            null
//                        )
//
//                    } else {
//                        binding.progressbar.visibility = View.GONE
//                        Toast.makeText(
//                            this@LoginActivity,
//                            response.body()!!.message,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                } else {
//                    binding.progressbar.visibility = View.GONE
//                    Toast.makeText(
//                        this@LoginActivity,
//                        response.errorBody().toString(),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<UserSignUpRes>, t: Throwable) {
//                //  errorMessage.postValue(t.message)
//                binding.progressbar.visibility = View.GONE
//                Log.d("error", t.message.toString())
//                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun appOpen(
//        userId: Int,
//        securityToken: String,
//        emailBinding: EmailLoginLayoutBinding?,
//        dialog: Dialog?
//    ) {
//        val call = RestClient.retrofit.create(ApiInterface::class.java)
//        call.appOpen(
//            userId.toString(),
//            securityToken,
//            BuildConfig.VERSION_NAME,
//            BuildConfig.VERSION_CODE.toString()
//        ).enqueue(object : Callback<AppOpenRes> {
//            override fun onResponse(call: Call<AppOpenRes>, response: Response<AppOpenRes>) {
//                if (response.body() != null) {
//                    if (response.isSuccessful && response.body()?.status == 200) {
//
//                        if (emailBinding != null && dialog != null) {
//                            emailBinding.btnLoginWithEmail.visibility = View.VISIBLE
//                            emailBinding.progressBar.visibility = View.GONE
//                            dialog.dismiss()
//                        }
//
//                        binding.progressbar.visibility = View.GONE
//                        MyPreference.saveAppOpenDetails(
//                            this@LoginActivity,
//                            response.body()?.socialEmail,
//                            response.body()?.forceUpdate,
//                            response.body()?.socialImgUrl,
//                            response.body()?.socialName,
//                            response.body()?.appUrl
//                        )
//                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                        startActivity(intent)
//                        finish()
//
//                    } else {
//                        if (emailBinding != null && dialog != null) {
//                            emailBinding.btnLoginWithEmail.visibility = View.VISIBLE
//                            emailBinding.progressBar.visibility = View.GONE
//                        }
//                        binding.progressbar.visibility = View.GONE
//                        Toast.makeText(
//                            this@LoginActivity,
//                            response.body()!!.message,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                } else {
//                    if (emailBinding != null && dialog != null) {
//                        emailBinding.btnLoginWithEmail.visibility = View.VISIBLE
//                        emailBinding.progressBar.visibility = View.GONE
//                    }
//                    binding.progressbar.visibility = View.GONE
//                    Toast.makeText(
//                        this@LoginActivity,
//                        response.errorBody().toString(),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<AppOpenRes>, t: Throwable) {
//                //  errorMessage.postValue(t.message)
//                if (emailBinding != null && dialog != null) {
//                    emailBinding.btnLoginWithEmail.visibility = View.VISIBLE
//                    emailBinding.progressBar.visibility = View.GONE
//                }
//                binding.progressbar.visibility = View.GONE
//                Log.d("error", t.message.toString())
//                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
//            }
//
//        })
//    }
//
//    private fun getAdvertisingId() {
//        GlobalScope.launch {
//            val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(this@LoginActivity)
//            Log.d("text", adInfo.id ?: "unknown")
//            myGid = adInfo.id.toString()
//        }
//    }
//
//    private fun setUtm() {
//        mReferrerClient = InstallReferrerClient.newBuilder(this).build()
//        mReferrerClient.startConnection(this)
//    }
//
//    fun openTab(url: String) {
//        try {
//            val builder = CustomTabsIntent.Builder()
//
//            // to set the toolbar color use CustomTabColorSchemeParams
//            // since CustomTabsIntent.Builder().setToolBarColor() is deprecated
//
//            val params = CustomTabColorSchemeParams.Builder()
//            params.setToolbarColor(ContextCompat.getColor(this, R.color.white))
//            builder.setDefaultColorSchemeParams(params.build())
//            // shows the title of web-page in toolbar
//            builder.setShowTitle(true)
//
//            // setShareState(CustomTabsIntent.SHARE_STATE_ON) will add a menu to share the web-page
//            builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)
//
//            // To modify the close button, use
//            // builder.setCloseButtonIcon(bitmap)
//
//            // to set weather instant apps is enabled for the custom tab or not, use
//            builder.setInstantAppsEnabled(true)
//
//            //  To use animations use -
//            //  builder.setStartAnimations(this, android.R.anim.start_in_anim, android.R.anim.start_out_anim)
//            //  builder.setExitAnimations(this, android.R.anim.exit_in_anim, android.R.anim.exit_out_anim)
//            val customBuilder = builder.build()
//            customBuilder.intent.setPackage("com.android.chrome")
//            customBuilder.launchUrl(this, Uri.parse(url))
//        }
//        catch (e:Exception)
//        {
//
//        }
//    }
//
//    override fun onInstallReferrerSetupFinished(p0: Int) {
//        when (p0) {
//            InstallReferrerClient.InstallReferrerResponse.OK ->                 // Connection established
//                try {
//                    getReferralUser()
//                } catch (e: RemoteException) {
//                    e.printStackTrace()
//                }
//
//            InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {}
//            InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {}
//            InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR -> {}
//            InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED -> {}
//        }
//    }
//
//    override fun onInstallReferrerServiceDisconnected() {
//
//    }
//
//    private fun getReferralUser() {
//        try {
//            val response: ReferrerDetails = mReferrerClient.installReferrer
//            val referrerData = response.installReferrer
//            referrerLink = referrerData.toString()
//            Log.d("LINK_", referrerLink)
//            Log.d("TAG12122", "Install referrer: $referrerData")
//            Log.d("TAG1212", "Install referrer: $referrerLink")
//
//            // Parse referrer data for UTM parameters
//            val values = HashMap<String, String>()
//            referrerData?.split("&")?.forEach { referrerValue ->
//                val keyValue = referrerValue.split("=").toTypedArray()
//                if (keyValue.size == 2) {
//                    values[URLDecoder.decode(keyValue[0], "UTF-8")] =
//                        URLDecoder.decode(keyValue[1], "UTF-8")
//                }
//            }
//
//            utmSource = values["utm_source"].toString()
//            utmMedium = values["utm_medium"].toString()
//            utmTerm = values["utm_term"].toString()
//            utmCompaign = values["utm_campaign"].toString()
//            utmContent = values["utm_content"].toString()
//
//            Log.d(
//                "API_UTM",
//                "utmSource: $utmSource, utmMedium: $utmMedium, utmTerm: $utmTerm, utmCompaign: $utmCompaign, utmContent: $utmContent"
//            )
//        } catch (e: Exception) {
//            Log.e("TAG1", "Failed to get install referrer: ${e.message}")
//        }
//    }
//
//    private fun checkValidation(emailBinding: EmailLoginLayoutBinding, dialog: Dialog) {
//        if (emailBinding.etEmail.getText().toString() != "testingyash8@gmail.com") {
//            Toast.makeText(this, "Type valid email id", Toast.LENGTH_SHORT).show()
//        } else if (emailBinding.etPass.getText().toString() != "yash@123") {
//            Toast.makeText(this, "Type valid password", Toast.LENGTH_SHORT).show()
//        } else {
//            if (NetworkConfig().networkIsConnected(this)) {
//                emailLoginApi(emailBinding, dialog)
//            } else {
//                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun emailLoginApi(emailBinding: EmailLoginLayoutBinding, dialog: Dialog) {
//        emailBinding.btnLoginWithEmail.visibility = View.GONE
//        emailBinding.progressBar.visibility = View.VISIBLE
//        var call = RestClient.retrofit.create(ApiInterface::class.java)
//        call.defaultUser(
//            emailBinding.etEmail.getText().toString(),
//            emailBinding.etPass.getText().toString(),
//            BuildConfig.VERSION_NAME,
//            BuildConfig.VERSION_CODE.toString()
//        ).enqueue(object : Callback<DefaultUserRes> {
//            override fun onResponse(
//                call: Call<DefaultUserRes>,
//                response: Response<DefaultUserRes>
//            ) {
//                if (response.body() != null) {
//                    if (response.isSuccessful && response.body()?.status == 200) {
//                        MyPreference.saveIdToken(
//                            this@LoginActivity,
//                            response.body()!!.userId,
//                            response.body()!!.securityToken
//                        )
//
//                        //hit appOpen api
//                        appOpen(
//                            response.body()!!.userId,
//                            response.body()!!.securityToken,
//                            emailBinding,
//                            dialog
//                        )
//                    } else {
//                        emailBinding.btnLoginWithEmail.visibility = View.VISIBLE
//                        emailBinding.progressBar.visibility = View.GONE
//                        Toast.makeText(
//                            this@LoginActivity,
//                            response.body()!!.message,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                } else {
//                    emailBinding.btnLoginWithEmail.visibility = View.VISIBLE
//                    emailBinding.progressBar.visibility = View.GONE
//                    Toast.makeText(
//                        this@LoginActivity,
//                        response.errorBody().toString(),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<DefaultUserRes>, t: Throwable) {
//                emailBinding.btnLoginWithEmail.visibility = View.VISIBLE
//                emailBinding.progressBar.visibility = View.GONE
//                Log.d("error", t.message.toString())
//                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
//            }
//
//        })
//    }
//}