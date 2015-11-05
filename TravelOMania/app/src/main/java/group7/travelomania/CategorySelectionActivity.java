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

    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        category = (ImageView) findViewById(R.id.imageView_category);
        categorySelected = null;
        player = Player.getInstance(this);

        if(player.currentContinent == Continents.Antarctica){
            if(Build.VERSION.SDK_INT >= 21){
                category.setImageDrawable(getDrawable(R.drawable.antarctica));
            }
            else category.setImageDrawable(getResources().getDrawable(R.drawable.antarctica));
        }



            category.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= 16) {
                        category.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else category.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                }
            });

        final Button buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity();

            }
        });
        category.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //get touch position
                int touchX = (int) event.getX();
                int touchY = (int) event.getY();
                Log.v("Touch X, Y", Integer.toString(touchX) + " " + Integer.toString(touchY));


                int pixel = ((BitmapDrawable) category.getDrawable()).getBitmap().getPixel(touchX, touchY);
                Log.v("pixel color", Integer.toString(pixel));

                if (player.currentContinent == Continents.Antarctica) {

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
                } else {
                    switch (pixel) {
                        case -14882191:
                            categorySelected = Category.Sports;
                            break;
                        case -10255826:
                            categorySelected = Category.Politics;
                            break;
                        case -14192504:
                            categorySelected = Category.Landmarks;
                            break;
                        case -16133868:
                            categorySelected = Category.Festivals;
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
            }
        });

}

    @Override
    public  void onResume(){
        super.onResume();
        Player.currentActivity = this;
        player = Player.getInstance(this);
        if(!player.loggedIn){
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        }
    }

    @Override
    public void onUserInteraction(){
        if(player != null) {
            if(!player.isTimerNull())
                player.resetLogoutTimer();
        }
    }

    @Override
    public void onBackPressed(){}


    private void goToNextActivity(){
        Intent intent = new Intent(this, LevelActivity.class);
        startActivity(intent);
        player.selectedCategory = categorySelected;

    }
        }


