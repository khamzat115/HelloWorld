package com.example.helloapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

//for handling user interaction
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  //show the activity_main layout file

        //1. Display customized to show hello from Kahmil.
        //2. User can tap a button to change the text color of the label
            //i. Add a button to layout
            //ii. Set up logic to know when user has tapped the button
            //iii. Change the color of the text

        findViewById<Button>(R.id.button).setOnClickListener {
            //Handle the button tap (change color of text)
            Log.i("Kahmil", "Tapped on the button") //confirms button is working
            //1. Set a reference to text view and set the color of the text view
            findViewById<TextView>(R.id.textView).setTextColor(getResources().getColor(R.color.white))
        } //get a reference to button
    }
}