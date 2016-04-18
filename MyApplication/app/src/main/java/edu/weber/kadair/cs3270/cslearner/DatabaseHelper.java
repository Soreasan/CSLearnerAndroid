package edu.weber.kadair.cs3270.cslearner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by Kenneth on 4/5/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private SQLiteDatabase myDatabase;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        Log.d("test", "Creating DatabaseHelper");
    }

    public SQLiteDatabase open(){
        myDatabase = getWritableDatabase();
        Log.d("test", "SQLiteDatabase open()");
        return myDatabase;
    }

    public void close(){
        if(myDatabase != null) {
            Log.d("test", "Closing the database.");
            myDatabase.close();
        }else{
            Log.d("test", "Failed to close the database.");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d("test", "Creating new database");
        String createQuery = "CREATE TABLE blogs " +
                "(_id integer primary key autoincrement," +
                "entryID TEXT, date TEXT, title TEXT, blogText TEXT)";
        db.execSQL(createQuery);//execute the query to create the database
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.d("test", "On Upgrade");
    }

    public long insertBlog(String entryID, String date, String title, String blogText){
        long rowID = -1;
        ContentValues newBlog = new ContentValues();
        newBlog.put("entryID", entryID);
        newBlog.put("date", date);
        newBlog.put("title", title);
        newBlog.put("blogText", blogText);
        if(open() != null){
            Log.d("test", "INSERT succeeded");
            rowID = myDatabase.insert("blogs", null, newBlog);
        }else{
            Log.d("test", "INSERT failed because the database wasn't open.");
        }

        return rowID;
    }

    public Cursor getOneBlog(long id){
        String[] params = new String[1];
        params[0] = "" + id;
        Cursor cursor = null;
        if(open()!= null){
            cursor = myDatabase.rawQuery("SELECT * FROM blogs WHERE _id = ?", params);
            Log.d("test", "Successfully SELECTED blogs where ID = " + params);
        }
        return cursor;
    }

    public Cursor getAllBlogs(){
        Cursor cursor = null;
        if(open() != null){
            Log.d("test", "SELECT ALL succeeded and returned a cursor");
            cursor = myDatabase.rawQuery("SELECT * FROM blogs", null);
        }else{
            Log.d("test", "SELECT ALL failed because open() failed.");
        }
        return cursor;
    }

    public long updateBlog(long _id, String entryID, String date, String title, String blogText){
        long rowID = -1;
        ContentValues editBlog = new ContentValues();
        editBlog.put("entryID", entryID);
        editBlog.put("date", date);
        editBlog.put("title", title);
        editBlog.put("blogText", blogText);
        if(open() != null){
            Log.d("test", "UPDATE SUCCEEDED");
            rowID = myDatabase.update("blogs", editBlog, "_id=" + _id, null);
            close();
        }else{
            Log.d("test", "UPDATE failed because database wasn't open.");
        }
        return rowID;
    }

    public void deleteBlog(long _id){
        if(open() != null){
            Log.d("test", "DELETE succeeded");
            myDatabase.delete("blogs", "_id=" + _id, null);
            close();
        }else{
            Log.d("test", "DELETE failed because database isn't open.");
        }
    }



    //Everything below this point would work for real data but doesn't match the dummy file.
    /*public long insertCommentorAccount(){
        return 0;
    }

    public long insertComment(){
        return 0;
    }

    public long insertBlogInfo(){
        return 0;
    }

    public long insertBlogContents(){
        return 0;
    }

    public long updateCommentorAccount(){
        return 0;
    }

    public long updateComment(){
        return 0;
    }

    public long updateBlogInfo(){
        return 0;
    }

    public long updateBlogContents(){
        return 0;
    }

    public void deleteCommentorAccount(){

    }

    public void deleteComment(){

    }

    public void deleteBlogInfo(){

    }

    public void deleteBlogContents(){

    }

    public Cursor getAllCommentorAccounts(){
        return null;
    }

    public Cursor getAllComments(){
        return null;
    }

    public Cursor getAllBlogInfo(){
        return null;
    }

    public Cursor getAllBlogContents(){
        return null;
    }

    public Cursor getOneCommentorAccounts(){
        return null;
    }

    public Cursor getOneComments(){
        return null;
    }

    public Cursor getOneBlogInfo(){
        return null;
    }

    public Cursor getOneBlogContents(){
        return null;
    }

*/




}
