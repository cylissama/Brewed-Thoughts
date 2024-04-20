package com.example.coffeeappcs372final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeeappcs372final.databinding.HomeBinding

class Home : AppCompatActivity() {

    private lateinit var binding: HomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()

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
    }
}