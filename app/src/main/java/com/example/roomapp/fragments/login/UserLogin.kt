package com.example.roomapp.fragments.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomapp.CompanionUserData
import com.example.roomapp.R
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_login.view.*


class UserLogin : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var viewTest: View
    private lateinit var user: User


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewTest = inflater.inflate(R.layout.fragment_user_login, container, false)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        viewTest.login_button.setOnClickListener {


            val password = viewTest.editPassword.text.toString()
            val name = viewTest.editName.text.toString()


            user = mUserViewModel.getUser(name)

            if (user.name == name) {
                if(user.password != password) {
                    Toast.makeText(context, "wrong Password pls try again " + user.name, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_userLogin_self)
                }else{
                    Toast.makeText(context, "welcome " + user.name, Toast.LENGTH_SHORT).show()
                    CompanionUserData.user = user
                    findNavController().navigate(R.id.action_userLogin_to_mainFragment)
                }
            } else {
                Toast.makeText(context, "welcome " + name, Toast.LENGTH_SHORT).show()
                insertDataToDatabase()
                CompanionUserData.user = user
                findNavController().navigate(R.id.action_userLogin_to_mainFragment)
            }

        }
        return viewTest
    }

    private fun insertDataToDatabase() {
        val name = viewTest.editName.text.toString()
        val password = viewTest.editPassword.text.toString()

        if(inputCheck(name, password)){
            // Create User Object
            val user = User(
                 name,
                0f,
                0f,
                password
            )
            // Add Data to Database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate Back
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName))
    }

}