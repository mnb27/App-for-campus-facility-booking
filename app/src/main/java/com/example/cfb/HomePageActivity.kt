package com.example.cfb

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.cfb.ClassroomBooking.ClassRoomBookingActivity
import com.example.cfb.LabBooking.LabBookingActivity
import com.example.cfb.SportBooking.SportsBookingActivity
import com.google.firebase.auth.FirebaseAuth

class HomePageActivity : AppCompatActivity() {

    companion object{
        private val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val classBooking: CardView = findViewById(R.id.classbook)
        val labBooking: CardView = findViewById(R.id.labbook)
        val sportBooking: CardView = findViewById(R.id.sportbook)
        val viewBooking: CardView = findViewById(R.id.viewBooking)

        val markAttendance: CardView = findViewById(R.id.MarkAttendanceCard)
        val viewAttendance: CardView = findViewById(R.id.ViewAttendanceCard)

        val editProfile: Button = findViewById(R.id.editProfileB)
        val auth = FirebaseAuth.getInstance()
        val logout: ImageView = findViewById(R.id.logout)


        editProfile.setOnClickListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }
        classBooking.setOnClickListener {
            val intent = Intent(this, ClassRoomBookingActivity::class.java)
            startActivity(intent)
        }

        labBooking.setOnClickListener {
            val intent = Intent(this, LabBookingActivity::class.java)
            startActivity(intent)
        }

        sportBooking.setOnClickListener {
            val intent = Intent(this, SportsBookingActivity::class.java)
            startActivity(intent)
        }

        viewBooking.setOnClickListener {
            val intent = Intent(this,ViewBookingHistoryActivity::class.java)
            startActivity(intent)
        }

        markAttendance.setOnClickListener {

            //check permission
            if (ContextCompat.checkSelfPermission(
                    applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    , HomePageActivity.REQUEST_PERMISSION_REQUEST_CODE
                )
            }

            else {
                val intent = Intent(this,MarkAttendanceActivity::class.java)
                startActivity(intent)
            }
        }

        viewAttendance.setOnClickListener {
            val intent = Intent(this,UserAttendanceActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }
}