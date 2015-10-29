package group7.travelomania;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by Matt on 10/27/15.
 *
 * Parity with the design document.
 *
 * Create using singleton method.
 *
 */
public class Player {


    public static volatile Player player;


    private String name;

    public String userName;
    public String password;
    public String confirmPassword;
    public String securityAnswer;

    public Bitmap avatar;

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
    }

    public void register(String n, String uname, String confirmPwd, String securityAns){

    }

    private void login(String uname, String pwd){

    }

    public static void viewRules(){
        Log.v("Player", "View Rules");
    }

    public void playGame(String uname){

    }




}
