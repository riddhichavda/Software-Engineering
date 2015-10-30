package group7.travelomania;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;


public class CategorySelectionActivity extends AppCompatActivity
{

    Category categorySelected;

    private ImageView category;
    //private float categoryX,categoryY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        categorySelected = null;


        category = (ImageView) findViewById(R.id.imageView_category);
        final Button buttonNext = (Button) findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity();

            }
        });

        category.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= 16){
                    category.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                else category.getViewTreeObserver().removeGlobalOnLayoutListener(this);

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

                int pixel = ((BitmapDrawable)category.getDrawable()).getBitmap().getPixel(touchX,touchY);
                Log.v("pixel color",Integer.toString(pixel));

                switch(pixel){
                    case -14882191:
                        categorySelected = Category.Sports;
                        break;
                    case -10255826:
                        categorySelected = Category.Politics;
                        break;
                    case  -14192504:
                        categorySelected = Category.Landmarks;
                        break;
                    case -16133868:
                        categorySelected = Category.Fesitvals;
                        break;
                    case -12767980:
                        categorySelected = Category.Cuisines;
                        break;
                    case -990562:
                        categorySelected = Category.Capitals;
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


