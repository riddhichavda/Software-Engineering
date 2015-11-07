package group7.travelomania;

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


        int total_score = player.totalScore;
        String hintText = "0";
        if(total_score>=50 && total_score<=70)
        {
            hintText = "+1";
            player.numHints ++;
            ImageView img = (ImageView)findViewById(R.id.imageView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                img.setBackground(ContextCompat.getDrawable(this, R.drawable.excellent));
            }
            img.setVisibility(View.VISIBLE);

        }
        else if(total_score<50 && total_score>20)
        {
            ImageView img = (ImageView)findViewById(R.id.imageView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                img.setBackground(ContextCompat.getDrawable(this, R.drawable.good_job));
            }
            img.setVisibility(View.VISIBLE);

        }
        else if(total_score >= 0 && total_score <= 20) {
            ImageView img = (ImageView)findViewById(R.id.imageView);
            img.setVisibility(View.INVISIBLE);
            btn_nextLevel.setVisibility(Button.INVISIBLE);

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



    private void restartLevel(){
        Intent intent = new Intent(this, CategorySelectionActivity.class);
        startActivity(intent);
        player.categoryCount++;
    }
    private void nextLevel(){
        player.continentsTraveled.add(0,player.currentContinent);
        player.currentContinent = null;
        player.saveProgress();

        Intent intent;

        if (player.continentsTraveled.size() == 7)
            intent = new Intent(this, EndActivity.class);
        else
            intent = new Intent(this, ContinentSelectionActivity.class);

        startActivity(intent);


    }
}
