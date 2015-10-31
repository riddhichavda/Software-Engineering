package group7.travelomania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
    public void onBackPressed(){ Log.v("Back Pressed", "Back");}

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
