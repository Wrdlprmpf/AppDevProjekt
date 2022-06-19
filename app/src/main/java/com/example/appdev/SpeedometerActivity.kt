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
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.removeUpdates


class SpeedometerActivity : AppCompatActivity(), LocationListener {
	lateinit var lm: LocationManager
	lateinit var speedOutput: TextView
	lateinit var startBtn: Button
	lateinit var stopBtn: Button
	var topSpeed: Float = 0F
	var averageSpeed: Float = 0F
	lateinit var topSpeedOutput: TextView
	lateinit var averageSpeedOutput: TextView
	var clicked : Boolean = false

	var speeds = ArrayList<Float>()

	@SuppressLint("MissingPermission")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_speedometer)
		speedOutput = findViewById(R.id.speedometerOutput)
		topSpeedOutput = findViewById(R.id.topSpeedOutput)
		averageSpeedOutput = findViewById(R.id.averageSpeedOutput)
		startBtn = findViewById(R.id.startBtn)
		stopBtn = findViewById(R.id.stopBtn)

		lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
		permissionCheck()

		if (!clicked) {
			lm.removeUpdates(this)

		} else {
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0F, this)
			var isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
			val lm = this.getSystemService(LOCATION_SERVICE) as LocationManager
		}
	}

	fun permissionCheck() {
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
			R.id.menuOptions -> startActivity(Intent(this, OptionsActivity::class.java))
			R.id.menuMain -> startActivity(Intent(this, MainActivity::class.java))
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onLocationChanged(location: Location) {
		if (location == null) {
			speedOutput.text = "0 km/h"
			println("0")
			speeds.add(0F)
		} else {
			var speed = (location.getSpeed() * 3600) / 1000
			speedOutput.text = speed.toString()
			maximumSpeed(speed)
			speeds.add(speed)
			println(speed.toString())
			println(location.latitude.toString())
		}
		averageSpeed()
	}

	fun maximumSpeed(speed: Float) {
		if (speed > topSpeed) {
			topSpeed = speed
			topSpeedOutput.text = topSpeed.toString()
		}
	}

	fun averageSpeed() {
		var avg = 0F;
		for (s in speeds) {
			avg += s
		}
		averageSpeedOutput.text = (avg / speeds.size).toString()
	}

	fun clickStart() {
		startBtn.setOnClickListener {
			clicked = true
		}
	}

	fun clickStop() {
		stopBtn.setOnClickListener {
			clicked = false
		}
	}
}