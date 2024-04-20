package com.example.coffeeappcs372final

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.Toast
import com.example.coffeeappcs372final.databinding.BrewBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.*

class Brew : AppCompatActivity() {

    private lateinit var binding: BrewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BrewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupSpinners()
    }

    private fun setupListeners() {

        binding.brewSaveButton.setOnClickListener {
            addToDataBase()
        }

        binding.brewBackButton.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

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