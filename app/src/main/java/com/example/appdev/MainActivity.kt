package com.example.appdev

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var optionsIntent:Intent
    lateinit var btnSpeedometer: Button
    lateinit var btnDatabase: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        optionsIntent = Intent(this@MainActivity, OptionsActivity::class.java)
        btnSpeedometer = findViewById(R.id.btnSpeedometer)
        btnDatabase = findViewById(R.id.btnDatabase)

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
        btnDatabase.setOnClickListener{
            startActivity(Intent(this,DatabaseActivity::class.java))
        }
    }
}