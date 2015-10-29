package group7.travelomania;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AvatarSelectionActivity extends AppCompatActivity {


    private ImageView imageView_avatar, avatar1, avatar2, avatar3;
    private ImageView leftArrow, rightArrow;

    private int avatarIndex, selectedIndex;

    private Player player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_selection);

        player = Player.getInstance(this);
        imageView_avatar = (ImageView) findViewById(R.id.imageView_Avatar);
        imageView_avatar.setImageBitmap(player.avatar);

        avatar1 = (ImageView) findViewById(R.id.imageView_Avatar1);
        avatar2 = (ImageView) findViewById(R.id.imageView_Avatar2);
        avatar3 = (ImageView) findViewById(R.id.imageView_Avatar3);

        leftArrow = (ImageView) findViewById(R.id.imageView_LeftArrow);
        rightArrow = (ImageView) findViewById(R.id.imageView_RightArrow);

        avatarIndex = 0;
        selectedIndex = 0;

        if(BitmapUtility.avatar_AF == null){
            BitmapUtility.avatar_AF = BitmapUtility.decodeSampledBitmapFromResource(getResources(),
                    R.drawable.avatar_africa,
                    64,
                    64);
            Log.v("Load", "Avatars");
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

        updateAvatars();

        final Button btn_next = (Button) findViewById(R.id.avatar_btn_Next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (selectedIndex){
                    case 0:
                        updatePlayerAvatar(BitmapUtility.avatar_AF);
                        break;
                    case 1:
                        updatePlayerAvatar(BitmapUtility.avatar_AS);
                        break;
                    case 2:
                        updatePlayerAvatar(BitmapUtility.avatar_EU);
                        break;
                    case 3:
                        updatePlayerAvatar(BitmapUtility.avatar_OC);
                        break;
                    case 4:
                        updatePlayerAvatar(BitmapUtility.avatar_NA);
                        break;
                    case 5:
                        updatePlayerAvatar(BitmapUtility.avatar_SA);
                        break;
                    default:
                        updatePlayerAvatar(BitmapUtility.avatar_NA);
                }
                goToNextActivity();
            }
        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarIndex = avatarIndex-1 == -1 ? 0:avatarIndex-1;
                updateAvatars();
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarIndex = avatarIndex+1 == 4 ? 3:avatarIndex+1;
                updateAvatars();
            }
        });

        avatar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedIndex = avatarIndex + 0;
                updateSelected();
            }
        });
        avatar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedIndex = avatarIndex + 1;
                updateSelected();
            }
        });
        avatar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedIndex = avatarIndex + 2;
                updateSelected();
            }
        });

    }

    private void updateSelected() {
        int selected = selectedIndex - avatarIndex;
        switch(selected){
            case 0:
                avatar1.setBackgroundColor(Color.GREEN);
                avatar2.setBackgroundColor(Color.TRANSPARENT);
                avatar3.setBackgroundColor(Color.TRANSPARENT);
                break;
            case 1:
                avatar1.setBackgroundColor(Color.TRANSPARENT);
                avatar2.setBackgroundColor(Color.GREEN);
                avatar3.setBackgroundColor(Color.TRANSPARENT);
                break;
            case 2:
                avatar1.setBackgroundColor(Color.TRANSPARENT);
                avatar2.setBackgroundColor(Color.TRANSPARENT);
                avatar3.setBackgroundColor(Color.GREEN);
                break;
            default:
                avatar1.setBackgroundColor(Color.TRANSPARENT);
                avatar2.setBackgroundColor(Color.TRANSPARENT);
                avatar3.setBackgroundColor(Color.TRANSPARENT);
                break;
        }
    }

    private void updateAvatars(){
        switch(avatarIndex) {
            case 0:
                avatar1.setImageBitmap(BitmapUtility.avatar_AF);
                avatar2.setImageBitmap(BitmapUtility.avatar_AS);
                avatar3.setImageBitmap(BitmapUtility.avatar_EU);
                break;
            case 1:
                avatar1.setImageBitmap(BitmapUtility.avatar_AS);
                avatar2.setImageBitmap(BitmapUtility.avatar_EU);
                avatar3.setImageBitmap(BitmapUtility.avatar_OC);
                break;
            case 2:
                avatar1.setImageBitmap(BitmapUtility.avatar_EU);
                avatar2.setImageBitmap(BitmapUtility.avatar_OC);
                avatar3.setImageBitmap(BitmapUtility.avatar_NA);
                break;
            case 3:
                avatar1.setImageBitmap(BitmapUtility.avatar_OC);
                avatar2.setImageBitmap(BitmapUtility.avatar_NA);
                avatar3.setImageBitmap(BitmapUtility.avatar_SA);
                break;
            default:
                Log.e("Avatar Index", "Avatar Index is Past Set Limit. Avatar Index: " + Integer.toString(avatarIndex));
                break;
        }
        updateSelected();
    }

    private void updatePlayerAvatar(Bitmap bitmap){
        player.avatar = bitmap;
        imageView_avatar.setImageBitmap(bitmap);
    }

    private void goToNextActivity(){
        Intent intent = new Intent(this, CategorySelectionActivity.class);
        startActivity(intent);
    }

}
