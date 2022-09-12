package com.example.firebaseuiauthenticationmarkusneuhoff.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseuiauthenticationmarkusneuhoff.R
import com.example.firebaseuiauthenticationmarkusneuhoff.adapter.MyAdapter
import com.example.firebaseuiauthenticationmarkusneuhoff.data.Person
import com.example.firebaseuiauthenticationmarkusneuhoff.databinding.FragmentListBinding
import com.example.firebaseuiauthenticationmarkusneuhoff.myViewModel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), MyAdapter.ClickListener, SearchView.OnQueryTextListener {

    private lateinit var _binding: FragmentListBinding
    private val binding get() = _binding!!

    private val myUserViewModel: MyViewModel by viewModels()
    private val myAdapter: MyAdapter by lazy { MyAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = myAdapter

        // UserViewModel
        myUserViewModel.readData.observe(viewLifecycleOwner, Observer { user ->
            myAdapter.setData(user)
        })

        binding.floatingActionButton.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToAddFragment()
            findNavController().navigate(action)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onMyItemClick(person: Person) {
        val action = ListFragmentDirections.actionListFragmentToEditFragment(person)
        findNavController().navigate(action)
    }


    // deleting stuff

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_order_by_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)


    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_sort_by_date_created_desc) {

            /// sort by last name
            myUserViewModel.sortByNames().observe(this) { list ->
                list.let {
                    myAdapter.setData(list)
                }
            }
        }

        if (item.itemId == R.id.action_sort_by_age_desc) {
            myUserViewModel.orderByAgeDesc().observe(this) { list ->
                list.let {
                    myAdapter.setData(list)
                }
            }
        }

        if (item.itemId == R.id.action_delete_all_completed_tasks) {

            deleteAllUsers()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        myUserViewModel.searchDatabase(searchQuery).observe(this, Observer { list ->
            list.let {
                myAdapter.setData(list)
            }
        })
    }

    private fun deleteAllUsers() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            myUserViewModel.deleteAllData()
            Toast.makeText(requireContext(), "Deleted All Users", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("No") { _, _ -> }
        builder.setMessage("Are you sure you want to delete ${myUserViewModel.readData.value?.size} users?")
        builder.create().show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding.root
    }
}