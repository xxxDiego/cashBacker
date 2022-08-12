package com.astetech.omnifidelidade.ui.cashback

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.network.BonusNetwork
import com.astetech.omnifidelidade.network.NetworkCashback
import com.astetech.omnifidelidade.network.NetworkCashbackContainer
import com.astetech.omnifidelidade.network.asDomainModel
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

    private fun refreshDataFromNetwork() = viewModelScope.launch {
        try {

            val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJvbW5pc2lnZTk5OUB3ZWJic3lzLmNvbS5iciIsImp0aSI6ImIyMTQxMDg4LWQ5ZDUtNDBmYi05MTM4LTNmMzkzZWZjNGM3MyIsImlhdCI6MTYyMTk1OTExOCwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvZ2l2ZW5uYW1lIjoiT21uaXNpZ2UiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJBZG1pbmlzdHJhZG9yIiwidGVuYW50IjoiT21uaXNpZ2UiLCJsb2phIjoiOTk5IiwibmJmIjoxNjIxOTU5MTE3LCJleHAiOjE2MjIwMDIzMTcsImlzcyI6Imh0dHBzOi8vYXBpLm9tbmlzaWdlLmNvbS5iciIsImF1ZCI6Imh0dHBzOi8vYXBpLm9tbmlzaWdlLmNvbS5iciJ9.WTWaRwJd7ouOpo2OSrJ12afDQNi5ip0OcmU-TbMkegY"
            val filtroBonus = "Todos"
            val clienteId = "clientes/18699-A"
            val dataInicio = "2022-01-01"
            val dataFim = "2022-12-01"

            val callback = BonusNetwork.bonus.buscarBonus(token,"Aste", clienteId,filtroBonus, dataInicio, dataFim)

            callback.enqueue(object : Callback<List<NetworkCashback>> {
                override fun onFailure(call: Call<List<NetworkCashback>>, t: Throwable) {
                    Log.e(TAG,t.message.toString())
                }

                override fun onResponse(call: Call<List<NetworkCashback>>, response: Response<List<NetworkCashback>>) {
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