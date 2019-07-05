package com.example.newfinal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.app.Activity.RESULT_OK;


public class Fragment2 extends Fragment {

    //ArrayList<Binary> imglist;
    Context context;
    OnTabItemSelectedListener listener;
    GridView grid2;
    GridView grid;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    ImageAdapter imageAdapter;
    private ArrayList<Uri> array_image= new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

        if (context instanceof OnTabItemSelectedListener) {
            listener = (OnTabItemSelectedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (context != null) {
            context = null;
            listener = null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate( R.layout.fragment2, container, false );
        GridView grid = view.findViewById(R.id.myGrid);
        grid2=view.findViewById(R.id.myGrid);
        //getdata();

        imageAdapter = new ImageAdapter( context );
        grid.setAdapter( imageAdapter );

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent (getActivity(), FullImageActivity.class);
                i.putExtra("id", position);
                startActivity(i);
            }
        });
        initUI(view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //super.onActivityResult(requestCode, resultCode, data);
        ImageAdapter mImageAdapter = new ImageAdapter( context);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000: {
                    if (data != null) {
                        Uri uri = data.getData();
                        mImageAdapter.images.add(uri);
                        //System.out.println(uri.toString());
                        grid2.setAdapter(mImageAdapter);
                       // String imagePath= getRealPathFromURI(uri);
                      //  Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
                        //System.out.println(bitmap);

                        //grid2.setAdapter(mImageAdapter);
                    }
                }
            }
        }
    }



    private void initUI(final ViewGroup rootView) {
        FloatingActionButton plusbutton= rootView.findViewById(R.id.plus);
        plusbutton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View V) {
                                              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) // M is Marshmallow
                                              {
                                                  if (ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) { // 만약 외부 저장소 접근이 허용되어있지 않다면
                                                      // permission not granted, request it.
                                                      String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE}; // 외부 저장소(ex. 갤러리) 읽기 권한 요청을 담기
                                                      requestPermissions(permissions, 1001); // 요청 보내기
                                                      // 이 함수는 사용자에게 [Allow/Deny] 여부를 팝업 창으로부터 선택하게 하고
                                                      // onRequestPermissionsResult 함수를 호출함
                                                  } else {
                                                      // permission already granted
                                                      pickImageFromGallery();
                                                  }
                                              } else {
                                                  // system os is less than M(Marshmallow) (ex. IceCreamSandwich)
                                                  pickImageFromGallery();
                                              }

                                          }
                                      });

            // 갤러리에서 사진을 고르는 행위

            }


private void pickImageFromGallery() {
        Intent intent = new Intent(android.content.Intent.ACTION_PICK); // ACTION_PICK 혹은 ACTION_GET_CONTENT 사용
        intent.setType("image/*");
        startActivityForResult(intent, 1000); // 이 함수는 사용자가 사진을 선택(PICK) 한 후에 onActivityResult 함수를 호출
        //class.add()~~

        }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){ column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); }
        return cursor.getString(column_index); }

}


