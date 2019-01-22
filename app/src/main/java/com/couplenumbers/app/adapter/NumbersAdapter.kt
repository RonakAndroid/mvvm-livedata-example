package com.couplenumbers.app.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.couplenumbers.app.R
import com.couplenumbers.app.model.NumberModel
import com.couplenumbers.app.utils.COUPLE_NUMBER
import kotlinx.android.synthetic.main.row_couple_numbers.view.*
import kotlinx.android.synthetic.main.row_other_numbers.view.*

class NumbersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var numberList: MutableList<NumberModel.Number> = ArrayList()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CoupleNumberViewHolder -> holder.itemView.tvCoupleNumber.text = numberList[position].number.toString()
            else -> holder.itemView.tvOtherNumber.text = numberList[position].number.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, itemViewType: Int): RecyclerView.ViewHolder {
        return if (itemViewType == COUPLE_NUMBER) {
            CoupleNumberViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.row_couple_numbers,
                    parent,
                    false
                )
            )
        } else {
            OtherNumberViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.row_other_numbers,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return numberList.size
    }

    override fun getItemViewType(position: Int): Int {
        return numberList[position].typeOfNumber
    }

    /**
     * this will help to update list of adapter.
     */
    fun updateList(numberList: MutableList<NumberModel.Number>?, clear: Boolean = false) {
        if (clear) {
            this.numberList.clear()
        }
        numberList?.let {
            this.numberList.addAll(it)
        }
        notifyDataSetChanged()
    }

    /**
     * this class refer to Couple Number View Holder
     */
    inner class CoupleNumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * this class refer to Couple Number View Holder
     */
    inner class OtherNumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}