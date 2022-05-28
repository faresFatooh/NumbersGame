package ps.school.numbersgamefinalproject.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ps.school.numbersgamefinalproject.R;
import ps.school.numbersgamefinalproject.adapter.HistoryAdapter;
import ps.school.numbersgamefinalproject.constant.Constant;
import ps.school.numbersgamefinalproject.database.DBHelper;
import ps.school.numbersgamefinalproject.model.History;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView listView;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SAVE, MODE_PRIVATE);
        String user = sharedPreferences.getString(Constant.USER, "");

        listView = findViewById(R.id.re_game_history);
        db = new DBHelper(getApplicationContext());

        ArrayList<History> histories = db.getAllGames(user);

        listView.setLayoutManager(new LinearLayoutManager(this));
        HistoryAdapter historyAdapter = new HistoryAdapter(histories);
        listView.setAdapter(historyAdapter);
    }
}