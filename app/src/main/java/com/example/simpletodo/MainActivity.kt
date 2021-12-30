package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter :TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // Remove item from the list
                listOfTasks.removeAt(position)
                // Notify adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

        // 1. Detect when the user clicks on the add button
//       findViewById<Button>(R.id.button).setOnClickListener {
//           // Code in here will be executed when the user clicks on the button
//            Log.i("Caren", "User clicked on button")
//        }
        //TEST LIST BELOW----------------------------------
        //listOfTasks.add("Do Laundry")
        //listOfTasks.add("Go for a walk")
        //-------------------------------------------------

        loadItems()

        //Lookup recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Set up the button and input field, so that the user can enter a task and add to the list

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // Get a reference to the button, and setting OnClick listener to it
        findViewById<Button>(R.id.button).setOnClickListener {
            // Grab the text the user has inputted into @id/addtaskfeild
            val userInputtedTask = inputTextField.text.toString()


            //Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            //Reset Text Field
            inputTextField.setText("")

            saveItems()

        }

    }

    // Save the data the user has inputted
    //Save data from writing and reading from a file

    //Get the file we need
        fun getDataFile() : File {
            // Every line in this file is going to represent a specific task
            return File(filesDir, "data.txt")
        }

    //load the methods by reading every line in our data file
        fun loadItems() {
            try {
                listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
            } catch(ioException: IOException){
                ioException.printStackTrace()
            }

        }

    //Save files by writing them into out data file
        fun saveItems() {
            try {
                FileUtils.writeLines(getDataFile(), listOfTasks)
            } catch(ioException: IOException){
                ioException.printStackTrace()
            }

        }


}