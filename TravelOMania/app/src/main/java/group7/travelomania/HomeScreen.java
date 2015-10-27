package group7.travelomania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {

    Button btnSignIn,btnSignUp,btnContinent;
  //  LoginDatabaseHelper loginDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);

//		loginDbHelper = new LoginDatabaseHelper(this);
//		loginDbHelper = loginDbHelper.open();

		btnSignIn = (Button) findViewById(R.id.buttonSignIN);
		btnSignUp = (Button) findViewById(R.id.buttonSignUP);
		btnContinent = (Button) findViewById(R.id.button_ContinentSelection);

		btnContinent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToContinentSelectionActvity();
			}
		});

		btnSignIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToLoginActivity();
			}
		});

		btnSignUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToSignUpActivity();
            }
        });
	}

	private void goToContinentSelectionActvity(){
		Intent intent = new Intent(this, ContinentSelectionActivity.class);
		startActivity(intent);
	}

	private void goToSignUpActivity(){
		Intent intent =new Intent(getApplicationContext(),SignUpActivity.class);
		startActivity(intent);
	}

	private void goToLoginActivity(){
		Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
		startActivity(intent);
	}

    }

