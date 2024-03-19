package com.websarva.wings.android.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date


class BondViewModel(private val bondDao: BondDao) : ViewModel() {
    // BondDaoから取得したデータをLiveDataとして保持
    val bondListLiveData: LiveData<List<Bond>> = bondDao.getAll().asLiveData()

    // 新しいBondをデータベースに追加するメソッド
    fun insert(bond: Bond) = viewModelScope.launch {
        bondDao.insert(bond)
    }

    fun delete(bond: Bond) = viewModelScope.launch(Dispatchers.IO) {
        bondDao.delete(bond)
    }


    fun getBondsByInterestPaymentDate(date: Date): LiveData<List<Bond>> {
        return bondDao.findBondByInterestPaymentDate(date).asLiveData()
    }

    fun getBondsByRedemptionDate(date: Date): LiveData<List<Bond>> {
        return bondDao.findBondByRedemptionDate(date).asLiveData()
    }

}