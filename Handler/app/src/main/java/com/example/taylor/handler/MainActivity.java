package com.example.taylor.handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    EditText et;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String content= (String) msg.obj;

            tv.setText(content);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        et=findViewById(R.id.et);
    }

    public  void processPostUrl(View view){

        new Thread(){
            @Override
            public void run() {
                System.out.println("网络获取源码测试......");

                StringBuffer vSrouce=new StringBuffer();
                try {
                    //创建一个URL实例
                    //URL url = new URL(objects[0]);

                    URL url = new URL("http://www.baidu.com");

                    try {
                        //通过URL的openStrean方法获取URL对象所表示的自愿字节输入流
                        InputStream is = url.openStream();
                        InputStreamReader isr = new InputStreamReader(is,"utf-8");

                        //为字符输入流添加缓冲
                        BufferedReader br = new BufferedReader(isr);
                        String data = br.readLine();//读取数据

                        while (data!=null){//循环读取数据
                            System.out.println(data);//输出数据
                            data = br.readLine();
                            vSrouce.append(data);
                        }
                        br.close();
                        isr.close();
                        is.close();

                        Message msg=new Message();
                        msg.obj=et.getText().toString();

                        handler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("源码数据----:"+vSrouce.toString());
            }
        }.start();
    }

}
