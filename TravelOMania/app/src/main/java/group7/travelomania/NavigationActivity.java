package group7.travelomania;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.HashMap;

public class NavigationActivity extends AppCompatActivity {

    public Continents CurrentContinent;

    private HashMap<Continents, float[]> continentPositions;


    private ImageView plane;
    private ImageView map;

    private int currentLocationX;
    private int currentLocationY;

    private int mapHeight;
    private int mapWidth;
    private float mapX;
    private float mapY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        map = (ImageView) findViewById(R.id.imageView_map);
        plane = (ImageView) findViewById(R.id.imageView_plane);

        continentPositions = new HashMap<>(7);

        continentPositions.put(Continents.Africa, new float[]{0.56f,0.46f});
        continentPositions.put(Continents.Australia, new float[]{0.93f,0.64f});
        continentPositions.put(Continents.Asia, new float[]{0.77f,0.18f});
        continentPositions.put(Continents.Antarctica, new float[]{0.73f, 0.96f});
        continentPositions.put(Continents.Europe, new float[]{0.57f, 0.14f});
        continentPositions.put(Continents.NorthAmerica, new float[]{0.13f,0.30f});
        continentPositions.put(Continents.SouthAmerica, new float[]{0.29f,0.57f});


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
                mapWidth = map.getWidth();
                mapHeight = (int) Math.floor(mapWidth * 0.61087511d);
                mapX = map.getX();
                mapY = map.getY() + (int) Math.ceil((map.getHeight() - mapHeight) / 2);
                Log.v("Map W, H, X, Y", Integer.toString(mapWidth) + " " +
                        Integer.toString(mapHeight) + " " +
                        Float.toString(mapX) + " " +
                        Float.toString(mapY));
                //Now you can get the width and height from content
            }
        });

        plane.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                //Plane initialized with defaults found in activity_navigation.xml

                //Remove Global Layout Listener for plane to ensure when attributes are changed
                // code doesn't go into an infinite loop.
                if(Build.VERSION.SDK_INT >= 16)
                    plane.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    plane.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                //Update the current location of the plane based on the current continent the player is in.

                currentLocationX = (int)Math.floor(continentPositions.get(CurrentContinent)[0] * mapWidth + mapX - plane.getWidth() / 2);
                currentLocationY = (int)Math.floor(continentPositions.get(CurrentContinent)[1] * mapHeight + mapY - plane.getHeight()/2);

                plane.setX(currentLocationX);
                plane.setY(currentLocationY);

                //Set the plane to visible.

                plane.setVisibility(View.VISIBLE);

                Log.v("Map W, H, X, Y plane", Integer.toString(mapWidth) + " " +
                        Integer.toString(mapHeight) + " " +
                        Float.toString(mapX) + " " +
                        Float.toString(mapY));

            }
        });



    }


    public void goToContinent(Continents Destination){
        //PreviousPlaneLocation = PlaneLocation;
        int newLocationX;
        int newLocationY;
        CurrentContinent = Destination;

        newLocationX = (int)Math.floor(continentPositions.get(Destination)[0] * mapWidth + mapX - plane.getWidth()/2);
        newLocationY = (int)Math.floor(continentPositions.get(Destination)[1] * mapHeight + mapY - plane.getHeight()/2);
        Log.v("Check", Integer.toString(newLocationX) + " " + Integer.toString(newLocationY));
        Log.v("Continent Check", Float.toString(continentPositions.get(Destination)[0]) + " " +
                Float.toString(continentPositions.get(Destination)[1]));


        double distance = Math.sqrt((newLocationX-currentLocationX)*(newLocationX-currentLocationX) +
                (newLocationY-currentLocationY)*(newLocationY-currentLocationY));

        double theta;
        if(newLocationY > currentLocationY)
            theta = Math.asin((newLocationY-currentLocationY)/distance)*180/Math.PI;
        else
            theta = Math.asin((currentLocationY-newLocationY)/distance)*180/Math.PI;
        Log.v("Theta", Float.toString((float) theta));
        if(theta == Double.NaN) theta = 0;


        if(newLocationX>currentLocationX){
            if(newLocationY<currentLocationY)
                theta = 90-theta;
            else
                theta += 90;
        }
        else{
            if(newLocationY<currentLocationY)
                theta = theta - 90;

            else
                theta = -1*(90 + theta);

        }
        Log.v("Updated Theta", Float.toString((float) theta));




        ValueAnimator rotate = ObjectAnimator.ofFloat(plane, "rotation", (float) theta);
        ValueAnimator moveX = ObjectAnimator.ofFloat(plane, "x", newLocationX);
        ValueAnimator moveY = ObjectAnimator.ofFloat(plane, "y", newLocationY);
        ValueAnimator rotateBack = ObjectAnimator.ofFloat(plane, "rotation", 0.0f);

        moveX.setDuration(2000);
        moveY.setDuration(2000);
        rotate.setDuration(1000);
        rotateBack.setDuration(1000);

        AnimatorSet planeAnimation = new AnimatorSet();
        planeAnimation.play(rotate).before(moveX);
        planeAnimation.play(moveX).with(moveY);
        planeAnimation.play(moveX).before(rotateBack);

        planeAnimation.start();

        planeAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                goToNextActivity();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        currentLocationX = newLocationX;
        currentLocationY = newLocationY;

        //((TextView)findViewById(R.id.textView_CurrentContinent)).setText("Current Continent: " + continent);
    }

    private void goToNextActivity(){
        Intent intent = new Intent(this, CategorySelectionActivity.class);
        int continent;
        switch(CurrentContinent){
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
