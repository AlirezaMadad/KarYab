package com.arsan.niloufar.karyab;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import util.IabHelper;
import util.IabResult;
import util.Inventory;
import util.Purchase;

public class InAppPurchaseActivity extends AppCompatActivity {

    // Debug tag, for logging
    // Debug tag, for logging
    static final String TAG = "KaryabCoin";

    // SKUs for our products: the premium upgrade (non-consumable)
    static final String SKU_PREMIUM = "KaryabEnableOnlineCVCoin";

    // Does the user have the premium upgrade?
    boolean mIsPremium = false;

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 1372;

    // The helper object
    IabHelper mHelper;
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_purchase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHelper.launchPurchaseFlow(InAppPurchaseActivity.this, SKU_PREMIUM, RC_REQUEST, mPurchaseFinishedListener, "payload-string");
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
        String base64EncodedPublicKey = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwCjOC9rT/jP3AHVuCdkw6U/Y4jFzW9a+vMxbxh13VqSH++FwTkrVJUFxpelqC5rDAjyiCVhTJ0Q9KHlJJE06pnHToi/LsYuskqxbFNOobCYpsSIE80u6pa7eq0nAXIte9iIjL6oeZW1QpAE8Anzw3BLtiHsOMUD97OAGI5dcDlCN6hCz+jlZNy1Yu2Q00fAuTfHOj7TwN4/B/9Nh2jDIwltgu0vEQQ8Ej4PNu3ySvECAwEAAQ==";

        mHelper = new IabHelper(this, base64EncodedPublicKey);


        mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                Log.d(TAG, "Query inventory finished.");
                if (result.isFailure()) {
                    Log.d(TAG, "Failed to query inventory: " + result);
                    return;
                }
                else {
                    Log.d(TAG, "Query inventory was successful.");
                    // does the user have the premium upgrade?
                    mIsPremium = inventory.hasPurchase(SKU_PREMIUM);
                    if (mIsPremium){
                        MasrafSeke(inventory.getPurchase(SKU_PREMIUM));
                    }
                    // update UI accordingly

                    Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
                }

                Log.d(TAG, "Initial inventory query finished; enabling main UI.");
            }
        };

        mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                if (result.isFailure()) {
                    Log.d(TAG, "Error purchasing: " + result);
                    return;
                }
                else if (purchase.getSku().equals(SKU_PREMIUM)) {
                    // give user access to premium content and update the UI
                    Toast.makeText(InAppPurchaseActivity.this, "خرید موفق", Toast.LENGTH_SHORT).show();
                    MasrafSeke(purchase);

                }
            }
        };


        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                }
                // Hooray, IAB is fully set up!
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }


    @Override
    public void onDestroy() {
        //از سرویس در زمان اتمام عمر activity قطع شوید
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }


    private void MasrafSeke(Purchase kala){
        // برای اینکه کاربر فقط یکبار بتواند از کالای فروشی استفاده کند
        // باید بعد از خرید آن کالا را مصرف کنیم
        // در غیر اینصورت کاربر با یکبار خرید محصول می تواند چندبار از آن استفاده کند
        mHelper.consumeAsync(kala, new IabHelper.OnConsumeFinishedListener() {
            @Override
            public void onConsumeFinished(Purchase purchase, IabResult result) {
                if (result.isSuccess())
                    Toast.makeText(InAppPurchaseActivity.this, "مصرف شد", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "NATIJE masraf: " + result.getMessage() + result.getResponse());

            }
        });
    }

}
