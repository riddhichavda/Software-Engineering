package group7.travelomania;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;

import java.util.HashMap;


//End of game

public class EndActivity extends AppCompatActivity {


    private HashMap<Continents, float[]> continentPositions;

    private HashMap<Continents, float[]> landmarkPositions;

    //private Admin admin;
    private Player player;

    private ImageView map;
    private ImageView planePaths;

    private float mapWidth, mapHeight;
    private float mapX, mapY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        BitmapUtility.initialize(this);

        map = (ImageView)findViewById(R.id.imageView_map);
        planePaths = (ImageView)findViewById(R.id.imageView_planePaths);
        map.setImageBitmap(BitmapUtility.map_addition_navigation);

        landmarkPositions = new HashMap<>(7);
        landmarkPositions.put(Continents.Africa, new float[]{0.56f, 0.46f});
        landmarkPositions.put(Continents.Oceania, new float[]{0.93f, 0.64f});
        landmarkPositions.put(Continents.Asia, new float[]{0.77f, 0.18f});
        landmarkPositions.put(Continents.Antarctica, new float[]{0.73f, 0.93f});
        landmarkPositions.put(Continents.Europe, new float[]{0.57f, 0.14f});
        landmarkPositions.put(Continents.NorthAmerica, new float[]{0.13f, 0.30f});
        landmarkPositions.put(Continents.SouthAmerica, new float[]{0.25f, 0.57f});

        continentPositions = new HashMap<>(7);
        continentPositions.put(Continents.Africa, new float[]{0.56f, 0.46f});
        continentPositions.put(Continents.Oceania, new float[]{0.93f, 0.64f});
        continentPositions.put(Continents.Asia, new float[]{0.77f, 0.18f});
        continentPositions.put(Continents.Antarctica, new float[]{0.73f, 0.96f});
        continentPositions.put(Continents.Europe, new float[]{0.57f, 0.14f});
        continentPositions.put(Continents.NorthAmerica, new float[]{0.13f, 0.30f});
        continentPositions.put(Continents.SouthAmerica, new float[]{0.29f, 0.57f});

        //admin = Admin.getInstance(this);
        player = Player.getInstance(this);

        final Button btn_playAgain = (Button) findViewById(R.id.btn_playAgain);

        btn_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //admin.continentsTraveled.clear();
                player.newGame(player.userName);

                goToNextActivity();
            }
        });

        map.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= 16)
                    map.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    map.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                mapWidth = map.getWidth();
                mapHeight = (int) Math.round(mapWidth * 0.61087511d);
                mapX = map.getX();
                mapY = map.getY() + Math.round((map.getHeight() - BitmapUtility.mapHeight) / 2);

                Log.v("Map W, H, X, Y", Integer.toString(BitmapUtility.mapWidth) + " " +
                        Integer.toString(BitmapUtility.mapHeight) + " " +
                        Float.toString(mapX) + " " +
                        Float.toString(mapY));

                createPathsForEnd();

                planePaths.setImageBitmap(BitmapUtility.planePaths);
                map.setImageBitmap(BitmapUtility.map_addition_navigation);

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



    public void createPathsForEnd(){

        BitmapUtility.map_addition_navigation.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(BitmapUtility.map_addition_navigation);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        canvas.drawBitmap(BitmapUtility.map_af, 0, 0, paint);
        canvas.drawBitmap(BitmapUtility.map_an, 0, 0, paint);
        canvas.drawBitmap(BitmapUtility.map_as, 0, 0, paint);
        canvas.drawBitmap(BitmapUtility.map_eu, 0, 0, paint);
        canvas.drawBitmap(BitmapUtility.map_oc, 0, 0, paint);
        canvas.drawBitmap(BitmapUtility.map_na, 0, 0, paint);
        canvas.drawBitmap(BitmapUtility.map_sa, 0, 0, paint);

        for(Continents c: player.landmarksAquired.keySet()){
            String res = "landmark_" + c.toString().toLowerCase() + "_" + player.landmarksAquired.get(c);
            Log.v("HELp", res);
            int resID = getResources().getIdentifier(res, "drawable", getPackageName());
            Log.v("HELp", String.valueOf(resID));
            Bitmap temp = BitmapUtility.decodeSampledBitmapFromResource(getResources(), resID, 32, 32);
            if(temp == null) Log.v("Damn it", "Yup");
            canvas.drawBitmap(temp,landmarkPositions.get(c)[0]*BitmapUtility.map_addition_selection.getWidth(), landmarkPositions.get(c)[1]*BitmapUtility.map_addition_selection.getHeight(), paint);

        }

        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(8);

        if(BitmapUtility.planePaths == null) {
            BitmapUtility.planePaths = Bitmap.createBitmap(planePaths.getWidth(), planePaths.getHeight(), Bitmap.Config.ARGB_8888);
        }

        canvas = new Canvas(BitmapUtility.planePaths);

        for(int continentIndex = 0; continentIndex < player.continentsTraveled.size() - 1; continentIndex++) {
            int currentLocationX = (int) Math.floor(continentPositions.get(player.continentsTraveled.get(continentIndex))[0] * mapWidth + mapX - 45);
            int currentLocationY = (int) Math.floor(continentPositions.get(player.continentsTraveled.get(continentIndex))[1] * mapHeight + mapY - 15);
            int newLocationX = (int) Math.floor(continentPositions.get(player.continentsTraveled.get(continentIndex+1))[0] * mapWidth + mapX - 45);
            int newLocationY = (int) Math.floor(continentPositions.get(player.continentsTraveled.get(continentIndex+1))[1] * mapHeight + mapY - 15);
            canvas.drawPath(getPlanePath(newLocationX, newLocationY, currentLocationX, currentLocationY), paint);
        }


    }

    private Path getPlanePath(int newLocationX, int newLocationY, int currentLocationX, int currentLocationY){
        Path path = new Path();
        path.moveTo((float) currentLocationX, (float) currentLocationY);

        final float xControl = (newLocationX + currentLocationX)/2;
        final float yControl = Math.min(newLocationY, currentLocationY);

        path.quadTo(xControl, yControl, (float) newLocationX, (float) newLocationY);
        return path;
    }

    private void goToNextActivity(){
        player.newGame(player.userName);
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
        finish();
    }




}
