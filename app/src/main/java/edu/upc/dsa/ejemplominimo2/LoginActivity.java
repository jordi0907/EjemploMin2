package edu.upc.dsa.ejemplominimo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static final String MY_PREFS_NAME = "";
    private EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.Name);
        password = (EditText) findViewById(R.id.Password);

        Intent intent = getIntent();
        String usuarioName= intent.getStringExtra("User1");
        NotifyUser("el user" + usuarioName);
    }

    public void onButtonLoginClick(View view) {

        String user = username.getText().toString();
        String pass = password.getText().toString();

       // NotifyUser("el user" + user);
        //NotifyUser("la contrase" + pass);

        if (user.equals("user") && pass.equals("dsamola")){

            SharedPreferences settings  = getSharedPreferences("UserInfo", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("Username", user);
            editor.putString("Password", pass);
            editor.commit();

            Intent returnIntent = new Intent();
            returnIntent.putExtra("User",user);
            returnIntent.putExtra("Pass",pass);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
        else{
            NotifyUser("Unauthorized");

        }

    }

    private void NotifyUser(String MSG){
        Toast toast = Toast.makeText(LoginActivity.this,MSG,Toast.LENGTH_SHORT);
        toast.show();
    }











}