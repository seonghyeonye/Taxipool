package com.example.newfinal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.util.JSON;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PhoneBookRegisterFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PhoneBookRegisterFrag#} factory method to
 * create an instance of this fragment.
 */
public class PhotoRegisterPop extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    EditText name;
    EditText group;

    private OnFragmentInteractionListener mListener;

    PortToServer port;
    QueryToServerMongoBuilder builderGallery = new QueryToServerMongoBuilder("madcamp", "gallery");

    public PhotoRegisterPop() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void mOnCancle(View v){
        finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String uriString = getArguments().getString("uri");
        Uri uri = Uri.parse(uriString);
        port = new PortToServer("http://143.248.36.38:3000", ((MainActivity)getActivity()).cookies);
        final View view = inflater.inflate(R.layout.fragment_photo_register_pop, container, false);
        LinearLayout layout = view.findViewById(R.id.registerLayout);
        Bitmap imageBitmap = null;
        try {
            imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        } catch (Exception e){
            e.printStackTrace();
        }
        final ImageView image = view.findViewById(R.id.newImage);
        final Bitmap image_bitmap = imageBitmap;
        image.setImageBitmap(image_bitmap);
        Button buttonInsert = (Button)view.findViewById(R.id.registerButton);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 읽기
                System.out.println("inserted");
                name = getActivity().findViewById (R.id.newName);
                group = view.findViewById(R.id.newGroup);
                String _name = name.getText().toString();
                String _group = group.getText().toString();
                if (_name.isEmpty()){
                    System.out.println("empty");
                    finish();
                }
                try {
                    port.postToServerV2(builderGallery.getQueryC(new JSONArray().put(new BasicDBObject().append("gallery", new BasicDBObject().append("photo", bitmapToByteArray(image_bitmap)).append("name", _name).append("group", _group)))));
                } catch (IOException e){
                    e.printStackTrace();
                }
                finish();
            }
        });
        Button buttonCancel = (Button)view.findViewById(R.id.cancel_button);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void finish(){
        System.out.println("on finish: " + ((MainActivity)getActivity()).currentTab);
        ((MainActivity)getActivity()).showProperFragment();
    }

    public byte[] bitmapToByteArray( Bitmap bitmap ) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
        byte[] byteArray = stream.toByteArray() ;
        return byteArray ;
    }

    public Bitmap byteArrayToBitmap( byte[] byteArray ) {
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteArray, 0, byteArray.length ) ;
        return bitmap ;
    }
}
