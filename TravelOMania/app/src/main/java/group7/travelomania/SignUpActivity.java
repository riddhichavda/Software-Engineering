package group7.travelomania;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

        // check if any of the fields are vacant
        if(userName.equals("")||password.equals("")||confirmPassword.equals("")||fullName.equals("")
                ||question.equals("")||answer.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Field Vacant", Toast.LENGTH_LONG).show();
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
            Handler handler = new Handler();
            handler.postDelayed(goToHomeScreen, 1000);

        }
    }

    private Runnable goToHomeScreen = new Runnable()
    {
        @Override
        public void run()
        {
            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
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
