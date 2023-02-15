package com.example.wtf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bookstore.db";
    private static final int SCHEMA = 6;
    static final String TABLE = "books";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "bookName";
    public static final String COLUMN_AUTHOR = "bookAuthor";
    public static final String COLUMN_RATING = "bookRating";


    public DbHelper (Context context){
        super(context, DB_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_AUTHOR + " TEXT, " + COLUMN_RATING + " REAL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }
}
