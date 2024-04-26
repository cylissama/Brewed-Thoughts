package com.example.coffeeappcs372final.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coffeeappcs372final.R;
import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.ViewHolder> {

    private final Context context;
    private final List<String> tipsList;

    public TipsAdapter(Context context, List<String> tipsList) {
        this.context = context;
        this.tipsList = tipsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tips_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String tip = tipsList.get(position);
        holder.tipTextView.setText(tip);
    }

    @Override
    public int getItemCount() {
        return tipsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tipTextView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.tips_card_view);
            tipTextView = itemView.findViewById(R.id.tip_text_view);
        }
    }
}
