package com.jack.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by idea_jack on 2017/11/27.
 */

public class IdeaDatebaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "create table book (" +
            "id integer primary key autoincrement, " +
            "name text, " +
            "author text, " +
            "price real, " +
            "pages integer)";
    public static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement,"
            + "category_name text,"
            + "category_code)";
    private Context mContext;

    public IdeaDatebaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);//create table
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "create table", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(CREATE_BOOK);
            case 2:
                db.execSQL(CREATE_CATEGORY);
        }

        Toast.makeText(mContext, "upgrade db", Toast.LENGTH_SHORT).show();
    }
}
