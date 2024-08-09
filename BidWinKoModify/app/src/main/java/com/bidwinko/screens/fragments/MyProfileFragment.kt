package com.bidwinko.screens.fragments

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bidwinko.R
import com.bidwinko.databinding.MyprofileFragmentBinding
import com.bidwinko.model.RequestModels.GetProfileRequest
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker

class MyProfileFragment : Fragment(), View.OnClickListener {

    lateinit var binding: MyprofileFragmentBinding
    var progressDialog: ProgressDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = MyprofileFragmentBinding.inflate(layoutInflater)
        requireActivity().findViewById<TextView>(R.id.title).text = getString(R.string.myprofile)

        binding.anim.let {
            it.playAnimation()
            it.addAnimatorListener(object : AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    it.playAnimation()
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }

            })
        }

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == Activity.RESULT_OK) {
                // Image Uri will not be null for RESULT_OK
                val uri = data!!.data
                // Use Uri object instead of File to avoid storage permissions
                binding.anim.visibility = View.GONE
                binding.profileimage.setImageURI(uri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
//                Toast.makeText(this,ImagePicker.getError(data),Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

        getUserDetails()

        binding.submitUserinfo.setOnClickListener {
            UpdateUserDetails()
        }
        return binding.root
    }


    private fun getUserDetails() {
        if (!(activity?.isFinishing)!!) {
            progressDialog = ProgressDialog(requireContext())
            progressDialog!!.setMessage(getString(R.string.loadingwait))
            progressDialog!!.show()
            progressDialog!!.setCancelable(false)
        }

        val request = GetProfileRequest(
            userId = SessionManager(requireContext()).GetValue(Constants.USER_ID).toString(),
            securityToken = SessionManager(requireContext()).GetValue(Constants.SECURITY_TOKEN),
            versionName = SessionManager(requireContext()).GetValue(Constants.VERSION_NAME),
            versionCode = SessionManager(requireContext()).GetValue(Constants.VERSION_CODE),
            method = "GET"
        )

        mainViewModel(requireContext()).GetProfileResponse(request).observe(requireActivity()) {
            progressDialog?.dismiss()

            if (it.status == 200) {
                if (it.userImage.equals("") || it.userImage.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "Something Went Wrong !!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    binding.anim.visibility = View.GONE
                    Glide.with(requireContext()).load(it.userImage)
                        .placeholder(R.drawable.ic_user_pic).into(binding.profileimage)
                }
                binding.etUseremail.setText(it.userEmail)
                binding.etUsermobile.setText(it.mobileNumber)
                binding.etShipaddress.setText(it.address)
                binding.etUsername.setText(it.userName)
            }
        }

    }

    private fun UpdateUserDetails() {

        val email = binding.etUseremail.text.toString()
        val name = binding.etUsername.text.toString()
        val address = binding.etShipaddress.text
        val mobile = binding.etUsermobile.text.toString()

        if (name.isBlank()) {
            binding.etUsername.error = "Enter Valid User Name"
        } else {
            if (email.equals(" ") || !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                    .matches()
            ) {
                binding.etUseremail.error = "Enter Valid Email"
            } else {
                if (mobile.isBlank()) {
                    binding.etUsermobile.error = "Enter Valid Mobile Number"
                } else {
                    if (mobile.length < 10 || mobile.length > 10) {
                        binding.etUsermobile.error = "Enter 10 Digit Mobile Number"
                    } else {
                        if (address.isNullOrBlank()) {
                            binding.etShipaddress.error = "Enter Valid Address"
                        } else {
                            if (!(activity?.isFinishing)!!) {
                                progressDialog = ProgressDialog(requireContext())
                                progressDialog!!.setMessage(getString(R.string.loadingwait))
                                progressDialog!!.show()
                                progressDialog!!.setCancelable(false)
                            }

                            val request = GetProfileRequest(
                                userId = SessionManager(requireContext()).GetValue(Constants.USER_ID)
                                    .toString(),
                                securityToken = SessionManager(requireContext()).GetValue(Constants.SECURITY_TOKEN),
                                versionName = SessionManager(requireContext()).GetValue(Constants.VERSION_NAME),
                                versionCode = SessionManager(requireContext()).GetValue(Constants.VERSION_CODE),
                                method = "POST",
                                email = binding.etUseremail.text.toString(),
                                name = binding.etUsername.text.toString(),
                                phone = binding.etUsermobile.text.toString(),
                                address = binding.etShipaddress.text.toString(),
                            )

                            mainViewModel(requireContext()).GetProfileResponse(request)
                                .observe(requireActivity()) {
                                    progressDialog?.dismiss()
                                    if (it.status == 200) {
                                        if (it.userImage.equals("") || it.userImage.isNullOrEmpty()) {

                                            Toast.makeText(
                                                requireContext(),
                                                "Something Went Wrong !!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {

                                            Toast.makeText(
                                                requireContext(),
                                                "Update Successfully !!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            binding.anim.visibility = View.GONE
                                            Glide.with(requireContext()).load(it.userImage)
                                                .placeholder(R.drawable.ic_user_pic)
                                                .into(binding.profileimage)
                                        }
                                        binding.etUseremail.setText(it.userEmail)
                                        binding.etUsermobile.setText(it.mobileNumber)
                                        binding.etShipaddress.setText(it.address)
                                        binding.etUsername.setText(it.userName)
                                    }
                                }
                        }
                    }
                }

            }
        }


    }

    private fun dismissProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    override fun onDestroy() {
        dismissProgressDialog()
        super.onDestroy()
    }

    override fun onPause() {
        dismissProgressDialog()
        super.onPause()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab_edit -> profile()
//            R.id.submit_userinfo -> updateUserIfo()
        }
    }

    fun profile() {
        ImagePicker.with(this)
            .crop(1F, 1F) //Crop image(Optional), Check Customization for more option
            .compress(1024) //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            ) //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    companion object {
        fun newInstance(): MyProfileFragment {
            return MyProfileFragment()
        }
    }

}

//
//    fun updateUserIfo() {
//        val username = etUserName!!.getText().toString()
//        val useremail = etUserEmail!!.getText().toString()
//        val usermobile = etMobile!!.getText().toString()
//        val shipingaddress = etShipAddress!!.getText().toString()
//        if (username == "") {
//            etUserName!!.error = "User Name is Required"
//        } else if (useremail == "") {
//            etUserEmail!!.error = "User Email is Required"
//        } else if (usermobile == "") {
//            etMobile!!.error = "User Mobile is Required"
//        } else if (shipingaddress == "") {
//            etShipAddress!!.error = "Shiping Address is Required"
//        } else {
//
////			submitResponse(username,useremail,usermobile,shipingaddress,"update");
//        }
//    }

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
//    private fun disableallFields() {
//        etUserName!!.setEnabled(false)
//        etUserEmail!!.setEnabled(false)
//        etMobile!!.setEnabled(false)
//        etShipAddress!!.setEnabled(false)
//        submit_userinfo!!.setEnabled(false)
//    }

