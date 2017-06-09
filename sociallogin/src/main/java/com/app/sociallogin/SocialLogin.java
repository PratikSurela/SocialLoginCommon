package com.app.sociallogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.app.sociallogin.interfaces.SocialLoginResult;
import com.app.sociallogin.model.SocialLoginItems;
import com.app.sociallogin.utlis.ConnectionCheck;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by nct119 on 1/6/17.
 */

public class SocialLogin extends Activity {

    private String TAG = "SocialLogin";
    private Context context;
    private boolean isInternetAvailable;
    private ConnectionCheck connectionCheck;
    private CallbackManager callbackmanager;
    private SocialLoginResult socialLoginResult;
    private String[] PERMISSION = new String[]{"user_photos", "email",
            "user_about_me", "public_profile", "user_about_me", "user_friends",
            "user_likes", "user_hometown",
            "user_education_history", "user_work_history", "user_birthday"};

    @SuppressLint("StringFormatInvalid")
    public SocialLogin(Context context, SocialLoginResult socialLoginResult) {
        this.context = context;
        this.socialLoginResult = socialLoginResult;
    }

    public void facebookLogin() {
        connectionCheck = new ConnectionCheck();
        isInternetAvailable = connectionCheck.isNetworkConnected(context);
        if (isInternetAvailable) {

            FacebookSdk.sdkInitialize(context);
            AppEventsLogger.activateApp(context);
            callbackmanager = CallbackManager.Factory.create();

            loginWithFacebook();
        } else {
            new ConnectionCheck().showconnectiondialog(context).show();
        }
    }

    private void loginWithFacebook() {

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions((Activity) context, Arrays.asList(PERMISSION));
        LoginManager.getInstance().registerCallback(callbackmanager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        GraphRequest request1 = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse response) {
                                        if (response.getError() != null) {
                                            // handle error
                                            Log.e("Response ERROR : ", "JSON Result" + json.toString());
                                            Log.e("Response ERROR : ", "GraphResponse Result" + response.toString());
                                        } else {
                                            try {
                                                Log.e(TAG, "onCompleted: FB Response : " + String.valueOf(json));

                                                SocialLoginItems socialLoginItems = new SocialLoginItems();
                                                socialLoginItems.setStrFBId(json.getString("id"));
                                                socialLoginItems.setStrFirstName(json.getString("first_name"));
                                                socialLoginItems.setStrLastName(json.getString("last_name"));
                                                socialLoginItems.setStrEmail(json.getString("email"));
                                                socialLoginItems.setStrUserName(json.getString("name"));
                                                socialLoginItems.setStrGender(json.getString("gender"));

                                                JSONObject data = response.getJSONObject();
                                                if (data.has("picture")) {
                                                    socialLoginItems.setStrProfilePic(data.getJSONObject("picture").getJSONObject("data").getString("url"));
                                                }

                                                socialLoginResult.onSuccess(socialLoginItems);
                                            } catch (JSONException e) {
                                                Toast.makeText(context, "Error. Please provide all the required details via facebook.", Toast.LENGTH_LONG).show();
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                        );
                        Bundle parameter = new Bundle();
                        parameter.putString("fields", "id,name,email,first_name,last_name, picture.type(large), gender, education");
                        request1.setParameters(parameter);
                        request1.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Log.e("Cancel", "On cancel");
                        reload();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(context, "Error. Please sign up with email", Toast.LENGTH_LONG).show();
                        Log.e("ERROR", error.toString());
                        if (error instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }
                    }
                }
        );
    }

    public CallbackManager getCallback() {
        return callbackmanager;
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}