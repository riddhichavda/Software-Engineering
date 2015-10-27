package group7.travelomania;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDatabaseHelper
{
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text); ";
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
        db = dbHelper.getWritableDatabase();
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

    public void insertEntry(String userName, String password, String fullname, String question, String answer)
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
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String UserName)
    {
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("PLAYER", where, new String[]{UserName}) ;

        return numberOFEntriesDeleted;
    }
    public String getSinlgeEntry(String userName)
    {
        Cursor cursor=db.query("PLAYER", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
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