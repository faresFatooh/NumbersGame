package ps.school.numbersgamefinalproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import ps.school.numbersgamefinalproject.database.DBHelper;
import ps.school.numbersgamefinalproject.R;

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
                        Toast.makeText(Login_Activity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Game_Activity.class);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putBoolean("rem", remember.isChecked());
                        myEdit.apply();
                        startActivity(intent);
                        finish();
                    }else {
                        input_user_name_login.setError("theirs no user name registered");
                        input_password_login.setError("theirs no password name registered");
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
            input_password_login.setError("Password is too short (Min. 8 Characters)");
            return false;
        }

        input_password_login.setError(null);
        return true;
    }

}