package com.example.coffeeappcs372final

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coffeeappcs372final.databinding.FragmentBeansBinding
import com.example.coffeeappcs372final.databinding.FragmentBrewersBinding
import com.example.coffeeappcs372final.databinding.HomeBinding

class BeansFragment : Fragment() {


    private lateinit var binding: FragmentBeansBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBeansBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): BeansFragment {
            return BeansFragment()
        }
    }

}