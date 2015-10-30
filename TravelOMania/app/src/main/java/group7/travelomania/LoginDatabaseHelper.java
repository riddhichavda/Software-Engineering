package group7.travelomania;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

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

    public ArrayList<Continents> getContinentsCompleted(String userName){
        ArrayList<Continents> ret = new ArrayList<>();
        Cursor cursor = getPlayerRow(userName);
        if(cursor == null)
            return null;
        String all_levels = cursor.getString(cursor.getColumnIndex("level_completed"));
        String[] level_arr = all_levels.split(",");
        for(String s: level_arr){
            ret.add(Continents.valueOf(s));
        }
        cursor.close();
        return ret;
    }

    public Continents getCurrentLevel(String userName){
        Cursor cursor = getPlayerRow(userName);
        if (cursor == null)
            return null;
        Continents c = Continents.valueOf(cursor.getString(cursor.getColumnIndex("current_level")));
        cursor.close();
        return c;
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


    public void  updateEntry(String userName, String password, String fullname, String question, String answer)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD",password);
        updatedValues.put("FULL_NAME", fullname);
        updatedValues.put("SECURITY_QUESTION", question);
        updatedValues.put("SECURITY_ANSWER", answer);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userName});
    }
}