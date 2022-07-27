package com.astetech.omnifidelidade.ui.bonus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.ui.login.LoginViewModel

class BonusFragment : Fragment() {
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bonus, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val navController = findNavController()
        val textProfileWelcome = view.findViewById<TextView>(R.id.textProfileWelcome)

        viewModel.authenticationStateEvent.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.Authenticated -> {
                    textProfileWelcome.text = getString(R.string.profile_text_welcome, viewModel.usuarioNome)
                }
                LoginViewModel.AuthenticationState.Unauthenticated -> {
                   // navController.navigate(BonusFragmentDirections.actionBonusFragmentToLoginFragment())
                }
                else -> {}
            }
        })
    }
}