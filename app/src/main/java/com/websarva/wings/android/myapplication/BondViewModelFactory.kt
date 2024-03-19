package com.websarva.wings.android.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BondViewModelFactory(private val bondDao: BondDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BondViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BondViewModel(bondDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}