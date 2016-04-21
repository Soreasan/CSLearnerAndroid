package edu.weber.kadair.cs3270.cslearner;

/**
 * Created by Kenneth on 4/5/2016.
 */
public class CSLearnerObjects {

    //This object matches the dummy file
    //http://cslearner.com/blogEntries.json
    protected static class blogEntries{
        protected int blogEntryID;
        protected String blogDate;
        protected String blogTitle;
        protected String blogText;

        public blogEntries(int entryID, String date, String title, String text){
            this.blogEntryID = entryID;
            this.blogDate = date;
            this.blogTitle = title;
            this.blogText = text;
        }
    }

    //THE OBJECTS BELOW MATCH THE *-*-*REAL*-*-* DATABASE BUT DON'T MATCH THE DUMMY FILE.
    protected class CommentorAccounts{
        protected int commentorID;
        protected String username;
        protected String email;
        protected String password;
    }

    protected class Comments{
        protected int commentID;
        protected int commentorID;
        protected int blogEntryID;
        protected String comment;
        protected String date;
    }

    protected class BlogInfo{
        protected int blogEntryID;
        protected String title;
        protected String date;
    }

    protected class BlogContents{
        protected int blogContentID;
        protected int blogEntryID;
        protected String blogText;
    }
}
