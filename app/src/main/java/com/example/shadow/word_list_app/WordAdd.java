package com.example.shadow.word_list_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shadow on 2017/10/16.
 */

public class WordAdd {

    public SQLiteDatabase db;
    //public final String TABLE_WORDS = "Words"

    public WordAdd(Context context) {
        WordsDBHelper helper = new WordsDBHelper(context, "Words.db", null, 1);
        db = helper.getWritableDatabase();
    }


    public void wordSave(Words word) {
        //判断是否是有效对象，即有数据
        ContentValues values = new ContentValues();
        values.put("word", word.getKey());
        //values.put("ps_B", word.getPs_B());
        values.put("meaning", word.getPosAcceptation());
        db.insert(Words.Word.TABLE_NAME, null, values);
        System.out.println("**保存到数据库成功**");
        values.clear();
        // return true;
    }
}
