package group7.travelomania;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
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
    public int avatarId;
    public ArrayList<Continents> continentsTraveled;
    public Continents currentContinent;

    public int levelAttempts;

    public Category selectedCategory;

    public int numHints;
    public int totalScore;

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
        loginDbHelper.open();
    }


    public void login(String uname){
        loggedIn = true;
        userName = uname;
        continentsTraveled = loginDbHelper.getContinentsCompleted(userName);
        currentContinent = loginDbHelper.getCurrentLevel(userName);
        numHints = loginDbHelper.getNumHints(userName);
        avatarId = loginDbHelper.getAvatar(userName);
        switch(avatarId){
            case 0:
                avatar = BitmapUtility.avatar_AF;
                break;
            case 1:
                avatar = BitmapUtility.avatar_AS;
                break;
            case 2:
                avatar = BitmapUtility.avatar_EU;
                break;
            case 3:
                avatar = BitmapUtility.avatar_OC;
                break;
            case 4:
                avatar = BitmapUtility.avatar_NA;
                break;
            case 5:
                avatar = BitmapUtility.avatar_SA;
                break;
            default:
                avatar = BitmapUtility.avatar_basic;
                break;
        }

        if(currentContinent != null || continentsTraveled != null){
            hasPlayed = true;
        }
    }

    public void saveProgress(){
        loginDbHelper.saveProgress(userName,0,currentContinent == null ? "":currentContinent.toString(),getContinentString(),"", numHints, totalScore, 0, avatarId);
    }

    public static void viewRules(Context context){
        Log.v("Player", "View Rules");

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.rules_popup);
        dialog.setTitle("Rules");
        dialog.show();
    }

    public void newGame(String uname){
        loginDbHelper.resetProgress(uname);
        totalScore = 0;
        currentContinent = null;
        continentsTraveled.clear();
    }

    public String getContinentString(){
        String ret = "";

        for(Continents c: continentsTraveled){
            ret = ret + c.toString() + ",";
        }
        ret = ret.substring(0, ret.length()-1);

        Log.v("continentString", ret);

        return ret;
    }





}
