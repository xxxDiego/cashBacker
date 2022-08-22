package com.astetech.omnifidelidade.ui.cashback

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.models.Config
import com.astetech.omnifidelidade.network.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class CashbackViewModel() : ViewModel() {

    private val TAG = "CashbackViewModel"

    private var _playlist = MutableLiveData<List<Cashback>>()

    val playlist: LiveData<List<Cashback>>
        get() = _playlist

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    init {
       refreshDataFromNetwork()
      //  refreshDataFromLocalData()
    }

//    private fun refreshDataFromLocalData(){
//         cashbackDao.getAll().apply {
//
//         }
//    }


     fun refreshDataFromNetwork() = viewModelScope.launch {
        try {

            val filtroBonus = "Todos"
            val dataInicio = "2022-01-01"
            val dataFim = "2022-12-01"

            val callback = FidelidadeNetwork.fidelidade.buscarCashback(Config.token,Config.tenant, Config.clienteId,filtroBonus, dataInicio, dataFim)

            callback.enqueue(object : Callback<List<CashbackNetwork>> {
                override fun onFailure(call: Call<List<CashbackNetwork>>, t: Throwable) {
                    Log.e(TAG,t.message.toString())
                }

                override fun onResponse(call: Call<List<CashbackNetwork>>, response: Response<List<CashbackNetwork>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _playlist.postValue(NetworkCashbackContainer(it).asDomainModel())

                            _eventNetworkError.value = false
                            _isNetworkErrorShown.value = false
                        }
                    }
                    else{
                        Log.e(TAG, response.raw().toString())
                    }
                }
            })
        } catch (networkError: IOException) {
            // Show a Toast error message and hide the progress bar.
            Log.e(TAG,networkError.message.toString())
            _eventNetworkError.value = true
        }
    }
}

//class CashbackViewModelFactory(
//    private val cashbackDao: CashbackDao
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(CashbackViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return CashbackViewModel(cashbackDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}