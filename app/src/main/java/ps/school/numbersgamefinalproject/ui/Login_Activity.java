package ps.school.numbersgamefinalproject.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import ps.school.numbersgamefinalproject.R;
import ps.school.numbersgamefinalproject.database.DBHelper;

public class Login_Activity extends AppCompatActivity {
    EditText user_name_login, password_login;
    Button login, register;
    RadioGroup radioGroup;
    CheckBox remember;
    DBHelper DB;
    TextInputLayout input_user_name_login, input_password_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user_name_login = findViewById(R.id.user_name_login);
        password_login = findViewById(R.id.password_login);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        radioGroup = findViewById(R.id.radio_group);
        remember = findViewById(R.id.remember);
        input_user_name_login = findViewById(R.id.input_user_name_login);
        input_password_login = findViewById(R.id.input_password_login);
        DB = new DBHelper(this);

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animeion2);
        register.startAnimation(animation1);
        login.startAnimation(animation1);
        user_name_login.startAnimation(animation1);
        password_login.startAnimation(animation1);
        remember.startAnimation(animation1);
        input_password_login.startAnimation(animation1);
        input_user_name_login.startAnimation(animation1);

        SharedPreferences sharedPreferences = getSharedPreferences("Save", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        boolean rem = sharedPreferences.getBoolean("rem", false);
        String user = sharedPreferences.getString("user", "");
        String password_this = sharedPreferences.getString("password", "");
        myEdit.apply();

        user_name_login = findViewById(R.id.user_name_login);
        password_login = findViewById(R.id.password_login);
        if (user != null) {
            user_name_login.setText(user);
            password_login.setText(password_this);
        }

        if (rem) {
            Intent intent = new Intent(getApplicationContext(), Game_Activity.class);
            startActivity(intent);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = user_name_login.getText().toString().trim();
                String password = password_login.getText().toString().trim();

                validateUserName(userName);
                validatePassword(password);

                if (!password.isEmpty() && !userName.isEmpty()) {
                    Boolean checkuserpass = DB.checkusernamepassword(userName, password);
                    if (checkuserpass == true) {
                        Toast.makeText(Login_Activity.this, R.string.sign_in_successful, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Game_Activity.class);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putBoolean("rem", remember.isChecked());
                        myEdit.apply();
                        startActivity(intent);
                        finish();
                    }else {
                        input_user_name_login.setError(getString(R.string.theirs_no_password_name_registered));
                        input_password_login.setError(getString(R.string.theirs_no_password_name_registered));
                    }
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), register_Activity.class);
                startActivity(intent);
            }
        });


    }

    private Boolean validateUserName(String userName) {

        if (userName.isEmpty()) {
            input_user_name_login.setError(getString(R.string.enterName));
            return false;
        }
        input_user_name_login.setError(null);
        return true;
    }

    private Boolean validatePassword(String password) {

        if (password.isEmpty()) {
            input_password_login.setError(getString(R.string.userpasseord));
            return false;
        }

        if (password.length() < 8) {
            input_password_login.setError(getString(R.string.min_eight_characters));
            return false;
        }

        input_password_login.setError(null);
        return true;
    }

}