package group7.travelomania;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreCardActivity extends AppCompatActivity {


    private Player player;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);

        player = Player.getInstance(this);

        final Button btn_nextLevel = (Button) findViewById(R.id.next_level);
        final Button btn_restartLevel = (Button) findViewById(R.id.restart_level);

        //Initialize views.


        avatar = (ImageView) findViewById(R.id.imageView_avatar);
        avatar.setImageBitmap(player.avatar);
        TextView review = (TextView)findViewById(R.id.good_job);
        ImageView hint_bulb = (ImageView)findViewById(R.id.hint_bulb);

        int total_score = player.totalScore;
        String hintText = "0";
        if(total_score>=50 && total_score<=70)
        {
            hintText = "+1";
            player.numHints++;
            ImageView img = (ImageView)findViewById(R.id.imageView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                img.setBackground(ContextCompat.getDrawable(this, R.drawable.excellent));
            }
            img.setVisibility(View.VISIBLE);
            review.setText("Excellent!!");
            hint_bulb.setVisibility(View.VISIBLE);

        }
        else if(total_score<50 && total_score>20)
        {
            ImageView img = (ImageView)findViewById(R.id.imageView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                img.setBackground(ContextCompat.getDrawable(this, R.drawable.good_job));
            }
            img.setVisibility(View.VISIBLE);
            review.setText("Good Job!!");
            hint_bulb.setVisibility(View.INVISIBLE);

        }
        else if(total_score >= 0 && total_score <= 20) {
            ImageView img = (ImageView)findViewById(R.id.imageView);

            btn_nextLevel.setVisibility(Button.INVISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                img.setBackground(ContextCompat.getDrawable(this, R.drawable.level_failed));
            }
            img.setVisibility(View.VISIBLE);
            review.setText("Level failed!");
            hint_bulb.setVisibility(View.INVISIBLE);

        }
        else {
            System.out.println("---------------------invalid total score-----------------");
        }


        TextView txtScore = (TextView)findViewById(R.id.score_value);
        txtScore.setText(Integer.toString(total_score));
        TextView txtHint = (TextView)findViewById(R.id.number_of_hints);
        txtHint.setText(hintText);

        btn_nextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextLevel();
            }
        });
        btn_restartLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                restartLevel();
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


    private void restartGame(){
        player.selectedCategory = null;
        player.CategorySelected.clear();
        player.categoryCount = 0;
        player.newGame(player.userName);
        Intent intent = new Intent(this, ContinentSelectionActivity.class);
        startActivity(intent);
        finish();
    }

    private class clickEndGame implements View.OnClickListener{
        @Override
        public void onClick(View view){
            restartGame();
        }
    }

    private void restartLevel(){
        player.categoryCount++;
        if(player.categoryCount>=3){
            new AlertDialog.Builder(ScoreCardActivity.this).setTitle("Sorry!").setMessage("You have lost the game! Now restarting!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    restartGame();
                }
            }).show();
            //restartGame();
        }
        else {
            Intent intent = new Intent(this, CategorySelectionActivity.class);
            startActivity(intent);
        }
    }

    private void nextLevel(){
        player.continentsTraveled.add(0, player.currentContinent);
        player.saveProgress();
        Intent intent = new Intent(this, IdentifyLandmarkActivity.class);
        startActivity(intent);
    }
}
