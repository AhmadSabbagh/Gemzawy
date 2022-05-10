package com.am.gemzawy.fifa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.am.gemzawy.fifa.Api.Webservice;
import com.am.gemzawy.fifa.Payments.StripePayments;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.Date;

public class Payment_choice extends AppCompatActivity {
ImageView card,paypal;
    int product_id=0;
    int coins_number=0;
    int type_of_payment=0; // 1 for coins - 2 for competitions
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    private static final String CONFIG_CLIENT_ID = "WUwpnIMLFXCy3X_SCDDneJRQBItc2nRsAQvaS-XF7SivjsKGc5i1X_efQ2E_htKBtW1zbydx0oa9oBF";

         //   AZEiqtjPDZK6frlYa_PD97B9ufK5CurmeQXAl8ppXCPoF1U1NZ54PNDJyi6YoTJXuOM7E5uomolP_XK9  Live
    //AfVhNTlK5Tov0jO3uyAzLwVI72N247c9Iht4SoTyxutiGypDgSjj3ACCqmFtyudlIZs9ParQuVifplV2 Test

    ///////////////////////// client_id for support
    //AWUwpnIMLFXCy3X_SCDDneJRQBItc2nRsAQvaS-XF7SivjsKGc5i1X_efQ2E_htKBtW1zbydx0oa9oBF  TEST
    //Adwv2e49azIuRA55XwDbAig2b4b6uXDAHLVO13pSE_ONjMgLM56e9Io3yy5IN-eRev5hdtVaZ5aDs4Xq Live
ProgressBar payment_bar;
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Hipster Store")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.example.com/legal"));
    PayPalPayment thingToBuy;
    int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_choice);
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        card=findViewById(R.id.cardimg);
        paypal=findViewById(R.id.paypalimg);
        payment_bar=findViewById(R.id.paymentBar);
        Intent intent1= getIntent();
        product_id=intent1.getIntExtra("id",0);
        coins_number=intent1.getIntExtra("number",0);
        type_of_payment=intent1.getIntExtra("type",0);
        price=intent1.getIntExtra("price",0);
payment_bar.setVisibility(View.INVISIBLE);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Payment_choice.this, StripePayments.class);
                intent.putExtra("id",product_id);
                intent.putExtra("number",coins_number);
                if(coins_number==0) {
                    intent.putExtra("type", 2);
                }
                else
                {
                    intent.putExtra("type", 1);

                }

                startActivity(intent);
                finish();
            }
        });
        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thingToBuy = new PayPalPayment(new BigDecimal(1), "USD",
                        "amount", PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent = new Intent(Payment_choice.this,
                        PaymentActivity.class);

                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

                startActivityForResult(intent, REQUEST_CODE_PAYMENT);


            }
        });

    }

    public void onFuturePaymentPressed(View pressed) {
        Intent intent = new Intent(Payment_choice.this,
                PayPalFuturePaymentActivity.class);

        startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject()
                                .toString(4));

                        Toast.makeText(getApplicationContext(), "تم تاكيد الدفع الرجاء الانتظار لاكمال العملية",
                                Toast.LENGTH_LONG).show();
                        if(type_of_payment==1) {
                            Webservice webservice = new Webservice();
                            webservice.Add_coins(Payment_choice.this, coins_number,"PayPalClientSecret"+ new Date().toString(),"12",
                                    "sucess", Long.valueOf(price),"PayPal");
                            payment_bar.setVisibility(View.VISIBLE);
                        }
                        else if(type_of_payment==2)
                        {
                            //add the user to competition
                            Webservice webservice = new Webservice();
                            webservice.SubscribeToPaidComp(Payment_choice.this,product_id,"PayPalClientSecret"+ new Date().toString(),"12",
                                    "sucess", Long.valueOf(price),"PayPal");
                            payment_bar.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
                Log.e("paypaltag", "canceld");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
                Log.e("paypaltag", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");

            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.e("paypaltag", auth.toJSONObject()
                                .toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.e("paypaltag", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(),
                                "Future Payment code received from PayPal",
                                Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("paypaltag",
                                "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("paypaltag", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.e("paypaltag",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }

    public void onFuturePaymentPurchasePressed(View pressed) {
        // Get the Application Correlation ID from the SDK
        String correlationId = PayPalConfiguration
                .getApplicationCorrelationId(this);

        Log.i("FuturePaymentExample", "Application Correlation ID: "
                + correlationId);

        // TODO: Send correlationId and transaction details to your server for
        // processing with
        // PayPal...
        Toast.makeText(getApplicationContext(),
                "App Correlation ID received from SDK", Toast.LENGTH_LONG)
                .show();
    }
}
