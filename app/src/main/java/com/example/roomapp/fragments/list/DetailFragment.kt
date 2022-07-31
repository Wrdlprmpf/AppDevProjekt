package com.example.roomapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_detail.view.*


class DetailFragment : Fragment() {

    private val args : DetailFragmentArgs by navArgs()

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        view.name_detail.text = args.curentUser.name
        view.average_speed_detail.text = args.curentUser.average_speed.toString()
        view.top_speed_detail.text = args.curentUser.top_speed.toString()


        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Add menu
        setHasOptionsMenu(true)

        return view
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
            mUserViewModel.deleteUser(args.curentUser)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.curentUser.name}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_detailFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.curentUser.name}?")
        builder.setMessage("Are you sure you want to delete ${args.curentUser.name}?")
        builder.create().show()
    }
}