package com.example.shadow.word_list_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hbs on 2015-10-5.
 */
public class WordsDBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "words.db";//数据库名字
    private final static int DATABASE_VERSION = 1;//数据库版本

    //建表SQL
    private final static String SQL_CREATE_DATABASE = "CREATE TABLE " + Words.Word.TABLE_NAME + " (" +
            Words.Word._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Words.Word.COLUMN_NAME_WORD + " TEXT" + "," +
            Words.Word.COLUMN_NAME_MEANING + " TEXT" + ","
            + Words.Word.COLUMN_NAME_SAMPLE + " TEXT" + " )";

    //删表SQL
    private final static String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS " + Words.Word.TABLE_NAME;

    public WordsDBHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        db.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //当数据库升级时被调用，首先删除旧表，然后调用OnCreate()创建新表
        db.execSQL(SQL_DELETE_DATABASE);
        onCreate(db);
    }
}
