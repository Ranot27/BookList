package com.example.wtf;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity   {

    Button addBtn;
    ListView mainList;
    DbHelper dbHelper;
    Cursor bookCursor;
    EditText search;
    BookListAdapter bookListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = findViewById(R.id.addButton);
        mainList = findViewById(R.id.mainList);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BookActivity.class);
                startActivity(intent);
            }
        });

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book = bookListAdapter.getItem(i);
                if (book != null) {
                    Intent intent = new Intent(getApplicationContext(), BookActivity.class);
                    intent.putExtra("id", book.getId());
                    startActivity(intent);
                }
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();

        DbAdapter adapter = new DbAdapter(this);
        adapter.open();
        List<Book> books = adapter.getBooks();

        bookListAdapter = new BookListAdapter(this, R.layout.book_list_item, books);

        mainList.setAdapter(bookListAdapter);

        adapter.close();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
        bookCursor.close();
    }

    public void goBookActivity(Book book){
        Intent intent = new Intent(getApplicationContext(), BookActivity.class);
        intent.putExtra("id", book.getId());
        startActivity(intent);
    }
}