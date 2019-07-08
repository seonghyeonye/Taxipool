package com.example.newfinal;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.login_signup_screendesign_demo.R;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.media.MediaCas;
import android.media.session.MediaSession;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import android.support.v4.app.Fragment;
import android.se.omapi.Session;
import android.telecom.Call;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class SignUp_fragment extends Fragment implements OnClickListener {
    private static View view;
    private static EditText fullName, emailId,
            password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    public  static CallbackManager callbackManager;
    private CircleImageView circleImageView;
    private TextView txtName, txtEmail;
    private LoginButton loginButton;
    private String usertoken =null;
    private String userId=null;
    PortToServer port;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        port = new PortToServer("http://143.248.36.38:3000", ((MainActivity)getActivity()).cookies);
        view = inflater.inflate(R.layout.signup_layout, container, false);
        printKeyHash();
        initViews();
        setListeners();
        loginButton = view.findViewById(R.id.login_button);
        txtName = view.findViewById(R.id.profile_name);
        txtEmail = view.findViewById(R.id.profile_email);
        circleImageView = view.findViewById(R.id.profile_email);

        //FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        //LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile","email"));
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);
        System.out.println("running");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //AccessToken accessToken = AccessToken.getCurrentAccessToken();

                usertoken= loginResult.getAccessToken().getToken();
                userId = loginResult.getAccessToken().getUserId();
                System.out.println(usertoken);
                System.out.println("success");
                System.out.println(userId);

            }

            @Override
            public void onCancel() {
                Log.d("TAG", "취소됨");
                System.out.println("cancel");
            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
                System.out.println("error");
            }
        });
        System.out.println("finish");
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        System.out.println(isLoggedIn);
        System.out.println(accessToken);
        //System.out.println(accessToken);

        //Log.v("TAG", "Token::" + session.getAccessToken().getToken());
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // Initialize all views
    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);

        // Setting text selector over textviews

    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:

                // Call checkValidation method
                checkValidation();

                break;

            case R.id.already_user:

                // Replace login fragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameContainer, new Login_fragment())
                        .addToBackStack(null)
                        .commit();

                break;
        }

    }

    // Check Validation Method
    private  void checkValidation() {
        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required.");
        else if(usertoken==null){
            new CustomToast().Show_Toast(getActivity(),view,"Please authenticate with Facebook");
        }
        // Check if email id valid or not
        /*(else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");
*/
        // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please select Terms and Conditions.");

            // Else do signup or do your stuff
        else {
            try {
                JSONObject obj = port.postToServerV2(new BasicQueryToServer("/register").setData(new BasicDBObject().append("id", getEmailId).append("password", getConfirmPassword).append("email", getEmailId).append("name", getFullName)));
                if (obj!=null){
                    if (obj.getString("result").equals("OK")) {
                        new CustomToast().Show_Toast(getActivity(), view,
                                "Sign Up Success");
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.down_enter, R.anim.up_out)
                                .replace(R.id.frameContainer, ((MainActivity) getActivity()).loginFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    else{
                        new CustomToast().Show_Toast(getActivity(), view,
                                obj.getString("data"));
                    }
                }
                else{
                    new CustomToast().Show_Toast(getActivity(), view,
                            "Try Again");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        LoginManager.getInstance().logOut();
    }

    private void printKeyHash(){
        try{
            PackageInfo info= getActivity().getPackageManager().getPackageInfo("com.example.newfinal", PackageManager.GET_SIGNATURES);
            for(Signature signature: info.signatures){
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
                }
                catch(NoSuchAlgorithmException e){
                    e.printStackTrace();
                }
            }
        }
        catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
    }
}