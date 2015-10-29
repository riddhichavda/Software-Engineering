package group7.travelomania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RulesActivity extends AppCompatActivity {
    Button btnPlayGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        btnPlayGame = (Button)findViewById(R.id.btnPlayGame);

        btnPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToContinentSelectionActvity();
            }
        });
    }




    private void goToContinentSelectionActvity(){
        Intent intent = new Intent(getApplicationContext(), ContinentSelectionActivity.class);
        startActivity(intent);
    }
}


