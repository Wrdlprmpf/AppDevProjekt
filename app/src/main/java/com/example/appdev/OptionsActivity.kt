package com.example.appdev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class OptionsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var units = arrayOf("km/h", "mph", "m/s")
    lateinit var speedo : SpeedometerActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        val spinner = findViewById<Spinner>(R.id.unitSpinner)
        spinner.onItemSelectedListener = this

        val adapter: ArrayAdapter <*> = ArrayAdapter<Any>(this, android.R.layout.simple_spinner_dropdown_item, units)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        speedo = SpeedometerActivity()

        conversion()
    }

    var pos = 0
    fun conversion() {
        when(pos) {
            0 -> speedo.units1.text = "km/h"
            1 -> speedo.units2.text = "mph"
            2 -> speedo.units3.text = "m/s"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuOptions -> startActivity(Intent(this,OptionsActivity::class.java))
            R.id.menuMain -> startActivity(Intent(this,MainActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
        pos = position
        conversion()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}