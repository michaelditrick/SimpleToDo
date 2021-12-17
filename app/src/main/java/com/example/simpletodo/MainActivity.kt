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
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun OnItemLongClicked(position: Int) {

                //1.Remove item from the list
                listOfTasks.removeAt(position)
                //2. Notify adapter that item has been removed
                adapter.notifyDataSetChanged()

                //save Item when user delete it
                saveItems()
            }
        }

        /*
        //1. detect when a user clicks a button
        findViewById<Button>(R.id.button).setOnClickListener {
            // code inside this block is going to be executed when the user clicks the button
            Log.i("Caren","user clicked on button")

        }
        */
         loadItems()

        //Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks,onLongClickListener)

        //Attach the adapter the recyclerview to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        /*
        Set up the button and input field, so that the user can enter tasks.
        Get a reference to the button and then set an OnClickListener         */

        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        findViewById<Button>(R.id.button).setOnClickListener {
            // 1. Grab the text user has in the input field

            val userInputTask = findViewById<EditText>(R.id.addTaskField).text.toString()

            // 2. Add that string the list of tasks: listOfTasks
            listOfTasks.add(userInputTask)

            // Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            //3. Reset text field
            inputTextField.setText("")

            //save items after user input

            saveItems()


        }
    }

    // save data from user input
    //save it by writing and reading from a file

    //get file needed
    fun getDataFile() : File {
        //Each line in the file stands for a single task
        return File(filesDir, "data.txt")
    }

    //Load items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }


    }

    //Save items by writing them into our data file
    fun saveItems() {

        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }
}