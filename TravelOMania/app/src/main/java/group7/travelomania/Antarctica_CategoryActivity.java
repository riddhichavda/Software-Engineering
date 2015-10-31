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
import android.widget.Button;
import android.widget.ImageView;

public class Antarctica_CategoryActivity extends AppCompatActivity {

    Category categorySelected;

    private ImageView category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antarctica__category);

        categorySelected = null;
        category = (ImageView) findViewById(R.id.imageView_antarctica);
        final Button btnNext = (Button) findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity();

            }
        });

        category.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= 16) {
                    category.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else category.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                //categoryX = category.getX();
                //categoryY = category.getY();
            }
        });

        category.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //get touch position
                int touchX = (int) event.getX();
                int touchY = (int) event.getY();
                Log.v("Touch X, Y", Integer.toString(touchX) + " " + Integer.toString(touchY));


                // get the true x and y in pixel in bitmap
                //int realX = (int) (touchX - categoryX);
                //int realY = (int) (touchY - categoryY);

                int pixel = ((BitmapDrawable) category.getDrawable()).getBitmap().getPixel(touchX, touchY);
                Log.v("pixel color", Integer.toString(pixel));

                switch (pixel) {
                    case -313229:
                        categorySelected = Category.Research_Facilities;
                        break;
                    case -16524679:
                        categorySelected = Category.Biodiversity;
                        break;
                    case -13176842:
                        categorySelected = Category.Research;
                        break;
                    case -3575364:
                        categorySelected = Category.Geography_and_Geology;
                        break;
                    case -15412862:
                        categorySelected = Category.History_of_Exploration;
                        break;
                    case -3977263:
                        categorySelected = Category.Politics;
                        break;
                }
                return true;
            }
        });
    }




    private void goToNextActivity(){
        Intent intent = new Intent(this, ScoreCardActivity.class);
        startActivity(intent);


    }
}
