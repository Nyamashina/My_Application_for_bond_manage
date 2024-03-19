package com.websarva.wings.android.myapplication

import kotlinx.coroutines.flow.map

class BondRepository(private val bondDao: BondDao) {
    fun getBonds() = bondDao.getAll()

    fun getBondsSortedByPurchasePrice() = getBonds().map { bondList ->
        bondList.sortedBy { bond -> bond.purchasePrice }
    }

    fun getBondsSortedByValuation() = getBonds().map { bondList ->
        bondList.sortedBy { bond -> bond.valuation }
    }

    fun getBondsSortedByInterestRate() = getBonds().map { bondList ->
        bondList.sortedBy { bond -> bond.interestRate }
    }

    /*テスト用サンプルデータ生成関数
    fun generateTestSampleData() {
        bondDao.insert(Bond(-1, "Bond A TEST:THIS IS DEBUG MODE", "105", "100", "101", "1.5%", "2%", "2023-03-01", "2028-03-01", "AAA", "5", "100"))
    }

     */
}