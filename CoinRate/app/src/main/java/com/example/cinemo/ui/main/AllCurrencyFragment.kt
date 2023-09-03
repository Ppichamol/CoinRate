package com.example.cinemo.ui.main

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemo.databinding.FragmentCoinRateBinding
import com.example.cinemo.base.BaseFragment
import com.example.cinemo.base.StatusBarTextFlag
import com.example.cinemo.ui.main.AllCurrencyViewModel.Companion.BTC_DEFAULT_AMOUNT
import com.example.cinemo.ui.main.adapter.CoinAdapter
import com.example.cinemo.ui.main.model.CoinItemModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllCurrencyFragment: BaseFragment<FragmentCoinRateBinding, AllCurrencyViewModel>(
    FragmentCoinRateBinding::inflate, StatusBarTextFlag.WHITE)
{
    override val viewModel: AllCurrencyViewModel by viewModel()
    private lateinit var coinAdapter : CoinAdapter
    private var currency: String = "USD"

    override fun setupView() {
        onBackInvokedCallback()
        viewModel.getAllCoinList()

        viewModel.coinItems.observe(this) { coinList ->
            coinAdapter = CoinAdapter(coinList, onCoinCardClicked)

            binding.recyclerViewAllPromotion.apply {
                adapter = coinAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }

        viewModel.validateText.observe(this) {
            if (!it) {
                binding.fieldError.text = "Please Input Currency amount"
                binding.fieldError.visibility = View.VISIBLE
                binding.edittext.isActivated = true
            } else {
                binding.fieldError.visibility = View.INVISIBLE
                binding.edittext.isActivated = false
                viewModel.calculateBtc(
                    currency, binding.edittext.text.toString().toDouble()
                )

            }
        }

        viewModel.btcAmountLiveData.observe(this){
            binding.btcAmount.text = String.format("%.8f", it)
        }

    }

    override fun setupListener() {

        setUpDropDown()

        binding.buttonCalculate.setOnClickListener {
            viewModel.validateTextField(binding.edittext.text.toString())
        }

        binding.btcAmount.text = String.format("%.8f", viewModel.btcAmount)

    }

    private fun setUpDropDown() {
        val listCoinName = arrayOf("USD","EUR","GBP")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listCoinName)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dropdownMenu.adapter = adapter

        binding.dropdownMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val item = parent?.getItemAtPosition(pos)
                currency = item.toString()
                binding.currency.text = item.toString()
                binding.edittext.clearFocus()
                binding.edittext.setText("")
                binding.btcAmount.text = String.format("%.8f", BTC_DEFAULT_AMOUNT)
                binding.fieldError.visibility = View.INVISIBLE
                binding.edittext.isActivated = false
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
    }

    private val onCoinCardClicked: (coin: CoinItemModel) -> Unit = { coin ->
        val name = coin.code
        val arrayCoin = viewModel.selectedCoin(name)
        val action = AllCurrencyFragmentDirections.actionAllCurrencyFragmentToCoinHistoryFragment(arrayCoin)
        findNavController().navigate(action)
    }

    private fun onBackInvokedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(this) { }
    }



}