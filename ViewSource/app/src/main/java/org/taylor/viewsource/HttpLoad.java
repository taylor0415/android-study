package org.taylor.viewsource;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Taylor on 2018/1/18.
 */

public class HttpLoad extends AsyncTask<String,Integer,String> {

    TextView tv;

    public HttpLoad(TextView tv){
        this.tv=tv;
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        System.out.println("源码数据:"+o);
        tv.setText(o.toString());
    }

    @Override
    protected String doInBackground(String... objects) {
        StringBuffer vSrouce=new StringBuffer();
        try {
            //创建一个URL实例
            URL url = new URL(objects[0]);

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("源码数据----:"+vSrouce.toString());
        return vSrouce.toString();
    }


}
