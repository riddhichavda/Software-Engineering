package group7.travelomania;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.graphics.Bitmap;

public class ContinentSelectionActivity extends AppCompatActivity {


    private ImageView map;
    private ImageView avatar;

    private Bitmap bitmapMap;

    private int mapHeight, mapWidth;
    private float mapX, mapY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent_selection);

        map = (ImageView) findViewById(R.id.imageView_map);
        avatar = (ImageView) findViewById(R.id.imageView_avatar);

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

                bitmapMap = ((BitmapDrawable)map.getDrawable()).getBitmap();

                Log.v("Map W, H, X, Y", Integer.toString(bitmapMap.getWidth()) + " " +
                        Integer.toString((int) Math.floor(bitmapMap.getWidth() * 0.61087511d)));

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

                return false;
            }
        });






    }
}
