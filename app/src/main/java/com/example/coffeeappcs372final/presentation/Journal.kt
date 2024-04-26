package com.example.coffeeappcs372final.presentation

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeappcs372final.adapters.BrewAdapter
import com.example.coffeeappcs372final.database.DataBaseHelper
import com.example.coffeeappcs372final.databinding.JournalBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Journal : AppCompatActivity() {

    private lateinit var binding: JournalBinding
    private lateinit var adapter: BrewAdapter
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

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

        coroutineScope.launch {
            try {
                val dataBaseHelper = DataBaseHelper(this@Journal)
                val allBrews = withContext(Dispatchers.IO) {
                    dataBaseHelper.allBrews
                }
                adapter = BrewAdapter(
                    this@Journal,
                    allBrews,
                    dataBaseHelper,
                    false,
                    false
                )
                brewRecyclerView.adapter = adapter
                adapter.filter.filter("0")
            } catch (e: Exception) {
                Toast.makeText(this@Journal, "Error Loading from DB", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}