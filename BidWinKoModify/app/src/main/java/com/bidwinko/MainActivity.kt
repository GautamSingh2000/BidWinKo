package com.bidwinko

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bidwinko.BottomNavigationViewHelper.disableShiftMode
import com.bidwinko.databinding.ActivityMainBinding
import com.bidwinko.screens.activity.ShareEarnActivity
import com.bidwinko.screens.fragments.AuctionFragment
import com.bidwinko.screens.fragments.BuyBidFragment
import com.bidwinko.screens.fragments.MenuFragment
import com.bidwinko.screens.fragments.MyProfileFragment
import com.bidwinko.screens.fragments.WinnerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var mBottomNavigationView: BottomNavigationView? = null
    private lateinit var binding: ActivityMainBinding

    //    var fab: FloatingActionButton? = null
    private var indicator = "auction"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        fab = findViewById<View>(R.id.fab) as FloatingActionButton
        setupBottomNavigation()
        if (savedInstanceState == null) {
            loadHomeFragment()
        }
//        fab!!.setOnClickListener { //                Toast.makeText(MainActivity.this, "click me", Toast.LENGTH_SHORT).show();
//            val intentshare = Intent(this@MainActivity, ShareEarnActivity::class.java)
//            startActivity(intentshare)
//        }
    }

    fun setupBottomNavigation() {
        mBottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        disableShiftMode(mBottomNavigationView!!)
        mBottomNavigationView!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    Log.e("mainactivity", "$indicator")
                    hideIndicator("auction")
                    binding.auctionIndicator.visibility = View.VISIBLE
                    val anim = AnimationUtils.loadAnimation(this,R.anim.slideup)
                    binding.auctionIndicator.animation = anim
                    loadHomeFragment()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.action_buy_bid -> {
                    Log.e("mainactivity", "$indicator")
                    hideIndicator("buy")
                    binding.buybidIndicator.visibility = View.VISIBLE
                    val anim = AnimationUtils.loadAnimation(this,R.anim.slideup)
                    binding.buybidIndicator.animation = anim
                    loadBuyBidFragment()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.action_winner -> {
                    Log.e("mainactivity", "$indicator")
                    hideIndicator("winner")
                    binding.winnerIndicator.visibility = View.VISIBLE
                    val anim = AnimationUtils.loadAnimation(this,R.anim.slideup)
                    binding.winnerIndicator.animation = anim
                    loadWinnerFragment()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.action_myprofile -> {
                    Log.e("mainactivity", "$indicator")
                    hideIndicator("profile")
                    binding.profileIndicator.visibility = View.VISIBLE
                    val anim = AnimationUtils.loadAnimation(this,R.anim.slideup)
                    binding.profileIndicator.animation = anim
                    loadProfileFragment()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.action_menu -> {

                    Log.e("mainactivity", "$indicator")
                    hideIndicator("menu")
                    binding.menuIndicator.visibility = View.VISIBLE
                    val anim = AnimationUtils.loadAnimation(this,R.anim.slideup)
                    binding.menuIndicator.animation = anim
                    loadMenuFragment()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    private fun hideIndicator(value: String) {
        val anim = AnimationUtils.loadAnimation(this,R.anim.slidedown)
        if (indicator.equals("auction")) {
            binding.auctionIndicator.startAnimation(anim)
            anim.setAnimationListener(object : AnimationListener{
                override fun onAnimationStart(animation: Animation?) {
                    
                }
                override fun onAnimationEnd(animation: Animation?) {
                    binding.auctionIndicator.visibility = View.INVISIBLE
                }

                override fun onAnimationRepeat(animation: Animation?) {
                    
                }

            })

            }
            if (indicator.equals("buy")) {
                binding.buybidIndicator.startAnimation(anim)
                anim.setAnimationListener(object : AnimationListener{
                    override fun onAnimationStart(animation: Animation?) {

                    }
                    override fun onAnimationEnd(animation: Animation?) {
                        binding.buybidIndicator.visibility = View.INVISIBLE
                    }

                    override fun onAnimationRepeat(animation: Animation?) {

                    }

                })
                }
                if (indicator.equals("menu")) {
                    binding.menuIndicator.startAnimation(anim)
                    anim.setAnimationListener(object : AnimationListener{
                        override fun onAnimationStart(animation: Animation?) {

                        }
                        override fun onAnimationEnd(animation: Animation?) {
                            binding.menuIndicator.visibility = View.INVISIBLE
                        }

                        override fun onAnimationRepeat(animation: Animation?) {

                        }

                    })
                    }
                    if (indicator.equals("winner")) {
                        binding.winnerIndicator.startAnimation(anim)
                        anim.setAnimationListener(object : AnimationListener{
                            override fun onAnimationStart(animation: Animation?) {

                            }
                            override fun onAnimationEnd(animation: Animation?) {
                                binding.winnerIndicator.visibility = View.INVISIBLE
                            }

                            override fun onAnimationRepeat(animation: Animation?) {

                            }

                        })
                        }
                        if (indicator.equals("profile")) {
                            binding.profileIndicator.startAnimation(anim)
                            anim.setAnimationListener(object : AnimationListener{
                                override fun onAnimationStart(animation: Animation?) {

                                }
                                override fun onAnimationEnd(animation: Animation?) {
                                    binding.profileIndicator.visibility = View.INVISIBLE
                                }

                                override fun onAnimationRepeat(animation: Animation?) {

                                }

                            })
                            }
                            indicator = value
    }

    fun setupBottomNavigationFrom(id: Int) {
        mBottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        disableShiftMode(mBottomNavigationView!!)
        mBottomNavigationView!!.menu.findItem(id).setChecked(true)
        mBottomNavigationView!!.menu.performIdentifierAction(id, 0)
    }

    fun loadHomeFragment() {
        val fragment = AuctionFragment.newInstance()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_frame, fragment)
        ft.commit()
    }

    fun loadBuyBidFragment() {
        val fragment = BuyBidFragment.newInstance()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_frame, fragment)
        ft.commit()
    }

    private fun loadWinnerFragment() {
        val fragment = WinnerFragment.newInstance()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_frame, fragment)
        ft.commit()
    }

    private fun loadProfileFragment() {
        val fragment = MyProfileFragment.newInstance()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_frame, fragment)
        ft.commit()
    }

    private fun loadMenuFragment() {
        val fragment = MenuFragment.newInstance()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_frame, fragment)
        ft.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.dashboard, menu)
        val action_share = menu.findItem(R.id.action_share)
        action_share.setOnMenuItemClickListener {
            val intent = Intent(this@MainActivity, ShareEarnActivity::class.java)
            startActivity(intent)
            false
        }
        return super.onCreateOptionsMenu(menu)
    }

    //    @Override
    //    public boolean onOptionsItemSelected(MenuItem item) {
    //
    //        int id = item.getItemId();
    //        if (id == R.id.action_share) {
    //            MenuFragment fragment = MenuFragment.newInstance();
    //            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    //            ft.replace(R.id.fragment_frame, fragment);
    //            ft.commit();
    //
    //        }
    //
    //        return true;
    //    }
    var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit From BidWinko", Toast.LENGTH_SHORT)
            .show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
