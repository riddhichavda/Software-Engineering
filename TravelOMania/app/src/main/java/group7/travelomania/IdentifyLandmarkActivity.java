package group7.travelomania;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;


public class IdentifyLandmarkActivity extends AppCompatActivity {

    private Player player;
    private PopupWindow popup;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_landmark);

        player = Player.getInstance(this);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        frameLayout.setForeground(new ColorDrawable(Color.BLACK));
        frameLayout.getForeground().setAlpha(0);

        int numLandmarks = 6;
        if(player.currentContinent == Continents.Antarctica)
            numLandmarks = 2;

        for(int i = 0; i < numLandmarks; i++){
            String res = "imageView_landmark" + String.valueOf(i);
            int resID = getResources().getIdentifier(res, "id", getPackageName());
            ImageView imageView_landmark = (ImageView) findViewById(resID);
            res = "landmark_" + player.currentContinent.toString().toLowerCase() + "_" + getLandmarkName(i).toLowerCase();
            resID = getResources().getIdentifier(res, "drawable", getPackageName());
            if(Build.VERSION.SDK_INT >= 21)
                imageView_landmark.setImageDrawable(getDrawable(resID));
            else
                imageView_landmark.setImageDrawable(getResources().getDrawable(resID));
            imageView_landmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = 0;
                    switch (v.getId()){
                        case R.id.imageView_landmark0:
                            id = 0;
                            break;
                        case R.id.imageView_landmark1:
                            id = 1;
                            break;
                        case R.id.imageView_landmark2:
                            id = 2;
                            break;
                        case R.id.imageView_landmark3:
                            id = 3;
                            break;
                        case R.id.imageView_landmark4:
                            id = 4;
                            break;
                        case R.id.imageView_landmark5:
                            id = 5;
                            break;
                    }
                    initializePopup(id);
                }
            });
        }

    }

    @Override
    public void onBackPressed(){}

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


    private void initializePopup(int chosenView){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_identify_landmark, (ViewGroup) findViewById(R.id.LinearLayout_MainView));
        popup = new PopupWindow(layout, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);

        frameLayout.getForeground().setAlpha(220);

        ImageView imageView = (ImageView) layout.findViewById(R.id.imageView_chosenLandmark);
        //imageView.setImageDrawable(chosenView.getBackground());
        String res = "landmark_" + player.currentContinent.toString().toLowerCase() + "_" + getLandmarkName(chosenView).toLowerCase();
        int resID = getResources().getIdentifier(res, "drawable", getPackageName());
        Log.v("HEEELP", res + " " + resID);
        if(Build.VERSION.SDK_INT >= 21)
            imageView.setImageDrawable(getDrawable(resID));
        else
            imageView.setImageDrawable(getResources().getDrawable(resID));

        Button btn = (Button) layout.findViewById(R.id.button_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popup.dismiss();
                nextLevel();
            }
        });



    }



    private String getLandmarkName(int view){
        String name = "";
        switch (player.currentContinent){
            case Africa:
                switch (view){
                    case 0:
                        name = "KarnakTemple";
                        break;
                    case 1:
                        name = "MtKilimanjaro";
                        break;
                    case 2:
                        name = "Obelisk";
                        break;
                    case 3:
                        name = "Pyramid";
                        break;
                    case 4:
                        name = "TableMountain";
                        break;
                    case 5:
                        name = "VictoriaFalls";
                        break;
                }
                break;
            case Antarctica:
                switch (view){
                    case 0:
                        name = "EmperorPenguin";
                        break;
                    case 1:
                        name = "RoseIceShelf";
                        break;
                }
                break;
            case Asia:
                switch (view){
                    case 0:
                        name = "BurjAlArab";
                        break;
                    case 1:
                        name = "GreatWallOfChina";
                        break;
                    case 2:
                        name = "StBasilsCathedral";
                        break;
                    case 3:
                        name = "TajMahal";
                        break;
                    case 4:
                        name = "TheGoldenTemple";
                        break;
                    case 5:
                        name = "TianTanBuddha";
                        break;
                }
                break;
            case Europe:
                switch (view){
                    case 0:
                        name = "BigBen";
                        break;
                    case 1:
                        name = "BuckinghamPalace";
                        break;
                    case 2:
                        name = "Colosseum";
                        break;
                    case 3:
                        name = "EiffelTower";
                        break;
                    case 4:
                        name = "LeaningtowerOfPisa";
                        break;
                    case 5:
                        name = "ThePantheon";
                        break;
                }
                break;
            case NorthAmerica:
                switch (view){
                    case 0:
                        name = "Disneyland";
                        break;
                    case 1:
                        name = "Hollywood";
                        break;
                    case 2:
                        name = "MtRushmore";
                        break;
                    case 3:
                        name = "NiagraFalls";
                        break;
                    case 4:
                        name = "StatueOfLiberty";
                        break;
                    case 5:
                        name = "TheWhiteHouse";
                        break;
                }
                break;
            case Oceania:
                switch (view){
                    case 0:
                        name = "BoraBoraLagoon";
                        break;
                    case 1:
                        name = "GreatBarrierReef";
                        break;
                    case 2:
                        name = "LadyKnoxGeyser";
                        break;
                    case 3:
                        name = "SydneyHarborBridge";
                        break;
                    case 4:
                        name = "SydneyOperaHouse";
                        break;
                    case 5:
                        name = "ThePinnacles";
                        break;
                }
                break;
            case SouthAmerica:
                switch (view){
                    case 0:
                        name = "aveOfTheCrystals";
                        break;
                    case 1:
                        name = "ChristTheRedeemerStatue";
                        break;
                    case 2:
                        name = "MachuPicchu";
                        break;
                    case 3:
                        name = "PetronasTwinTowers";
                        break;
                    case 4:
                        name = "TheDesertHand";
                        break;
                    case 5:
                        name = "AngelFalls";
                        break;
                }
                break;
        }
        return name;
    }




    private void nextLevel(){

        player.currentContinent = null;
        player.categoryCount = 0;
        player.CategorySelected.clear();
        player.saveProgress();
        Intent intent;
        if (player.continentsTraveled.size() == 7)
            intent = new Intent(this, EndActivity.class);
        else
            intent = new Intent(this, ContinentSelectionActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        this.finish();
    }

}
