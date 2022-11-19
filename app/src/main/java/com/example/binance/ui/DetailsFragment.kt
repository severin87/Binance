package com.example.binance.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.binance.databinding.FragmentDetailBinding
import com.example.binance.viewmodels.DetailsViewModel
import com.example.binance.viewmodels.DetailsViewModelFactory

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val currency = DetailsFragmentArgs.fromBundle(requireArguments()).selectedCurrency
        val viewModelFactory = DetailsViewModelFactory(currency)

        binding.viewModel = ViewModelProvider(
            this, viewModelFactory
        )[DetailsViewModel::class.java]

        return binding.root
    }
}
