package group7.travelomania;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

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
    //public static volatile Context currentContext;
    public static volatile Activity currentActivity;


    public String userName;
    public boolean hasPlayed;
    public Bitmap avatar;
    public int avatarId;
    public ArrayList<Continents> continentsTraveled;
    public Continents currentContinent;

    public HashMap<Continents, String> landmarksAquired;

    public int levelAttempts;

    public Category selectedCategory;
    public ArrayList<Category> CategorySelected;
    public int categoryCount;
    
    public int numHints;
    public int totalScore;

    public boolean loggedIn;

    private LoginDatabaseHelper loginDbHelper;

    private CountDownTimer logoutTimer;

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
        selectedCategory = null;
        categoryCount = 0;
        selectedCategory = null;
        CategorySelected = new ArrayList<>();
    }



    public void resetLogoutTimer(){
        logoutTimer.cancel();
        logoutTimer.start();
    }

    public void stopTimer(){
        logoutTimer.cancel();
    }

    public void destroyTimer(){
        logoutTimer = null;
    }

    public boolean isTimerNull(){
        return logoutTimer == null;
    }

    private static void logout(){
        if(player != null) {
            player.saveProgress();
            player.loggedIn = false;
        }
        if(currentActivity.hasWindowFocus()) {
            Intent intent = new Intent(currentActivity, HomeScreen.class);
            currentActivity.startActivity(intent);
        }
        player.stopTimer();
        player.destroyTimer();
        player = null;
        currentActivity = null;
    }



    public void login(String uname){
        loggedIn = true;
        userName = uname;
        landmarksAquired = new HashMap<>();
        continentsTraveled = loginDbHelper.getContinentsCompleted(userName,landmarksAquired);

        //Log.v("Continents Traveled Check", "" + continentsTraveled.size());
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

        if(continentsTraveled.size() != 0){
            hasPlayed = true;
        }

        logoutTimer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Time until logout", "" + millisUntilFinished);
            }
            @Override
            public void onFinish() {
                if(player!=null)
                    player.logout();
            }
        };

        logoutTimer.start();
    }

    public void saveProgress(){
        loginDbHelper.saveProgress(userName, 0, currentContinent == null ? "" : currentContinent.toString(), getContinentString(), "", numHints, totalScore, 0, avatarId);
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
        landmarksAquired.clear();
    }

    public String getContinentString(){
        String ret = "";

        for(Continents c: continentsTraveled){
            ret = ret + c.toString() + ":";
            if(landmarksAquired.containsKey(c)){
                ret = ret + landmarksAquired.get(c).toString() + ",";
            }
            else{
                ret = ret + ",";
            }
        }
        if(ret.length() > 0)
            ret = ret.substring(0, ret.length()-1);

        Log.v("continentString", ret);

        return ret;
    }

}
