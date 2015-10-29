package group7.travelomania;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Matt on 10/27/15.
 *
 * Parity with design document
 *
 * Create using singleton method.
 *
 */
public class Admin {


    public static volatile Admin admin;


    public int level;
    public int level_completed;
    private int login_attempts;
    private int level_attempts;

    //Some way to see which continents the player has gone to.
    public ArrayList<Continents> continentsTraveled;


    public static Admin getInstance(Context context){
        if(admin == null){
            synchronized (Admin.class){
                admin = new Admin(context);
            }
        }
        return admin;
    }

    private Admin(Context context){
        continentsTraveled = new ArrayList<>(7);
        level = 0;
    }


    public void storeRegisteredDetails(){



    }

    public boolean verifyUser(String uname, String pwd){


        return true;
    }

    public void startGame(String uname){


    }

    public void checkSessionTimeout(){

    }

    public void storeLevelSummary(int level, int level_score, int hints){


    }

}
