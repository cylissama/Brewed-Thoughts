package com.example.coffeeappcs372final.presentation

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeappcs372final.adapters.BrewAdapter
import com.example.coffeeappcs372final.database.DataBaseHelper
import com.example.coffeeappcs372final.databinding.JournalBinding


class Journal : AppCompatActivity() {

    private lateinit var binding: JournalBinding
    private lateinit var adapter: BrewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = JournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // enable the actionbar and implement back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()

        setupListeners()
    }

    private fun setupListeners() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Add your code here if you need to handle the query submission
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

    }

    private fun setupRecyclerView() {
        val brewRecyclerView = binding.brewRecyclerView
        brewRecyclerView.layoutManager = LinearLayoutManager(this)

        try {
            val dataBaseHelper =
                DataBaseHelper(this@Journal)
            val allBrews = dataBaseHelper.allBrews
            adapter = BrewAdapter(
                this@Journal,
                allBrews,
                dataBaseHelper,
                false
            )
            brewRecyclerView.adapter = adapter
        } catch (e: Exception) {
            Toast.makeText(this@Journal, "Error Loading from DB", Toast.LENGTH_LONG).show()
        }
    }
}