package com.bidwinko.screens.fragments

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bidwinko.R
import com.bidwinko.databinding.MyprofileFragmentBinding
import com.bidwinko.model.RequestModels.GetProfileRequest
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker

private lateinit var const: FragmentActivity

class MyProfileFragment : Fragment(), View.OnClickListener {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            const = context
        }
    }

    lateinit var binding: MyprofileFragmentBinding
    var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = MyprofileFragmentBinding.inflate(layoutInflater)
        const.findViewById<TextView>(R.id.title).text = getString(R.string.myprofile)

        binding.profileRefresh.setOnRefreshListener {
            binding.submitUserinfo.isCheckable = false
            binding.etUsername.isEnabled = false
            binding.etUsermobile.isEnabled = false
            binding.etShipaddress.isEnabled = false
            binding.etUseremail.isEnabled = false
            getUserDetails()
            binding.submitUserinfo.setBackgroundColor(
                ContextCompat.getColor(
                    const,
                    R.color.gray
                )
            )
            binding.submitUserinfo.setTextColor(ContextCompat.getColor(const, R.color.gray_4))
        }

        binding.submitUserinfo.isCheckable = false
        binding.etUsername.isEnabled = false
        binding.etUsermobile.isEnabled = false
        binding.etShipaddress.isEnabled = false
        binding.etUseremail.isEnabled = false

        binding.fabEdit.setOnClickListener {
            binding.etUsername.isEnabled = true
            binding.etUsermobile.isEnabled = true
            binding.etShipaddress.isEnabled = true
            binding.submitUserinfo.isCheckable = true
            binding.submitUserinfo.setBackgroundColor(
                ContextCompat.getColor(
                    const,
                    R.color.colorAccent
                )
            )
            binding.submitUserinfo.setTextColor(ContextCompat.getColor(const, R.color.white))
        }
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
            if (binding.submitUserinfo.isCheckable) {
                UpdateUserDetails()
            }
        }
        return binding.root
    }


    private fun getUserDetails() {
        if (!(activity?.isFinishing)!!) {
            progressDialog = ProgressDialog(const)
            progressDialog!!.setMessage(getString(R.string.loadingwait))
            progressDialog!!.show()
            progressDialog!!.setCancelable(false)
        }

        val request = GetProfileRequest(
            userId = SessionManager(const).GetValue(Constants.USER_ID).toString(),
            securityToken = SessionManager(const).GetValue(Constants.SECURITY_TOKEN),
            versionName = SessionManager(const).GetValue(Constants.VERSION_NAME),
            versionCode = SessionManager(const).GetValue(Constants.VERSION_CODE),
            method = "GET"
        )

        mainViewModel(const).GetProfileResponse(request).observe(const) {
            binding.profileRefresh.isRefreshing = false
            progressDialog?.dismiss()
            if (it.status == 200) {
                if (it.userImage.equals("") || it.userImage.isNullOrEmpty()) {
                    Toast.makeText(const, "Something Went Wrong !!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    binding.anim.visibility = View.GONE
                    Glide.with(const).load(it.userImage)
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
                        if (address?.trim()?.length!! < 8) {
                            binding.etShipaddress.error = "Enter Valid Address"
                        } else {
                            if (address.isNullOrBlank() || address.length < 8 || address.equals("        ")) {
                                binding.etShipaddress.error = "Enter Valid Address"
                            } else {
                                if (!(activity?.isFinishing)!!) {
                                    progressDialog = ProgressDialog(const)
                                    progressDialog!!.setMessage(getString(R.string.loadingwait))
                                    progressDialog!!.show()
                                    progressDialog!!.setCancelable(false)
                                }

                                val request = GetProfileRequest(
                                    userId = SessionManager(const).GetValue(Constants.USER_ID)
                                        .toString(),
                                    securityToken = SessionManager(const).GetValue(Constants.SECURITY_TOKEN),
                                    versionName = SessionManager(const).GetValue(Constants.VERSION_NAME),
                                    versionCode = SessionManager(const).GetValue(Constants.VERSION_CODE),
                                    method = "POST",
                                    email = binding.etUseremail.text.toString(),
                                    name = binding.etUsername.text.toString(),
                                    phone = binding.etUsermobile.text.toString(),
                                    address = binding.etShipaddress.text.toString(),
                                )

                                mainViewModel(const).GetProfileResponse(request)
                                    .observe(const) {
                                        progressDialog?.dismiss()
                                        if (it.status == 200) {
                                            if (it.userImage.equals("") || it.userImage.isNullOrEmpty()) {

                                                Toast.makeText(
                                                    const,
                                                    "Something Went Wrong !!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {

                                                Toast.makeText(
                                                    const,
                                                    "Update Successfully !!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                binding.etUsername.isEnabled = false
                                                binding.etUsermobile.isEnabled = false
                                                binding.etShipaddress.isEnabled = false
                                                binding.submitUserinfo.isCheckable = false
                                                binding.submitUserinfo.setBackgroundColor(
                                                    ContextCompat.getColor(const, R.color.gray)
                                                )
                                                binding.submitUserinfo.setTextColor(
                                                    ContextCompat.getColor(
                                                        const,
                                                        R.color.gray_4
                                                    )
                                                )
                                                binding.anim.visibility = View.GONE
                                                Glide.with(const).load(it.userImage)
                                                    .placeholder(R.drawable.ic_user_pic)
                                                    .into(binding.profileimage)
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

