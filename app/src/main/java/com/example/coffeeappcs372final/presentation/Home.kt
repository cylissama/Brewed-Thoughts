package com.example.coffeeappcs372final.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeappcs372final.adapters.BrewAdapter
import com.example.coffeeappcs372final.database.DataBaseHelper
import com.example.coffeeappcs372final.databinding.HomeBinding

class Home : AppCompatActivity() {

    private lateinit var binding: HomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BrewAdapter
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var scrollPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()

        setupRecyclerView()

        setupAutoScroll()

    }

    private fun setupListeners() {

        binding.brewButton.setOnClickListener {
            val intent = Intent(this, Brew::class.java)
            startActivity(intent)
        }

        binding.journalButton.setOnClickListener {
            val intent = Intent(this, Journal::class.java)
            startActivity(intent)
        }

        binding.tipsButton.setOnClickListener {
            val intent = Intent(this, Tips::class.java)
            startActivity(intent)
        }

        binding.favsButton.setOnClickListener {
            val intent = Intent(this, Favorites::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding.brewRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        try {
            val dataBaseHelper =
                DataBaseHelper(this@Home)
            val allBrews = dataBaseHelper.allBrews
            adapter = BrewAdapter(
                this@Home,
                allBrews,
                dataBaseHelper,
                false,
                true
            )
            recyclerView.adapter = adapter
        } catch (e: Exception) {
            Toast.makeText(this@Home, "Error Loading from DB", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupAutoScroll() {
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                if (scrollPosition == recyclerView.adapter!!.itemCount - 1) {
                    scrollPosition = 0
                    recyclerView.scrollToPosition(scrollPosition)
                } else {
                    recyclerView.smoothScrollToPosition(++scrollPosition)
                }
                handler.postDelayed(this, 3000)  // Scroll every 3 seconds
            }
        }
        handler.postDelayed(runnable, 3000)  // Initial delay
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)  // Stop scrolling when the activity is not visible
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3000)  // Resume scrolling when the activity is back in the foreground
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)  // Clean up handler when the activity is destroyed
    }
}