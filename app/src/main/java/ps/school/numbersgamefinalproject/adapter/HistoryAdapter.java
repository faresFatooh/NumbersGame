package ps.school.numbersgamefinalproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ps.school.numbersgamefinalproject.R;
import ps.school.numbersgamefinalproject.model.History;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private ArrayList<History> listData;

    // RecyclerView recyclerView;
    public HistoryAdapter(ArrayList<History> listData) {
        this.listData = listData;
    }

    public HistoryAdapter() {
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.show_all_game, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final History myListData = listData.get(position);
        holder.textView1.setText(myListData.getUsername());
        holder.textView2.setText(myListData.getDate());
        holder.show_item_score.setText(myListData.getScore());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;
        public TextView show_item_score;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView1 = itemView.findViewById(R.id.text_view_full);
            this.textView2 = itemView.findViewById(R.id.text_view_date);
            this.show_item_score = itemView.findViewById(R.id.show_item_score);
        }
    }
}