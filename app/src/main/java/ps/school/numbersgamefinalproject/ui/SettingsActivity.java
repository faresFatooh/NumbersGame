package ps.school.numbersgamefinalproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import ps.school.numbersgamefinalproject.R;
import ps.school.numbersgamefinalproject.adapter.HistoryAdapter;
import ps.school.numbersgamefinalproject.database.DBHelper;
import ps.school.numbersgamefinalproject.model.History;

public class SettingsActivity extends AppCompatActivity {
    Button show, show_last, change, clear;
    DBHelper db;
    TextInputLayout password;
    TextInputLayout re_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        show = findViewById(R.id.show);
        show_last = findViewById(R.id.show_last);
        change = findViewById(R.id.change);
        clear = findViewById(R.id.clear);
        db = new DBHelper(getApplicationContext());


        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animeion2);
        show.startAnimation(animation1);
        show_last.startAnimation(animation1);
        change.startAnimation(animation1);
        clear.startAnimation(animation1);

        SharedPreferences sharedPreferences = getSharedPreferences("Save", MODE_PRIVATE);
        String user = sharedPreferences.getString("user", "");


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
            }
        });


        show_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<History> histories = db.getAllGames();
                int lastIndex = histories.size() - 1;
                if (!(histories.size() == 0)) {
                    if (String.valueOf(histories.get(lastIndex).getDate()).equals(null)) {
                        Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), String.valueOf(histories.get(lastIndex).getDate()), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();

                }

            }
        });


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.password_changing_settings);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button done = dialog.findViewById(R.id.done);


                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        password = dialog.findViewById(R.id.dialog_password);
                        re_password = dialog.findViewById(R.id.re_password_dialog);
                        String password_setting = password.getEditText().getText().toString();
                        String _re_password = re_password.getEditText().getText().toString();
                        if (validatePassword(password_setting, _re_password)) {
                            db.changePassword(user, password_setting);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("password", password_setting);
                            myEdit.apply();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();

            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.delete);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button done = dialog.findViewById(R.id.yes);
                Button no = dialog.findViewById(R.id.no);

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.clearHistory();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

        });

    }

    private Boolean validatePassword(String password_now, String rePassword) {

        if (password_now.isEmpty()) {
            password.setError(getString(R.string.userpasseord));
            return false;
        }
        if (rePassword.isEmpty()) {
            re_password.setError(getString(R.string.userpasseordre));
            return false;
        }
        if (!(password_now.equals(rePassword))) {
            re_password.setError(getString(R.string.comformPassword));
            password.setError(getString(R.string.comformPassword));
            return false;
        }
        if (password_now.length() < 8) {
//            في مشكلة
            password.setError("Password is too short (Min. 8 Characters)");
            return false;
        }
        password.setError(null);
        re_password.setError(null);
        return true;
    }

}