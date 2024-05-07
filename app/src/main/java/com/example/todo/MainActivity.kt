package com.example.todo

import android.app.DatePickerDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.databinding.AddItemDialogBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var binding : ActivityMainBinding?= null
    private lateinit var userViewModel:UserViewModel
    private lateinit var userAdapter:UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        userAdapter= UserAdapter(this,ArrayList<User>())
        binding?.recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(this@MainActivity)
            adapter=userAdapter
        }


        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.getAllUserData(applicationContext)?.observe(this, Observer {
            userAdapter.setData(it as ArrayList<User>)
        })
        binding?.addTaskBtn?.setOnClickListener {
            data_dialog()
        }


    }

    private fun data_dialog(){
        val customDialog = Dialog(this@MainActivity)
        val dialogBinding = AddItemDialogBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)

        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.goBackBtn.setOnClickListener {
            customDialog.dismiss()
        }
        dialogBinding.imgCalendar.setOnClickListener {
            val myCalender = Calendar.getInstance()
            val year = myCalender.get(Calendar.YEAR)
            val month = myCalender.get(Calendar.MONTH)
            val day = myCalender.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                    view, year,month,dayOfMonth ->
                Toast.makeText(this, "Date is Selected ", Toast.LENGTH_SHORT).show()
                val selectedDate = "$dayOfMonth / ${month+1} / $year"
                dialogBinding.dateInDialog.text=selectedDate
                val sdf = SimpleDateFormat("dd /MM /yyyy", Locale.ENGLISH)
                val date = sdf.parse(selectedDate)

            },
                year,
                month,
                day
            ).show()
        }

        dialogBinding.addDataBtn.setOnClickListener {
            val getTask = dialogBinding.txtTask.text.toString().trim()
            val getDate = dialogBinding.dateInDialog.text.toString().trim()

            if(!TextUtils.isEmpty(getTask) && !TextUtils.isEmpty(getDate)){
                userViewModel.insert(this,User(getTask,getDate))
                customDialog.dismiss()

            }
            else{
                Toast.makeText(this, "Enter the feilds !!", Toast.LENGTH_SHORT).show()
            }
        }
        customDialog.show()
    }


}
