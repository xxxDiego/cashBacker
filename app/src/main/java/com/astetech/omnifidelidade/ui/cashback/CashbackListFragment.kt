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
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.ui.login.LoginViewModel

class CashbackListFragment : Fragment() {
    private val viewModel: LoginViewModel by activityViewModels()

    private val bonusviewModel: CashbackViewModel by activityViewModels()



    //private lateinit var myDataset: List<Cashback>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cashback_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val navController = findNavController()



        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        bonusviewModel.playlist.observe(viewLifecycleOwner, Observer<List<Cashback>> { bonus ->
            bonus?.apply {

                recyclerView.adapter = CashbackAdapter(bonus)
            }
        })

        //val textProfileWelcome = view.findViewById<TextView>(R.id.textProfileWelcome)

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
}