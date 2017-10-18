package com.example.shadow.word_list_app;

import android.provider.BaseColumns;

/**
 * Created by hbs on 2015-10-5.
 */
public class Words {
    /*public Words() {
    }*/

    public static abstract class Word implements BaseColumns {
        public static final String TABLE_NAME="words";
        public static final String COLUMN_NAME_WORD="word";//列：单词
        public static final String COLUMN_NAME_MEANING="meaning";//列：单词含义
        public static final String COLUMN_NAME_SAMPLE="sample";//单词示例
    }

    private String key;//单词
    private String ps_B;//英音标
    private String ps_US;//美音标
    private String posAcceptation;//释义
    private String sent;//例句及其翻译

    public Words() {
        this.key = "";
        this.ps_B = "";
        this.ps_US = "";
        this.posAcceptation = "";
        this.sent="";
    }
    
    public void setKey(String key){
        this.key=key;
    }
    public String getKey(){
        return key;
    }
    public void setPs_B(String ps_B){
        this.ps_B=ps_B;
    }
    public String getPs_B(){
        return ps_B;
    }
    public void setPosAcceptation(String posAcceptation){
        this.posAcceptation=posAcceptation;
    }
    public String getPosAcceptation(){
        return posAcceptation;
    }
    public void setPs_US(String ps_US){this.ps_US=ps_US; }
    public String getPs_US(){return ps_US;}
    public void setSent(String sent){this.sent=sent;}
    public String getSent(){return sent;}
}
