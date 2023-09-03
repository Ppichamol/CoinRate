package com.example.cinemo.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

enum class StatusBarTextFlag {
    WHITE,
    DARK
}

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<Binding : ViewBinding, VM : BaseViewModel>(
    private val inflate: Inflate<Binding>,
    private val statusBarTextFlag: StatusBarTextFlag
) : Fragment() {

    protected lateinit var binding: Binding
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate.invoke(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarTextColor()
        setupView()
        setupListener()
    }

    private fun setStatusBarTextColor() {
        when (statusBarTextFlag) {
            StatusBarTextFlag.WHITE -> setStatusBarTextColor(false)
            StatusBarTextFlag.DARK -> setStatusBarTextColor(true)
        }
    }

    @Suppress("DEPRECATION")
    fun setStatusBarTextColor(isAppearanceLight: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            WindowInsetsControllerCompat(
                requireActivity().window,
                requireActivity().window.decorView
            ).run {
                isAppearanceLightStatusBars = isAppearanceLight
            }
        } else {
            val compatActivity = requireActivity() as AppCompatActivity
            val window = compatActivity.window
            val decorView = window.decorView
            if (isAppearanceLight) {
                decorView.systemUiVisibility = decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
    }

    abstract fun setupView()

    abstract fun setupListener()
}