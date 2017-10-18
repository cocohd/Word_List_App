package com.example.shadow.word_list_app;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * Created by shadow on 2017/10/11.
 */
public class WordsHandler extends DefaultHandler {
    //记录当前节点
    private String nodeName;
    public Words word=null;
    private StringBuilder posAcceptation;
    private StringBuilder sent;

    /**
     * 获取解析后的words对象
     */
    public Words getWord() {
        return word;
    }

    //开始解析XML时调用
    @Override
    public void startDocument() throws SAXException {
        //初始化
        System.out.println("***11***");
        word = new Words();
        System.out.println("***22***");
        posAcceptation = new StringBuilder();
        sent=new StringBuilder();
    }

    //结束解析XML时调用
    @Override

    public void endDocument() throws SAXException {
        //将所有解析出来的内容赋予words
        word.setPosAcceptation(posAcceptation.toString().trim());
        word.setSent(sent.toString().trim());
    }

    //开始解析节点时调用
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        nodeName = localName;
    }

    //结束解析节点时调用
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //在读完整个节点后换行
        if ("acceptation".equals(localName)) {
            posAcceptation.append("\n");
        }
        /*例句注释*/
        else if ("orig".equals(localName)) {
            sent.append("\n");
        } else if ("trans".equals(localName)) {
            sent.append("\n");
            sent.append("\n");
        }
    }

    //获取节点中内容时调用
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String a = new String(ch, start, length);
        //去掉文本中原有的换行
        for (int i = start; i < start + length; i++) {
            if (ch[i] == '\n')
                return;
        }
        //将节点的内容存入Words对象对应的属性中
        if ("key".equals(nodeName)) {
            System.out.println("***33***");
            word.setKey(a);
            Log.d("1",word.getKey());
            System.out.println("***44***");
            //text_key.setText(word.getKey());
        } else if ("ps".equals(nodeName)) {
            if ((word.getPs_B()).length() <= 0) {
                word.setPs_B(a);
                Log.d("1", word.getPs_B());
            }else{
                word.setPs_US(a);
                Log.d("1", word.getPs_US());
            }
        }else if ("pos".equals(nodeName)) {
            posAcceptation.append(a);
        }else if ("acceptation".equals(nodeName)) {
            posAcceptation.append(a);
            //word.setPosAcceptation(a);
            Log.d("1",word.getPosAcceptation());
        }else if ("orig".equals(nodeName)) {
            sent.append(a);
        } else if ("trans".equals(nodeName)) {
            sent.append(a);
        }



        }
    }
