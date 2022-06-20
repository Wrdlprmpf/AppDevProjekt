package com.example.appdev

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.core.location.LocationManagerCompat.removeUpdates


class SpeedometerActivity : AppCompatActivity(), LocationListener, SensorEventListener {
	lateinit var lm: LocationManager
	lateinit var sensorManager: SensorManager

	var accelerator: Sensor? = null

	lateinit var speedOutput: TextView
	lateinit var startBtn: Button
	lateinit var stopBtn: Button
	lateinit var topSpeedOutput: TextView
	lateinit var averageSpeedOutput: TextView
	lateinit var pointer: ImageView
	lateinit var ring: ImageView

	lateinit var units1: TextView
	lateinit var units2: TextView
	lateinit var units3: TextView

	var topSpeed: Float = 0F
	var averageSpeed: Float = 0F
	var clicked: Boolean = false

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
		pointer = findViewById(R.id.pointer)
		ring = findViewById(R.id.ring)
		units1 = findViewById(R.id.unitText1)
		units2 = findViewById(R.id.unitText2)
		units3 = findViewById(R.id.unitText3)

		lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
		sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

		accelerator = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER).also {
			sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
		}

		permissionCheck()

		startBtn.setOnClickListener {
			clicked = true
			println(clicked)
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0F, this)
		}

		stopBtn.setOnClickListener {
			clicked = false
			println(clicked)
			lm.removeUpdates(this)
			speedOutput.text = "0.0"
		}
	}

	override fun onPause() {
		super.onPause()
		sensorManager.unregisterListener(
			this,
			sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
		);
	}

	fun zeroToHundred(){
		/**TODO Wenn Geschwindigkeit zb 3 sekunden auf 0 konstant bleibt (oder auch per button als reset)
		 * Geschwindigkeit > 0 erst zu zählen beginnen da ansonsten Standzeit gezählt wird.
		 * Einfach sobald über 100kmh erreicht wurde Timer zählen und in Sekunden anzeigen in TextView.
		 *
		 *
		 */
	}

	fun movePointer(x: Float, y: Float) {
		var width = getScreenWidth(this)
		var height = getScreenHeight(this)
		pointer.x = width / 2F - 25 + (x * 150)
		pointer.y = height / 2F - 25 + (y * 150)

		//Init für Ringheight
		ring.y = height / 2F - 300
	}

	private fun getScreenHeight(activity: Activity): Int {
		val displayMetrics = DisplayMetrics()
		activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
		return displayMetrics.heightPixels
	}

	private fun getScreenWidth(activity: Activity): Int {
		val displayMetrics = DisplayMetrics()
		activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
		return displayMetrics.widthPixels
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
			var speed = (location.speed * 3600) / 1000
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
		var avg = 0F
		for (s in speeds) {
			avg += s
		}
		averageSpeedOutput.text = (avg / speeds.size).toString()
	}

	override fun onSensorChanged(p0: SensorEvent?) {
		if (p0?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

			var gravityV = FloatArray(3)

			val alpha = 0.8f;
			//gravity is calculated here
			gravityV[0] = alpha * gravityV[0] + (1 - alpha) * p0.values[0];
			gravityV[1] = alpha * gravityV[1] + (1 - alpha) * p0.values[1];
			gravityV[2] = alpha * gravityV[2] + (1 - alpha) * p0.values[2];
			//acceleration retrieved from the event and the gravity is removed
			var x = p0.values[0] - gravityV[0];
			var y = p0.values[1] - gravityV[1];
			var z = p0.values[2] - gravityV[2];

			//m/s^2 to g-force
			x = x / 9.81f
			y = y / 9.81f
			//z = z/9.81f

			println("x " + roundNumber(x))
			println("y " + roundNumber(y))
			//no need
			//println("z " + roundNumber(z))

			movePointer(x, y)
		}
	}

	fun roundNumber(n: Float): String {
		return "%.1f".format(n)
	}

	override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
	}
}