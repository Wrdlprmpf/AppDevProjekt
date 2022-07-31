package com.example.roomapp.fragments.main


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.roomapp.R



class MainFragment : Fragment() {
    private lateinit var btnSpeedometer: Button
    private lateinit var btnDatabase: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newView = inflater.inflate(R.layout.fragment_main, container, false)
        btnDatabase = newView.findViewById(R.id.btnDatabase)
        btnSpeedometer = newView.findViewById(R.id.btnSpeedometer)

        btnSpeedometer.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_speedometerActivity)
        }

        btnDatabase.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_listFragment)

        }

            return newView
    }
}