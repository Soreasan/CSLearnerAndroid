package edu.weber.kadair.cs3270.cslearner;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlogInsert extends Fragment {
    protected EditText date;
    protected EditText id;
    protected EditText title;
    protected EditText entry;
    protected View myView;
    protected Button save;


    public BlogInsert() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_blog_insert, container, false);
        // Inflate the layout for this fragment
        title = (EditText) myView.findViewById(R.id.edtInsertTitle);
        entry = (EditText) myView.findViewById(R.id.edtInsertBlogEntry);
        date = (EditText) myView.findViewById(R.id.edtInsertDate);
        id = (EditText) myView.findViewById(R.id.edtInsertEntryID);
        save = (Button) myView.findViewById(R.id.btInsertSaveEntry);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatabaseHelper dbHelper = new DatabaseHelper(getActivity(), "blogs", null, 1);
                //insertBlog(String entryID, String date, String title, String blogText)
                String thisID = String.valueOf(id.getText());
                String thisDate = String.valueOf(date.getText());
                String thisTitle = String.valueOf(title.getText());
                String thisText = String.valueOf(entry.getText());
                Log.d("test", "thisID " + thisID);
                Log.d("test", "thisDate" + thisDate);
                Log.d("test", "thisTitle" + thisTitle);
                Log.d("test", "thisText" + thisText);
                long rowID = dbHelper.insertBlog(thisID, thisDate, thisTitle, thisText);
                Log.d("test", String.valueOf(rowID));
                //MainActivity ma = (MainActivity) getActivity();
                //ma.reloadCourseList();
            }
        });

        return myView;
    }

}
