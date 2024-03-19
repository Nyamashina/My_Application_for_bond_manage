package com.websarva.wings.android.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.sql.Date


@Dao
interface BondDao {
    @Insert
    fun insert(bond: Bond)

    @Delete
    fun delete(bond: Bond)

    @Query("delete from savedbond")
    fun deleteAll()

    @Query("select * from SavedBond")
    fun getAll(): Flow<List<Bond>>

    /*@Query("SELECT * FROM SavedBond")
    fun getAllBonds(): Flow<List<@JvmSuppressWildcards Bond>>*/
    @Query("SELECT * FROM SavedBond")
    fun getAllBonds(): LiveData<List<Bond>>

    @Query("SELECT * FROM SavedBond WHERE label = :label")
    fun findBondByLabel(label: String): Flow<List<Bond>>

    @Query("SELECT * FROM SavedBond WHERE usdJpyRate = :usdJpyRate")
    fun findBondByUsdJpyRate(usdJpyRate: String): Flow<List<Bond>>

    @Query("SELECT * FROM SavedBond WHERE purchasePrice = :purchasePrice")
    fun findBondByPurchasePrice(purchasePrice: String): Flow<List<Bond>>

    @Query("SELECT * FROM SavedBond WHERE valuation = :valuation")
    fun findBondByValuation(valuation: String): Flow<List<Bond>>

    @Query("SELECT * FROM SavedBond WHERE interestRate = :interestRate")
    fun findBondByInterestRate(interestRate: String): Flow<List<Bond>>

    @Query("SELECT * FROM SavedBond WHERE yield = :yield")
    fun findBondByYield(yield: String): Flow<List<Bond>>

    @Query("SELECT * FROM SavedBond WHERE interestPaymentDate = :interestPaymentDate")
    fun findBondByInterestPaymentDate(interestPaymentDate: Date): Flow<List<Bond>>

    @Query("SELECT * FROM SavedBond WHERE redemptionDate = :redemptionDate")
    fun findBondByRedemptionDate(redemptionDate: Date): Flow<List<Bond>>

    @Query("SELECT * FROM SavedBond WHERE issuerRating = :issuerRating")
    fun findBondByIssuerRating(issuerRating: String): Flow<List<Bond>>

    @Query("SELECT * FROM SavedBond WHERE remainingYears = :remainingYears")
    fun findBondByRemainingYears(remainingYears: String): Flow<List<Bond>>

    @Query("SELECT * FROM SavedBond WHERE quantity = :quantity")
    fun findBondByQuantity(quantity: String): Flow<List<Bond>>

}