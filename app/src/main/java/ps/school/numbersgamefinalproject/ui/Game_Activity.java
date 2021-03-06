package ps.school.numbersgamefinalproject.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.time.LocalDate;
import java.util.ArrayList;

import ps.school.numbersgamefinalproject.R;
import ps.school.numbersgamefinalproject.constant.Constant;
import ps.school.numbersgamefinalproject.database.DBHelper;
import ps.school.numbersgamefinalproject.viewmodel.Question;
import ps.school.numbersgamefinalproject.viewmodel.Util;

public class Game_Activity extends AppCompatActivity {
    TextView score, tv_name, tv_age;
    Button check, new_game;
    EditText enter;
    Toolbar toolbar;
    //  عرفنا هين ال TextView وعرفنا كمان ArrayList عشان نحط الأرقام فيها
    TextView number1, number2, number3, number4, number5, number6, number7, number8, number9;
    ArrayList<String> game = new ArrayList<String>();
    String Enter_text;
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
        toolbar = findViewById(R.id.toolbar);


        mediaPlayer = MediaPlayer.create(this, R.raw.bacmusec);
        mediaPlayer.start();

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animeion2);
        check.startAnimation(animation1);
        new_game.startAnimation(animation1);
        enter.startAnimation(animation1);
        score.startAnimation(animation1);
        tv_name.startAnimation(animation1);


        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SAVE, MODE_PRIVATE);
        String user = sharedPreferences.getString(Constant.USER, "");
        toolbar.inflateMenu(R.menu.tools_menu);
        toolbar.setSubtitle(R.string.new_game);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.stings) {
                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    mediaPlayer.pause();
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.out) {
                    SharedPreferences sharedPreferences = getSharedPreferences(Constant.SAVE, MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putBoolean(Constant.REMEMBER, false);
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

        toolbar.setSubtitleTextColor(Color.parseColor(getString(R.string.toolbar)));
        db = new DBHelper(getApplicationContext());
        tv_name.setText(db.getName(user));
        tv_age.setText(db.getAge(user));
        score.setText(db.getScore(user));

        newGame();
        new_game.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                newGame();
//                db.clear(user);
                if (Integer.parseInt(db.getScore(user)) > 0) {
                    db.updateLos(user);
                }
                score.setText(db.getScore(user));
                LocalDate myObj = LocalDate.now();
                db.insertGame(tv_name.getText().toString(), score.getText().toString(), String.valueOf(myObj));

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
                    Toast.makeText(context, R.string.enter_a_umber, Toast.LENGTH_SHORT).show();
                } else {

                    if (Enter.equals(Enter_text)) {
                        MediaPlayer mediaPlayer1 = MediaPlayer.create(Game_Activity.this, R.raw.correct);
                        mediaPlayer1.start();
                        new AlertDialog.Builder(context)
                                .setTitle(R.string.well_done)
                                .setMessage(R.string.your_answer_is_correct)
                                .setPositiveButton(R.string.next_level, new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    public void onClick(DialogInterface dialog, int which) {

                                        newGame();
                                        db.update(user);
                                        score.setText(String.valueOf(db.getScore(user)));
                                        LocalDate myObj = LocalDate.now();
                                        db.insertGame(tv_name.getText().toString(), score.getText().toString(), String.valueOf(myObj));                                    }
                                })
                                .setNegativeButton(R.string.cancel, null)
                                .setIcon(R.drawable.ic_baseline_check_24)
                                .setCancelable(false)
                                .show();

                    } else {
                        MediaPlayer mediaPlayer2 = MediaPlayer.create(Game_Activity.this, R.raw.wrong);
                        mediaPlayer2.start();
                        new AlertDialog.Builder(context)
                                .setTitle(R.string.wrong_answer)
                                .setMessage(R.string.try_again)
                                .setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    public void onClick(DialogInterface dialog, int which) {
                                        newGame();
                                        if (Integer.parseInt(db.getScore(user)) > 0) {
                                            db.updateLos(user);
                                        }
                                        score.setText(db.getScore(user));
                                        LocalDate myObj = LocalDate.now();
                                        db.insertGame(tv_name.getText().toString(), score.getText().toString(), String.valueOf(myObj));
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        newGame();
                                        if (Integer.parseInt(db.getScore(user)) > 0) {
                                            db.updateLos(user);
                                        }
                                        score.setText(db.getScore(user));
                                        LocalDate myObj = LocalDate.now();
                                        db.insertGame(tv_name.getText().toString(), score.getText().toString(), String.valueOf(myObj));
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
        Enter_text = String.valueOf(question.getHiddenNumber());
        Toast.makeText(getApplicationContext(), Enter_text, Toast.LENGTH_LONG).show();
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


        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.amime);
        number1.startAnimation(animation1);
        number2.startAnimation(animation1);
        number3.startAnimation(animation1);
        number4.startAnimation(animation1);
        number5.startAnimation(animation1);
        number6.startAnimation(animation1);
        number7.startAnimation(animation1);
        number8.startAnimation(animation1);
        number9.startAnimation(animation1);
    }

    @Override
    protected void onStart() {
        mediaPlayer.start();
        super.onStart();
    }
}

