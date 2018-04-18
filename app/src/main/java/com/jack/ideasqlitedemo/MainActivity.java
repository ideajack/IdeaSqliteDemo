package com.jack.ideasqlitedemo;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

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

    }
}
