package com.example.firebaseuiauthenticationmarkusneuhoff.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.firebaseuiauthenticationmarkusneuhoff.data.Person

class MyDiffUtil(
    private val oldList : List<Person>,
    private val newList : List<Person>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemPosition == newItemPosition
//        return when{
//            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
//                false
//            }
//
//            oldList[oldItemPosition].firstName != newList[newItemPosition].firstName -> {
//                false
//            }
//
//            oldList[oldItemPosition].middleName != newList[newItemPosition].middleName -> {
//                false
//            }
//
//            oldList[oldItemPosition].lastName != newList[newItemPosition].lastName -> {
//                false
//            }
//
//            oldList[oldItemPosition].dateCreated != newList[newItemPosition].dateCreated -> {
//                false
//            }
//
//            oldList[oldItemPosition].gender != newList[newItemPosition].gender -> {
//                false
//            }
//
//            oldList[oldItemPosition].age != newList[newItemPosition].age -> {
//                false
//            }
//            else -> true
//        }
    }
}