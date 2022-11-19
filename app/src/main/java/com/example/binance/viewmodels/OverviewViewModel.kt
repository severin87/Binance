package com.example.binance.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.binance.database.getDatabase
import com.example.binance.repository.CurrenciesRepository
import kotlinx.coroutines.launch

class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val currenciesRepository = CurrenciesRepository(database)
    val currencyList = currenciesRepository.currencies
    val isLoading = currenciesRepository.isLoading

    private val _isInternetAvailable = MutableLiveData<Boolean?>()
    val isInternetAvailable: LiveData<Boolean?>
        get() = _isInternetAvailable

    init {
        refreshCurrencies(application)
    }

    fun refreshCurrencies(context: Context?) =
        context?.let {
            _isInternetAvailable.value = setIsInternetAvailable(it)

            if (isInternetAvailable.value == true)
                viewModelScope.launch { currenciesRepository.refreshCurrencies() }
        }

    fun resetIsInternetAvailable() {
        _isInternetAvailable.value = null
    }

    private fun setIsInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}
