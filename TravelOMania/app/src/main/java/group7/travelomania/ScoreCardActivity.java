package group7.travelomania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScoreCardActivity extends AppCompatActivity {


    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);

        player = Player.getInstance(this);

        final Button btn_nextLevel = (Button) findViewById(R.id.next_level);

        btn_nextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextLevel();
            }
        });

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
