package group7.travelomania;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

    private Admin admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Log.i("Start", "NavigationActivity");

        //Get admin instance.
        admin = Admin.getInstance(this);

        //Initialize bitmaps and useful bitmap information.
        Log.i("Start", "Bitmap.initialize");
        BitmapUtility.initialize(this);
        Log.i("End", "Bitmap.initialize");

        //Initialize continents from the admin construct.
        CurrentContinent = admin.continentsTraveled.get(1);
        goTo = admin.continentsTraveled.get(0);

        //Initialize views.
        map = (ImageView) findViewById(R.id.imageView_map);
        plane = (ImageView) findViewById(R.id.imageView_plane);
        planePaths = (ImageView) findViewById(R.id.imageView_paths);


        createBitmap();
        map.setImageBitmap(BitmapUtility.map_addition_navigation);



        continentPositions = new HashMap<>(7);
        continentPositions.put(Continents.Africa, new float[]{0.56f, 0.46f});
        continentPositions.put(Continents.Oceania, new float[]{0.93f, 0.64f});
        continentPositions.put(Continents.Asia, new float[]{0.77f, 0.18f});
        continentPositions.put(Continents.Antarctica, new float[]{0.73f, 0.96f});
        continentPositions.put(Continents.Europe, new float[]{0.57f, 0.14f});
        continentPositions.put(Continents.NorthAmerica, new float[]{0.13f, 0.30f});
        continentPositions.put(Continents.SouthAmerica, new float[]{0.29f, 0.57f});



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
                //createBitmap();
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
            createBitmap();
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


        Path planePath = getPlanePath(newLocationX, newLocationY);

        if(BitmapUtility.planePaths == null) {
            BitmapUtility.planePaths = Bitmap.createBitmap(planePaths.getWidth(), planePaths.getHeight(), Bitmap.Config.ARGB_8888);
        }
        BitmapUtility.planePaths.eraseColor(Color.TRANSPARENT);

        Canvas canvas = new Canvas(BitmapUtility.planePaths);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(8);
        canvas.drawPath(planePath, paint);
        planePaths.setImageBitmap(BitmapUtility.planePaths);



        ValueAnimator pathAnimator = ValueAnimator.ofObject(new PathEvaluator(planePath), new float[2], new float[2]);
        pathAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float[] pos = (float[]) animation.getAnimatedValue();
                if (plane.getY() < pos[1])
                    plane.setRotation((float) (180 + Math.toDegrees(Math.tan(-pos[2]))));
                else
                    plane.setRotation((float) Math.toDegrees(Math.tan(pos[2])));

                plane.setX(pos[0]);
                plane.setY(pos[1]);
                //Log.v("UpdateListener", Float.toString(pos[0]) + " " + Float.toString(pos[1]) + " " + Double.toString(Math.toDegrees(Math.tan(pos[2]))));
            }
        });

        //ValueAnimator rotate = ObjectAnimator.ofFloat(plane, "rotation", (float) theta);


        float[] tan = new float[2];
        float theta;

        PathMeasure pathMeasure = new PathMeasure(planePath, false);
        pathMeasure.getPosTan(0,null,tan);

        if(currentLocationY < newLocationY) {
            theta = ((float) (180 + Math.toDegrees(Math.tan(-tan[0]))));
        }
        else {
            theta = ((float) Math.toDegrees(Math.tan(tan[0])));
        }

        if(theta > 180) theta = theta - 360;

        ValueAnimator rotate = ObjectAnimator.ofFloat(plane, "rotation", theta);
        ValueAnimator rotateBack = ObjectAnimator.ofFloat(plane, "rotation", 0.0f);

        pathAnimator.setDuration((int) Math.round(distance / planeSpeed));
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

        path.quadTo(xControl, yControl, (float) newLocationX, (float) newLocationY);
        return path;
    }

    private void createBitmap(){
        //BitmapUtility.map_bw.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(BitmapUtility.map_addition_navigation);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(BitmapUtility.map_bw, 0,0, paint);
        //canvas.drawBitmap(BitmapUtility.map_af, 0, 0, paint);
        if(goTo == Continents.Africa | CurrentContinent == Continents.Africa){
            canvas.drawBitmap(BitmapUtility.map_af, 0, 0, paint);
        }
        if(goTo == Continents.Antarctica | CurrentContinent == Continents.Antarctica){
            canvas.drawBitmap(BitmapUtility.map_an, 0, 0, paint);
        }
        if(goTo == Continents.Asia | CurrentContinent == Continents.Asia){
            canvas.drawBitmap(BitmapUtility.map_as, 0, 0, paint);
        }
        if(goTo == Continents.Europe | CurrentContinent == Continents.Europe){
            canvas.drawBitmap(BitmapUtility.map_eu, 0, 0, paint);
        }
        if(goTo == Continents.Oceania | CurrentContinent == Continents.Oceania){
            canvas.drawBitmap(BitmapUtility.map_oc, 0, 0, paint);
        }
        if(goTo == Continents.NorthAmerica | CurrentContinent == Continents.NorthAmerica){
            canvas.drawBitmap(BitmapUtility.map_na, 0, 0, paint);
        }
        if(goTo == Continents.SouthAmerica | CurrentContinent == Continents.SouthAmerica){
            canvas.drawBitmap(BitmapUtility.map_sa, 0, 0, paint);
        }
        //map.setImageBitmap(BitmapUtility.map_addition);
    }

    private void goToNextActivity(){
        Intent intent = new Intent(this, AvatarSelectionActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
