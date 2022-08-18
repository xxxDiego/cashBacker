package com.astetech.omnifidelidade.ui.cashback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.astetech.omnifidelidade.databinding.FragmentCashbackListBinding
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.ui.login.LoginViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CashbackListFragment : Fragment(), CashbackClickListener {

    private val viewModel: LoginViewModel by activityViewModels()
    private val cashbackviewModel: CashbackViewModel by activityViewModels()

    private var _binding: FragmentCashbackListBinding? = null
    private val binding get() = _binding!!

    private val cashbackAdapter = CashbackAdapter(this)

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    )= FragmentCashbackListBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onClick(cashback: Cashback) {
        var directions = CashbackListFragmentDirections.actionBonusFragmentToCashbackDetailFragment(cashback)
        navController.navigate(directions)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCashbackAdapter()

        cashbackviewModel.refreshDataFromNetwork()

        cashbackviewModel.playlist.observe(viewLifecycleOwner, Observer<List<Cashback>> { bonus ->
            bonus?.apply {

                val filtered = this.filter{ c ->  stringToLocalDate(c.dataValidade) > obterDataCorrente()}

                if (filtered.isNotEmpty()){
                    _binding?.contentImage?.visibility = View.GONE
                    cashbackAdapter.submitList(filtered)
                }
                else{
                   _binding?.contentImage?.visibility = View.VISIBLE
                }

            }
        })

        viewModel.authenticationStateEvent.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.Authenticated -> {
                   // textProfileWelcome.text = getString(R.string.profile_text_welcome, viewModel.usuarioNome)
                }
                LoginViewModel.AuthenticationState.Unauthenticated -> {
                   // navController.navigate(BonusFragmentDirections.actionBonusFragmentToLoginFragment())
                }
                else -> {}
            }
        })
    }

    private fun initCashbackAdapter() {
        with(binding.recyclerView) {
            setHasFixedSize(true)
            adapter = cashbackAdapter
        }
    }

    private fun obterDataCorrente(): LocalDate{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        var date =  current.format(formatter)
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

    private fun stringToLocalDate (data: String): LocalDate{
        return LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

}