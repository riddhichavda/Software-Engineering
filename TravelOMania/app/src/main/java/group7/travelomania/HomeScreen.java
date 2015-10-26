package group7.travelomania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        final Button continentSelection = (Button) findViewById(R.id.button_ContinentSelection);

        continentSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToContinentSelectionActvity();

            }
        });
    }

    private void goToContinentSelectionActvity(){
        Intent intent = new Intent(this, ContinentSelectionActivity.class);
        startActivity(intent);
    }
}
