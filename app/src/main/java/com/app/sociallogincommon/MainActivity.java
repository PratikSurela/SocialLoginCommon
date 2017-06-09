package com.app.sociallogincommon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.app.sociallogin.SocialLogin;
import com.app.sociallogin.interfaces.SocialLoginResult;
import com.app.sociallogin.model.SocialLoginItems;
import com.facebook.CallbackManager;

public class MainActivity extends AppCompatActivity {

    private SocialLogin socialLogin;
    private String TAG = "MainActivity";
    private TextView txtView;
    private CallbackManager callbackmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = (TextView) findViewById(R.id.txtView);
        callbackmanager = CallbackManager.Factory.create();

        /*
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.app.sociallogincommon",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/

        SocialLoginResult loginResult = new SocialLoginResult() {
            @Override
            public void onSuccess(SocialLoginItems loginItems) {
                txtView.setText(loginItems.getStrFirstName());
                Log.e(TAG, "onSuccess: loginItems : fname : " + loginItems.getStrFirstName());
            }
        };

        socialLogin = new SocialLogin(this, loginResult);
        socialLogin.facebookLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackmanager.onActivityResult(requestCode, resultCode, data);
        socialLogin.getCallback().onActivityResult(requestCode, resultCode, data);
    }
}