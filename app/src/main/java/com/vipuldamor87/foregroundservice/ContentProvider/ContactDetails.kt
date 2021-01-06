package com.vipuldamor87.foregroundservice.ContentProvider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.vipuldamor87.foregroundservice.R
import kotlinx.android.synthetic.main.activity_contact_details.*

class ContactDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)
        val name = intent.getStringExtra("name")
        val number = intent.getStringExtra("number")
        coName.setText("Contact Name: $name")
        coNum.setText("Contact Num: $number")
       // Toast.makeText(applicationContext,"$name $number",Toast.LENGTH_SHORT).show()
    }
}