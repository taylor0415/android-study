package org.taylor.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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
    }

    public void viewImage(View view) {
        new Thread() {
            @Override
            public void run() {

                String urlStr = editText.getText().toString();

                try {
                    URL url = new URL(urlStr);

                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(5000);
                    int code=urlConnection.getResponseCode();

                    if (code==200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                        Message msg=Message.obtain();
                        msg.obj=bitmap;
                        handler.sendMessage(msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
