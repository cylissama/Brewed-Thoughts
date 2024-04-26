package com.example.coffeeappcs372final.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeappcs372final.R
import com.example.coffeeappcs372final.adapters.TipsAdapter
import com.example.coffeeappcs372final.databinding.FragmentBrewersBinding

class BrewersFragment : Fragment() {

    private lateinit var binding: FragmentBrewersBinding

    private lateinit var adapter: TipsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBrewersBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        val tipsData = listOf(resources.getString(R.string.mocha_pot_tip),resources.getString(R.string.french_press_tip),resources.getString(R.string.v60_tip))
        adapter = TipsAdapter(requireContext(), tipsData)
        binding.recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): BrewersFragment {
            return BrewersFragment()
        }
    }

}