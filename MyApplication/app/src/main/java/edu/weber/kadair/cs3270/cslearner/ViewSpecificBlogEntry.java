package edu.weber.kadair.cs3270.cslearner;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewSpecificBlogEntry extends Fragment {
    protected View myView;
    protected TextView title;
    protected TextView date;
    protected TextView text;

    public ViewSpecificBlogEntry() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_view_specific_blog_entry, container, false);
        title = (TextView) myView.findViewById(R.id.txvViewSpecificBlogEntryTitle);
        date = (TextView) myView.findViewById(R.id.txvViewSpecificBlogEntryDate);
        text = (TextView) myView.findViewById(R.id.txvViewSpecificBlogEntryText);

        return myView;
    }

    protected void updateText(long dbID){
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity(),"blogs", null, 1);
        Cursor cursor = dbHelper.getOneBlog(dbID);
        cursor.moveToFirst();
        String myTitle = cursor.getString(cursor.getColumnIndex("title"));
        String myDate = cursor.getString(cursor.getColumnIndex("date"));
        String myText= cursor.getString(cursor.getColumnIndex("blogText"));
        title.setText(myTitle);
        date.setText(myDate);
        text.setText(myText);

    }

}
