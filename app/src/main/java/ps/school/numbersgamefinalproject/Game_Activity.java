package ps.school.numbersgamefinalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class Game_Activity extends AppCompatActivity {
    TextView score, tv_name, tv_age;
    Button check, new_game;
    EditText enter;
    Toolbar toolbar;
    //  عرفنا هين ال TextView وعرفنا كمان ArrayList عشان نحط الأرقام فيها
    TextView number1, number2, number3, number4, number5, number6, number7, number8, number9;
    ArrayList<String> cars = new ArrayList<String>();
    String Entere;
    Context context = this;
    int sco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        score = findViewById(R.id.score);
        tv_name = findViewById(R.id.tv_name);
        tv_age = findViewById(R.id.tv_age);
        enter = findViewById(R.id.enter);
        check = findViewById(R.id.check);
        new_game = findViewById(R.id.new_game);
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        number4 = findViewById(R.id.number4);
        number5 = findViewById(R.id.number5);
        number6 = findViewById(R.id.number6);
        number7 = findViewById(R.id.number7);
        number8 = findViewById(R.id.number8);
        number9 = findViewById(R.id.number9);
        toolbar = findViewById(R.id.toolbar2);


        score = findViewById(R.id.score);
        tv_name = findViewById(R.id.tv_name);
        tv_age = findViewById(R.id.tv_age);
        enter = findViewById(R.id.enter);
        check = findViewById(R.id.check);
        new_game = findViewById(R.id.new_game);
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        number4 = findViewById(R.id.number4);
        number5 = findViewById(R.id.number5);
        number6 = findViewById(R.id.number6);
        number7 = findViewById(R.id.number7);
        number8 = findViewById(R.id.number8);
        number9 = findViewById(R.id.number9);
        toolbar = findViewById(R.id.toolbar2);

        toolbar.inflateMenu(R.menu.tools_menu);
        toolbar.setSubtitle("sex");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.stings) {
                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.out) {
                    SharedPreferences sharedPreferences = getSharedPreferences("Save", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putBoolean("rem", false);
                    myEdit.apply();
                    Intent intent2 = new Intent(getApplicationContext(), Login_Activity.class);
                    startActivity(intent2);
                    finish();
                    return true;
                } else {
                    return false;
                }

            }
        });

        //بعدها استدعينا كلاس الQuestion
        Question question = Util.generateQuestion();
        //هين عرفنا questions   من نوع ARRAY

        String[][] questions = question.getQuestion();
        // بعدها عملنا الفور لوب عشان تطبع محتويات الأري

        for (int i = 0; i < questions.length; i++) {
            // questionsعملنا فور لوب ثانية للمتغير

            for (int j = 0; j < questions[i].length; j++) {
                //   ي بعدها ضفنا على الArrayList الفور لوب الي عملناه

                cars.add(j, questions[i][j]);
                //هين لما يدخل الرقم المستخدم يتأكل منه

                Entere = String.valueOf(question.getHiddenNumber());
                new_game.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), Game_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }
        //هين الأرقام المتغيرة من أول عنصر


        number1.setText(cars.get(8));
        number2.setText(cars.get(7));
        number3.setText(cars.get(6));
        number4.setText(cars.get(5));
        number5.setText(cars.get(4));
        number6.setText(cars.get(3));
        number7.setText(cars.get(2));
        number8.setText(cars.get(1));
        number9.setText(cars.get(0));

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans = enter.getText().toString().trim();
                String Enter = enter.getText().toString();
                if (ans.isEmpty()) {
                    Toast.makeText(context, "Enter A Number", Toast.LENGTH_SHORT).show();
                } else {


                    if (Enter.equals(Entere)) {
                        new AlertDialog.Builder(context)
                                .setTitle("Well done")
                                .setMessage("your answer is correct")
                                .setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .setIcon(R.drawable.ic_baseline_check_24)
                                .setCancelable(false)
                                .show();
                        String x = score.getText().toString();
                        score.setText("score : " + (sco += 1));
                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("wrong answer")
                                .setMessage("Try again...")
                                .setPositiveButton("New game", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .setIcon(R.drawable.ic_baseline_dangerous_24)
                                .show();
                    }
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tools_menu, menu);
        return true;

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tools_menu, menu);
    }
}




