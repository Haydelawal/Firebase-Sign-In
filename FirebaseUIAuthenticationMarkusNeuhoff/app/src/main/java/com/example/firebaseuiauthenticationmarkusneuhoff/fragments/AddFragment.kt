package com.example.firebaseuiauthenticationmarkusneuhoff.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.firebaseuiauthenticationmarkusneuhoff.R
import com.example.firebaseuiauthenticationmarkusneuhoff.data.Person
import com.example.firebaseuiauthenticationmarkusneuhoff.databinding.FragmentAddBinding
import com.example.firebaseuiauthenticationmarkusneuhoff.myViewModel.MyViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {

    private lateinit var _binding: FragmentAddBinding
    private val binding get() = _binding!!

    private val myUserViewModel: MyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)


        binding.fabSaveTask.setOnClickListener {
            inputCheck()
        }

        return binding.root
    }

    private fun inputCheck() {
        val fNameData = binding.fNameTV.text.toString()
        val mNameData = binding.mNameTV.text.toString()
        val lNameData = binding.surnameTV.text.toString()

        val selectedGender = binding.autoCompleteTextView.text.toString()
        val ageData = binding.ageTV.text.toString().toInt()
        val dateData = System.currentTimeMillis()

       if (fNameData.isEmpty() or fNameData.isBlank()) {
           Snackbar.make(requireView(), "First Name Cannot Be Empty", Snackbar.LENGTH_LONG)
               .setAction("OKAY") {}
               .show()
       }

        else if (mNameData.isEmpty() or mNameData.isBlank()) {
            Snackbar.make(requireView(), "Middle Name Cannot Be Empty", Snackbar.LENGTH_LONG)
                .setAction("OKAY") {}
                .show()
        } else if (lNameData.isEmpty() or lNameData.isBlank()) {
            Snackbar.make(requireView(), "Surname Name Cannot Be Empty", Snackbar.LENGTH_LONG)
                .setAction("OKAY") {}
                .show()
        }
       else if (ageData == 0 ) {
            Snackbar.make(requireView(), "Age Cannot Be Empty", Snackbar.LENGTH_LONG)
                .setAction("OKAY") {}
                .show()
        }
       else {
            val newPerson = Person(0,fNameData, mNameData, lNameData, dateData, selectedGender, ageData.toInt())
           myUserViewModel.insertData(newPerson)

           Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_SHORT).show()

           val action = AddFragmentDirections.actionAddFragmentToListFragment()
           findNavController().navigate(action)
        }

    }


    override fun onResume() {
        super.onResume()
        // using exposed drop down
        val genders = resources.getStringArray(R.array.Gender)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, genders)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding.root
    }

}