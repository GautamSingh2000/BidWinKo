package com.bidwinko.screens.fragments

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bidwinko.BuildConfig
import com.bidwinko.MainActivity
import com.bidwinko.R
import com.bidwinko.databinding.MenuFragmentBinding
import com.bidwinko.screens.activity.TransactionActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MenuFragment : Fragment(), View.OnClickListener {
    var versionCode = 0
    var versionName: String? = null
    lateinit var binding : MenuFragmentBinding

    private lateinit var const: FragmentActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            const = context
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MenuFragmentBinding.inflate(layoutInflater)
        const.findViewById<TextView>(R.id.title).text = getString(R.string.menu)
        versionCode = BuildConfig.VERSION_CODE
        versionName = BuildConfig.VERSION_NAME

        binding.txtvesion.text = "Version: $versionName"
        binding.cardAuction.setOnClickListener(this)
        binding.cardBuybid.setOnClickListener(this)
        binding.cardWinner.setOnClickListener(this)
        binding.cardMyprofile.setOnClickListener(this)
//        binding.cardLikefb.setOnClickListener(this)
//        binding.cardTwitter.setOnClickListener(this)
//       binding.cardTelegram.setOnClickListener(this)
        binding.cardTransaction.setOnClickListener(this)
        binding.termcondition.setOnClickListener(this)
        binding.privacy.setOnClickListener(this)
        binding.aboutus.setOnClickListener(this)
        binding.contactus.setOnClickListener(this)
        binding.returnrefund.setOnClickListener(this)
        binding.workflow.setOnClickListener(this)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.card_auction -> (activity as MainActivity?)!!.setupBottomNavigationFrom(R.id.action_home)
            R.id.card_buybid -> (activity as MainActivity?)!!.setupBottomNavigationFrom(R.id.action_buy_bid)
            R.id.card_winner -> (activity as MainActivity?)!!.setupBottomNavigationFrom(R.id.action_winner)
            R.id.card_myprofile -> (activity as MainActivity?)!!.setupBottomNavigationFrom(R.id.action_myprofile)
            R.id.card_transaction -> {
                val intentTransaction = Intent(activity, TransactionActivity::class.java)
                startActivity(intentTransaction)
            }

            R.id.termcondition -> {
                val urlcondition = "http://www.bidwin.co.in/terms.html"
                openTab(urlcondition)
             }

            R.id.privacy -> {
                val url = "http://www.bidwin.co.in/privacy.html"
                openTab(url)
             }

            R.id.aboutus -> {
                val urlaboutus = "http://www.bidwin.co.in/about.html"
                openTab(urlaboutus)
            }

            R.id.contactus -> {
                val urlcontactus = "http://www.bidwin.co.in/contact-us.html"
                openTab(urlcontactus)
            }

            R.id.returnrefund -> {
                val urlreturnrefund = "http://www.bidwin.co.in/refund.html"
                openTab(urlreturnrefund)
            }

            R.id.workflow -> {
                val urlworkflow = "http://www.bidwin.co.in/how_it_works.html"
                openTab(urlworkflow)
            }
        }
    }

    fun openTab(url: String) {
        try {
            val builder = CustomTabsIntent.Builder()

            // to set the toolbar color use CustomTabColorSchemeParams
            // since CustomTabsIntent.Builder().setToolBarColor() is deprecated

            val params = CustomTabColorSchemeParams.Builder()
            params.setToolbarColor(ContextCompat.getColor(const, R.color.black))
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
            customBuilder.launchUrl(const, Uri.parse(url))
        }
        catch (e:Exception)
        {
            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
        }
    }
    companion object {
        fun newInstance(): MenuFragment {
            return MenuFragment()
        }
    }
}