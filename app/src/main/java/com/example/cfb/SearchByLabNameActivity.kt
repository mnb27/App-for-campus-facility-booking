package com.example.cfb

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class SearchByLabNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_lab_name)

        val search: Button = findViewById(R.id.searchButton)

        val datePicker: Button = findViewById(R.id.pickDate)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val pickedDate: TextInputLayout = findViewById(R.id.date)
        var date = ""
        pickedDate.isEnabled = false

        datePicker.setOnClickListener {
            var dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                pickedDate.editText?.setText("" + dayOfMonth + " / " + (monthOfYear.toInt()+1).toString() + " / " + year)
                var monthnumber = ""
                var correctMonth = monthOfYear+1
                if(correctMonth < 10){
                    monthnumber = "0$correctMonth"
                }
                date = dayOfMonth.toString() + monthnumber + year.toString()
            }, year, month, day)

            val now = System.currentTimeMillis() - 1000
            //dpd.datePicker.minDate = now
            dpd.datePicker.maxDate = now + (1000*60*60*24*7)
            dpd.show()
        }

        val name: TextInputLayout = findViewById(R.id.name)

        val firestore = FirebaseFirestore.getInstance()
        var list: MutableMap<String,String> = mutableMapOf()
        search.setOnClickListener {
            val dateText = date
            firestore.collection(dateText).document(name.editText?.text.toString()).get()
                .addOnSuccessListener {
                    if(it.exists()) {
                        var result = it.data

                        if (result != null) {

                            val intent = Intent(this,RoomSlotActivity::class.java)
                            intent.putExtra("date",dateText)
                            intent.putExtra("name",name.editText?.text.toString())
                            startActivity(intent)
                        }
                    }
                    else{
                        Toast.makeText(this,"No Such Room Exists Or Room is Not Available for Booking on this Particular Date",Toast.LENGTH_LONG).show()
                    }

                }

        }
    }
}