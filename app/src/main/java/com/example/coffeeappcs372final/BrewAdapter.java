package com.example.coffeeappcs372final;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

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

        holder.brewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                // Example: Log or Toast
                //Toast.makeText(v.getContext(), "Button clicked at position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog() {

        final Dialog dialog = new Dialog(inflater.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        LinearLayout editLayout = dialog.findViewById(R.id.layoutEdit);
        LinearLayout editLayout2 = dialog.findViewById(R.id.layoutEdit2);

        editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    @Override
    public int getItemCount() {
        return brewsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView brewView;
        CardView brewContainer;

        ViewHolder(View itemView) {
            super(itemView);
            brewView = itemView.findViewById(R.id.brewView);
            brewContainer = itemView.findViewById(R.id.brewContainer);
        }
    }
}
