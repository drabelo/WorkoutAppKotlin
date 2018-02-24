package com.dailtonrabelo.workoutapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_results.*
import android.support.v7.widget.DividerItemDecoration


class Results : AppCompatActivity() {

    val results: MutableList<Workout> = mutableListOf()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("workout-fabcb")
        val context = this;

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(results) { it.getValue<Workout>(Workout::class.java) }
                var layoutManager = LinearLayoutManager(context)
                recycler_view.layoutManager = layoutManager;

                val dividerItemDecoration = DividerItemDecoration(recycler_view.getContext(),
                        layoutManager.getOrientation())
                recycler_view.addItemDecoration(dividerItemDecoration)

                adapter = RecyclerAdapter(results)
                recycler_view.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
    }

    class RecyclerAdapter(val results: MutableList<Workout>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
            val v = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.card_view, viewGroup, false)
            return ViewHolder(v)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var benchTV: TextView
            var squatTV: TextView
            var deadliftTV: TextView
            var dateTV: TextView

            init {
                benchTV = itemView.findViewById(R.id.benchCardTextView)
                squatTV = itemView.findViewById(R.id.squatCardTextView)
                deadliftTV = itemView.findViewById(R.id.deadliftCardView)
                dateTV = itemView.findViewById(R.id.dateCardTextView);
            }
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
            viewHolder.benchTV.text = results.get(i).bench
            viewHolder.squatTV.text = results.get(i).squat
            viewHolder.dateTV.text = results.get(i).date
            viewHolder.deadliftTV.text = results.get(i).deadlift
        }

        override fun getItemCount(): Int {
            return results.size
        }
    }
}
