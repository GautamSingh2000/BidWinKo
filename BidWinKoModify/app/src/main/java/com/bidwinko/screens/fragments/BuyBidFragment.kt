package com.bidwinko.screens.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R
import com.bidwinko.adapter.BuyBidPlanAdapter
import com.bidwinko.databinding.BuybidFragmentBinding
import com.bidwinko.model.BuyBidValue
import com.bidwinko.model.RequestModels.CommonRequest
import com.bidwinko.model.ResponseModels.BidPlan
import com.bidwinko.screens.activity.ShareEarnActivity
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel


var progressDialog: ProgressDialog? = null
class BuyBidFragment : Fragment() {

    lateinit var binding: BuybidFragmentBinding

    var recyclerView: RecyclerView? = null
    var buybidArraylists = ArrayList<BuyBidValue>()
    private var mAdapter: BuyBidPlanAdapter? = null
    var txtBidBalance: TextView? = null
    var unlimitedBid: CardView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BuybidFragmentBinding.inflate(layoutInflater)
        requireActivity().findViewById<TextView>(R.id.title).text = getString(R.string.buybid)
        recyclerView = binding.bidRecyclerView
        txtBidBalance = binding.bidbalance
        unlimitedBid = binding.cardUnlimitedBid

        getBuyBids();
        unlimitedBid!!.setOnClickListener(View.OnClickListener { //				Toast.makeText(getActivity(),"Feature Coming Soon..",Toast.LENGTH_SHORT).show();
            val intent = Intent(activity, ShareEarnActivity::class.java)
            startActivity(intent)
        })
        return binding.root
    }

    private fun getBuyBids() {

        if (!(activity?.isFinishing)!!) {
            progressDialog = ProgressDialog(requireContext());
            progressDialog!!.setMessage(getString(R.string.loadingwait));
            progressDialog!!.show();
            progressDialog!!.setCancelable(false);
        }

        val request = CommonRequest(
            userId = SessionManager(requireContext()).GetValue(Constants.USER_ID).toString(),
            securityToken = SessionManager(requireContext()).GetValue(Constants.SECURITY_TOKEN),
            versionName = SessionManager(requireContext()).GetValue(Constants.VERSION_NAME),
            versionCode = SessionManager(requireContext()).GetValue(Constants.VERSION_CODE)
        )
        mainViewModel(requireContext()).GetBidsPacakage(request).observe(requireActivity()) {
            if (it.status == 200) {
                progressDialog?.dismiss()
                mAdapter = BuyBidPlanAdapter(it.bidPlans as ArrayList<BidPlan>, requireContext())
                binding.bidRecyclerView.setAdapter(mAdapter)
            }
        }
    }
    //
    //		APIService apiService = Retrofit.getClient().create(APIService.class);
    //		Call<BuyBid> call = apiService.getbuyBids(Constants.getSharedPreferenceInt(getActivity(),"userId",0),
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
    //		call.enqueue(new Callback<BuyBid>() {
    //			@Override
    //			public void onResponse(Call<BuyBid>call, Response<BuyBid> response) {
    //				dismissProgressDialog();
    //
    //				if(response!=null){
    //					if(response.isSuccessful()){
    //						if(response.body().getStatus()==200){
    ////							txtBidBalance.setText(""+response.body().getBidBal());
    //							buybidArraylists = response.body().getBuyBidValues();
    //							String bidremain = response.body().getBidBalance();
    //
    ////							Log.e("bidremain", "onResponse:pd_BB "+bidremain );
    //							Constants.setSharedPreferenceString(getActivity(),"bidBalance",bidremain);
    //							txtBidBalance.setText(""+Constants.getSharedPreferenceString(getActivity(),"bidBalance",""));
    //							mAdapter = new BuyBidListAdapter(response,buybidArraylists,getActivity());
    //							RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    //							recyclerView.setLayoutManager(mLayoutManager);
    //							recyclerView.setItemAnimator(new DefaultItemAnimator());
    //							recyclerView.setAdapter(mAdapter);
    //						}else{
    //							Toast.makeText(getActivity(),getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
    //						}
    //
    //					}
    //				}
    //				else{
    //					Toast.makeText(getActivity(),getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
    //				}
    //
    //
    //			}
    //
    //			@Override
    //			public void onFailure(Call<BuyBid>call, Throwable t) {
    //				// Log error here since request failed
    //				Log.e("response", t.toString());
    //			}
    //		});
    //
    //

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 103) {
            requireActivity().finish()
            startActivity(requireActivity().intent)
        }
    }

    companion object {
        fun newInstance(): BuyBidFragment {
            return BuyBidFragment()
        }
    }
}
