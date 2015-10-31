package group7.travelomania;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Chronometer;
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

    private TextView textView_level;
    private TextView textView_questionNumber;
    private TextView textView_question;

    private ArrayList<Button> buttonList;
    private ArrayList<String> nameList;
    private Button correctButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        //player = player.getInstance(this);
        final TextView textView_timer = (TextView) findViewById(R.id.textView_Timer);
        final Button btn_next = (Button) findViewById(R.id.btn_next);
        buttonList = new ArrayList<>();
        buttonList.add((Button) findViewById(R.id.btn_option1));
        buttonList.add((Button) findViewById(R.id.btn_option2));
        buttonList.add((Button) findViewById(R.id.btn_option3));
        buttonList.add((Button) findViewById(R.id.btn_option4));
        textView_question = (TextView) findViewById(R.id.textView_question);
        textView_questionNumber = (TextView) findViewById(R.id.textView_questionNumber);
        textView_level = (TextView) findViewById(R.id.textView_level);

        nameList = new ArrayList<>();
        nameList.add("a. ");
        nameList.add("b. ");
        nameList.add("c. ");
        nameList.add("d. ");

        QuestionDatabaseHelper qdbh = new QuestionDatabaseHelper(this);
        qdbh.open();
        questions = qdbh.getQuestions("NorthAmerica", "easy", "capitals");
        qdbh.close();

        for(Question q: questions)
            Log.v("Question", q.toString());

        Collections.shuffle(questions);

        for(Question q: questions)
            Log.v("Question", q.toString());

        questionIter = questions.iterator();
        questionCounter = 0;
        nextQuestion();

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
            }
        };

        countDownTimer.start();


        buttonList.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_timer.setBackgroundColor(Color.TRANSPARENT);
                checkCorectness((Button) v);
            }
        });
        buttonList.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_timer.setBackgroundColor(Color.TRANSPARENT);
                checkCorectness((Button)v);
            }
        });
        buttonList.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_timer.setBackgroundColor(Color.TRANSPARENT);
                checkCorectness((Button)v);
            }
        });
        buttonList.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_timer.setBackgroundColor(Color.TRANSPARENT);
                checkCorectness((Button)v);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionAnswered)
                    nextQuestion();
            }
        });
    }

    private void checkCorectness(Button choice){
        if(!questionAnswered) {
            questionAnswered = true;
            countDownTimer.cancel();
            if (choice.equals(correctButton)) {
                choice.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            } else {
                correctButton.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                choice.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
            }
        }
    }

    private void nextQuestion(){
        if(questionIter.hasNext()) {
            questionAnswered = false;
            questionCounter++;
            textView_questionNumber.setText(questionCounter + "/7");
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
        }
        else{
            //TODO quiz is done.
        }
    }

    private void resetTimer(){
        countDownTimer.cancel();
        countDownTimer.start();
    }

}
