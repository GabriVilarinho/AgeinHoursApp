package gv.projetos.ageinminutes2

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

   private var tvSelectedDate : TextView? = null // won't init with a value at this point, it's private because
                                                 // I don't want the UI element to be accessed by something else when it's not being shown (it will crash otherwise)
   private var tvAgeInHours : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate) // So we can Init the variables on OnCreate
        tvAgeInHours = findViewById(R.id.tvAgeInHours)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker(){ // Sets to private so it's accessed only by the class

        val myCalendar = Calendar.getInstance() // That's how we can access the calendar library from a java .util
        val year = myCalendar.get(Calendar.YEAR) //To get the year
        val month = myCalendar.get(Calendar.MONTH) // idem, the month
        val day = myCalendar.get(Calendar.DAY_OF_MONTH) // can't be ''DAY''
        val dpd = DatePickerDialog(this, //We transform the DatePickerDialog into a variable to make it easier to use
            DatePickerDialog.OnDateSetListener{ view, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(this, "Dia $selectedDayOfMonth do mês ${selectedMonth+1} do ano de $selectedYear", Toast.LENGTH_LONG).show()

            val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear" //now we can store the date in the right format inside DatePickerDialog

            tvSelectedDate?.text = selectedDate //assigns a text to the id

            //We need a dateformat as an object to calculate how much time has passed:

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH) //It defines a pattern that we want to use for our date

            val theDate = sdf.parse(selectedDate) // creates a date object from the SimpleDateFormat
            theDate?.let{   //We create this .let when it's not empty
                val selectedDateInMinutes = theDate.time / 60000 //60 mins in milliseconds

                val currentDateInMinutes = System.currentTimeMillis()/60000 // How much time has passed until now

                val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes // Time between the current one and the selected one
                val timeInHours = differenceInMinutes / 60

                tvAgeInHours?.text = timeInHours.toString() //We need to convert TimeInHours from Long to String
            }


          },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000 // Milissegundos agora - no total do dia
        dpd.show()  // the .show() method is used with the variable dpd


    }
}