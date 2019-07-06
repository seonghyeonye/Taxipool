package com.example.newfinal;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PhoneBookRegisterPop extends Activity {
    EditText name;
    EditText phoneNumber;
    EditText email;
    EditText group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_phone_book_register_pop);
        LinearLayout layout = findViewById(R.id.registerLayout);
        layout.setBackground(getDrawable(R.drawable.round_bg));
        Button buttonInsert = (Button)findViewById(R.id.registerButton);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 읽기
                System.out.println("inserted");
                name = findViewById (R.id.newName);
                phoneNumber = findViewById(R.id.newPhoneNumber);
                email = findViewById(R.id.newEmail);
                group = findViewById(R.id.group);
                String _name = name.getText().toString();
                if (_name.isEmpty()){
                    System.out.println("empty");
                    finish();
                }
                String _phoneNumber = phoneNumber.getText().toString();
                String _email = email.getText().toString();
                String _group = group.getText().toString();
                String[] data = {_name, _phoneNumber, _email, _group};
                System.out.println("data built");
                //데이터 전달하기
                Intent intent = new Intent();
                intent.putExtra("item", data);
                setResult(RESULT_OK+1, intent);
                //액티비티(팝업) 닫기
                finish();
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
    public void mOnCancle(View v){
        System.out.println("canceled");
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}

