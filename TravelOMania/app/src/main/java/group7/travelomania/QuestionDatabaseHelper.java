package group7.travelomania;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


public class QuestionDatabaseHelper {
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    public  QuestionDatabaseHelper(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context);
    }
    public QuestionDatabaseHelper open() throws SQLException
    {
        try {
            db = dbHelper.openDataBase();
        }
        catch (java.sql.SQLException e){
            Log.v("Database Check", e.getMessage());
        }
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }


    public ArrayList<Question> getQuestions(String continent, String difficulty, String category)
    {
        ArrayList<Question> ret = new ArrayList<>();
        Cursor cursor=db.query("QUESTIONS_DB", null, "CONTINENT=? AND \"DIFFICULTY LEVEL\"=? AND CATEGORY=?", new String[]{continent, difficulty, category}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return null;
        }
        while(cursor.moveToNext()){
            String wrong_ans[] = new String[3];
            String question = cursor.getString(cursor.getColumnIndex("question"));
            String ans = cursor.getString(cursor.getColumnIndex("correct_ans"));
            for(int i = 1; i<=3; i++){
                wrong_ans[i-1] = cursor.getString(cursor.getColumnIndex("ans"+i));
            }
            ret.add(new Question(question, ans, wrong_ans));
        }
        cursor.close();
        return ret;
    }

}
