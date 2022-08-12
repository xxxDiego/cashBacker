package com.astetech.omnifidelidade.ui.cashback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.databinding.FragmentCashbackListBinding
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.ui.login.LoginViewModel

class CashbackListFragment : Fragment() {

    private val viewModel: LoginViewModel by activityViewModels()
    private val cashbackviewModel: CashbackViewModel by activityViewModels()

    private var _binding: FragmentCashbackListBinding? = null
    private val binding get() = _binding!!

    private val cashbackAdapter = CashbackAdapter()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCashbackAdapter()

        cashbackviewModel.playlist.observe(viewLifecycleOwner, Observer<List<Cashback>> { bonus ->
            bonus?.apply {
                cashbackAdapter.submitList(this)
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
}