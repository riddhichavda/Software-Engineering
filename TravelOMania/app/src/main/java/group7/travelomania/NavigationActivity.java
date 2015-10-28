package group7.travelomania;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    public Continents goTo;

    private HashMap<Continents, float[]> continentPositions;


    private ImageView plane;
    private ImageView map;
    private ImageView planePaths;

    private int currentLocationX;
    private int currentLocationY;

    private int mapHeight;
    private int mapWidth;
    private float mapX;
    private float mapY;

    private float planeSpeed = .1f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        CurrentContinent = Continents.Africa;

        map = (ImageView) findViewById(R.id.imageView_map);
        plane = (ImageView) findViewById(R.id.imageView_plane);
        planePaths = (ImageView) findViewById(R.id.imageView_paths);

        Log.v("Start Navigation", "");
        continentPositions = new HashMap<>(7);

        continentPositions.put(Continents.Africa, new float[]{0.56f,0.46f});
        continentPositions.put(Continents.Oceania, new float[]{0.93f,0.64f});
        continentPositions.put(Continents.Asia, new float[]{0.77f,0.18f});
        continentPositions.put(Continents.Antarctica, new float[]{0.73f, 0.96f});
        continentPositions.put(Continents.Europe, new float[]{0.57f, 0.14f});
        continentPositions.put(Continents.NorthAmerica, new float[]{0.13f, 0.30f});
        continentPositions.put(Continents.SouthAmerica, new float[]{0.29f, 0.57f});

        Intent intent = getIntent();
        switch (intent.getIntExtra("nextContinent",-1)) {
            case 0:
                goTo = Continents.Africa;
                break;
            case 1:
                goTo = Continents.Antarctica;
                break;
            case 2:
                goTo = Continents.Asia;
                break;
            case 3:
                goTo = Continents.Oceania;
                break;
            case 4:
                goTo = Continents.Europe;
                break;
            case 5:
                goTo = Continents.NorthAmerica;
                break;
            case 6:
                goTo = Continents.SouthAmerica;
                break;
            default:
                goTo = Continents.NorthAmerica;
                break;
        }


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
                mapHeight = (int) Math.round(mapWidth * 0.61087511d);
                mapX = map.getX();
                mapY = map.getY() + (int) Math.round((map.getHeight() - mapHeight) / 2.0);
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


    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        if(hasFocus){
            goToContinent(goTo);
        }
        super.onWindowFocusChanged(hasFocus);
    }

    public void goToContinent(Continents Destination){
        //PreviousPlaneLocation = PlaneLocation;
        //int newLocationX;
        //int newLocationY;
        CurrentContinent = Destination;

        final int newLocationX = (int)Math.floor(continentPositions.get(Destination)[0] * mapWidth + mapX - plane.getWidth()/2);
        final int newLocationY = (int)Math.floor(continentPositions.get(Destination)[1] * mapHeight + mapY - plane.getHeight()/2);
        Log.v("Check", Integer.toString(newLocationX) + " " + Integer.toString(newLocationY));
        Log.v("Continent Check", Float.toString(continentPositions.get(Destination)[0]) + " " +
                Float.toString(continentPositions.get(Destination)[1]));


        double distance = Math.sqrt((newLocationX-currentLocationX)*(newLocationX-currentLocationX) +
                (newLocationY-currentLocationY)*(newLocationY-currentLocationY));

        Log.v("Distance", Double.toString(distance));

        double theta;
        if(newLocationY > currentLocationY)
            theta = Math.asin((newLocationY-currentLocationY)/distance)*180/Math.PI;
        else
            theta = Math.asin((currentLocationY-newLocationY)/distance)*180/Math.PI;
        Log.v("Theta", Float.toString((float) theta));
        if(theta == Double.NaN) theta = 0.0f;


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





        Path planePath = getPlanePath(newLocationX, newLocationY);
        Bitmap tempBitmap = Bitmap.createBitmap(planePaths.getWidth(), planePaths.getHeight(), Bitmap.Config.ARGB_8888);
        //Bitmap.crea
        Canvas canvas = new Canvas(tempBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.TRANSPARENT);
        paint.setStyle(Paint.Style.FILL);

        //canvas.drawPaint(paint);
        //canvas.drawColor(Color.TRANSPARENT);
        canvas.drawPaint(paint);

        paint.setColor(Color.RED);
        canvas.drawCircle(50, 50, 10, paint);
        paint.setStyle(Paint.Style.STROKE);
        //paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10);

        canvas.drawPath(planePath, paint);
        planePaths.setImageBitmap(tempBitmap);


        ValueAnimator pathAnimator = ValueAnimator.ofObject(new PathEvaluator(planePath), new float[2], new float[2]);
        pathAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float[] pos = (float[]) animation.getAnimatedValue();
                plane.setX(pos[0]);
                plane.setY(pos[1]);
            }
        });

        ValueAnimator rotate = ObjectAnimator.ofFloat(plane, "rotation", (float) theta);
        ValueAnimator rotateBack = ObjectAnimator.ofFloat(plane, "rotation", 0.0f);

        pathAnimator.setDuration((int) Math.ceil(distance / planeSpeed));
        rotate.setDuration(1000);
        rotateBack.setDuration(1000);

        AnimatorSet planeAnimation = new AnimatorSet();
        planeAnimation.play(rotate).before(pathAnimator);
        planeAnimation.play(pathAnimator).before(rotateBack);

        planeAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {goToNextActivity();}
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        planeAnimation.start();

        currentLocationX = newLocationX;
        currentLocationY = newLocationY;
    }

    private Path getPlanePath(int newLocationX, int newLocationY){
        Path path = new Path();
        path.moveTo((float)currentLocationX, (float)currentLocationY);

        final float xControl = (newLocationX + currentLocationX)/2;
        final float yControl = Math.min(newLocationY, currentLocationY);

        //path.quadTo(xControl, yControl, newLocationX, newLocationY);
        path.quadTo(xControl, yControl, (float) newLocationX, (float) newLocationY);
        //Log.v("Path", path.toString());
        //path.close();
        return path;

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
            case Oceania:
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
        finish();
    }


}
