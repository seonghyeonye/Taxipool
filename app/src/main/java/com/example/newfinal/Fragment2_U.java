package com.example.newfinal;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class Fragment2_U extends Fragment {

    //ArrayList<Binary> imglist;
    Context context;
    OnTabItemSelectedListener listener;
    GridView grid2;
    GridView grid;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    ImageAdapter imageAdapter;
    CircleImageView testView = null;
    private ArrayList<Uri> array_image= new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = getActivity();

        if (context instanceof OnTabItemSelectedListener) {
            listener = (OnTabItemSelectedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("frag2U on detatch");

        if (context != null) {
            context = null;
            listener = null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("frag2U on create");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate( R.layout.fragment_fragment2__u, container, false );
        GridView grid = view.findViewById(R.id.myGrid);
        grid2=view.findViewById(R.id.myGrid);

        System.out.println("frag2U on create view");
        imageAdapter = new ImageAdapter( context , array_image);
        grid.setAdapter( imageAdapter );

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                /*
                Intent i = new Intent (getActivity(), FullImageActivity.class);
                i.putExtra("id", position);
                startActivity(i);
*/
                Uri uri = imageAdapter.getImages().get(position);
                Bundle bundle = new Bundle(1);
                bundle.putString("uri", uri.toString());
                PhotoRegisterPop photoRegisterPop = new PhotoRegisterPop();
                photoRegisterPop.setArguments(bundle);
                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, photoRegisterPop).commit();
            }
        });

        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                System.out.println("long click");
                new AlertDialog.Builder(context)
                        .setTitle("Confirm Message")
                        .setMessage("사진를 삭제하시겠습니까?")
                        .setIcon(android.R.drawable.ic_menu_save)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(context.getApplicationContext(), "사진이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                                imageAdapter.images.remove(i);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                return true;
            }
        });
        initUI(view);
        return view;
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

    public static byte[] inputStreamToByteArray(InputStream is) {

        byte[] resBytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int read = -1;
        try {
            while ( (read = is.read(buffer)) != -1 ) {
                bos.write(buffer, 0, read);
            }

            resBytes = bos.toByteArray();
            bos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return resBytes;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //super.onActivityResult(requestCode, resultCode, data);
        ImageAdapter mImageAdapter = new ImageAdapter( context, array_image);
        final List<Bitmap> bitmaps = new ArrayList<>();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000: {
                    System.out.println("UUUUU");
                    if (data != null) {
                        final Uri uri = data.getData();
                        mImageAdapter.images.add(uri);
                        //System.out.println(uri.toString());
                        grid2.setAdapter(mImageAdapter);
                    }
                }
            }
        }
    }
/*
    // 선택된 이미지 파일명 가져오기
    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        getImgURL = imgPath;
        getImgName = imgName;

        return "success";
    }

*/
    /**
     * Upload Image Client Code
     */

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
        Intent intent = new Intent(Intent.ACTION_PICK); // ACTION_PICK 혹은 ACTION_GET_CONTENT 사용
        intent.setType("image/*");
        startActivityForResult(intent, 1000); // 이 함수는 사용자가 사진을 선택(PICK) 한 후에 onActivityResult 함수를 호출
        //class.add()~~

        }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){ column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); }
        return cursor.getString(column_index);
    }



}


