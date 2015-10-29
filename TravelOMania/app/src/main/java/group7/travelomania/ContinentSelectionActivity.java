package group7.travelomania;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;


//TODO Load bitmaps more efficiently.

public class ContinentSelectionActivity extends AppCompatActivity {

    Continents selectedContinent;
    Continents currentContinent;

    private Player player;
    private Admin admin;


    private ImageView map;
    private ImageView avatar;

    private boolean isNewGame;

    private int mapHeight, mapWidth;
    private float mapX, mapY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent_selection);
        Log.v("Start", "ContinentSelectionActivity");

        player = Player.getInstance(this);
        admin = Admin.getInstance(this);

        //Initialize bitmaps and useful bitmap information.
        Log.i("Start", "Bitmap.initialize");
        BitmapUtility.initialize(this);
        Log.i("End", "Bitmap.initialize");

        if(admin.continentsTraveled.size() > 0) {
            Log.v("Game","Continue Game");
            currentContinent = admin.continentsTraveled.get(0);
            isNewGame = false;
        }
        else {
            isNewGame = true;
        }

        selectedContinent = null;


        //Initialize views.
        map = (ImageView) findViewById(R.id.imageView_map);
        avatar = (ImageView) findViewById(R.id.imageView_avatar);
        avatar.setImageBitmap(player.avatar);

        final Button btn_Next = (Button)findViewById(R.id.btn_Next);
        final Button btn_Help = (Button)findViewById(R.id.btn_help);

        map.setImageBitmap(BitmapUtility.map);

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {goToNextActivity(selectedContinent);
            }
        });
        btn_Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player.viewRules();
            }
        });

        map.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //Remove it here unless you want to get this callback for EVERY
                //layout pass, which can get you into infinite loops if you ever
                //modify the layout from within this method.
                if (Build.VERSION.SDK_INT >= 16)
                    map.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    map.getViewTreeObserver().removeGlobalOnLayoutListener(this);


                mapHeight = (int) Math.round(map.getWidth() * 0.61087511d);
                mapWidth = map.getWidth();
                mapX = map.getX();
                mapY = map.getY() + Math.round((map.getHeight() - mapHeight) / 2);
                Log.v("Map W, H, X, Y", Integer.toString(mapWidth) + " " +
                        Integer.toString(mapHeight) + " " +
                        Float.toString(mapX) + " " +
                        Float.toString(mapY));

                Log.v("Map W, H, X, Y", Integer.toString(BitmapUtility.mapKey.getWidth()) + " " +
                        Integer.toString((int) Math.floor(BitmapUtility.mapKey.getHeight())));

            }
        });

        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Get touch event position.
                int touchX = (int) event.getX();
                int touchY = (int) event.getY();
                Log.v("Touch X, Y", Integer.toString(touchX) +" " + Integer.toString(touchY));

                /*
                  Get the true x and y of the pixel in our bitmap for the Map.
                 */

                int trueMapTouchX = (int)Math.floor(touchX - mapX);
                int trueMapTouchY = (int)Math.floor(touchY - mapY);
                int pixel = 0;

                if(trueMapTouchY >= 0 && trueMapTouchY <= mapHeight && trueMapTouchX >= 0 && trueMapTouchX <= mapWidth){
                    trueMapTouchX = (int)Math.floor((((float) trueMapTouchX) / mapWidth) * BitmapUtility.mapKey.getWidth());
                    trueMapTouchY = (int)Math.floor((((float) trueMapTouchY) / mapHeight) * BitmapUtility.mapKey.getHeight());
                    pixel = BitmapUtility.mapKey.getPixel(trueMapTouchX, trueMapTouchY);
                }
                else{
                    trueMapTouchX = 0;
                    trueMapTouchY = 0;
                }

                Log.v("Continent Color", Integer.toString(pixel) + " " + Integer.toString(Color.red(pixel)) + " " + Integer.toString(Color.green(pixel)) + " " + Integer.toString(Color.blue(pixel)));
                Log.v("True Touch Position", Integer.toString(trueMapTouchX) + " " + Integer.toString(trueMapTouchY));

                //Check which continent with corresponding color.
                switch(pixel){
                    //Africa
                    case -76498:
                        selectedContinent = Continents.Africa;
                        break;
                    //Antarctica
                    case -16760577:
                        selectedContinent = Continents.Antarctica;
                        break;
                    //Asia
                    case -836095:
                        selectedContinent = Continents.Asia;
                        break;
                    //Oceania
                    case -4177792:
                        selectedContinent = Continents.Oceania;
                        break;
                    //Europe
                    case -4128768:
                        selectedContinent = Continents.Europe;
                        break;
                    //NorthAmerica
                    case -16724992:
                        selectedContinent = Continents.NorthAmerica;
                        break;
                    //SouthAmerica
                    case -16744448:
                        selectedContinent = Continents.SouthAmerica;
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        avatar.setImageBitmap(player.avatar);
        if(admin.continentsTraveled.size() > 0) {
            Log.v("Game","Continue Game");
            currentContinent = admin.continentsTraveled.get(0);
            isNewGame = false;
        }
        else {
            isNewGame = true;
        }
    }

    private void goToNextActivity(Continents nextContinent){
        if(nextContinent != null && !admin.continentsTraveled.contains(nextContinent)) {
            admin.continentsTraveled.add(0,nextContinent);
            Intent intent;
            if(isNewGame){
                intent = new Intent(this, AvatarSelectionActivity.class);
            }
            else{
                intent = new Intent(this, NavigationActivity.class);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }




}
