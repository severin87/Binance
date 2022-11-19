package com.example.binance.repository

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.binance.database.CurrenciesDatabase
import com.example.binance.database.asDomainModel
import com.example.binance.domainmodel.Currency
import com.example.binance.network.CurrencyApi
import com.example.binance.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrenciesRepository(private val database: CurrenciesDatabase) {

    private val _isLoading = MutableLiveData<Int>().apply {
        value = View.GONE
    }
    val isLoading: LiveData<Int>
        get() = _isLoading

    val currencies: LiveData<List<Currency>> =
        Transformations.map(database.currencyDao.getCurrencies()) { it.asDomainModel() }

    suspend fun refreshCurrencies() {
        _isLoading.value = View.VISIBLE
        withContext(Dispatchers.IO) {
            try {
                val currencyList = CurrencyApi.retrofitService.getCurrenciesAsync().await()
                database.currencyDao.insertAll(*currencyList.asDatabaseModel())
            } catch (Ex: Exception) {
                Ex.localizedMessage?.let { Log.e("Error", it) }
            }
        }
        _isLoading.value = View.GONE
    }
}