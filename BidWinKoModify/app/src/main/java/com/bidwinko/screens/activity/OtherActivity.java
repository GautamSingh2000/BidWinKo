package com.bidwinko.screens.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bidwinko.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class OtherActivity extends Activity {

    Context context;
    public TabLayout tabLayout;
    FloatingActionButton fab;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);

        LocalBroadcastManager.getInstance(this).registerReceiver(mHandler,new IntentFilter("com.bidwinko_FCM"));

        fab = (FloatingActionButton) findViewById(R.id.fab);

        context = this;

        Log.e("testing", "onReceive:100 " );
        if (getIntent().getExtras() != null) {
            Log.e("testing", "onCreate:101 " );
//            String mykey = "";
            for (String key : getIntent().getExtras().keySet()) {
                if (key.equals("title")) {
//                    mykey = key;
                    String title = getIntent().getExtras().getString(key);
//                            String message = getIntent().getExtras().getString(key);
                    Log.e("testing", "onReceive:10 " + title);
//                            Log.e("testing", "onReceive:11 "+ message);
                    Intent intentProductdDtails = new Intent(OtherActivity.this, ProductDetailsActivity.class);
                    intentProductdDtails.putExtra("bidofferId", 2);
                    startActivity(intentProductdDtails);
                }
                else if(key.equals("message")){
                    String message = getIntent().getExtras().getString(key);
                    Log.e("testing", "onReceive:11 " + message);
                }
            }
//            Log.e("testing", "onCreate: "+mykey );
        }



        tabLayout = (TabLayout)findViewById(R.id.tablayout);

//        replaceFragment(new AuctionFragment());
//        setupTabIcons();
        bindWidgetsWithAnEvent();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentshare = new Intent(OtherActivity.this, ShareEarnActivity.class);
                startActivity(intentshare);
            }
        });



    }

//    public void setupTabIcons() {
//
//        TextView home = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        home.setText(R.string.home);
//        home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.auction_tab_layout, 0, 0);
//        tabLayout.addTab(tabLayout.newTab().setCustomView(home));
//
//
//        TextView profile = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        profile.setText(R.string.buybid);
//        profile.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.bid_tab_layout, 0, 0);
//        tabLayout.addTab(tabLayout.newTab().setCustomView(profile));
//
//
//        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        tabThree.setText(R.string.winner);
//        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.winner_tab_layout, 0, 0);
//        tabLayout.addTab(tabLayout.newTab().setCustomView(tabThree));
//
//        TextView myprofile = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        myprofile.setText(R.string.myprofile);
//        myprofile.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.myprofile_tab_layout, 0, 0);
//        tabLayout.addTab(tabLayout.newTab().setCustomView(myprofile));
//
//        TextView txtmenu = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        txtmenu.setText(R.string.menu);
//        txtmenu.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.menu_tab_layout, 0, 0);
//        tabLayout.addTab(tabLayout.newTab().setCustomView(txtmenu));
//
//
//    }

    private void bindWidgetsWithAnEvent(){
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setCurrentTabFragment(int tabPosition){
//        switch (tabPosition){
//            case 0 :
//                replaceFragment(new AuctionFragment());
//                break;
//            case 1 :
//                    replaceFragment(new BuyBidFragment());
//                break;
//
//            case 2 :
//                    replaceFragment(new WinnerFragment());
//                break;
//
//            case 3 :
//                replaceFragment(new MyProfileFragment());
//
//                break;
//
//            case 4 :
//                replaceFragment(new MenuFragment());
//                break;
//
//
//        }

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.action_winner:
//                Intent intent = new Intent(OtherActivity.this,WinnerActivity.class);
//                startActivity(intent);
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


    private BroadcastReceiver mHandler = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//            playNotificationSound(context);
            String title = intent.getStringExtra("title");
            String message = intent.getStringExtra("message");
            Log.e("testing", "onReceive:12 "+ title);
            Log.e("testing", "onReceive:13 "+ message);

            DialogBox("2");

        }
    };


    @Override
    protected void onResume() {
        super.onResume();
//        LocalBroadcastManager.getInstance(this).registerReceiver(mHandler,new IntentFilter("com.bidwinko_FCM"));
    }

    private void DialogBox(final String title){
        AlertDialog.Builder alert = new AlertDialog.Builder(OtherActivity.this);
        alert.setTitle("Product Details");
        alert.setCancelable(false);
        alert.setMessage("Do you want to check details page?");
        alert.setPositiveButton("Go To Product Details", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intentProductdDtails = new Intent(OtherActivity.this, ProductDetailsActivity.class);
                intentProductdDtails.putExtra("bidofferId",title);
                startActivityForResult(intentProductdDtails,101);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    public void playNotificationSound(Context context) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101){
            Log.e("testing", "onActivityResult:11 " );
            LocalBroadcastManager.getInstance(this).registerReceiver(mHandler,new IntentFilter("com.bidwinko_FCM"));
        }

    }
}
