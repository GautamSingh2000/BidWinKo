package com.bidwinko.screens.fragments;

import android.app.Activity;
import android.app.ProgressDialog;

import android.os.Bundle;
import android.text.Html;
import android.text.InputType;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

import com.bidwinko.R;

import com.bidwinko.API.APIService;
import com.bidwinko.model.UserDetailsModel;
import com.bidwinko.utilies.Constants;
import com.bidwinko.API.Retrofit;
import com.bidwinko.utilies.PicassoCircleTransformation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MyProfileFragment extends Fragment implements View.OnClickListener {

	View view = null;
	EditText etUserName,etUserEmail,etMobile,etShipAddress;
	ProgressDialog progressDialog;
	ImageView profileimage;
	TextView submit_userinfo;
	FloatingActionButton fab_edit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 view = getActivity().getLayoutInflater().inflate(R.layout.myprofile_fragment,null);

		((AppCompatActivity)getActivity()).getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#ffffff\">" +getString(R.string.app_name)+" / "+ getString(R.string.myprofile) + "</font>")));
		 init(view);


//		 getUserDetails();
		return view;
	}

	public static MyProfileFragment newInstance() {

		return new MyProfileFragment();
	}

	private void init(View view) {
		profileimage = view.findViewById(R.id.profileimage);
		fab_edit = view.findViewById(R.id.fab_edit);
		etUserName = view.findViewById(R.id.et_username);
		etUserEmail = view.findViewById(R.id.et_useremail);
		etMobile = view.findViewById(R.id.et_usermobile);
		etShipAddress = view.findViewById(R.id.et_shipaddress);
		submit_userinfo = view.findViewById(R.id.submit_userinfo);
		profileimage.setOnClickListener(this);
		fab_edit.setOnClickListener(this);
		submit_userinfo.setOnClickListener(this);

	}

//	private void getUserDetails() {
//
//		APIService apiService = Retrofit.getClient().create(APIService.class);
//
//		Call<UserDetailsModel> call = apiService.getUserDetails(Constants.getSharedPreferenceInt(getActivity(),"userId",0),
//				Constants.getSharedPreferenceString(getActivity(),"securitytoken",""),
//				Constants.getSharedPreferenceString(getActivity(),"versionName",""),
//				Constants.getSharedPreferenceInt(getActivity(),"versionCode",0),
//				Constants.getSharedPreferenceString(getActivity(),"userFrom",""));
//
//		if(!((Activity) getActivity()).isFinishing()) {
//			progressDialog = new ProgressDialog(getActivity());
//			progressDialog.setMessage(getString(R.string.loadingwait));
//			progressDialog.show();
//			progressDialog.setCancelable(false);
//		}
//
//		call.enqueue(new Callback<UserDetailsModel>() {
//			@Override
//			public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
//
//				dismissProgressDialog();
//                 UserDetailsModel data=response.body();
//				if(data!=null){
//					if(response.isSuccessful()){
//						if(data.getStatus()==200){
//							Picasso.get().load(data.getSocialImgurl())
//									.placeholder(R.drawable.ic_user_pic)
//									.error(R.drawable.ic_user_pic).centerCrop()
//									.resize(100,100)
//									.transform(new PicassoCircleTransformation())
//									.into((ImageView)view.findViewById(R.id.profileimage));
//
////							Constants.setSharedPreferenceString(getActivity(),"displayName",response.body().getSocialName());
////							Constants.setSharedPreferenceString(getActivity(),"email",response.body().getSocialEmail());
////							Constants.setSharedPreferenceString(getActivity(),"mobile",response.body().getMobileNo());
//							etUserName.setText(data.getSocialName());
//							etMobile.setText(""+data.getMobileNo());
//							etUserEmail.setText(data.getSocialEmail());
//							etShipAddress.setText(data.getShippingAddress());
//
//						}else{
//							Toast.makeText(getActivity(),getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
//						}
//					}
//				}
//				else{
//					Toast.makeText(getActivity(),getString(R.string.systemmessage)+"Response Not Found.",Toast.LENGTH_SHORT).show();
//				}
//			}
//
//			@Override
//			public void onFailure(Call<UserDetailsModel> call, Throwable t) {
//				Toast.makeText(getActivity(),getString(R.string.systemmessage)+t,Toast.LENGTH_SHORT).show();
//			}
//		});
//
//	}


	private void dismissProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	@Override
	public void onDestroy() {
		dismissProgressDialog();
		super.onDestroy();
	}

	@Override
	public void onPause() {
		dismissProgressDialog();
		super.onPause();
	}


	@Override
	public void onClick(View view) {

		switch (view.getId()){
			case R.id.profileimage:
				break;

			case R.id.fab_edit:
				enableTextView();
				break;

			case R.id.submit_userinfo:
				updateUserIfo();
				break;
		}

	}

	private void enableTextView() {
		etUserName.setEnabled(true);
		etUserName.requestFocus();
		etUserName.setInputType(InputType.TYPE_CLASS_TEXT);
		etUserEmail.setEnabled(true);
		etMobile.setEnabled(true);
		etShipAddress.setEnabled(true);
		submit_userinfo.setEnabled(true);

	}

	public void updateUserIfo(){

		String username = etUserName.getText().toString();
		String useremail = etUserEmail.getText().toString();
		String usermobile = etMobile.getText().toString();
		String shipingaddress = etShipAddress.getText().toString();

		if(username.equals("")){
			etUserName.setError("User Name is Required");
		}
		else if(useremail.equals("")){
			etUserEmail.setError("User Email is Required");
		}
		else if(usermobile.equals("")){
			etMobile.setError("User Mobile is Required");
		}
		else if(shipingaddress.equals("")){
			etShipAddress.setError("Shiping Address is Required");
		}
		else{

//			submitResponse(username,useremail,usermobile,shipingaddress,"update");
		}
	}

