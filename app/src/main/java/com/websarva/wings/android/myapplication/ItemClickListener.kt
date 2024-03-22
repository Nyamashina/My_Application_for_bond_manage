package com.websarva.wings.android.myapplication

class ItemClickListener(val clickListener: (bond: Bond) -> Unit) {
    fun onClick(bond: Bond) = clickListener(bond)
}
