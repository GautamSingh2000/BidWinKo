//package com.bidwinko.testing;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.bidwinko.R;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//
//public class SomeActivity extends Activity {
//
//
// String reslut = "{\"orderId\":\"GPA.3389-3802-7063-04105\",\"packageName\":\"com.bidwinko\",\"productId\":\"purchase_five_bid\",\"purchaseTime\":1574060380438,\"purchaseState\":0,\"developerPayload\":\"purchasetoken\",\"purchaseToken\":\"midlaglfdekannhamnoogaah.AO-J1Ox7wgN__eixuCieMTKyXf8JkoBrNRibyoxlxxAC8DJFDno7I62prf4aPi0piBJpj9PvhOTSbElkwtZiTgGaZM1tXsfQPLRYi1nHOTVDCQsjLJHlmxs\"}\n" +
//         "\n";
// String TAG = "testing";
//
//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_testing);
//
//
//      try {
//          JSONObject mainObject = new JSONObject(reslut);
//
//          String orderId = mainObject.getString("orderId");
//          String productId = mainObject.getString("productId");
//          String packageName = mainObject.getString("packageName");
//          String developerPayload = mainObject.getString("developerPayload");
//          String purchaseTime = mainObject.getString("purchaseTime");
//          String purchaseState = mainObject.getString("purchaseState");
//
//
//          if(orderId.equals("") || orderId.isEmpty()){
//              orderId = "orderId";
//          } else if(productId.equals("") || productId.isEmpty()){
//              productId = "productId";
//          } else if(packageName.equals("") || packageName.isEmpty()){
//              packageName = "packageName";
//          }else if(developerPayload.equals("") || developerPayload.isEmpty()){
//              developerPayload = "developerPayload";
//          }
//          else if(purchaseTime.equals("") || purchaseTime.isEmpty()){
//              purchaseTime = "purchaseTime";
//          }else if(purchaseState.equals("")|| purchaseState.isEmpty()){
//              purchaseState = "purchaseState";
//          }
//          else {
//              Log.e(TAG, "onCreate:else API "+orderId+"\n"+productId+"\n"+packageName+"\n"
//                      +developerPayload+"\n"+purchaseTime+"\n"+purchaseState );
//             // bidPurchase(buybidId, "googleplay", orderId, productId, packageName, developerPayload, purchaseTime, purchaseState);
//          }
//      } catch (JSONException e) {
//          e.printStackTrace();
//      }
//
//  }
//
//}