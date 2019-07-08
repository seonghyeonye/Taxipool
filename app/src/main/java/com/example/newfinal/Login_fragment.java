package com.example.newfinal;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.login_signup_screendesign_demo.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mongodb.BasicDBObject;

import org.json.JSONException;
import org.json.JSONObject;


public class Login_fragment extends Fragment {
    private static View view;
    private static Context mContext;
    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    private static EditText loginid;
    private static EditText loginpassword;
    MainFragment mainFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.loginpage, container, false);
        initViews();
        setListeners();
        mainFragment = ((MainActivity)getActivity()).mainFragment;
        return view;
    }

    // Initiate Views
    private void initViews() {
        //fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        signUp = (TextView) view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) view.findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);
        loginid=(EditText) view.findViewById(R.id.login_emailid);
        loginpassword=(EditText) view.findViewById(R.id.login_password);
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });
        signUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment nextFrag= new SignUp_fragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.right_enter,R.anim.left_out)
                        .replace(R.id.frameContainer, nextFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });
        show_hide_password.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button,
                                         boolean isChecked) {

                // If it is checkec then show password else hide
                // password
                if (isChecked) {

                    show_hide_password.setText(R.string.hide_pwd);// change
                    // checkbox
                    // text

                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                } else {
                    show_hide_password.setText(R.string.show_pwd);// change
                    // checkbox
                    // text

                    password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());// hide password

                }

            }
        });
    }

    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        // Check patter for email id
        //Pattern p = Pattern.compile(Utils.regEx);

        //Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
//            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both.");

        }
        // Check if email id is valid or not
        //else if (!m.find())
        //  new CustomToast().Show_Toast(getActivity(), view,
        //          "Your Email Id is Invalid.");
        // Else do login and do your stuff
        else {
            PortToServer port  = new PortToServer("http://143.248.36.38:3000", ((MainActivity)getActivity()).cookies);
            JSONObject obj = null;
            try {
                obj=port.postToServerV2(new BasicQueryToServer("/login").setData(new BasicDBObject().append("id", getEmailId).append("password", getPassword)));
                if (obj!=null) {
                    if (obj.getString("result").equals("OK")) {
                        System.out.println("success");
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.down_enter, R.anim.up_out)
                                .replace(R.id.frameContainer, mainFragment)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        new CustomToast().Show_Toast(getActivity(), view,
                                obj.getString("data"));
                    }
                }
                else {
                    new CustomToast().Show_Toast(getActivity(), view,
                            "try again");
                }
            } catch(IOException e){
                e.printStackTrace();
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
        System.out.println(loginid.getText().toString());
        System.out.println(loginpassword.getText().toString());
    }

}