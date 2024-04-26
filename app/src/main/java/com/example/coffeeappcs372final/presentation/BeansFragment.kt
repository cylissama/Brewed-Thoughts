package com.example.coffeeappcs372final.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeappcs372final.R
import com.example.coffeeappcs372final.adapters.TipsAdapter
import com.example.coffeeappcs372final.databinding.FragmentBeansBinding

class BeansFragment : Fragment() {


    private lateinit var binding: FragmentBeansBinding

    private lateinit var adapter: TipsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBeansBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        val tipsData = listOf(resources.getString(R.string.light_roast), resources.getString(R.string.medium_roast), resources.getString(R.string.dark_roast))
        adapter = TipsAdapter(requireContext(), tipsData)
        binding.recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): BeansFragment {
            return BeansFragment()
        }
    }
}