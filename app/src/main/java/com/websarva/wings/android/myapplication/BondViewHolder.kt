package com.websarva.wings.android.myapplication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BondViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val label: TextView = view.findViewById(R.id.editText_label)
    val USD_JPY: TextView = view.findViewById(R.id.editText_USD_JPY)
    val hyoka: TextView = view.findViewById(R.id.editText_hyoka)
    val kakuduke : TextView = view.findViewById(R.id.editText_kakuduke)
    val nennsuu : TextView = view.findViewById(R.id.editText_nennsuu)
    val ribaraibi : TextView = view.findViewById(R.id.editText_ribaraibi)
    val rimawari : TextView = view.findViewById(R.id.editText_rimawari)
    val riritu : TextView = view.findViewById(R.id.editText_riritu)
    val suuryou : TextView = view.findViewById(R.id.editText_suuryou)
    val syoukann : TextView = view.findViewById(R.id.editText_syoukann)
    val tannka : TextView = view.findViewById(R.id.editText_tannka)

    fun bind(bond: Bond) {
        label.text = bond.label
        USD_JPY.text = bond.usdJpyRate?.toString() ?: "N/A"
        hyoka.text = (bond.valuation ?: "N/A").toString() //hyoka.text = bond.hyoka?.let { String.format("%.2f", it) } ?: "N/A" この場合hyokaがnullでない場合、String.format()を使用して小数点以下2桁で表示しています。nullの場合は"N/A"と表示
        kakuduke.text = bond.issuerRating ?: "N/A"
        nennsuu.text = (bond.remainingYears ?: "N/A").toString()
        ribaraibi.text = bond.interestPaymentDate?.toString() ?: "N/A" // 日付の表示形式を適宜変更してください
        rimawari.text = (bond.yield ?: "N/A").toString()
        riritu.text = (bond.interestRate ?: "N/A").toString()
        suuryou.text = (bond.quantity ?: "N/A").toString()
        syoukann.text = bond.redemptionDate?.toString() ?: "N/A" // 日付の表示形式を適宜変更してください
        tannka.text = bond.purchasePrice?.toString() ?: "N/A"
    }
}