package group7.travelomania;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class LevelActivity extends AppCompatActivity {

    private Player player;


    private CountDownTimer countDownTimer;
    private ArrayList<Question> questions;
    private Iterator<Question> questionIter;

    private Question currentQuestion;
    private int questionCounter;
    private boolean questionAnswered;
    private boolean hintUsed;

    private TextView textView_level;
    private TextView textView_questionNumber;
    private TextView textView_question;

    private ArrayList<Button> buttonList;
    private ArrayList<String> nameList;
    private Button correctButton;
    private Button btn_next;
    private RelativeLayout btn_hint;

    private ImageView imageView_bulb;
    private TextView textView_numHints;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        player = Player.getInstance(this);

        player.totalScore = 0;


        final TextView textView_timer = (TextView) findViewById(R.id.textView_Timer);
        btn_next = (Button) findViewById(R.id.btn_next);
        final Button btn_help = (Button) findViewById(R.id.btn_help);
        final ImageView imageView_avatar = (ImageView) findViewById(R.id.imageView_avatar);
        imageView_avatar.setImageBitmap(player.avatar);

        buttonList = new ArrayList<>();
        buttonList.add((Button) findViewById(R.id.btn_option1));
        buttonList.add((Button) findViewById(R.id.btn_option2));
        buttonList.add((Button) findViewById(R.id.btn_option3));
        buttonList.add((Button) findViewById(R.id.btn_option4));
        textView_question = (TextView) findViewById(R.id.textView_question);
        textView_questionNumber = (TextView) findViewById(R.id.textView_questionNumber);
        textView_level = (TextView) findViewById(R.id.textView_level);
        if(player.continentsTraveled != null)
            textView_level.setText("LEVEL " + (player.continentsTraveled.size()+1));
        else
            textView_level.setText("LEVEL " + 1);


        btn_hint = (RelativeLayout) findViewById(R.id.btn_hint);
        imageView_bulb = (ImageView) findViewById(R.id.imageView_bulb);
        textView_numHints = (TextView) findViewById(R.id.textView_numHints);
        textView_numHints.setText(Integer.toString(player.numHints));


        nameList = new ArrayList<>();
        nameList.add("a. ");
        nameList.add("b. ");
        nameList.add("c. ");
        nameList.add("d. ");

        QuestionDatabaseHelper qdbh = new QuestionDatabaseHelper(this);
        qdbh.open();
        questions = qdbh.getQuestions(player.currentContinent.toString(),
                                      player.continentsTraveled.size()+1 > 3 ? (player.continentsTraveled.size()+1 > 6 ? "hard":"medium"):"easy",
                                      player.selectedCategory.toString().toLowerCase());
        qdbh.close();


        if(questions == null) {
            Log.e("Questions", "There were no questions in the database for " + player.selectedCategory.toString() + " in " + player.currentContinent.toString());
            Log.e("Questions", "Don't know what to do! Ending Application");
        }

        for(Question q: questions)
            Log.v("Question", q.toString());

        Collections.shuffle(questions);

        for(Question q: questions)
            Log.v("Question", q.toString());


        questionIter = questions.iterator();
        questionCounter = 0;


        countDownTimer = new CountDownTimer(59999, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long timeLeft = millisUntilFinished/1000;
                textView_timer.setText("00:" + (timeLeft<10 ? "0" + timeLeft : timeLeft));
            }

            @Override
            public void onFinish() {
                textView_timer.setBackgroundColor(Color.RED);
                textView_timer.setText("00:00");
                checkCorrectness(null);
            }
        };




        buttonList.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCorrectness((Button) v);
            }
        });
        buttonList.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCorrectness((Button) v);
            }
        });
        buttonList.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCorrectness((Button) v);
            }
        });
        buttonList.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCorrectness((Button) v);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionAnswered)
                    nextQuestion();
            }
        });
        btn_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHint();
            }
        });
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRules();
            }
        });

        nextQuestion();

    }

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

    @Override
    public void onBackPressed(){}



    private void checkCorrectness(Button choice){
        if(!questionAnswered) {
            questionAnswered = true;
            countDownTimer.cancel();
            if(choice == null){
                correctButton.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            }
            else if (choice.equals(correctButton)) {
                choice.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                if(player.currentContinent == Continents.Antarctica)
                    player.totalScore += 70;
                else {
                    player.totalScore += 10;
                }
            } else {
                correctButton.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                choice.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
            }
            btn_next.getBackground().setColorFilter(null);
        }
    }

    private void nextQuestion(){
        if(questionIter.hasNext()) {
            questionAnswered = false;
            hintUsed = false;
            questionCounter++;
            textView_questionNumber.setText(questionCounter + "/" + questions.size());
            currentQuestion = questionIter.next();
            textView_question.setText(currentQuestion.question);
            long seed = System.nanoTime();
            Collections.shuffle(buttonList, new Random(seed));
            Collections.shuffle(nameList, new Random(seed));
            buttonList.get(0).setText(nameList.get(0) + currentQuestion.answer);
            buttonList.get(1).setText(nameList.get(1) + currentQuestion.wrong_answers[0]);
            buttonList.get(2).setText(nameList.get(2) + currentQuestion.wrong_answers[1]);
            buttonList.get(3).setText(nameList.get(3) + currentQuestion.wrong_answers[2]);
            correctButton = buttonList.get(0);

            for(Button btn: buttonList) btn.getBackground().setColorFilter(null);
            findViewById(R.id.textView_Timer).setBackgroundColor(Color.TRANSPARENT);
            btn_next.getBackground().setColorFilter(Color.DKGRAY, PorterDuff.Mode.MULTIPLY);
            countDownTimer.start();
        }
        else{
            //TODO quiz is done.
            player.levelAttempts++;
            Intent intent = new Intent(this, ScoreCardActivity.class);
            Log.v("score", Integer.toString(player.totalScore));
            startActivity(intent);
            finish();
        }
    }

    private void resetTimer(){
        countDownTimer.cancel();
        countDownTimer.start();
    }

    private void showHint(){
        if(player.numHints>0 && !hintUsed){
            long seed = System.nanoTime();
            Collections.shuffle(buttonList, new Random(seed));
            Collections.shuffle(nameList, new Random(seed));

            int answersTakenOff = 0;
            int index = 0;
            while(answersTakenOff < 2){
                if(!buttonList.get(index).equals(correctButton)){
                    buttonList.get(index).getBackground().setColorFilter(Color.DKGRAY, PorterDuff.Mode.MULTIPLY);
                    answersTakenOff++;
                }
                index++;
            }

            if(Build.VERSION.SDK_INT >= 21)
                imageView_bulb.setImageDrawable(getDrawable(R.drawable.bulb_on));
            else
                imageView_bulb.setImageDrawable(getResources().getDrawable(R.drawable.bulb_on));

            player.numHints--;
            textView_numHints.setText(Integer.toString(player.numHints));
            hintUsed = true;
        }
    }

    private void showRules(){
        Player.viewRules(this);
    }

}
