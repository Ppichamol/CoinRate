package com.example.cinemo.ui.coin_history

import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemo.base.BaseFragment
import com.example.cinemo.base.StatusBarTextFlag
import com.example.cinemo.databinding.FragmentCoinHistoryBinding
import com.example.cinemo.databinding.FragmentCoinRateBinding
import com.example.cinemo.ui.main.adapter.CoinAdapter
import com.example.cinemo.ui.main.model.CoinItemModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinHistoryFragment: BaseFragment<FragmentCoinHistoryBinding, CoinHistoryViewModel>(
    FragmentCoinHistoryBinding::inflate, StatusBarTextFlag.WHITE)
{
    override val viewModel: CoinHistoryViewModel by viewModel()
    val args: CoinHistoryFragmentArgs by navArgs()
    private lateinit var coinAdapter : CoinAdapter


    override fun setupView() {
//        onBackInvokedCallback()

//        viewModel.coinItems.observe(this) { coinList ->
            coinAdapter = CoinAdapter(args.CoinList.toList(), onCoinCardClicked, true)

            binding.recyclerViewAllPromotion.apply {
                adapter = coinAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
//        }

    }

    override fun setupListener() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
//        binding.favoriteButton.setOnClickListener {
//            moviesList = MovieDetailViewModel.movieFavoriteList
//            val action = AllMovieFragmentDirections.actionMainFragmentToFavoriteMovieFragment(moviesList.toTypedArray())
//            findNavController().navigate(action)
//        }
    }

    private val onCoinCardClicked: (coin: CoinItemModel) -> Unit = { coin ->
//        val action = AllMovieFragmentDirections.actionMainFragmentToMovieDetailFragment(movie)
//        findNavController().navigate(action)
    }
//
    private fun onBackInvokedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {}
    }
}