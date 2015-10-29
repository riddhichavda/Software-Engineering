package group7.travelomania;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by Matt on 10/23/15.
 * DataBaseHelper class
 * Used to open, and create our database.
 */
public class DataBaseHelper extends SQLiteOpenHelper{

    //TODO add the database path!

    private String DB_PATH;

    private static String DB_NAME = "travel_db.sqlite";

    private SQLiteDatabase database;

    private final Context context;


    public DataBaseHelper(Context con){
        super(con, DB_NAME, null, 1);
        this.context = con;
        DB_PATH = context.getDatabasePath(DB_NAME).toString();

    }

    public void createDataBase() throws IOException{
        boolean dbExist = checkDataBase();
        if(!dbExist){
            this.getReadableDatabase();
            try{
                copyDataBase();
            }
            catch (IOException e){
                throw new Error("Error Copying Database");
            }
        }
    }



    public boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String path = DB_PATH;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e){
            //Does not exist;
            Log.v("Database Check", "Need to create the database.");
        }

        if(checkDB != null){
             checkDB.close();
        }

        return checkDB!=null;
    }

    public void copyDataBase() throws IOException{
        Log.v("Copy database", "Starting database copy");
        InputStream input = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH;
        Log.v("copy database path", DB_PATH);
        Log.v("copy database name", DB_NAME);
        OutputStream output = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while((length = input.read(buffer)) > 0){
            output.write(buffer,0,length);
        }

        output.flush();
        output.close();
        input.close();
        Log.v("Copy database", "Finished database copy");
    }

    public SQLiteDatabase openDataBase() throws SQLException{

        String path = DB_PATH;
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return database;
    }

    @Override
    public synchronized void close(){

        if(database != null){
            database.close();
        }

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db){

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }






}
