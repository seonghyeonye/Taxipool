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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;

import org.json.JSONArray;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class Fragment2_D extends Fragment {

    //ArrayList<Binary> imglist;
    Context context;
    OnTabItemSelectedListener listener;
    GridView grid2;
    GridView grid;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    ImageAdapter imageAdapter;
    private ArrayList<Bitmap> array_image= new ArrayList<>();
    PortToServer port;


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
        port = new PortToServer("http://143.248.36.38:3000", ((MainActivity)getActivity()).cookies);
        System.out.println("frag2D on create");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate( R.layout.fragment_fragment2__d, container, false );
        System.out.println("frag2D on create view");
        GridView grid = view.findViewById(R.id.myGrid);
        grid2=view.findViewById(R.id.myGrid);
        RelativeLayout layout = view.findViewById(R.id.layout);
        //getdata();

        imageAdapter = new ImageAdapter( context, array_image, false);
        grid.setAdapter( imageAdapter );

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent i = new Intent (getActivity(), FullImageActivity.class);
                i.putExtra("url", array_image.get(position).toString());
                startActivity(i);

            }
        });

        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                new AlertDialog.Builder(context)
                        .setTitle("Confirm Message")
                        .setMessage("사진를 삭제하시겠습니까?")
                        .setIcon(android.R.drawable.ic_menu_save)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(context.getApplicationContext(), "사진이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                                imageAdapter.images.remove(i);
                            }
                        });
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

    public void uploadBmp(Bitmap bitmap) {
        // 이미지
// 기타 필요한 내용
        /*
        String attachmentName = "bitmap";
        String attachmentFileName = "bitmap.bmp";
        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";
        Map<String, String> cookies = ((MainActivity)getActivity()).cookies;
// request 준비
        HttpURLConnection httpUrlConnection = null;
        */
        try {
            /*
            URL url = new URL("http://143.248.36.38:3000/upload/bmp");
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            String prevCookie = cookies.getOrDefault(url, "");
            if (!prevCookie.equals("")) {
                httpUrlConnection.setRequestProperty("Cookie", prevCookie);
            }
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);

            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
            httpUrlConnection.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);

            /*
            // content wrapper시작
            DataOutputStream request = new DataOutputStream(
                    httpUrlConnection.getOutputStream());

            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" +
                    attachmentName + "\";filename=\"" +
                    attachmentFileName + "\"" + crlf);
            request.writeBytes(crlf);
            // Bitmap을 ByteBuffer로 전환
            */

            byte[] pixels;
            pixels = bitmapToByteArray(bitmap);
            //String strings = byteArrayToBinaryString(pixels);
            PortToServer port = new PortToServer("http://143.248.36.38:3000", ((MainActivity)getActivity()).cookies);
            QueryToServerMongo mongoQuery = new QueryToServerMongo("madcamp", "gallery", "/crud/update", new JSONArray().put(new BasicDBObject()).put(new BasicDBObject().append("$push", new BasicDBObject().append("photos", pixels))));
            JSONObject obj = port.postToServerV2(mongoQuery);
            if (obj!=null) {
                if (obj.getString("result").equals("OK")) {
                    System.out.println(obj.get("data"));
                }
                else {
                    System.out.println("failed");
                }
            }
            else{
                System.out.println("response: null");
            }
            /*
            pixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
            for (int i = 0; i < bitmap.getWidth(); ++i) {
                for (int j = 0; j < bitmap.getHeight(); ++j) {
                    //we're interested only in the MSB of the first byte,
                    //since the other 3 bytes are identical for B&W images
                    pixels[i + j] = (byte) ((bitmap.getPixel(i, j) & 0x80) >> 7);
                }
            }
            */
            /*
            request.write(pixels);

            // content wrapper종료
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary +
                    twoHyphens + crlf);

            // buffer flush
            request.flush();
            request.close();

            // Response받기

            InputStream responseStream = new BufferedInputStream(httpUrlConnection.getInputStream());
            //responseStream.read()
            byte[] res = inputStreamToByteArray(responseStream);
            /*
            BufferedReader responseStreamReader =
                    new BufferedReader(new InputStreamReader(responseStream));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            responseStreamReader.close();
            String response = stringBuilder.toString();
            */
            /*
            System.out.println("이얏호우");
            bitmap = byteArrayToBitmap(res);
            System.out.println("size: "+res.length);
            //Response stream종료
            responseStream.close();

            // connection종료
            httpUrlConnection.disconnect();
            */
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //super.onActivityResult(requestCode, resultCode, data);
        ImageAdapter mImageAdapter = new ImageAdapter( context, array_image, false);
        final List<Bitmap> bitmaps = new ArrayList<>();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1002: {
                    System.out.println("DDDDDD");
                    if (data != null) {
                        final Uri uri = data.getData();
                        mImageAdapter.images.add(uri);
                        //System.out.println(uri.toString());
                        grid2.setAdapter(mImageAdapter);
                        // String imagePath= getRealPathFromURI(uri);
                        //  Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
                        //System.out.println(bitmap);
                        //Uri에서 이미지 이름을 얻어온다.
                        //이미지 데이터를 비트맵으로 받아온다.
/*
                        //ImageView image = (ImageView)(getActivity().findViewById(R.id.imageView1));
                        Thread thrd = new Thread() {
                            @Override
                            public void run() {
                                synchronized (this) {
                                    try {
                                        Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                                        uploadBmp(image_bitmap);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    notify();
                                }
                            }
                        };
                        thrd.start();
                        synchronized (thrd) {
                            try {
                                thrd.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
*/
                        //배치해놓은 ImageView에 set
                        //image.setImageBitmap(image_bitmap);

                        //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();

                        //grid2.setAdapter(mImageAdapter);

                    }
                    break;
                }
            }
        }
        /*
        try{
            PortToServer port = new PortToServer("http://143.248.36.38:3000", ((MainActivity)getActivity()).cookies);
            QueryToServerMongo mongoQuery = new QueryToServerMongo("madcamp", "gallery", "/crud/research", new JSONArray().put(new BasicDBObject()));
            JSONObject obj = port.postToServerV2(mongoQuery);
            if (obj!=null) {
                if (obj.getString("result").equals("OK")) {
                    Gson gson = new Gson();
                    JSONObject found = (JSONObject)obj.getJSONArray("data").get(0);
                    List<byte[]> photos = (List<byte[]>) gson.fromJson(found.getString("photos"), new TypeToken<List<byte[]>>() {
                    }.getType());
                    testView.setImageBitmap(byteArrayToBitmap(photos.get(0)));
                }
                else {
                    System.out.println("failed");
                }
            }
            else{
                System.out.println("response: null");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
*/
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
                QueryToServerMongoBuilder builder = new QueryToServerMongoBuilder("madcamp", "gallery");
                final List<String> selected = new ArrayList<>();
                try {
                    JSONObject obj = port.postToServerV2(builder.getQueryR(new JSONArray().put(new BasicDBObject()).put(new BasicDBObject().append("gallery.photo", 0))));
                    if (obj.getString("result").equals("OK")){
                        Gson gson = new Gson();
                        JSONArray found = obj.getJSONArray("data");
                        List<String> gallery = new ArrayList<>();
                        for (int i=0; i<found.length(); i++){
                            gallery.add(found.getJSONObject(i).getJSONObject("gallery").getString("name"));
                            show(gallery, selected);
                        }
                        System.out.println("ok");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        // 갤러리에서 사진을 고르는 행위

    }

    public void loadImage(String name){
        try {
            String selectedName = name;
            QueryToServerMongoBuilder builder = new QueryToServerMongoBuilder("madcamp", "gallery");
            JSONObject obj = port.postToServerV2(builder.getQueryR(new JSONArray().put(new BasicDBObject().append("gallery.name", selectedName))));
            if (obj.getString("result").equals("OK")){
                Gson gson = new Gson();
                JSONArray found = obj.getJSONArray("data");
                for (int i=0; i<found.length(); i++){
                    array_image.add(byteArrayToBitmap((byte[])gson.fromJson(found.getJSONObject(i).getJSONObject("gallery").getJSONArray("photo").toString(),new TypeToken<byte[]>(){}.getType())));
                }
                System.out.println("load ok");
                imageAdapter.notifyDataSetChanged();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(android.content.Intent.ACTION_PICK); // ACTION_PICK 혹은 ACTION_GET_CONTENT 사용
        intent.setType("image/*");
        startActivityForResult(intent, 1002); // 이 함수는 사용자가 사진을 선택(PICK) 한 후에 onActivityResult 함수를 호출
        //class.add()~~

    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){ column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); }
        return cursor.getString(column_index);
    }

    /**
     * 바이너리 바이트 배열을 스트링으로 변환
     *
     * @param b
     * @return
     */
    public static String byteArrayToBinaryString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; ++i) {
            sb.append(byteToBinaryString(b[i]));
        }
        return sb.toString();
    }

    /**
     * 바이너리 바이트를 스트링으로 변환
     *
     * @param n
     * @return
     */
    public static String byteToBinaryString(byte n) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++) {
            if (((n >> bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }

    /**
     * 바이너리 스트링을 바이트배열로 변환
     *
     * @param s
     * @return
     */
    public static byte[] binaryStringToByteArray(String s) {
        int count = s.length() / 8;
        byte[] b = new byte[count];
        for (int i = 1; i < count; ++i) {
            String t = s.substring((i - 1) * 8, i * 8);
            b[i - 1] = binaryStringToByte(t);
        }
        return b;
    }

    /**
     * 바이너리 스트링을 바이트로 변환
     *
     * @param s
     * @return
     */
    public static byte binaryStringToByte(String s) {
        byte ret = 0, total = 0;
        for (int i = 0; i < 8; ++i) {
            ret = (s.charAt(7 - i) == '1') ? (byte) (1 << i) : 0;
            total = (byte) (ret | total);
        }
        return total;
    }

    void show(List<String> texts, final List<String> selected) {
        final List<String> ListItems = new ArrayList<>();
        ListItems.addAll(texts);

        final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

        final List<String> SelectedItems = new ArrayList();
        int defaultItem = 0;
        SelectedItems.add(items[defaultItem].toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("AlertDialog Title");
        builder.setSingleChoiceItems(items, defaultItem,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SelectedItems.clear();
                        SelectedItems.add(items[which].toString());
                    }
                });
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        loadImage(SelectedItems.get(0));
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
}


