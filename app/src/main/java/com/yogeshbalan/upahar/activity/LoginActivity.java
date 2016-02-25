package com.yogeshbalan.upahar.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.yogeshbalan.upahar.Constants;
import com.yogeshbalan.upahar.R;
import com.yogeshbalan.upahar.SessionManager;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout username, email, password, phone;
    Button loginButton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    String emailtxt;
    String phonetxt;
    SessionManager sessionManager;
    SharedPreferences sharedpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(this);

        username = (TextInputLayout) findViewById(R.id.usernameWrapper);
        email = (TextInputLayout) findViewById(R.id.emailWrapper);
        password = (TextInputLayout) findViewById(R.id.passwordWrapper);
        //phone = (TextInputLayout) findViewById(R.id.phoneWrapper);
        loginButton = (Button) findViewById(R.id.login_button);
        signup = (Button) findViewById(R.id.signup_button);

        // Login Button Click Listener
        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                hideKeyboard();
                // Retrieve the text entered from the EditText
                usernametxt = username.getEditText().getText().toString();
                passwordtxt = password.getEditText().getText().toString();

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    // If user exist and authenticated, send user to Welcome.class
                                    Intent intent = new Intent(
                                            LoginActivity.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully Logged in",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "No such user exist, please signup",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        // Sign up Button Click Listener
        signup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getEditText().getText().toString();
                passwordtxt = password.getEditText().getText().toString();
                emailtxt = email.getEditText().getText().toString();
                //phonetxt = phone.getEditText().getText().toString();

                // Force user to fill up the form
                if (usernametxt.equals("") && passwordtxt.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please complete the sign up form",
                            Toast.LENGTH_LONG).show();

                } else {
                    // Save new user data into Parse.com Data Storage
                    final ParseUser user = new ParseUser();
                    final ParseObject loginObject = new ParseObject("user");
                    loginObject.put("username", usernametxt);
                    loginObject.put("email", emailtxt);
                    loginObject.put("password", passwordtxt);
                    user.setUsername(usernametxt);
                    user.setPassword(passwordtxt);
                    user.setEmail(emailtxt);

                    try {
                        loginObject.save();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                hideKeyboard();

                                //user.put("phone", phonetxt);
                                // Show a simple Toast message upon successful registration
                                sessionManager.createLoginSession(usernametxt, emailtxt, passwordtxt);
                                //sharedpref = this.getShsaredPreference(getString(Constants.PREFERENCE_KEY, Context.MODE_PRIVATE);
                                sharedpref = getSharedPreferences(Constants.PREFERENCE_KEY, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpref.edit();
                                editor.putString(Constants.KEY_EMAIL, email.getEditText().getText().toString());
                                editor.putString(Constants.KEY_USERNAME,username.getEditText().getText().toString());
                                editor.putString(Constants.KEY_PASSWORD, password.getEditText().getText().toString());
                                editor.commit();
                                Toast.makeText(getApplicationContext(),
                                        "Successfully Signed up, please log in.",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Sign up Error", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
                }

            }
        });


    }

    @Override
    protected void onResume() {
        sharedpref = this.getSharedPreferences(Constants.PREFERENCE_KEY, Context.MODE_PRIVATE);
        if(sharedpref.contains(Constants.KEY_USERNAME) && sharedpref.contains(Constants.KEY_EMAIL) && sharedpref.contains(Constants.KEY_PASSWORD))
        {
                Intent dashboard = new Intent(this, MainActivity.class);
                startActivity(dashboard);

        }
        super.onResume();
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
