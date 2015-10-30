package group7.travelomania;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Matt on 10/27/15.
 *
 * Parity with the design document.
 *
 * Create using singleton method.
 *
 */
public class Player {


    private static volatile Player player;


    public String userName;
    public boolean hasPlayed;
    public Bitmap avatar;
    public ArrayList<Continents> continentsTraveled;
    public Continents currentContinent;

    public boolean loggedIn;




    private LoginDatabaseHelper loginDbHelper;

    public static Player getInstance(Context context){

        if(player == null){
            synchronized (Player.class){
                if(player == null){
                    player = new Player(context);
                }
            }
        }
        return player;

    }

    private Player(Context context){
        if(BitmapUtility.avatar_basic == null){
            BitmapUtility.avatar_basic = BitmapUtility.decodeSampledBitmapFromResource(context.getResources(),
                    R.drawable.basic_avatar,
                    64,
                    64);
        }
        avatar = BitmapUtility.avatar_basic;
        loggedIn = false;
        hasPlayed = false;
        loginDbHelper = new LoginDatabaseHelper(context);
    }


    public void login(String uname){
        loggedIn = true;
        userName = uname;
        continentsTraveled = loginDbHelper.getContinentsCompleted(userName);
        currentContinent = loginDbHelper.getCurrentLevel(userName);

        if(currentContinent != null || continentsTraveled != null){
            hasPlayed = true;
        }

    }

    public static void viewRules(Context context){
        Log.v("Player", "View Rules");

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.rules_popup);
        dialog.setTitle("Rules");
        dialog.show();
    }

    public void playGame(String uname){

    }




}
