package com.example.wtf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class DbAdapter {

    private DbHelper dbHelper;
    private SQLiteDatabase sqlDb;

    public DbAdapter(Context context){
        dbHelper = new DbHelper(context.getApplicationContext());
    }

    public DbAdapter open(){
        sqlDb = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DbHelper.COLUMN_ID, DbHelper.COLUMN_NAME, DbHelper.COLUMN_AUTHOR, DbHelper.COLUMN_RATING};
        return sqlDb.query(DbHelper.TABLE, columns, null, null, null, null, null, null);
    }

    public List<Book> getBooks(){
        ArrayList<Book> books = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_NAME));
            String author = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_AUTHOR));
            double rating = cursor.getDouble(cursor.getColumnIndex(DbHelper.COLUMN_RATING));
            books.add(new Book(id, name, author, rating));

        }
        cursor.close();
        return books;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(sqlDb, DbHelper.TABLE);
    }

    public Book getBook(long id){
        Book book = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DbHelper.TABLE, DbHelper.COLUMN_ID);
        Cursor cursor = sqlDb.rawQuery(query, new String[]{ String.valueOf(id)});
        if (cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_NAME));
            String author = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_AUTHOR));
            double rating = cursor.getDouble(cursor.getColumnIndex(DbHelper.COLUMN_RATING));
            book = new Book((int)id, name, author, rating);
        }
        cursor.close();
        return book;
    }

    public long insert(Book book){
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.COLUMN_NAME, book.getName());
        cv.put(DbHelper.COLUMN_AUTHOR, book.getAuthor());
        cv.put(DbHelper.COLUMN_RATING, book.getRating());

        return sqlDb.insert(DbHelper.TABLE, null, cv);
    }

    public long delete(long bookId){
        String whereClause = "_id = ?";
        String[] whereAgrs = new String[]{String.valueOf(bookId)};
        return sqlDb.delete(DbHelper.TABLE, whereClause, whereAgrs);
    }

    public long update(Book book){
        String whereClause = DbHelper.COLUMN_ID + "=" + book.getId();
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.COLUMN_NAME, book.getName());
        cv.put(DbHelper.COLUMN_AUTHOR, book.getAuthor());
        cv.put(DbHelper.COLUMN_RATING, book.getRating());
        return sqlDb.update(DbHelper.TABLE, cv, whereClause, null);
    }



}
