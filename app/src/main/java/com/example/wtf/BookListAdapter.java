package com.example.wtf;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookListAdapter extends ArrayAdapter<Book> {

    private LayoutInflater inflater;
    private int layout;
    private List<Book> books;

    public BookListAdapter(Context context, int res, List<Book> books){
        super(context, res, books);
        this.books = books;
        this.layout = res;
        this.inflater = LayoutInflater.from(context);
    }


    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Book book = books.get(position);

        viewHolder.nameView.setText(book.getName());
        viewHolder.authorView.setText(book.getAuthor());


        return convertView;
    }



    private class ViewHolder {
        final TextView nameView, authorView;
        ViewHolder(View view){
            nameView = view.findViewById(R.id.nameView);
            authorView = view.findViewById(R.id.authorView);
        }
    }
}
