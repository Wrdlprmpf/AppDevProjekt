package com.example.roomapp

import com.example.roomapp.model.User

class CompanionUserData {
    //var unit: String = "km/h"

    companion object {
        var unit: String = "km/h"
        var user : User = User("",0f,0f,"")

        fun changeUnit(newUnit: String) {
            unit = newUnit
        }
    }
}