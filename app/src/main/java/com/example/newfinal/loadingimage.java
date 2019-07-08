package com.example.newfinal;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import static com.facebook.FacebookSdk.getApplicationContext;

public class loadingimage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.loginpage, container, false);
        Thread welcome = new Thread() {
            @Override
            public void run() {
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                onShakeImage(view);
                                sleep(10000);  //Delay of 10 seconds
                            }catch(Exception e){
                                System.out.println("1");
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println("fa");
                } finally {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.right_enter,R.anim.left_out)
                            .replace(R.id.frameContainer, new Login_fragment())
                            .addToBackStack(null)
                            .commit();
                }
            }
        };
        welcome.start();

        return view;
    }

    public void onShakeImage(View view) {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        ImageView image;
        image = (ImageView) view.findViewById(R.id.load);

        image.startAnimation(shake); // starts animation
    }
}
