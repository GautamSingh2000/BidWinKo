package com.bidwinko.screens.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import com.bidwinko.R
import com.bidwinko.databinding.PaymodeFragmentBinding
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager

class ReferActivity : AppCompatActivity() {
    lateinit var binding: PaymodeFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PaymodeFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val upArrow = getResources().getDrawable(R.drawable.ic_arrow_back)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.black)))
        supportActionBar!!.title = "Invite"
        val link = SessionManager(this).GetValue(Constants.APP_URL)

        binding.copy.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val textToCopy = link // Replace with the text you want to copy

            val clip = ClipData.newPlainText("Copied Text", textToCopy)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        binding.insta.setOnClickListener {
            shareLink("com.instagram.android", link, "Instagram app not found")
        }

        binding.facebook.setOnClickListener {
            shareLink("com.facebook.katana", link, "Facebook app not found")
        }

        binding.teligram.setOnClickListener {
            shareLink("org.telegram.messenger", link, "Telegram app not found")
        }

        binding.whatsapp.setOnClickListener {
            shareLink("com.whatsapp", link, "WhatsApp app not found")
        }

        binding.other.setOnClickListener {
            // Replace with the copied link
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, link)
            val chooserIntent = Intent.createChooser(intent, "Share with")
            startActivity(chooserIntent)
        }


        binding.referalcode.text = link
    }

    private fun shareLink(packageName: String, link: String, notFoundMessage: String) {
        if (isAppInstalled(packageName)) {
            val intent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(link)
                .intent
                .setPackage(packageName)

            startActivity(intent)
        } else {
            Toast.makeText(this, notFoundMessage, Toast.LENGTH_SHORT).show()
        }
    }


    // Helper function to check if an app is installed
    private fun isAppInstalled(packageName: String): Boolean {
        val packageManager = packageManager
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}