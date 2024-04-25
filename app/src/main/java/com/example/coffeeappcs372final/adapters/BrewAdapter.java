package com.example.coffeeappcs372final.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeappcs372final.R;
import com.example.coffeeappcs372final.database.BrewModel;
import com.example.coffeeappcs372final.database.DataBaseHelper;
import com.example.coffeeappcs372final.presentation.Brew;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BrewAdapter extends RecyclerView.Adapter<BrewAdapter.ViewHolder> implements Filterable {

    private final List<BrewModel> brewsList;
    private final List<BrewModel> brewsListFull;
    private final LayoutInflater inflater;
    private final DataBaseHelper dbHelper;
    private Boolean isCard = false;
    private Boolean boxed = false;

    public BrewAdapter(Context context, List<BrewModel> brewsList, DataBaseHelper dbHelper, Boolean isCard, Boolean boxed) {
        this.inflater = LayoutInflater.from(context);
        this.brewsList = brewsList;
        this.dbHelper = dbHelper;
        this.brewsListFull = new ArrayList<>(brewsList);
        this.isCard = isCard;
        this.boxed = boxed;
    }
    @Override
    public Filter getFilter() {
        return brewFilter;
    }
    Filter brewFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<BrewModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(brewsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (BrewModel item : brewsListFull) {
                    // Check if the search term is found in any of the attributes
                    if (item.getBeans().toLowerCase().contains(filterPattern) ||
                            item.getBrewer().toLowerCase().contains(filterPattern) ||
                            item.getMethod().toLowerCase().contains(filterPattern) ||
                            item.getFavorite().toString().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            brewsList.clear();
            brewsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.brew_item, parent, false);
        if (this.isCard) {
            view = inflater.inflate(R.layout.brew_item_favorites, parent, false);
        }
        if (this.boxed) {
            view = inflater.inflate(R.layout.brew_item_boxed, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BrewModel brew = brewsList.get(position);
        String brewText = "Brew on " + brew.getTime() +
                "\nBeans: " + brew.getBeans() +
                "\nBrewer: " + brew.getBrewer();
        String noteText = brew.getNote();

        holder.brewView.setText(brewText);

        holder.noteView.setText(noteText);

        holder.brewContainer.setOnClickListener(v -> showDialog(brew));
    }

    private void showDialog(BrewModel brew) {

        final Dialog dialog = new Dialog(inflater.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.brew_popup_dialog);

        // clickables
        Button delete = dialog.findViewById(R.id.deleteButton);
        Button note = dialog.findViewById(R.id.addNoteButton);
        ImageView favorite = dialog.findViewById(R.id.favoriteButton);

        TextView title = dialog.findViewById(R.id.chooseTxt);
        TextView setBeans = dialog.findViewById(R.id.beansText);
        TextView setBrewer = dialog.findViewById(R.id.brewerText);
        TextView setMethod = dialog.findViewById(R.id.methodText);
        TextView setGrams = dialog.findViewById(R.id.gramsText);
        TextView setWaterML = dialog.findViewById(R.id.mlText);
        TextView setWaterTemp = dialog.findViewById(R.id.tempText);
        TextView setNote = dialog.findViewById(R.id.noteText);

        title.setText("Brewed on: " + brew.getTime());
        setBeans.setText(String.valueOf("Beans: " + brew.getBeans()));
        setBrewer.setText(String.valueOf("Brewer: " + brew.getBrewer()));
        setMethod.setText(String.valueOf("Method: " + brew.getMethod()));
        setGrams.setText(String.valueOf("Coffee (gm): " + brew.getGrams()));
        setWaterML.setText(String.valueOf("Water (ml): " + brew.getWater()));
        setWaterTemp.setText(String.valueOf("Temp (°C): " + brew.getTemp()));
        if (brew.getNote() != null) {
            setNote.setText(String.valueOf("Note: " + brew.getNote()));
        } else {
            setNote.setText(String.valueOf("Note: N/A"));
        }
        if (brew.getFavorite() == 1) {favorite.setImageResource(R.drawable.heart_icon_faved); }// if its faved


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


        note.setOnClickListener(v -> {
            // Create an AlertDialog for entering the note
            AlertDialog.Builder builder = new AlertDialog.Builder(inflater.getContext());
            builder.setTitle("Edit Note");

            // Set up the input
            final EditText input = new EditText(inflater.getContext());
            // Specify the type of input expected
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setText(brew.getNote()); // Pre-fill with the current note
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", (alertdialog, which) -> {
                String newNote = input.getText().toString();
                updateNoteInDatabase(newNote, brew);
            });
            builder.setNegativeButton("Cancel", (alertdialog, which) -> dialog.cancel());

            builder.show();
        });


        favorite.setOnClickListener(v -> {
            int heart;
            if (brew.getFavorite()==1){
                brew.setFavorite(0);
                heart = R.drawable.heart_icon_unfaved;
                //heart = ContextCompat.getDrawable(this.inflater.getContext(), R.drawable.heart_icon_unfaved);
            } else {
                brew.setFavorite(1);
                heart = R.drawable.heart_icon_faved;
                //heart = ContextCompat.getDrawable(this.inflater.getContext(), R.drawable.heart_icon_faved);
            }
            try {
                if (dbHelper.updateFavorite(brew.getId(), brew.getFavorite())) {
                    favorite.setImageResource(heart);
                } else {
                    Toast.makeText(inflater.getContext(), "Failed to update favorite", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(inflater.getContext(), "Error updating favorite: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace(); // Log the stack trace to help with debugging
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

    private void updateNoteInDatabase(String note, BrewModel brew) {
        // Update the brew model
        brew.setNote(note);

        // Update the database
        if (dbHelper.updateNote(brew.getId(), brew.getNote())) {
            int position = brewsList.indexOf(brew);
            if (position >= 0) {
                brewsList.set(position, brew);
                notifyItemChanged(position);  // Notify the adapter of item changed at the position
            }
        } else {
            Toast.makeText(inflater.getContext(), "Failed to update note!", Toast.LENGTH_SHORT).show();
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView brewView;
        TextView noteView;
        CardView brewContainer;
        ViewHolder(View itemView) {
            super(itemView);
            brewView = itemView.findViewById(R.id.brewView);
            brewContainer = itemView.findViewById(R.id.brewContainer);
            noteView = itemView.findViewById(R.id.noteView);
        }

    }
}
