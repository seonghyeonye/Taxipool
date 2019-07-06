package com.example.newfinal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.util.JSON;

import org.json.JSONArray;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PhoneBookRegisterFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PhoneBookRegisterFrag#} factory method to
 * create an instance of this fragment.
 */
public class PhoneBookRegisterFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    EditText name;
    EditText phoneNumber;
    EditText email;
    EditText group;

    private OnFragmentInteractionListener mListener;

    PortToServer port = new PortToServer("http://143.248.36.38:3000");
    QueryToServerMongoBuilder builderContacts = new QueryToServerMongoBuilder("madcamp", "contacts");

    public PhoneBookRegisterFrag() {
        // Required empty public constructor
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
        final View view = inflater.inflate(R.layout.fragment_phone_book_register, container, false);
        LinearLayout layout = view.findViewById(R.id.registerLayout);
        layout =view.findViewById(R.id.registerTop);
        Button buttonInsert = (Button)view.findViewById(R.id.registerButton);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 읽기
                System.out.println("inserted");
                name = getActivity().findViewById (R.id.newName);
                phoneNumber = view.findViewById(R.id.newPhoneNumber);
                email = view.findViewById(R.id.newEmail);
                group = view.findViewById(R.id.group);
                String _name = name.getText().toString();
                if (_name.isEmpty()){
                    System.out.println("empty");
                    finish();
                }
                String _phoneNumber = phoneNumber.getText().toString();
                String _email = email.getText().toString();
                String[] data = {_name, _phoneNumber, _email};
                System.out.println("data built");
                //데이터 전달하기
                Contact contact = new Contact(data[0], data[1], data[2]);
                try {
                    port.postToServerV2(builderContacts.getQueryU(new JSONArray().put(QueryBuilder.start("account._id").is("myhwang99").get()).put(QueryBuilder.start("$push").is(QueryBuilder.start("contacts").is((BasicDBObject) JSON.parse(contact.toString())).get()).get())));
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
        ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container,((MainActivity)getActivity()).fragment1).commit();
    }

}
