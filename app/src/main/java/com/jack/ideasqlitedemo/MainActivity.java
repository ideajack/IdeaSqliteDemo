package com.jack.ideasqlitedemo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jack.sqlite.IdeaDatebaseHelper;

/**
 * Sqlite数据库的基本操作
 * （经过很长时间的android开发，会发现android开发人员（包括自己）只是会一些android技术，很少了解java
 * 后端的技术的话，对于自己的后续发展是很不利的）
 * 数据库技术在后端开发中很重要，在android开发中也占有很重要的角色
 * 因此，一定要掌握，并熟练运用。
 * （sp和文件只适用于保存一些简单的数据和键值对）
 * （数据库可以用来保存 数据量大，结构性复杂的数据）
 */
public class MainActivity extends Activity {

    private IdeaDatebaseHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mDbHelper = new IdeaDatebaseHelper(this, "BookStore.db", null, 2);
        findViewById(R.id.create_table).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create database
                mDbHelper.getWritableDatabase();
            }
        });

        findViewById(R.id.update_db).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update db
                //onUpgrade() 中操作
            }
        });

        findViewById(R.id.insert_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert data
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", "葵花宝典");
                values.put("author", "瓜瓜");
                values.put("price", 5.5);
                values.put("pages", 108);
                db.insert("Book", null, values);

                values.clear();
                values.put("name", "辟邪剑谱");
                values.put("author", "瓜瓜");
                values.put("price", 6.5);
                values.put("pages", 123);
                db.insert("Book", null, values);

                //使用sql语句
                db.execSQL("insert into Book(name, author, price, pages) values(?, ?, ?, ?)",
                        new String[] { "野蛮女友", "瓜瓜", "99.9", "520"});
                db.execSQL("insert into Book(name, author, price, pages) values(?, ?, ?, ?)",
                        new String[] {"多行动，少计划", "大狗子", "1.2", "66"});
                Toast.makeText(MainActivity.this, "insert data", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.update_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update data
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 5.0);
                db.update("Book", values, "name = ?", new String[] {"葵花宝典"});

                db.execSQL("update Book set price = ? where name = ?",
                        new String[] {"99", "多行动，少计划"});

            }
        });

        findViewById(R.id.delete_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete data
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                db.delete("Book", "pages > ?", new String[] {"120"});

                db.execSQL("delete from Book where author = ?",
                        new String[] {"瓜"});
            }
        });

        findViewById(R.id.query_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //query data
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                Cursor book = db.query("Book", null, null, null, null, null, null);
//                Cursor cursor = db.rawQuery("select * from Book", null);
                if (book.moveToFirst()) {
                    do {
                        String name = book.getString(book.getColumnIndex("name"));
                        String author = book.getString(book.getColumnIndex("author"));
                        double price = book.getDouble(book.getColumnIndex("price"));
                        int pages = book.getInt(book.getColumnIndex("pages"));
                        Log.i("zxj", "name-" + name);
                        Log.i("zxj", "author-" + author);
                        Log.i("zxj", "price-" + price);
                        Log.i("zxj", "pages-" + pages);
                    } while (book.moveToNext());
                }
                book.close();

            }
        });

        findViewById(R.id.transaction_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                db.beginTransaction();
                try {
                    db.delete("Book", null, null);
                    /*if (true) {
                        throw new NullPointerException();
                    }*/
                    db.execSQL("insert into Book(name, author, price, pages) values(?, ?, ?, ?)",
                            new String[] {"二狗是怎么炼成的", "二狗", "56.5", "101"});
                    db.setTransactionSuccessful();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                }

            }
        });

    }
}
