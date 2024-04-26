package com.example.coffeeappcs372final.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coffeeappcs372final.adapters.BrewAdapter
import com.example.coffeeappcs372final.database.DataBaseHelper
import com.example.coffeeappcs372final.databinding.FavoritesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Favorites : AppCompatActivity() {

    private lateinit var binding: FavoritesBinding
    private lateinit var adapter: BrewAdapter
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupListeners()
        setupRecyclerView()
    }

    private fun setupListeners() {
    }


    private fun setupRecyclerView() {
        val brewRecyclerView = binding.favsRecyclerView
        brewRecyclerView.layoutManager = GridLayoutManager(this, 2)

        coroutineScope.launch {
            try {
                val dataBaseHelper = DataBaseHelper(this@Favorites)
                val allBrews = withContext(Dispatchers.IO) {
                    dataBaseHelper.allBrews
                }
                adapter = BrewAdapter(
                    this@Favorites,
                    allBrews,
                    dataBaseHelper,
                    true,
                    false
                )
                brewRecyclerView.adapter = adapter
                adapter.filter.filter("1")
            } catch (e: Exception) {
                Toast.makeText(this@Favorites, "Error Loading from DB", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}