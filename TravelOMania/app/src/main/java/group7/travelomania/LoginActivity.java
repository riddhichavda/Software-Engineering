package group7.travelomania;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUserName,editTextPassword;
    Button btnLogin;

    LoginDatabaseHelper loginDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get Instance  of Database Adapter
        loginDbHelper=new LoginDatabaseHelper(this);
        loginDbHelper=loginDbHelper.open();

        editTextUserName=(EditText)findViewById(R.id.editTextUserNameLogin);
        editTextPassword=(EditText)findViewById(R.id.editTextPasswordLogin);

        btnLogin = (Button)findViewById(R.id.buttonSignIn);

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

        String dbPassword = loginDbHelper.getPlayerPassword(userName);

        if(password.equals("NOT EXIST") || !password.equals(dbPassword)){
            Toast.makeText(getApplicationContext(), "No registered user with that Username/Password combination.", Toast.LENGTH_LONG).show();
            editTextPassword.setText("");
            return;
        }
        else{
            Toast.makeText(getApplicationContext(), "Welcome, " + userName, Toast.LENGTH_SHORT);
            int player_id = loginDbHelper.getPlayerId(userName);
            Player p = Player.getInstance(this);
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
            //TODO: Change this line to go to history
            Intent intent = new Intent(getApplicationContext(), RulesActivity.class);
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