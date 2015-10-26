package group7.travelomania;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;

public class ContinentSelectionActivity extends AppCompatActivity {

    Continents selectedContinent;


    private ImageView map;
    private ImageView avatar;

    boolean newGame;

    private Bitmap bitmapMap;

    private int mapHeight, mapWidth;
    private float mapX, mapY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent_selection);

        selectedContinent = null;

        Intent intent = getIntent();
        intent.getBooleanExtra("newGame", true);

        map = (ImageView) findViewById(R.id.imageView_map);
        avatar = (ImageView) findViewById(R.id.imageView_avatar);

        final Button btn_Next = (Button)findViewById(R.id.btn_Next);

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity(selectedContinent);
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
                mapHeight = (int) Math.floor(map.getWidth() * 0.61087511d);
                mapWidth = map.getWidth();
                mapX = map.getX();
                mapY = map.getY() + (int) Math.ceil((map.getHeight() - mapHeight) / 2);
                Log.v("Map W, H, X, Y", Integer.toString(mapWidth) + " " +
                        Integer.toString(mapHeight) + " " +
                        Float.toString(mapX) + " " +
                        Float.toString(mapY));

                //Grab bitmap from map ImageView.
                if(Build.VERSION.SDK_INT >= 21)
                    bitmapMap = Bitmap.createBitmap(((BitmapDrawable)getDrawable(R.drawable.map_colors)).getBitmap());
                else
                    bitmapMap = Bitmap.createBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.map_colors)).getBitmap());

                Log.v("Map W, H, X, Y", Integer.toString(bitmapMap.getWidth()) + " " +
                        Integer.toString((int) Math.floor(bitmapMap.getHeight())));

            }
        });

        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int touchX = (int) event.getX();
                int touchY = (int) event.getY();

                //TODO Find Continent
                //Find the clicked continent by observing the event, and extrapolating the color
                //of which the event clicked.
                //The event gives where we touch in the whole screen(because

                Log.v("Touch X, Y", Integer.toString(touchX) +" " + Integer.toString(touchY));

                //Continents nextContinent;

                /*
                  Get the true x and y of the pixel in our bitmap for the Map.
                 */

                //TODO create another image to ensure we always touch a color when touching a continent.

                int trueMapTouchX = (int)Math.floor(touchX - mapX);
                int trueMapTouchY = (int)Math.floor(touchY - mapY);


                if(trueMapTouchY >= 0 && trueMapTouchY <= mapHeight && trueMapTouchX >= 0 && trueMapTouchX <= mapWidth){
                    trueMapTouchX = (int)Math.floor((((float)trueMapTouchX)/mapWidth) * bitmapMap.getWidth());
                    trueMapTouchY = (int)Math.floor((((float)trueMapTouchY)/mapHeight) * bitmapMap.getHeight());
                }
                else{
                    trueMapTouchX = 0;
                    trueMapTouchY = 0;
                }

                //TODO Check if antarctica!




                int pixel = bitmapMap.getPixel(trueMapTouchX, trueMapTouchY);

                Log.v("Continent Color", Integer.toString(pixel) + " " + Integer.toString(Color.red(pixel)) + " " + Integer.toString(Color.green(pixel)) + " " + Integer.toString(Color.blue(pixel)));
                Log.v("True Touch Position", Integer.toString(trueMapTouchX) + " " + Integer.toString(trueMapTouchY));
                //goToNextActivity(nextContinent);

                //TODO check which continent with corresponding color.

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
                    //Australia
                    case -4177792:
                        selectedContinent = Continents.Australia;
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
                    case 16744448:
                        selectedContinent = Continents.SouthAmerica;
                        break;
                    default:
                        break;
                }



                return false;
            }
        });
    }


    private void goToNextActivity(Continents nextContinent){
        if(nextContinent != null) {
            Intent intent = new Intent(this, NavigationActivity.class);
            int continent;
            switch (nextContinent) {
                case Africa:
                    continent = 0;
                    break;
                case Antarctica:
                    continent = 1;
                    break;
                case Asia:
                    continent = 2;
                    break;
                case Australia:
                    continent = 3;
                    break;
                case Europe:
                    continent = 4;
                    break;
                case NorthAmerica:
                    continent = 5;
                    break;
                case SouthAmerica:
                    continent = 6;
                    break;
                default:
                    continent = -1;
                    break;
            }

            intent.putExtra("Continent", continent);
            startActivity(intent);
        }
    }

}
