package com.bidwinko;

import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bidwinko.screens.activity.ShareEarnActivity;
import com.bidwinko.screens.fragments.AuctionFragment;
import com.bidwinko.screens.fragments.BuyBidFragment;
import com.bidwinko.screens.fragments.MenuFragment;
import com.bidwinko.screens.fragments.MyProfileFragment;
import com.bidwinko.screens.fragments.WinnerFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        setupBottomNavigation();

        if (savedInstanceState == null) {
            loadHomeFragment();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "click me", Toast.LENGTH_SHORT).show();
                Intent intentshare = new Intent(MainActivity.this, ShareEarnActivity.class);
                startActivity(intentshare);
            }
        });

    }



    public void setupBottomNavigation() {

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_home: {
                        loadHomeFragment();
                        return true;
                    }

                    case R.id.action_buy_bid: {
                        loadBuyBidFragment();
                        return true;
                    }

                    case R.id.action_winner: {
                        loadWinnerFragment();
                        return true;
                    }

                    case R.id.action_myprofile: {
                        loadProfileFragment();
                        return true;

                    }
                    case R.id.action_menu: {
                        loadMenuFragment();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void setupBottomNavigationFrom(final int id) {
        mBottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.getMenu().findItem(id).setChecked(true);
        mBottomNavigationView.getMenu().performIdentifierAction(id, 0);

    }



    public void loadHomeFragment() {
        AuctionFragment fragment = AuctionFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    public void loadBuyBidFragment() {
        BuyBidFragment fragment = BuyBidFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadWinnerFragment() {
        WinnerFragment fragment = WinnerFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadProfileFragment() {
        MyProfileFragment fragment = MyProfileFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadMenuFragment() {
        MenuFragment fragment = MenuFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard, menu);
        MenuItem action_share = menu.findItem(R.id.action_share);

        action_share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this,ShareEarnActivity.class);
                startActivity(intent);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit From BidWinko", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
