package com.example.coffeeappcs372final

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeappcs372final.databinding.BrewBinding
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer

class Brew : AppCompatActivity() {

    private lateinit var binding: BrewBinding
    // for timer
    private var timerRunning = false
    private var startTime = 0L
    private var handler = Handler(Looper.getMainLooper())
    private var timeElapsed = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BrewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timer: Timer

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupListeners()
        setupSpinners()
    }

    private fun setupListeners() {

        binding.brewSaveButton.setOnClickListener {
            addToDataBase()
        }

//        binding.brewBackButton.setOnClickListener {
//            val intent = Intent(this, Home::class.java)
//            startActivity(intent)
//        }

        val timerButton = binding.timerButton
        timerButton.setOnClickListener {
            startTime()
        }
    }

    private fun startTime() {
        if (!timerRunning) {
            startTime = System.currentTimeMillis() - timeElapsed
            handler.postDelayed(updateTimer, 0)
            timerRunning = true
            binding.timerButton.text = "Stop"
        } else {
            handler.removeCallbacks(updateTimer)
            timerRunning = false
            binding.timerButton.text = "Start"
            binding.timerText.text = "${formatTime(timeElapsed)}"
        }
    }

    private val updateTimer = object : Runnable {
        override fun run() {
            timeElapsed = System.currentTimeMillis() - startTime
            binding.timerText.text = "${formatTime(timeElapsed)}"
            handler.postDelayed(this, 1000)
        }
    }

    private fun formatTime(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        val hours = (millis / (1000 * 60 * 60)) % 24
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }


    private fun setupSpinners() {

        val brewerSpinner: Spinner = binding.brewerSpinner
        val brewerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.brewers,
            android.R.layout.simple_spinner_item
        )
        brewerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        brewerSpinner.adapter = brewerAdapter
    }

    // fix this
    private fun clearInputFields() {
        binding.beansTextInput.text = null
        binding.gramsAmountInput.text = null
        binding.mlAmountInput.text = null
        binding.waterTempInput.text = null
        binding.methodTextInput.text = null
    }

    private fun addToDataBase() = CoroutineScope(Dispatchers.IO).launch {
        val dataBaseHelper: DataBaseHelper = DataBaseHelper(this@Brew)
        val beans: String = binding.beansTextInput.text.toString()
        val brewer: String = binding.brewerSpinner.selectedItem.toString()
        val grams: Float = binding.coffeeGramsNumberPicker.text.toString().toFloatOrNull() ?: 0f
        val water: Float = binding.waterMLNumberPicker.text.toString().toFloatOrNull() ?: 0f
        val temp: Float = binding.waterTempNumberPicker.text.toString().toFloatOrNull() ?: 0f
        val method: String = binding.methodTextInput.text.toString()

        val dateFormat = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
        val time: String = simpleDateFormat.format(Date()).toString()

        try {
            val success = dataBaseHelper.addBrew(BrewModel(-1, beans, brewer, grams, water, temp, method, time))
            withContext(Dispatchers.Main) {
                if (success) {
                    Toast.makeText(this@Brew, "Brew added successfully!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@Brew, "Failed to add brew!", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: SQLiteConstraintException) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@Brew, "Duplicate entry, not added.", Toast.LENGTH_LONG).show()
                Log.d("Insertion", "Duplicate EPC: not added.")
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@Brew, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                Log.d("Insertion", "Exception: ${e.message}")
            }
        }
    }

}