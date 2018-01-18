package org.taylor.viewsource;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

    }

    public void clickViewSource(View view){

        TextView textView=findViewById(R.id.textView);
        EditText editText=findViewById(R.id.editText);
        String url=editText.getText().toString();
        HttpLoad httpLoad=new HttpLoad(textView);
        httpLoad.execute(url);


       /* try {
            String viewSrouce=getViewSource(url);
            System.out.println("-------->"+viewSrouce);
            textView.setText(viewSrouce);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    String getViewSource(String urlStr) throws Exception{
        System.out.println("要查看的地址为："+urlStr);

        StringBuffer vSrouce=new StringBuffer();
        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                urlConnection.getInputStream(), "UTF-8"));// 得到输入流，即获得了网页的内容
        String line; // 读取输入流的数据，并显示
        while ((line = reader.readLine()) != null) {
            vSrouce.append(line);
        }
        reader.close();

        return vSrouce.toString();
    }
}
