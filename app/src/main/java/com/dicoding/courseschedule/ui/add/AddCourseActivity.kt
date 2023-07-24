package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.ui.list.ListViewModelFactory
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var addCourseViewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        supportActionBar?.title = getString(R.string.add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ListViewModelFactory.createFactory(this)
        addCourseViewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                insertCourse()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun insertCourse() {
        val courseName = findViewById<TextInputEditText>(R.id.ed_course_name)
        val day = findViewById<Spinner>(R.id.spinner_day)
        val lecturer = findViewById<TextInputEditText>(R.id.ed_lecturer)
        val note = findViewById<TextInputEditText>(R.id.ed_note)

        val edCourse = courseName.text.toString().trim()
        val edLecturer = lecturer.text.toString().trim()
        val edNote = note.text.toString().trim()
        val edDay = day.selectedItemPosition
        val startTime = findViewById<TextView>(R.id.tv_start_time).text.toString()
        val endTime = findViewById<TextView>(R.id.tv_end_time).text.toString()

        if (edCourse.isNotEmpty() && edLecturer.isNotEmpty() && edNote.isNotEmpty()) {
            addCourseViewModel.insertCourse(
                courseName = edCourse,
                day = edDay,
                startTime = startTime,
                endTime = endTime,
                lecturer = edLecturer,
                note = edNote
            )
            finish()
        } else {

        }
        true
    }

    fun showTimePickerStartTime(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "startPicker")
    }

    fun showTimePickerEndTime(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "endPicker")
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        if (tag == "startPicker") {
            findViewById<TextView>(R.id.tv_start_time).text = dateFormat.format(calendar.time)
        } else {
            findViewById<TextView>(R.id.tv_end_time).text = dateFormat.format(calendar.time)
        }
    }
}