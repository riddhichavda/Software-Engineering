package group7.travelomania;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginDatabaseHelper
{
    // Variable to hold the database instance
    public  SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;

    public  LoginDatabaseHelper(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context);
    }
    public LoginDatabaseHelper open() throws SQLException
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

    public void addPlayer(String userName, String password, String fullname, String question, String answer)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD",password);
        newValues.put("FULL_NAME", fullname);
        newValues.put("SECURITY_QUESTION", question);
        newValues.put("SECURITY_ANSWER", answer);
        newValues.put("LEVEL_ATTEMPTS",0);
        newValues.put("CURRENT_LEVEL", "");
        newValues.put("LEVELS_COMPLETED", "");
        newValues.put("CURRENT_CATEGORY", "");
        newValues.put("AVATAR", -1);
        newValues.put("NUM_HINTS", 1);
        newValues.put("TOTAL_SCORE", 0);
        newValues.put("LEVEL_SCORE", 0);


        // Insert the row into your table
        db.insert("PLAYER", null, newValues);

    }

    public String getPlayerPassword(String userName)
    {
        Cursor cursor=getPlayerRow(userName);
        if(cursor == null)
            return "NOT EXIST";
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("password"));
        cursor.close();
        return password;
    }

    public String getPlayerQuestion(String userName)
    {
        Cursor cursor=getPlayerRow(userName);
        if(cursor == null)
            return "NOT EXIST";
        cursor.moveToFirst();
        String question= cursor.getString(cursor.getColumnIndex("security_question"));
        cursor.close();
        return question;
    }

    public String getPlayerAnswer(String userName)
    {
        Cursor cursor=getPlayerRow(userName);
        if(cursor == null)
            return "NOT EXIST";
        cursor.moveToFirst();
        String answer= cursor.getString(cursor.getColumnIndex("security_answer"));
        cursor.close();
        return answer;
    }

    public int getPlayerId(String userName)
    {
        Cursor cursor = getPlayerRow(userName);
        if(cursor == null)
            return -1;
        cursor.moveToFirst();
        int id= cursor.getInt(cursor.getColumnIndex("_id"));
        cursor.close();
        return id;
    }

    public ArrayList<Continents> getContinentsCompleted(String userName, HashMap<Continents, String> landmarksAquired){
        ArrayList<Continents> ret = new ArrayList<>();
        Cursor cursor = getPlayerRow(userName);
        if(cursor == null)
            return ret;
        cursor.moveToNext();
        String all_levels;
        all_levels = cursor.getString(cursor.getColumnIndex("levels_completed"));
        String[] level_arr = all_levels.split(",");
        for (String s : level_arr) {
            try {
                String t[] = s.split(":");
                if(t.length == 2) {
                    Log.v("Login", t[0] + " " + t[1]);
                    ret.add(Continents.valueOf(t[0]));
                    if (t[1].length() > 0) {
                        landmarksAquired.put(Continents.valueOf(t[0]), t[1]);
                    }
                }
                else {
                    Log.v("Login", t[0]);
                    ret.add(Continents.valueOf(t[0]));
                }
            }
            catch (Exception e){
                Log.e("Exception", e.toString());
            }
        }
        cursor.close();
        return ret;
    }


    public Continents getCurrentLevel(String userName){
        Cursor cursor = getPlayerRow(userName);
        if (cursor == null)
            return null;
        Log.v("index", Integer.toString(cursor.getColumnIndex("current_level")) + " " + Integer.toString(cursor.getColumnIndex("current_level")));

        Continents c;
        cursor.moveToNext();
        String cc = "RED";
        Log.v("cc", cc + " " + cursor.getString(cursor.getColumnIndex("current_level")));
        try{
            c = Continents.valueOf(cursor.getString(cursor.getColumnIndex("current_level")));
        }
        catch (Exception e){
            Log.v("Exception", e.toString());
            c = null;
        }

        Log.v("cc", "Obama");
        Log.v("cc", cc);
        //c = Continents.valueOf(cc);
        cursor.close();
        return c;
    }

    public int getNumHints(String userName){
        Cursor cursor = getPlayerRow(userName);
        if (cursor == null){
            return 0;
        }
        int numHints = 0;
        cursor.moveToNext();
        if(cursor.getColumnIndex("num_hints")>=0)
            numHints = cursor.getInt(cursor.getColumnIndex("num_hints"));
        cursor.close();
        return numHints;
    }

    public int getAvatar(String userName){
        Cursor cursor = getPlayerRow(userName);
        if(cursor == null){
            return -1;
        }

        cursor.moveToNext();
        return cursor.getInt(cursor.getColumnIndex("avatar"));



    }


    private Cursor getPlayerRow(String userName){
        Cursor cursor=db.query("PLAYER", null, "USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return null;
        }
        return cursor;
    }


    public void resetProgress(String userName)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("LEVEL_ATTEMPTS",0);
        updatedValues.put("CURRENT_LEVEL", "");
        updatedValues.put("LEVELS_COMPLETED", "");
        updatedValues.put("CURRENT_CATEGORY", "");
        updatedValues.put("NUM_HINTS", 1);
        updatedValues.put("TOTAL_SCORE", 0);
        updatedValues.put("LEVEL_SCORE", 0);


        String where="USERNAME = ?";
        db.update("PLAYER",updatedValues, where, new String[]{userName});
    }

    public void saveProgress(String userName, int level_attempts, String current_level, String levels_completed, String current_category, int num_hints, int total_score, int level_score, int avatar)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("LEVEL_ATTEMPTS",level_attempts);
        updatedValues.put("CURRENT_LEVEL", current_level);
        updatedValues.put("LEVELS_COMPLETED", levels_completed);
        updatedValues.put("CURRENT_CATEGORY", current_category);
        updatedValues.put("NUM_HINTS", num_hints);
        updatedValues.put("TOTAL_SCORE", total_score);
        updatedValues.put("LEVEL_SCORE", level_score);
        updatedValues.put("AVATAR", avatar);


        String where="USERNAME = ?";
        db.update("PLAYER",updatedValues, where, new String[]{userName});
    }


}