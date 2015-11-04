package group7.travelomania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RulesActivity extends AppCompatActivity {
    Button btnPlayGame;
    Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        btnPlayGame = (Button)findViewById(R.id.btnPlayGame);
        player = Player.getInstance(this);

        btnPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToContinentSelectionActvity();
            }
        });
    }

    @Override
    public  void onResume(){
        super.onResume();
        Player.currentContext = this;
        player = Player.getInstance(this);
    }

    @Override
    public void onUserInteraction(){
        if(player != null) {
            if(!player.isTimerNull())
                player.resetLogoutTimer();
        }
    }




    private void goToContinentSelectionActvity(){
        Intent intent = new Intent(getApplicationContext(), ContinentSelectionActivity.class);
        startActivity(intent);
    }
}


