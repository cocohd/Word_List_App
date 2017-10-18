package com.example.shadow.word_list_app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    TextView text_posAcceptation;
    TextView text_key;
    TextView text_ps;
    TextView text_sent;
    LinearLayout text_linerLayout;

    private WordAdd wordAdd;
    WordsHandler handler;

    private EditText edit_word;
    public Handler uiHandler=new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            Bundle b=msg.getData();
            text_key.setText(b.getString("name"));
            text_ps.setText("    "+"英:["+b.getString("EN")+"]"+"  "+"美:["+b.getString("US")+"]");
            //text_ps_US.setText("美:["+b.getString("US")+"]");
            text_posAcceptation.setText(b.getString("pos"));
            text_sent.setText(b.getString("sent"));


        }
    };

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button sendRequest=(Button)findViewById(R.id.send_request);
        text_linerLayout=(LinearLayout)findViewById(R.id.text_linerLayout);
        text_posAcceptation=(TextView)findViewById(R.id.text_posAcceptation);
        text_sent=(TextView)findViewById(R.id.text_sent);
        text_key=(TextView)findViewById(R.id.text_key);
        text_ps=(TextView)findViewById(R.id.text_ps);
        //text_ps_US=(TextView)findViewById(R.id.text_ps_US);
        edit_word=(EditText) findViewById(R.id.edit_word);


        sendRequest.setOnClickListener(SecondActivity.this);

    }

    //item:加入词库
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_item:
                wordAdd=new WordAdd(this);
                Words words=handler.getWord();
                wordAdd.wordSave(words);
                Toast.makeText(this,"成功加入",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second,menu);
        return true;
    }

    @Override
    public void onClick(View view){
        if(view.getId()==R.id.send_request){
            System.out.println("***1***");
            sendRequestWithOkHttp();
            //Word word_1=new WordsHandler().getWord();
            text_linerLayout.setVisibility(View.VISIBLE);
            System.out.println("***2***");

            //System.out.println("***3*** ： "+word_1.getKey());
            //text_key.setText("lol");
        }
    }

    private void sendRequestWithOkHttp(){
        String url="http://dict-co.iciba.com/api/dictionary.php?w=";
        String url_1="&key=82882FD7B4BAE7CF5D3FFA33444849CA";
        //有道APIString url="http://fanyi.youdao.com/openapi.do?keyfrom=youdao111&key=60638690&type=data&doctype=xml&version=1.1&q=";
        final String word_1=edit_word.getText().toString();
        url=url+word_1+url_1;
        //有道url=url+word_1;
        final String finalUrl = url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder().url(finalUrl).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    parseXMLWithSAX(responseData);



                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void parseXMLWithSAX(String xmlData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            handler =new WordsHandler();
            // 将ContentHandler的实例设置到XMLReader中
            xmlReader.setContentHandler(handler);
            // 开始执行解析
            xmlReader.parse(new InputSource(new StringReader(xmlData)));


            System.out.println("***3***");
            Words word = handler.getWord();//需从上述handler，获得实例
            System.out.println("***4*** ： "+word.getKey());
            /*System.out.println("***3.5***");
            text_key.setText(word.getKey());
            System.out.println("***4*** ： "+word.getKey());
            text_ps_B.setText(word.getPs_B());
            text_key.setText(word.getPosAcceptation());
            System.out.println("***4*** ： "+word.getPs_B());*/

            Message msg=new Message();
            Bundle b = new Bundle();// 存放数据
            b.putString("name",word.getKey());
            b.putString("EN",word.getPs_B());
            b.putString("US",word.getPs_US());
            b.putString("pos",word.getPosAcceptation());
            b.putString("sent",word.getSent());
            /*b.putString("uk",uk);
            b.putString("ex",a);*/
            msg.setData(b);
            uiHandler.sendMessage(msg);
            System.out.println("***4*** ： "+word.getPs_B());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}