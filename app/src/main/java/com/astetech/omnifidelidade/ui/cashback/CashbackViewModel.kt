package com.astetech.omnifidelidade.ui.cashback

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.models.Cliente
import com.astetech.omnifidelidade.models.Config
import com.astetech.omnifidelidade.network.*
import com.astetech.omnifidelidade.singleton.ClienteSingleton
import com.astetech.omnifidelidade.util.CashbackStatus
import com.astetech.omnifidelidade.util.obterDataCorrente
import com.astetech.omnifidelidade.util.stringToLocalDate
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class CashbackViewModel() : ViewModel() {

    private val TAG = "CashbackViewModel"

    private var _cashbackListLive = MutableLiveData<List<Cashback>>()
    val cashbackListLive: LiveData<List<Cashback>>
        get() = _cashbackListLive

    private var _cashbackList = listOf<Cashback>()
    val cashbackList: List<Cashback>
        get() = _cashbackList

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

    fun fitroCashback(tipo: CashbackStatus) {

        val dataCorrente = obterDataCorrente()

        when (tipo) {
            CashbackStatus.ATIVOS -> {
                val filtered =
                    _cashbackList.filter { c -> stringToLocalDate(c.dataValidade) >= dataCorrente &&
                            stringToLocalDate(c.dataAtivacao) <= dataCorrente &&
                            c.valorUtilizado == 0.0
                    }

                _cashbackListLive.postValue(filtered)
            }
            CashbackStatus.EXPIRADOS -> {
                val filtered =
                    _cashbackList.filter { c -> stringToLocalDate(c.dataValidade) < dataCorrente &&
                            c.valorUtilizado == 0.0
                    }
                _cashbackListLive.postValue(filtered)
            }
            CashbackStatus.RESGATADOS -> {
                val filtered =
                    _cashbackList.filter { c -> c.valorUtilizado > 0.0 }
                _cashbackListLive.postValue(filtered)
            }
            CashbackStatus.PENDENTES -> {
                val filtered =
                    _cashbackList.filter { c -> stringToLocalDate(c.dataAtivacao) > dataCorrente &&
                        stringToLocalDate(c.dataAtivacao) > dataCorrente &&
                                c.valorUtilizado == 0.0
                    }
                _cashbackListLive.postValue(filtered)
            }
            else -> {
                _cashbackListLive.postValue(_cashbackList)
            }
        }
    }


    fun refreshDataFromNetwork() = viewModelScope.launch {
        try {

            val filtroBonus = "Todos"
            val dataInicio = "2022-01-01"
            val dataFim = "2022-12-01"

            val callback = FidelidadeNetwork.fidelidade.buscaCashback(
                Config.token,
                Config.tenant,
                ClienteSingleton.cliente.id,
                filtroBonus,
                dataInicio,
                dataFim
            )

            callback.enqueue(object : Callback<List<CashbackNetwork>> {
                override fun onFailure(call: Call<List<CashbackNetwork>>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                }

                override fun onResponse(
                    call: Call<List<CashbackNetwork>>,
                    response: Response<List<CashbackNetwork>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val cashbackRetorno = NetworkCashbackContainer(it).asDomainModel()

                            _cashbackList = cashbackRetorno.filter { c-> c.valor > 0 }
                                .sortedByDescending { c -> stringToLocalDate(c.dataValidade) }
                            _cashbackListLive.postValue(_cashbackList)


                            _eventNetworkError.value = false
                            _isNetworkErrorShown.value = false
                        }
                    } else {
                        Log.e(TAG, response.raw().toString())
                    }
                }
            })
        } catch (networkError: IOException) {
            Log.e(TAG, networkError.message.toString())
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