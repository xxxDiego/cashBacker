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
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.databinding.FragmentCashbackListBinding
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.ui.LoadingDialogFragment
import com.astetech.omnifidelidade.ui.login.LoginViewModel
import com.astetech.omnifidelidade.util.doubleToUi
import com.astetech.omnifidelidade.util.stringToLocalDate
import com.google.android.material.chip.Chip
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CashbackListFragment : Fragment() {

    private val cashbackviewModel: CashbackViewModel by activityViewModels()

    private var _binding: FragmentCashbackListBinding? = null
    private val binding get() = _binding!!

    private val cashbackAdapter = CashbackAdapter()

    val list = mutableListOf<CarouselItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    )= FragmentCashbackListBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
        binding.semCashCard.visibility = View.GONE
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initCarousel()
        initCashbackAdapter()
        initViewListeners()
        cashbackviewModel.refreshDataFromNetwork()
    }

    private fun initViewListeners() {
        cashbackviewModel.cashbackListLive.observe(viewLifecycleOwner, Observer<List<Cashback>> { bonus ->
            if (bonus.isNotEmpty()){
                binding.semCashCard.visibility = View.GONE

                var bonusAgrupado = bonus
                    .sortedBy { c -> c.empresa }
                    .groupingBy { it.empresa }
                    .reduce { key, accumulator, element ->
                        accumulator.copy(empresa = key, valor = accumulator.valor + element.valor)
                    }
                    .values.toList()
                cashbackAdapter.submitList(bonusAgrupado)

                val total = bonus.sumOf { cash -> cash.valor }

                binding.totalHomeText.text = doubleToUi(total)
            }
            else{
                binding.semCashCard.visibility = View.VISIBLE
            }
        })
    }

    private fun initCarousel() {
        val carousel: ImageCarousel = binding.carousel
        carousel.registerLifecycle(lifecycle)
        list.add(CarouselItem(R.drawable.ds))
        list.add(CarouselItem(R.drawable.nb))
        list.add(CarouselItem(R.drawable.kp))
        list.add(CarouselItem(R.drawable.al))
        list.add(CarouselItem(R.drawable.jp))
        carousel.setData(list)
    }

    private fun initCashbackAdapter() {
        with(binding.recyclerView) {
            setHasFixedSize(true)
            adapter = cashbackAdapter
        }
    }


}