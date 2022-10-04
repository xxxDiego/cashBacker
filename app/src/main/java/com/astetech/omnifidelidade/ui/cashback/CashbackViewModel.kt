package com.astetech.omnifidelidade.ui.cashback

import androidx.lifecycle.*
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.network.response.BuscaCashbackResponseContainer
import com.astetech.omnifidelidade.repository.FidelidadeRepository
import com.astetech.omnifidelidade.repository.Resultado
import com.astetech.omnifidelidade.util.CashbackStatus
import com.astetech.omnifidelidade.util.obterDataCorrente
import com.astetech.omnifidelidade.util.stringToLocalDate
import kotlinx.coroutines.launch

class CashbackViewModel() : ViewModel() {

    private val TAG = "CashbackViewModel"
    private val repository =  FidelidadeRepository()

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

    private var _status: CashbackStatus = CashbackStatus.TODOS
    val status: CashbackStatus
    get() = _status


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

                _status = CashbackStatus.ATIVOS
                _cashbackListLive.postValue(filtered)
            }
            CashbackStatus.EXPIRADOS -> {
                val filtered =
                    _cashbackList.filter { c -> stringToLocalDate(c.dataValidade) < dataCorrente &&
                            c.valorUtilizado == 0.0
                    }
                _status = CashbackStatus.EXPIRADOS
                _cashbackListLive.postValue(filtered)
            }
            CashbackStatus.RESGATADOS -> {
                val filtered =
                    _cashbackList.filter { c -> c.valorUtilizado > 0.0 }
                _status = CashbackStatus.RESGATADOS
                _cashbackListLive.postValue(filtered)
            }
            CashbackStatus.PENDENTES -> {
                val filtered =
                    _cashbackList.filter { c -> stringToLocalDate(c.dataAtivacao) > dataCorrente &&
                        stringToLocalDate(c.dataAtivacao) > dataCorrente &&
                                c.valorUtilizado == 0.0
                    }
                _status = CashbackStatus.PENDENTES
                _cashbackListLive.postValue(filtered)
            }
            else -> {
                _status = CashbackStatus.TODOS
                _cashbackListLive.postValue(_cashbackList)
            }
        }
    }


    fun refreshDataFromNetwork() = viewModelScope.launch {
        val result = repository.BuscaCashback()
        result.asFlow().collect { resultado ->
            when (resultado) {
                is Resultado.Sucesso -> {
                    resultado.data?.let {
                        val cashbackRetorno = BuscaCashbackResponseContainer(it).toCashbackDomainModel()

                        _cashbackList = cashbackRetorno.filter { c -> c.valor > 0 }
                            .sortedByDescending { c -> stringToLocalDate(c.dataValidade) }
                        _status = CashbackStatus.TODOS
                        _cashbackListLive.postValue(_cashbackList)
                    }
                }
                else -> {}
            }
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