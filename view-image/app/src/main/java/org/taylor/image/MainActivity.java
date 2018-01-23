package org.taylor.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    EditText editText;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap=(Bitmap)msg.obj;
            imageView.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);
        editText = findViewById(R.id.url);
        System.out.println();
    }

    public void viewImage(View view) {
        new Thread() {
            @Override
            public void run() {

                String urlStr = editText.getText().toString();

                File file=new File(getCacheDir(), Base64.encodeToString(urlStr.getBytes(),Base64.DEFAULT));
                if(file.exists() && file.length()>0){
                    System.out.println("使用缓存图片...");
                }else {
                    System.out.println("第一次加载图片...");
                    try {
                        URL url = new URL(urlStr);

                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                        FileOutputStream fos = new FileOutputStream(file);
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setConnectTimeout(5000);
                        int code = urlConnection.getResponseCode();

                        if (code == 200) {
                            InputStream inputStream = urlConnection.getInputStream();
                            int len = -1;
                            byte[] buffer = new byte[1024];
                            while ((len = inputStream.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                            }
                            fos.close();
                            inputStream.close();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                Message msg = Message.obtain();
                msg.obj = bitmap;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
