package com.bidwinko.screens.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.bidwinko.BuildConfig;
import com.bidwinko.MainActivity;
import com.bidwinko.R;
import com.bidwinko.screens.activity.TransactionActivity;


public class MenuFragment extends Fragment implements View.OnClickListener {

	View view = null;
	CardView cardView_auction,cardView_buybid,cardView_winner,cardView_myprofile,cardView_likefb,cardView_twitter,cardView_telegram,cardView_mytransaction;
    TextView txtvesion,termcondition,privacy,aboutus,contactus,returnRefundCancell,workflow;
	int versionCode;
	String versionName;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 view = getActivity().getLayoutInflater().inflate(R.layout.menu_fragment,null);

		((AppCompatActivity)getActivity()).getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#ffffff\">" +getString(R.string.app_name)+" / "+ getString(R.string.menu) + "</font>")));

		versionCode = BuildConfig.VERSION_CODE;
		versionName = BuildConfig.VERSION_NAME;
		 init(view);

		return view;
	}

	public static MenuFragment newInstance() {

		return new MenuFragment();
	}

	private void init(View view) {
		cardView_auction = view.findViewById(R.id.card_auction);
		cardView_buybid = view.findViewById(R.id.card_buybid);
		cardView_winner = view.findViewById(R.id.card_winner);
		cardView_myprofile = view.findViewById(R.id.card_myprofile);
		cardView_mytransaction = view.findViewById(R.id.card_transaction);
		cardView_likefb = view.findViewById(R.id.card_likefb);
		cardView_twitter = view.findViewById(R.id.card_twitter);
		cardView_telegram = view.findViewById(R.id.card_telegram);
		termcondition = view.findViewById(R.id.termcondition);
		privacy = view.findViewById(R.id.privacy);
		aboutus = view.findViewById(R.id.aboutus);
		contactus = view.findViewById(R.id.contactus);
		returnRefundCancell = view.findViewById(R.id.returnrefund);
		workflow = view.findViewById(R.id.workflow);
		txtvesion = view.findViewById(R.id.txtvesion);

		txtvesion.setText("Version: "+versionName);
		cardView_auction.setOnClickListener(this);
		cardView_buybid.setOnClickListener(this);
		cardView_winner.setOnClickListener(this);
		cardView_myprofile.setOnClickListener(this);
		cardView_likefb.setOnClickListener(this);
		cardView_twitter.setOnClickListener(this);
		cardView_telegram.setOnClickListener(this);
		cardView_mytransaction.setOnClickListener(this);
		termcondition.setOnClickListener(this);
		privacy.setOnClickListener(this);
		aboutus.setOnClickListener(this);
		contactus.setOnClickListener(this);
		returnRefundCancell.setOnClickListener(this);
		workflow.setOnClickListener(this);

	}


	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()){

			case R.id.card_auction:
				((MainActivity)getActivity()).setupBottomNavigationFrom(R.id.action_home);
				break;

			case R.id.card_buybid:
				((MainActivity)getActivity()).setupBottomNavigationFrom(R.id.action_buy_bid);

				break;
			case R.id.card_winner:
				((MainActivity)getActivity()).setupBottomNavigationFrom(R.id.action_winner);

				break;

			case R.id.card_myprofile:
				((MainActivity)getActivity()).setupBottomNavigationFrom(R.id.action_myprofile);

				break;


			case R.id.card_transaction:
				Intent intentTransaction = new Intent(getActivity(), TransactionActivity.class);
				startActivity(intentTransaction);
				break;

			case R.id.card_likefb:
				Intent browserIntentFB = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.digi.spinpay"));
				startActivity(browserIntentFB);
				break;

			case R.id.card_twitter:
				Intent browserIntentTW = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.digi.spinpay"));
				startActivity(browserIntentTW);
				break;

			case R.id.card_telegram:
				Intent browserIntentTGM = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.digi.spinpay"));
				startActivity(browserIntentTGM);
				break;

			case R.id.termcondition:
				String urlcondition = "https://bidwinzo.app/terms-conditions.html";
				webViewLoad(urlcondition,"Terms Of Services");
				break;

			case R.id.privacy:
				String url = "https://bidwinzo.app/privacy-policy.html";
				webViewLoad(url,"Privacy Policy");
				break;

			case R.id.aboutus:
				String urlaboutus = "https://bidwinzo.app/about-us.html";
				webViewLoad(urlaboutus,"About Us");
				break;

			case R.id.contactus:
				String urlcontactus = "https://bidwinzo.app/contact-us.html";
				webViewLoad(urlcontactus,"Contact Us");
				break;

			case R.id.returnrefund:
				String urlreturnrefund = "https://bidwinzo.app/terms-conditions.html";
				webViewLoad(urlreturnrefund,"Refund & Cancellation");
				break;

			case R.id.workflow:
				String urlworkflow = "https://bidwinzo.app/works.html";
				webViewLoad(urlworkflow,"How It Work");
				break;
		}

	}


	private void webViewLoad(String url,String title){
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
		alert.setTitle(title);

		WebView wv = new WebView(getActivity());
		wv.loadUrl(url);
		wv.getSettings().setUserAgentString("Android");
		wv.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		alert.setView(wv);
		alert.setNegativeButton("Accept", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		alert.show();
	}
}