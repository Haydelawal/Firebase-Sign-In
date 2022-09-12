package com.example.firebaseuiauthenticationmarkusneuhoff.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseuiauthenticationmarkusneuhoff.data.Person
import com.example.firebaseuiauthenticationmarkusneuhoff.databinding.RowLayoutBinding


class MyAdapter(
    private val listener: ClickListener
    ) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var oldPersonList = emptyList<Person>()

    inner class MyViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = oldPersonList[position]
        holder.binding.ageTextView.text = oldPersonList[position].age.toString()
        holder.binding.firstNameTextView.text = oldPersonList[position].firstName
        holder.binding.lastNameTextView.text = oldPersonList[position].lastName

        holder.itemView.setOnClickListener {
            listener.onMyItemClick(currentItem)
        }

    }



    override fun getItemCount(): Int {
        return oldPersonList.size
    }

    fun setData(newPersonList: List<Person>){
        // diff util
       val diffUtil = MyDiffUtil(oldPersonList, newPersonList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldPersonList = newPersonList
        diffResults.dispatchUpdatesTo(this)
    }

    interface ClickListener {

        // item on click listener
        fun onMyItemClick(person: Person)
    }


    // then in my wherever its needed fragment
    // class ListFragment : Fragment(), MyAdapter.MyClickListener,

//    override fun onMyItemClick(person: Person) {
//        val selectedPerson = person
//
//        val action = ListFragmentDirections.actionListFragmentToEditFragment(selectedPerson!!)
//        findNavController().navigate(action)
//    }

}
