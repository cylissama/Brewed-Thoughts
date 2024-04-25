package com.example.coffeeappcs372final.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffeeappcs372final.databinding.FragmentMethodBinding

class MethodFragment : Fragment() {

    private lateinit var binding: FragmentMethodBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMethodBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): MethodFragment {
            return MethodFragment()
        }
    }

}