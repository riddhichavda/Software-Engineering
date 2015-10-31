package group7.travelomania;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Chronometer;
import android.widget.TextView;

public class LevelActivity extends AppCompatActivity {


    //private Chronometer timer;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        final TextView textView_timer = (TextView) findViewById(R.id.textView_Timer);
        textView_timer.setText("00:60");

        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView_timer.setText("00:" + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                textView_timer.setBackgroundColor(Color.RED);
            }
        };

        countDownTimer.start();



    }
}
