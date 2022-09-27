package com.astetech.omnifidelidade.ui.opcoes


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.databinding.FragmentOpcoesBinding
import com.astetech.omnifidelidade.extensions.firstCharUpper
import com.astetech.omnifidelidade.models.Cliente
import com.astetech.omnifidelidade.singleton.ClienteSingleton



class OpcoesFragment : Fragment() {

    private val navController: NavController by lazy {
        findNavController()
    }

    private var _binding: FragmentOpcoesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOpcoesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nomeText.text = getString(R.string.opcoes_text_name, ClienteSingleton.cliente.nome?.trim()?.split(" ")?.first()?.firstCharUpper()) + "!"

        when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.temaSwitch.isChecked = true
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.temaSwitch.isChecked = false
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                binding.temaSwitch.isChecked = false
            }
        }

        binding.temaSwitch.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
        }

        binding.nextText.setOnClickListener{

            val directions = OpcoesFragmentDirections.actionOpcoesFragmentToClienteFragment(ClienteSingleton.cliente.celular)

            navController.navigate(directions)
        }

        binding.logoutImage.setOnClickListener{
            alert()
        }
        binding.logoutLabel.setOnClickListener{
            alert()
        }
    }

    private fun alert(){
        val applicationContext = this.context

        val positiveButtonClick = { _: DialogInterface, _: Int ->
            navegar()
        }

        val negativeButtonClick = { _: DialogInterface, _: Int ->
        }

        val builder = AlertDialog.Builder(applicationContext)
        with(builder) {
            setTitle("Atenção!")
            setMessage("Deseja realmente sair?")
            setPositiveButton("Sim", DialogInterface.OnClickListener(function = positiveButtonClick))
            setNegativeButton("Não", negativeButtonClick)
        }
        val alertDialog = builder.create()
        alertDialog.show()


        val positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        with(positiveButton) {
            setTextColor(Color.BLUE)
        }

        val negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        with(negativeButton) {
            setTextColor(Color.BLUE)
        }
    }

    private fun navegar(){
        ClienteSingleton.cliente = Cliente()
        navController.popBackStack(R.id.loginFragment, false)
    }

}

