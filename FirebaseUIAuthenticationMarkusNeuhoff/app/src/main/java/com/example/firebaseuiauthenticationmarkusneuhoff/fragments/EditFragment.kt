package com.example.firebaseuiauthenticationmarkusneuhoff.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.firebaseuiauthenticationmarkusneuhoff.R
import com.example.firebaseuiauthenticationmarkusneuhoff.data.Person
import com.example.firebaseuiauthenticationmarkusneuhoff.databinding.FragmentEditBinding
import com.example.firebaseuiauthenticationmarkusneuhoff.myViewModel.MyViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFragment : Fragment() {

    private lateinit var _binding: FragmentEditBinding
    private val binding get() = _binding!!
    private val args: EditFragmentArgs by navArgs()
    private val myUserViewModel: MyViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBinding.inflate(inflater, container, false)

        binding.fNameTV.setText(args.person.firstName)
        binding.mNameTV.setText(args.person.middleName)
        binding.surnameTV.setText(args.person.lastName)

        binding.autoCompleteTextView.setText(args.person.gender)
        binding.ageTV.setText(args.person.age.toString())
        binding.dateTV.text = "Created At: ${args.person.createdDateFormatted}"


        binding.fabUpdateTask.setOnClickListener {
            inputCheck()
        }

        setHasOptionsMenu(true)

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
        }
        else if (lNameData.isEmpty() or lNameData.isBlank()) {
            Snackbar.make(requireView(), "Surname Name Cannot Be Empty", Snackbar.LENGTH_LONG)
                .setAction("OKAY") {}
                .show()
        }
        else if (ageData == 0) {
            Snackbar.make(requireView(), "Age Cannot Be Empty", Snackbar.LENGTH_LONG)
                .setAction("OKAY") {}
                .show()
        }
        else {
            val updatedPerson =
                Person(args.person.id, fNameData, mNameData, lNameData, dateData, selectedGender, ageData.toInt())
            myUserViewModel.updateData(updatedPerson)

            Toast.makeText(
                requireContext(),
                "Successfully Updated ${args.person.id}",
                Toast.LENGTH_SHORT
            ).show()

            val action = EditFragmentDirections.actionEditFragmentToListFragment()
            findNavController().navigate(action)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            myUserViewModel.deleteData(args.person)
            val action = EditFragmentDirections.actionEditFragmentToListFragment()
            findNavController().navigate(action)
            Toast.makeText(
                requireContext(),
                "Deleted ${args.person.firstName} ${args.person.lastName} user",
                Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNegativeButton("No") { _, _ -> }
        builder.setMessage("Are you sure you want to delete ${args.person.firstName} ${args.person.lastName} user?")
        builder.create().show()
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