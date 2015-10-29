package group7.travelomania;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AvatarSelectionActivity extends AppCompatActivity {


    private ImageView avatar1, avatar2, avatar3;
    private ImageView leftArrow, rightArrow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_selection);

        avatar1 = (ImageView) findViewById(R.id.imageView_Avatar1);
        avatar2 = (ImageView) findViewById(R.id.imageView_Avatar2);
        avatar3 = (ImageView) findViewById(R.id.imageView_Avatar3);

        if(BitmapUtility.avatar_AF == null){
            BitmapUtility.avatar_AF = BitmapUtility.decodeSampledBitmapFromResource(getResources(),
                    R.drawable.avatar_africa,
                    64,
                    64);
        }

        if(BitmapUtility.avatar_AS == null){
            BitmapUtility.avatar_AS = BitmapUtility.decodeSampledBitmapFromResource(getResources(),
                    R.drawable.avatar_asia,
                    64,
                    64);
        }

        if(BitmapUtility.avatar_EU == null){
            BitmapUtility.avatar_EU = BitmapUtility.decodeSampledBitmapFromResource(getResources(),
                    R.drawable.avatar_europe,
                    64,
                    64);
        }

        if(BitmapUtility.avatar_OC == null){
            BitmapUtility.avatar_OC = BitmapUtility.decodeSampledBitmapFromResource(getResources(),
                    R.drawable.avatar_ocieana,
                    64,
                    64);
        }

        if(BitmapUtility.avatar_NA == null){
            BitmapUtility.avatar_NA = BitmapUtility.decodeSampledBitmapFromResource(getResources(),
                    R.drawable.avatar_na,
                    64,
                    64);
        }

        if(BitmapUtility.avatar_SA == null){
            BitmapUtility.avatar_SA = BitmapUtility.decodeSampledBitmapFromResource(getResources(),
                    R.drawable.avatar_sa,
                    64,
                    64);
        }

        avatar1.setImageBitmap(BitmapUtility.avatar_AF);
        avatar2.setImageBitmap(BitmapUtility.avatar_AS);
        avatar3.setImageBitmap(BitmapUtility.avatar_EU);



        final Button btn_next = (Button) findViewById(R.id.avatar_btn_Next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity();
            }
        });
    }

    private void goToNextActivity(){
        Intent intent = new Intent(this, CategorySelectionActivity.class);
        startActivity(intent);
    }

}
