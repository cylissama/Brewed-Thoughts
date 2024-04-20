package com.example.coffeeappcs372final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BrewAdapter extends RecyclerView.Adapter<BrewAdapter.ViewHolder> {

    private final List<BrewModel> brewsList;
    private final LayoutInflater inflater;

    public BrewAdapter(Context context, List<BrewModel> tagsList) {
        this.inflater = LayoutInflater.from(context);
        this.brewsList = tagsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.brew_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BrewModel brew = brewsList.get(position);
        String text = "Brew on " + brew.getTime() +
                "\nBeans: " + brew.getBeans() +
                "\nBrewer: " + brew.getBrewer();

        holder.brewView.setText(text);
    }

    @Override
    public int getItemCount() {
        return brewsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView brewView;

        ViewHolder(View itemView) {
            super(itemView);
            brewView = itemView.findViewById(R.id.brewView);
        }
    }
}
