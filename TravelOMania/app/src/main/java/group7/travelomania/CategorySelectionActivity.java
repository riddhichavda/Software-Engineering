package group7.travelomania;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class CategorySelectionActivity extends AppCompatActivity
{

    Category categorySelected;
    Category currentCategory;

    private ImageView category;
    private Bitmap bitmap;
    private boolean isNewSelection;
    private int caHight, caWidth;
    Player player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        categorySelected = null;

        category = (ImageView) findViewById(R.id.imageView_category);
        final Button buttonNext = (Button) findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity();

            }
        });

}
    private void goToNextActivity(){
        Intent intent = new Intent(this, ScoreCardActivity.class);
        startActivity(intent);


    }
        }


