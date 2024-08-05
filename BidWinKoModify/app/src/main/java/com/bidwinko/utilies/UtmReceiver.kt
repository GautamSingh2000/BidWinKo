//    package com.bidwinko.utilies
//
//    import android.content.Context
//    import android.content.Intent
//    import com.google.android.gms.analytics.CampaignTrackingReceiver
//
//    class UtmReceiver : CampaignTrackingReceiver() {
//
//        override fun onReceive(context: Context, intent: Intent?) {
//            try {
//                val sessionManager = SessionManager(context)
//                val extra = intent!!.extras
//                if (extra != null) {
//                    val refer = extra.getString("referrer")
//                    if (refer != null) {
//                        val referrer = refer.substring(0, refer.length)
//                        val params = referrer.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//                        val utm_source = params[0].substring(params[0].lastIndexOf("=") + 1)
//                        val utm_medium = params[1].substring(params[1].lastIndexOf("=") + 1)
//                        val utm_term = params[2].substring(params[2].lastIndexOf("=") + 1)
//                        val utm_campaign = params[3].substring(params[3].lastIndexOf("=") + 1)
//                        val utm_content = params[4].substring(params[4].lastIndexOf("=") + 1)
//
//                        sessionManager.InitializeValue(Constants.UTM_SOURCE , utm_source)
//                        sessionManager.InitializeValue(Constants.UTM_Medium, utm_medium)
//                        sessionManager.InitializeValue(Constants.UTM_Term, utm_term)
//                        sessionManager.InitializeValue(Constants.UTM_Content, utm_content)
//                        sessionManager.InitializeValue(Constants.UTM_Campaign, utm_campaign)
//                        sessionManager.InitializeValue(Constants.UTM_ReferalLink, refer )
//                    }
//                }
//                CampaignTrackingReceiver().onReceive(context, intent)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//
//
//        fun saveUtmValues(context: Context, key: String?, `val`: String?) {
//            try {
//                val preference = context.getSharedPreferences("utm_campaign", Context.MODE_PRIVATE)
//                val editor = preference.edit()
//                editor.putString(key, `val`)
//                editor.apply()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }