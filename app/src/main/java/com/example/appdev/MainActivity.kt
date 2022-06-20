package com.example.appdev

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    lateinit var btnSpeedometer: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSpeedometer = findViewById(R.id.btnSpeedometer)

        listeners()
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

    fun listeners(){
        btnSpeedometer.setOnClickListener{
            startActivity(Intent(this,SpeedometerActivity::class.java))
        }
    }
}