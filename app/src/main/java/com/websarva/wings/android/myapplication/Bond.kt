package com.websarva.wings.android.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "SavedBond")
data class Bond(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String?,
    val usdJpyRate: Double?,
    val purchasePrice: Double?,
    val valuation: Double?,
    val interestRate: Double?,
    val yield: Double?,
    val interestPaymentDate: Date?,
    val redemptionDate: Date?,
    val issuerRating: String?,
    val remainingYears: Double?,
    val quantity: Double?
) : Parcelable {
    override fun toString(): String {
        return "Bond(id=$id, label='$label', usdJpyRate='$usdJpyRate', purchasePrice='$purchasePrice', valuation='$valuation', interestRate='$interestRate', yield='$yield', interestPaymentDate='$interestPaymentDate', redemptionDate='$redemptionDate', issuerRating='$issuerRating', remainingYears='$remainingYears', quantity='$quantity')"
    }
}