//	private void submitResponse(String username, String useremail, String usermobile,String shpingaddress,String actiontype) {
//
//		APIService apiService = Retrofit.getClient().create(APIService.class);
//
//		Call<UserDetailsModel> call = apiService.updateuserDetails(Constants.getSharedPreferenceInt(getActivity(),"userId",0),
//				Constants.getSharedPreferenceString(getActivity(),"securitytoken",""),
//				Constants.getSharedPreferenceString(getActivity(),"versionName",""),
//				Constants.getSharedPreferenceInt(getActivity(),"versionCode",0),
//				username,useremail,usermobile,shpingaddress,actiontype,
//				Constants.getSharedPreferenceString(getActivity(),"userFrom",""));
//
//		if(!((Activity) getActivity()).isFinishing()) {
//			progressDialog = new ProgressDialog(getActivity());
//			progressDialog.setMessage(getString(R.string.loadingwait));
//			progressDialog.show();
//			progressDialog.setCancelable(false);
//		}
//
//		call.enqueue(new Callback<UserDetailsModel>() {
//			@Override
//			public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
//				dismissProgressDialog();
//
//				if(response!=null){
//					if(response.isSuccessful()){
//
//						if(response.body().getStatus()==200){
//
//							Picasso.get().load(response.body().getSocialImgurl())
//									.placeholder(R.drawable.ic_user_pic)
//									.error(R.drawable.ic_user_pic).centerCrop()
//									.resize(100,100)
//									.transform(new PicassoCircleTransformation())
//									.into((ImageView)view.findViewById(R.id.profileimage));
//
////							Constants.setSharedPreferenceString(getActivity(),"displayName",response.body().getSocialName());
////							Constants.setSharedPreferenceString(getActivity(),"email",response.body().getSocialEmail());
////							Constants.setSharedPreferenceString(getActivity(),"mobile",response.body().getMobileNo());
//							etUserName.setText(response.body().getSocialName());
//							etMobile.setText(""+response.body().getMobileNo());
//							etUserEmail.setText(response.body().getSocialEmail());
//							etShipAddress.setText(response.body().getShippingAddress());
//
//							Toast.makeText(getActivity(),"Update "+response.body().getMessage()+"fully.",Toast.LENGTH_SHORT).show();
//
//							disableallFields();
//
//						}else{
//							Toast.makeText(getActivity(),getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
//						}
//
//					}
//				}
//				else{
//					Toast.makeText(getActivity(),getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
//				}
//			}
//
//			@Override
//			public void onFailure(Call<UserDetailsModel> call, Throwable t) {
//				Toast.makeText(getActivity(),getString(R.string.systemmessage)+t,Toast.LENGTH_SHORT).show();
//			}
//		});
//
//	}

	private void disableallFields() {

		etUserName.setEnabled(false);
		etUserEmail.setEnabled(false);
		etMobile.setEnabled(false);
		etShipAddress.setEnabled(false);
		submit_userinfo.setEnabled(false);

	}
}