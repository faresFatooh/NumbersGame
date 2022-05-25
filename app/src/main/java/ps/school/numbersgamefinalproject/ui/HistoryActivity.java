package ps.school.numbersgamefinalproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import ps.school.numbersgamefinalproject.R;
import ps.school.numbersgamefinalproject.adapter.HistoryAdapter;
import ps.school.numbersgamefinalproject.database.DBHelper;
import ps.school.numbersgamefinalproject.model.History;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView listView;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.re_game_history);
        db = new DBHelper(getApplicationContext());

        ArrayList<History> histories = db.getAllGames();

        listView.setLayoutManager(new LinearLayoutManager(this));
        HistoryAdapter historyAdapter = new HistoryAdapter(histories);
        listView.setAdapter(historyAdapter);
    }
}