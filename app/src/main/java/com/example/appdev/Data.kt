package com.example.appdev

class Data {
    //var unit: String = "km/h"

    companion object {
        var unit: String = "km/h"
        fun changeUnit(newUnit: String) {
            unit = newUnit
        }
    }
}