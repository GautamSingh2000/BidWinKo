package com.bidwinko.screens.activity

import android.app.Activity
import android.app.ActivityOptions
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.bidwinko.MainActivity
import com.bidwinko.R
import com.bidwinko.model.RequestModels.AppOpenRequest
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

private var handler: Handler? = null
private lateinit var adInfoData: String
private lateinit var mainViewModel: mainViewModel
private lateinit var sessionManager: SessionManager
lateinit var versionName: String

class SplashActivity() : AppCompatActivity() {
    var progressDialog: ProgressDialog? = null
    var TAG = "Splash Screen"
    var tranTxt = ""
    var txtVersion: TextView? = null
    override fun onStart() {
        super.onStart()
        Log.e("SplashActivity", "openig SplashActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("SplashActivity", "in OnDestroy  SplashActivity")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        txtVersion = findViewById(R.id.txtvesion)
        supportActionBar?.hide()
        mainViewModel = mainViewModel(this)
        sessionManager = SessionManager(this)
        val logo_image = findViewById<ImageView>(R.id.image)
        val logo_name = findViewById<TextView>(R.id.name)

//        image.startAnimation(AnimationUtils.loadAnimation(this,R.anim.zoom_out))
        coroutineScope.launch {
            try {
                val adInfo = async { getAdvertisingIdInfo() }
                val packageInfo = async { getPackageInfo() }

                adInfoData = adInfo.await()?.id ?: ""
                val versionCode = packageInfo.await().versionCode
                versionName = packageInfo.await().versionName
                txtVersion?.let { it.setText("Version: $versionName") }
                val deviceId =
                    Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                sessionManager.InitializeValue(Constants.DEVICE_ID, deviceId)
                val deviceType = Build.MODEL
                val deviceManufacturer = Build.MANUFACTURER
                val deviceName = "$deviceManufacturer $deviceType"
                Log.e(
                    "SplashScreen",
                    "$deviceId, $deviceType, $deviceName, $adInfoData, $versionName, $versionCode"
                )
                sessionManager.InitializeValue(Constants.VERSION_CODE, versionCode.toString())
                sessionManager.InitializeValue(Constants.VERSION_NAME, versionName)
                sessionManager.InitializeValue(Constants.DEVICE_NAME, deviceName)
                sessionManager.InitializeValue(Constants.DEVICE_TYPE, deviceType)
                sessionManager.InitializeValue(Constants.ADVERTISING_ID, adInfoData)

                if (isNetworkAvailable(this@SplashActivity)) {
                    if (!sessionManager.GetValue(Constants.USER_ID).equals("null") &&
                        !sessionManager.GetValue(Constants.SECURITY_TOKEN).equals("null")
                    ) {
                        Log.e("SplashSCreen", "openig OpenApp API")
                        openApp(this@SplashActivity)
                    } else {
                        Handler().postDelayed({
                            if (!(this@SplashActivity as Activity).isFinishing) {
                                progressDialog = ProgressDialog(this@SplashActivity)
                                progressDialog!!.setMessage(getString(R.string.loadingwait))
                                progressDialog!!.show()
                                progressDialog!!.setCancelable(false)
                            }
                            Log.e("SplashSCreen", "sending to google signin activity !")
                            val intent = Intent(this@SplashActivity, GoogleLoginActivity::class.java)
                            val options = ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity,
                                android.util.Pair(logo_image, "logo"),
                                android.util.Pair(logo_name,"appname"))
                            startActivity(intent,options.toBundle())
                            overridePendingTransition(R.anim.fast_right_slide_in, R.anim.fast_left_slide_out)
                            finish()
                        }, 3000)
                    }
                } else {
                    val builder = AlertDialog.Builder(this@SplashActivity)
                    builder.setTitle("Alert!")
                    builder.setMessage("Please Check Your Internet Connection.")
                    builder.setPositiveButton(
                        "Exit"
                    ) { dialog, which ->
                        dialog.dismiss()
                        finish()
                    }
                    builder.setCancelable(false)
                    builder.show()
                }

            } catch (e: Exception) {
                Toast.makeText(this@SplashActivity, "Restart Your App !!", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun openApp(context : Context) {
        if (this@SplashActivity.isFinishing) {
            val progressDialog = ProgressDialog(this@SplashActivity)
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

        mainViewModel.appOpen(appOpenRequest).observe(this@SplashActivity) {
            dismissProgressDialog()
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
                    SessionManager(this).InitializeValue(Constants.APP_URL,it.appUrl)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fast_right_slide_in, R.anim.fast_left_slide_out)
                    finish()
                }
            } else {
                Toast.makeText(this@SplashActivity, "Somthing Went Wrong Try Again Later!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        dismissProgressDialog()
    }

    override fun onStop() {
        super.onStop()
        dismissProgressDialog()
    }

    private fun dismissProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }
    companion object {
        fun isNetworkAvailable(c: Context): Boolean {
            val connectivityManager = c
                .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager
                .activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    private suspend fun getPackageInfo(): PackageInfo {
        return withContext(Dispatchers.IO) {
            try {
                packageManager.getPackageInfo(packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                PackageInfo()
            }
        }
    }

    private suspend fun getAdvertisingIdInfo(): AdvertisingIdClient.Info? {
        return withContext(Dispatchers.IO) {
            try {
                AdvertisingIdClient.getAdvertisingIdInfo(applicationContext)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }



}
