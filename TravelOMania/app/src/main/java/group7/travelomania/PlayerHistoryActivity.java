package group7.travelomania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PlayerHistoryActivity extends AppCompatActivity {

    private Player player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_history);

        player = Player.getInstance(this);

        final Button btn_resume = (Button) findViewById(R.id.btn_resume);
        final Button btn_restart = (Button) findViewById(R.id.btn_restart);
        final Button btn_help = (Button) findViewById(R.id.btn_help);

        final ImageView imageView_avatar = (ImageView)findViewById(R.id.imageView_avatar);
        imageView_avatar.setImageBitmap(player.avatar);

        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRules();
            }
        });

        btn_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resume();
            }
        });

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
            }
        });

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

    private void viewRules(){
        player.viewRules(this);
    }

    private void resume() {
        Intent intent = new Intent(this, ContinentSelectionActivity.class);
        startActivity(intent);
        finish();
    }

    private void restart() {
        player.newGame(player.userName);
        Intent intent = new Intent(this, ContinentSelectionActivity.class);
        startActivity(intent);
        finish();
    }


}
