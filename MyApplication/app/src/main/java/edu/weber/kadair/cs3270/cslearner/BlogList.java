package edu.weber.kadair.cs3270.cslearner;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlogList extends ListFragment {
    protected String[] columns = new String[]{"one", "two", "three"};
    protected String[] digits = new String[]{"1", "2", "3"};
    protected Cursor blogCursor;

    public BlogList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("test", "creating blog list.");
        DatabaseHelper dbhelp = new DatabaseHelper(getActivity(), "blogs", null, 1);
        Cursor cursor = dbhelp.getAllBlogs();
        columns = new String[]{"title"};
        int[] views = new int[]{android.R.id.text1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, cursor, columns, views, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        this.setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Log.d("Test", "ListView position:" + position + "-d" + id);
        MainActivity ma = (MainActivity) getActivity();
        //ma.populateCourse(id);
    }

}
