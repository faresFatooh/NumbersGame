package ps.school.numbersgamefinalproject.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
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

import ps.school.numbersgamefinalproject.database.DBHelper;
import ps.school.numbersgamefinalproject.viewmodel.Question;
import ps.school.numbersgamefinalproject.R;
import ps.school.numbersgamefinalproject.viewmodel.Util;

public class Game_Activity extends AppCompatActivity {
    TextView score, tv_name, tv_age;
    Button check, new_game;
    EditText enter;
    Toolbar toolbar;
    //  عرفنا هين ال TextView وعرفنا كمان ArrayList عشان نحط الأرقام فيها
    TextView number1, number2, number3, number4, number5, number6, number7, number8, number9;
    ArrayList<String> game = new ArrayList<String>();
    String Entere;
    Context context = this;
    DBHelper db;

    MediaPlayer mediaPlayer;

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
        mediaPlayer = MediaPlayer.create(this, R.raw.bacmusec);
        mediaPlayer.start();
        SharedPreferences sharedPreferences = getSharedPreferences("Save", MODE_PRIVATE);
        String user = sharedPreferences.getString("user", "");
        toolbar.inflateMenu(R.menu.tools_menu);
        toolbar.setSubtitle("Numbers Game");
        toolbar.setSubtitleTextColor(Color.parseColor("#fffe00"));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.stings) {
                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    mediaPlayer.pause();
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.out) {
                    SharedPreferences sharedPreferences = getSharedPreferences("Save", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putBoolean("rem", false);
                    myEdit.apply();
                    Intent intent2 = new Intent(getApplicationContext(), Login_Activity.class);
                    mediaPlayer.pause();
                    startActivity(intent2);
                    finish();
                    return true;
                } else {
                    return false;
                }

            }
        });
        db = new DBHelper(getApplicationContext());
        tv_name.setText(db.getName(user));
        tv_age.setText(db.getAge(user));
        score.setText(db.getScore(user));

        newGame();
        new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
//                db.clear(user);
                if (Integer.parseInt(db.getScore(user)) > 0) {
                    db.updateLos(user);
                }
                score.setText(db.getScore(user));
                db.insertGame(tv_name.getText().toString(), score.getText().toString(), String.valueOf(System.currentTimeMillis()));

            }
        });
        //هين الأرقام المتغيرة من أول عنصر

        check.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String ans = enter.getText().toString().trim();
                String Enter = enter.getText().toString();
                if (ans.isEmpty()) {
                    Toast.makeText(context, "Enter A Number", Toast.LENGTH_SHORT).show();
                } else {

                    if (Enter.equals(Entere)) {
                       MediaPlayer mediaPlayer1 = MediaPlayer.create(Game_Activity.this, R.raw.correct);
                        mediaPlayer1.start();
                        new AlertDialog.Builder(context)
                                .setTitle("Well done")
                                .setMessage("your answer is correct")
                                .setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        newGame();
                                        db.update(user);
                                        score.setText(String.valueOf(db.getScore(user)));
                                        db.insertGame(tv_name.getText().toString(), score.getText().toString(), String.valueOf(System.currentTimeMillis()));
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .setIcon(R.drawable.ic_baseline_check_24)
                                .setCancelable(false)
                                .show();

                    } else {
                        MediaPlayer mediaPlayer2 = MediaPlayer.create(Game_Activity.this, R.raw.wrong);
                        mediaPlayer2.start();
                        new AlertDialog.Builder(context)
                                .setTitle("wrong answer")
                                .setMessage("Try again...")
                                .setPositiveButton("New game", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        newGame();
                                        if (Integer.parseInt(db.getScore(user)) > 0) {
                                            db.updateLos(user);
                                        }
                                        score.setText(db.getScore(user));
                                        db.insertGame(tv_name.getText().toString(), score.getText().toString(), String.valueOf(System.currentTimeMillis()));

                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        newGame();
                                        if (Integer.parseInt(db.getScore(user)) > 0) {
                                            db.updateLos(user);
                                        }
                                        score.setText(db.getScore(user));
                                        db.insertGame(tv_name.getText().toString(), score.getText().toString(), String.valueOf(System.currentTimeMillis()));

                                    }
                                })
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

    public void newGame() {
        //بعدها استدعينا كلاس الQuestion
        Question question = Util.generateQuestion();
        //هين عرفنا questions   من نوع ARRAY
        Entere = String.valueOf(question.getHiddenNumber());
        Toast.makeText(getApplicationContext(), Entere, Toast.LENGTH_LONG).show();
        String[][] questions = question.getQuestion();
        // بعدها عملنا الفور لوب عشان تطبع محتويات الأري

        for (int i = 0; i < questions.length; i++) {
            // questionsعملنا فور لوب ثانية للمتغير

            for (int j = 0; j < questions[i].length; j++) {
                //   ي بعدها ضفنا على الArrayList الفور لوب الي عملناه

                game.add(j, questions[i][j]);
                //هين لما يدخل الرقم المستخدم يتأكل منه
            }
        }
        number1.setText(game.get(8));
        number2.setText(game.get(7));
        number3.setText(game.get(6));
        number4.setText(game.get(5));
        number5.setText(game.get(4));
        number6.setText(game.get(3));
        number7.setText(game.get(2));
        number8.setText(game.get(1));
        number9.setText(game.get(0));
        enter.setText("");
    }

    @Override
    protected void onStart() {
        mediaPlayer.start();
        super.onStart();
    }
}
