package com.example.appdev

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class SpeedometerActivity : AppCompatActivity(),LocationListener {
    lateinit var lm:LocationManager
    lateinit var speedOutput: TextView

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speedometer)
        speedOutput = findViewById(R.id.speedometerOutput)
        println("Gets Here1---------------------------------")
        permissionCheck()

        lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        println("Gets Here2---------------------------------")

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0F,this)
        var isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.d("Tag","Gets Here LOG --------------------------")
        val lm = this.getSystemService(LOCATION_SERVICE) as LocationManager
    }

    fun permissionCheck(){
        try {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    101
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
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

    override fun onLocationChanged(location: Location) {
        if(location==null){
            speedOutput.text = "0"
            println("IF")
        }
        else{
            speedOutput.text = ((location.getSpeed()*3600)/1000).toString()
            println("ELSE")
        }

    }


}