package com.example.coffeeappcs372final.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffeeappcs372final.databinding.FragmentBrewersBinding

class BrewersFragment : Fragment() {


    private lateinit var binding: FragmentBrewersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBrewersBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): BrewersFragment {
            return BrewersFragment()
        }
    }

}