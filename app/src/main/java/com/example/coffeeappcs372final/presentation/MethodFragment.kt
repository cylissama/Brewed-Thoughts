package com.example.coffeeappcs372final.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeappcs372final.R
import com.example.coffeeappcs372final.adapters.TipsAdapter
import com.example.coffeeappcs372final.databinding.FragmentMethodBinding

class MethodFragment : Fragment() {

    private lateinit var binding: FragmentMethodBinding

    private lateinit var adapter: TipsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMethodBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        val tipsData = listOf(resources.getString(R.string.v60_method),resources.getString(R.string.french_press_method),resources.getString(R.string.aeropress_method),resources.getString(R.string.chemex_method),resources.getString(R.string.moka_pot_method),resources.getString(R.string.phin_method))
        adapter = TipsAdapter(requireContext(), tipsData)
        binding.recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): MethodFragment {
            return MethodFragment()
        }
    }
}
