package com.dailtonrabelo.workoutapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_workout_main.*
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent


data class Workout(val bench: String = "", val squat: String = "", val deadlift: String = "", val date: String = "")

class WorkoutMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_main)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("workout-fabcb")


        submitButton.setOnClickListener {
            val c = Calendar.getInstance().getTime();
            val df = SimpleDateFormat("MMM-dd-yyyy")
            val formattedDate = df.format(c)
            val person1 = Workout(benchEditText.text.toString(), squatEditText.text.toString(), deadliftEditText.text.toString(), formattedDate.toString());
            myRef.push().setValue(person1)
        }

        resultsButton.setOnClickListener({
            val myIntent = Intent(this,
                    Results::class.java)
            startActivity(myIntent)
        })


    }

}


