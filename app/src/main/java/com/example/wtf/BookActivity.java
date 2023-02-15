package com.example.wtf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


public class BookActivity extends AppCompatActivity {

    EditText inputName, inputAuthor;
    Button saveButton, deleteButton;
    ImageView imageView;
    RatingBar ratingBar;

    Bitmap bitmap = null;
    DbHelper dbHelper;
    DbAdapter adapter;
    SQLiteDatabase db;
    Cursor cursor;
    long bookId = 0;

    String filePath = null;
    Uri selectedImage;


    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);





        imageView = findViewById(R.id.image);
        inputAuthor = findViewById(R.id.inputAuthor);
        inputName = findViewById(R.id.inputName);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
        ratingBar = findViewById(R.id.ratingBar);

        adapter = new DbAdapter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bookId = extras.getInt("id");
        }

        if (bookId > 0) {
            adapter.open();
            Book book = adapter.getBook(bookId);
            inputName.setText(book.getName());
            inputAuthor.setText(book.getAuthor());
            ratingBar.setRating((float)book.getRating());



            adapter.close();
        }else{
            deleteButton.setVisibility(View.GONE);
        }



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String author = inputAuthor.getText().toString();
                double rating = ratingBar.getRating();


                if (author.length() > 0 || name.length() > 0) {
                    Book book = new Book((int) bookId, name, author, rating);

                    adapter.open();
                    if (bookId > 0) {
                        adapter.update(book);
                    } else {
                        adapter.insert(book);
                    }
                    adapter.close();
                }
                Intent intent = new Intent(BookActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.open();
                adapter.delete(bookId);
                adapter.close();
                Intent intent = new Intent(BookActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });






    }


}