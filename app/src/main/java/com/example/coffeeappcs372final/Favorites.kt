package com.example.coffeeappcs372final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeappcs372final.databinding.BrewBinding
import com.example.coffeeappcs372final.databinding.FavoritesBinding

class Favorites : AppCompatActivity() {

    private lateinit var binding: FavoritesBinding
    private lateinit var adapter: BrewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupListeners()
        setupRecyclerView()
    }

    private fun setupListeners() {
        binding.favoriteSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                adapter.filter.filter("1") // Show only favorites
            } else {
                adapter.filter.filter("0") // Show all items, assuming 0 means not a favorite
            }
        }
    }


    private fun setupRecyclerView() {
        val brewRecyclerView = binding.favsRecyclerView
        brewRecyclerView.layoutManager = LinearLayoutManager(this)

        try {
            val dataBaseHelper = DataBaseHelper(this@Favorites)
            val allBrews = dataBaseHelper.allBrews
            adapter = BrewAdapter(this@Favorites, allBrews, dataBaseHelper)
            brewRecyclerView.adapter = adapter
        } catch (e: Exception) {
            Toast.makeText(this@Favorites, "Error Loading from DB", Toast.LENGTH_LONG).show()
        }
    }
}