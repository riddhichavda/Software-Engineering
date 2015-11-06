package group7.travelomania;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUserName,editTextPassword, editTextSecurityAnswer;
    TextView textViewSecurityQuestion, textViewSecurityAnswer, textViewPassword, textViewSecQuestion;
    Button btnLogin;
    int failedAttempts;
    LoginDatabaseHelper loginDbHelper;
    Player p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get Instance  of Database Adapter
        loginDbHelper=new LoginDatabaseHelper(this);
        loginDbHelper=loginDbHelper.open();

        editTextUserName=(EditText)findViewById(R.id.editTextUserNameLogin);
        editTextPassword=(EditText)findViewById(R.id.editTextPasswordLogin);
        editTextSecurityAnswer = (EditText)findViewById(R.id.editTextSecurityAnswer);

        textViewSecurityQuestion = (TextView)findViewById(R.id.textViewSecurityQuestion);
        textViewSecQuestion = (TextView)findViewById(R.id.textViewSecQuestion);
        textViewSecurityAnswer = (TextView)findViewById(R.id.textViewSecurityAnswer);
        textViewPassword = (TextView)findViewById(R.id.textViewPassword);

        btnLogin = (Button)findViewById(R.id.buttonSignIn);


        editTextSecurityAnswer.setVisibility(View.INVISIBLE);
        textViewSecQuestion.setVisibility(View.INVISIBLE);
        textViewSecurityAnswer.setVisibility(View.INVISIBLE);
        textViewSecurityQuestion.setVisibility(View.INVISIBLE);

        failedAttempts = 0;
        p = Player.getInstance(this);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                validateLogin();
            }
        });
    }

    private void validateLogin() {
        String userName = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();
        String answer = editTextSecurityAnswer.getText().toString();

        String dbPassword = loginDbHelper.getPlayerPassword(userName);
        if(failedAttempts >= 3){
            if(answer.equals(loginDbHelper.getPlayerAnswer(userName))){
                Toast.makeText(getApplicationContext(), "Your password is: "+dbPassword, Toast.LENGTH_LONG).show();
                Handler handler = new Handler();
                handler.postDelayed(backToLogin, 2000);
            }
            else{
                Toast.makeText(getApplicationContext(), "Incorrect answer to security question.", Toast.LENGTH_LONG).show();
            }
        }
        else if(password.equals("NOT EXIST") || !password.equals(dbPassword)){
            Toast.makeText(getApplicationContext(), "No registered user with that Username/Password combination.", Toast.LENGTH_LONG).show();
            editTextPassword.setText("");
            failedAttempts++;
            if(failedAttempts==3){
                editTextSecurityAnswer.setVisibility(View.VISIBLE);
                textViewSecQuestion.setVisibility(View.VISIBLE);
                textViewSecurityAnswer.setVisibility(View.VISIBLE);
                textViewSecurityQuestion.setVisibility(View.VISIBLE);

                editTextPassword.setVisibility(View.INVISIBLE);
                textViewPassword.setVisibility(View.INVISIBLE);

                textViewSecQuestion.setText(loginDbHelper.getPlayerQuestion(userName));
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Welcome, " + userName, Toast.LENGTH_SHORT).show();

            p.login(userName);

            Handler handler = new Handler();

            if(p.hasPlayed){
                handler.postDelayed(goToHistory, 1000);
            }
            else{
                handler.postDelayed(goToRules, 1000);
            }

        }
    }

    private Runnable goToRules = new Runnable()
    {
        @Override
        public void run()
        {
            Intent intent = new Intent(getApplicationContext(), RulesActivity.class);
            startActivity(intent);
        }
    };

    private Runnable goToHistory = new Runnable()
    {
        @Override
        public void run()
        {

            Intent intent = new Intent(getApplicationContext(), PlayerHistoryActivity.class);
            startActivity(intent);
        }
    };
    private Runnable backToLogin = new Runnable()
    {
        @Override
        public void run()
        {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        loginDbHelper.close();
    }
}

/*
package group7.travelomania;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity
{
    EditText editTextUserName,editTextPassword,editTextConfirmPassword,editTextFullName,
            editTextSecurityQuestion,editTextSecurityAnswer;
    Button btnCreateAccount;

    LoginDatabaseHelper loginDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // get Instance  of Database Adapter
        loginDbHelper=new LoginDatabaseHelper(this);
        loginDbHelper=loginDbHelper.open();

        // Get References of Views
        editTextUserName=(EditText)findViewById(R.id.editTextUserName);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);
        editTextSecurityQuestion = (EditText)findViewById(R.id.editTextSecurityQuestion);
        editTextSecurityAnswer = (EditText)findViewById(R.id.editTextSecurityAnswer);
        editTextFullName = (EditText)findViewById(R.id.editTextFullName);

        btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                validateSignUp();
            }
        });
    }

    private void validateSignUp(){
        String userName=editTextUserName.getText().toString();
        String password=editTextPassword.getText().toString();
        String confirmPassword=editTextConfirmPassword.getText().toString();
        String fullName = editTextFullName.getText().toString();
        String question = editTextSecurityQuestion.getText().toString();
        String answer = editTextSecurityAnswer.getText().toString();

        // check if any of the fields are vaccant
        if(userName.equals("")||password.equals("")||confirmPassword.equals("")||fullName.equals("")
                ||question.equals("")||answer.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
            return;
        }
        // check if both password matches
        if(!password.equals(confirmPassword))
        {
            Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            // Save the Data in Database
            loginDbHelper.addPlayer(userName, password, fullName, question, answer);
            Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        loginDbHelper.close();
    }
}

 */