package com.bidwinko.screens.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bidwinko.R;

import java.util.ArrayList;
import java.util.List;


public class ShareEarnActivity extends AppCompatActivity {


    ImageView imageShare;
    TextView txShare,sharedec;
    ProgressDialog progressDialog;
    String invitefburl,inviteTextUrl,inviteText,inviteAmount,tranTxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_earn);

        try{

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
            upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            ((AppCompatActivity)this).getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#ffffff\">" +getString(R.string.app_name)+" / "+getString(R.string.inviteearn) + "</font>")));

            imageShare = findViewById(R.id.image_share);
            txShare = findViewById(R.id.txShare);
            sharedec = findViewById(R.id.share_decription);


//            inviteaData();
            txShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareApp();
                }
            });

        } catch (Exception e){
           e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        dismissProgressDialog();
        super.onStop();
    }

    @Override
    public void onPause() {
        dismissProgressDialog();
        super.onPause();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

//    private void inviteaData() {
//        APIService service = Retrofit.getClient().create(APIService.class);
//        Call<InviteFriendModel> call = service.inviteDataFriend(Constants.getSharedPreferenceInt(ShareEarnActivity.this,"userId",0),
//                Constants.getSharedPreferenceString(ShareEarnActivity.this,"securitytoken",""),
//                Constants.getSharedPreferenceString(ShareEarnActivity.this,"versionName",""),
//                Constants.getSharedPreferenceInt(ShareEarnActivity.this,"versionCode",0),
//                Constants.getSharedPreferenceString(ShareEarnActivity.this,"userFrom",""));
//
//        //Call<InviteFriendModel>call=service.inviteDataFriend(1,"72d9c1b9-fb05-49ba-80f8-759fc5c5625b","1.0",1,"Bidwinzo");
//
//        if(!((Activity) ShareEarnActivity.this).isFinishing()){
//            progressDialog = new ProgressDialog(ShareEarnActivity.this);
//            progressDialog.setMessage(getString(R.string.loadingwait));
//            progressDialog.show();
//          //  progressDialog.setCancelable(false);
//        }
//
//        Log.d("TAG", "Id: "+Constants.getSharedPreferenceInt(ShareEarnActivity.this,"userId",0)+" sT: "+ Constants.getSharedPreferenceString(ShareEarnActivity.this,"securitytoken",""));
//
//        call.enqueue(new Callback<InviteFriendModel>() {
//            @Override
//            public void onResponse(Call<InviteFriendModel> call, Response<InviteFriendModel> response) {
//                dismissProgressDialog();
//                Log.d("TAG", "onInvite: "+response.message());
//              //  Toast.makeText(ShareEarnActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
//                try {
//                    Log.e("share", "onResponse:respo "+response);
//                    InviteFriendModel data=response.body();
//                    if(data!=null){
//                        if (response.isSuccessful()) {
//                            if (data.getStatus()==200) {
//                                invitefburl = data.getInviteFbUrl();
//                                inviteAmount = data.getInviteAmount();
//                                inviteText = data.getInviteTextNew();
//                                inviteTextUrl = data.getInviteTextUrl();
//                                sharedec.setText(inviteText);
////                                Glide.with(ShareEarnActivity.this).load(response.body().getInviteImgurl()).into(imageShare);
//
//                                Picasso.get().load(response.body().getInviteImgurl())
//                                        .placeholder(R.drawable.placeholder)
//                                        .error(R.drawable.placeholder)
//                                        .into((ImageView)findViewById(R.id.image_share));
//
//                            }
//                            else {
//                                Toast.makeText(ShareEarnActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                    else  Toast.makeText(ShareEarnActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                    Toast.makeText(ShareEarnActivity.this, getString(R.string.systemmessage) +e , Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<InviteFriendModel> call, Throwable t) {
//                Toast.makeText(ShareEarnActivity.this, getString(R.string.systemmessage) +t , Toast.LENGTH_SHORT).show();
//            }
//
//        } );
//    }

    public void shareApp(){
        try {

            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(shareIntent, 0);
            if (!resInfo.isEmpty()) {
                for (ResolveInfo resolveInfo : resInfo) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    Intent targetedShareIntent = new Intent(Intent.ACTION_SEND);
                    targetedShareIntent.setType("text/plain");
                    targetedShareIntent.putExtra(Intent.EXTRA_SUBJECT, "subject to be shared");
                    if (TextUtils.equals(packageName, "com.facebook.katana")) {
                        targetedShareIntent.putExtra(Intent.EXTRA_TEXT, invitefburl);
                    } else {
                        targetedShareIntent.putExtra(Intent.EXTRA_TEXT, inviteTextUrl);
                    }
                    targetedShareIntent.setPackage(packageName);
                    targetedShareIntents.add(targetedShareIntent);
                }
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[targetedShareIntents.size()]));
                startActivity(chooserIntent);
            }




        } catch(Exception e) {
            e.toString();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}