package com.example.coffeeappcs372final.presentation

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeappcs372final.R
import com.example.coffeeappcs372final.database.BrewModel
import com.example.coffeeappcs372final.database.DataBaseHelper
import com.example.coffeeappcs372final.databinding.BrewBinding
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer
import android.media.AudioAttributes
import android.media.SoundPool
import android.view.inputmethod.InputMethodManager
import androidx.core.view.get

class Brew : AppCompatActivity() {

    private lateinit var binding: BrewBinding
    // for timer
    private var timerRunning = false
    private var startTime = 0L
    private var handler = Handler(Looper.getMainLooper())
    private var timeElapsed = 0L
    private lateinit var soundPool: SoundPool
    private var soundID: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BrewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timer: Timer

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupListeners()
        setupSpinners()
        setupSoundPool()
    }

    private fun setupListeners() {

        val saveButton = binding.brewSaveButton
        saveButton.setOnClickListener {
            if (areInputsValid()) {
                addToDataBase()
                playSound(soundID)
            } else {
                Toast.makeText(this@Brew, "Please fill in all fields before saving.", Toast.LENGTH_LONG).show()
            }
        }

        val timerButton = binding.timerButton
        timerButton.setOnClickListener {
            toggleTimer()
        }

        val resetButton = binding.resetButton
        resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun setupSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()

        // Load the sound
        soundID = soundPool.load(this, R.raw.ping, 1)

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

    private fun areInputsValid(): Boolean {
        return !(binding.beansTextInput.text.toString().trim().isEmpty() ||
                binding.coffeeGramsNumberPicker.text.toString().trim().isEmpty() ||
                binding.waterMLNumberPicker.text.toString().trim().isEmpty() ||
                binding.waterTempNumberPicker.text.toString().trim().isEmpty() ||
                binding.brewerSpinner.selectedItem == binding.brewerSpinner[0])
    }

    fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // Find the currently focused view, so we can grab the correct window token from it.
        val view = currentFocus
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun clearInputFields() {
        binding.beansTextInput.text.clear()
        binding.coffeeGramsNumberPicker.text.clear()
        binding.waterMLNumberPicker.text.clear()
        binding.waterTempNumberPicker.text.clear()
        binding.methodTextInput.text.clear()
        binding.brewerSpinner.setSelection(0)
    }

    private fun playSound(id: Int) {
        soundPool.play(id, 1f, 1f, 0, 0, 1f)
    }

    private val updateTimer = object : Runnable {
        override fun run() {
            timeElapsed = System.currentTimeMillis() - startTime
            binding.timerText.text = formatTime(timeElapsed)
            handler.postDelayed(this, 1000)
        }
    }

    private fun toggleTimer() {
        if (!timerRunning) {
            startTime = System.currentTimeMillis() - timeElapsed
            handler.postDelayed(updateTimer, 0)
            timerRunning = true
            binding.timerButton.text = "Stop"
        } else {
            handler.removeCallbacks(updateTimer)
            timerRunning = false
            binding.timerButton.text = "Start"
            binding.timerText.text = formatTime(timeElapsed)
        }
    }

    private fun resetTimer() {
        handler.removeCallbacks(updateTimer)
        timeElapsed = 0L  // Reset the time elapsed to zero
        binding.timerText.text = formatTime(timeElapsed)  // Update the timer display to 00:00:00
        if (timerRunning) {
            startTime = System.currentTimeMillis()
            handler.postDelayed(updateTimer, 0)  // Restart the timer if it was running
        } else {
            binding.timerButton.text = "Start"  // Set the text back to Start if the timer was stopped
        }
    }

    private fun formatTime(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        val hours = (millis / (1000 * 60 * 60)) % 24
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun addToDataBase() = CoroutineScope(Dispatchers.IO).launch {
        val dataBaseHelper: DataBaseHelper =
            DataBaseHelper(this@Brew)
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
            val success = dataBaseHelper.addBrew(
                BrewModel(
                    -1,
                    beans,
                    brewer,
                    grams,
                    water,
                    temp,
                    method,
                    time
                )
            )
            withContext(Dispatchers.Main) {
                if (success) {
                    Toast.makeText(this@Brew, "Brew added successfully!", Toast.LENGTH_LONG).show()
                    clearInputFields()
                    hideKeyboard()
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

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release() // Release SoundPool resources
    }
}