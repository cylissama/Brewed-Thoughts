package com.example.coffeeappcs372final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.coffeeappcs372final.databinding.TipsBinding

class Tips : AppCompatActivity() {

    private lateinit var binding: TipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = TipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // So it initializes with a fragment already there
        val methodFragment = MethodFragment.newInstance()
        replaceFragment(methodFragment)

        setupListeners()

    }

    private fun setupListeners() {

        binding.tipsBackButton.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        binding.brewersButton.setOnClickListener {
            val brewerFragment = BrewersFragment.newInstance()
            replaceFragment(brewerFragment)
        }

        binding.methodsButton.setOnClickListener {
            val methodFragment = MethodFragment.newInstance()
            replaceFragment(methodFragment)
        }

        binding.beansButton.setOnClickListener {
            val beanFragment = BeansFragment.newInstance()
            replaceFragment(beanFragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}
