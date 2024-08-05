package com.bidwinko

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bidwinko.BottomNavigationViewHelper.disableShiftMode
import com.bidwinko.screens.activity.ShareEarnActivity
import com.bidwinko.screens.fragments.AuctionFragment
import com.bidwinko.screens.fragments.BuyBidFragment
import com.bidwinko.screens.fragments.MenuFragment
import com.bidwinko.screens.fragments.MyProfileFragment
import com.bidwinko.screens.fragments.WinnerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var mBottomNavigationView: BottomNavigationView? = null
    var fab: FloatingActionButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab = findViewById<View>(R.id.fab) as FloatingActionButton
        setupBottomNavigation()
        if (savedInstanceState == null) {
            loadHomeFragment()
        }
        fab!!.setOnClickListener { //                Toast.makeText(MainActivity.this, "click me", Toast.LENGTH_SHORT).show();
            val intentshare = Intent(this@MainActivity, ShareEarnActivity::class.java)
            startActivity(intentshare)
        }
    }

    fun setupBottomNavigation() {
        mBottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        disableShiftMode(mBottomNavigationView!!)
        mBottomNavigationView!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    loadHomeFragment()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.action_buy_bid -> {
                    loadBuyBidFragment()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.action_winner -> {
                    loadWinnerFragment()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.action_myprofile -> {
                    loadProfileFragment()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.action_menu -> {
                    loadMenuFragment()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
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
