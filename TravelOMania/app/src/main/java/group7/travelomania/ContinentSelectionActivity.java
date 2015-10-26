package group7.travelomania;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ContinentSelectionActivity extends AppCompatActivity {


    private ImageView map;
    private ImageView avatar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent_selection);

        map = (ImageView) findViewById(R.id.imageView_map);
        avatar = (ImageView) findViewById(R.id.imageView_avatar);

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
