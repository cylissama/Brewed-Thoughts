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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

public class BrewAdapter extends RecyclerView.Adapter<BrewAdapter.ViewHolder> {

    private final List<BrewModel> brewsList;
    private final LayoutInflater inflater;
    private final DataBaseHelper dbHelper;

    public BrewAdapter(Context context, List<BrewModel> tagsList, DataBaseHelper dbHelper) {
        this.inflater = LayoutInflater.from(context);
        this.brewsList = tagsList;
        this.dbHelper = dbHelper;
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

        holder.brewContainer.setOnClickListener(v -> showDialog(brew));
    }

    private void showDialog(BrewModel brew) {

        final Dialog dialog = new Dialog(inflater.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);


        Button delete = dialog.findViewById(R.id.deleteButton);
        Button note = dialog.findViewById(R.id.addNoteButton);

        TextView title = dialog.findViewById(R.id.chooseTxt);
        TextView setBeans = dialog.findViewById(R.id.beansText);
        TextView setBrewer = dialog.findViewById(R.id.brewerText);
        TextView setMethod = dialog.findViewById(R.id.methodText);
        TextView setGrams = dialog.findViewById(R.id.gramsText);
        TextView setWaterML = dialog.findViewById(R.id.mlText);
        TextView setWaterTemp = dialog.findViewById(R.id.tempText);

        title.setText("Brewed on: " + brew.getTime());
        setBeans.setText(String.valueOf("Beans: " + brew.getBeans()));
        setBrewer.setText(String.valueOf("Brewer: " + brew.getBrewer()));
        setMethod.setText(String.valueOf("Method: " + brew.getMethod()));
        setGrams.setText(String.valueOf("Coffee (gm): " + brew.getGrams()));
        setWaterML.setText(String.valueOf("Water (ml): " + brew.getWater()));
        setWaterTemp.setText(String.valueOf("Temp (°C): " + brew.getTemp()));

        delete.setOnClickListener(v -> {
            // Delete the brew from the database
            if (dbHelper.deleteBrew(brew.getId())) {
                // Remove brew from the list and notify adapter
                int position = brewsList.indexOf(brew);
                brewsList.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(inflater.getContext(), "Brew deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(inflater.getContext(), "Failed to delete brew", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss(); // Close the dialog
        });


        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(inflater.getContext(), "Note!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);

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
