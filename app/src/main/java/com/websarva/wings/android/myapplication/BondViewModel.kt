package com.websarva.wings.android.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BondViewModel(private val bondDao: BondDao) : ViewModel() {
    // BondDaoから取得したデータをLiveDataとして保持
    val bondListLiveData: LiveData<List<Bond>> = bondDao.getAll().asLiveData()
    fun createBondLiveList(): LiveData<List<Bond>> {
        return bondDao.getAll().asLiveData()
    }

    // 新しいBondをデータベースに追加するメソッド
    fun insert(bond: Bond) = viewModelScope.launch {
        bondDao.insert(bond)
    }

    fun delete(bond: Bond) = viewModelScope.launch(Dispatchers.IO) {
        bondDao.delete(bond)
    }


}