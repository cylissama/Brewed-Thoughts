package com.example.coffeeappcs372final

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeappcs372final.databinding.JournalBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Journal : AppCompatActivity() {

    private lateinit var binding: JournalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = JournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
        setupListeners()
    }

    private fun setupListeners() {
//        binding.journalBackButton.setOnClickListener {
//            val intent = Intent(this, Home::class.java)
//            startActivity(intent)
//        }
    }

    private fun setupRecyclerView() {
        val brewRecyclerView = binding.brewRecyclerView
        brewRecyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            try {
                val dataBaseHelper = DataBaseHelper(this@Journal)
                val allBrews = withContext(Dispatchers.IO) {
                    dataBaseHelper.allBrews // Perform DB operation on IO dispatcher
                }
                val adapter = BrewAdapter(this@Journal, allBrews, dataBaseHelper)
                brewRecyclerView.adapter = adapter
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Journal, "Error Loading from DB", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}