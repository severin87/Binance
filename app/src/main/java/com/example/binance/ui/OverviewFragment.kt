package com.example.binance.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.binance.R
import com.example.binance.adapter.CurrencyRecyclerViewAdapter
import com.example.binance.adapter.CurrencyRecyclerViewAdapter.CurrencyListener
import com.example.binance.databinding.FragmentOverviewBinding
import com.example.binance.viewmodels.OverviewViewModel
import com.example.binance.viewmodels.OverviewViewModelFactory
import com.google.android.material.snackbar.Snackbar

class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(
            this,
            OverviewViewModelFactory(requireNotNull(activity).application)
        )[OverviewViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOverviewBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setSnackBar(binding)
        setOnRefreshListener(binding)
        setRecyclerViewAdapter(binding)

        return binding.root
    }

    private fun setOnRefreshListener(binding: FragmentOverviewBinding) {
        binding.container.setOnRefreshListener {
            binding.container.isRefreshing = false
            viewModel.refreshCurrencies(context)
        }
    }

    private fun setRecyclerViewAdapter(binding: FragmentOverviewBinding) {
        val adapter = CurrencyRecyclerViewAdapter(CurrencyListener { currency ->
            this.findNavController().navigate(
                OverviewFragmentDirections.actionOverviewFragmentToDetailsFragment(currency)
            )
        })

        binding.currencyList.adapter = adapter
        viewModel.currencyList.observe(viewLifecycleOwner) {
            it?.let { currencies ->
                adapter.submitList(currencies)
            }
        }
    }

    private fun setSnackBar(binding: FragmentOverviewBinding) {
        viewModel.isInternetAvailable.observe(viewLifecycleOwner) {
            if (it == false) {
                Snackbar.make(
                    binding.container,
                    getString(R.string.no_internet_connection),
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.resetIsInternetAvailable()
            }
        }
    }
}
