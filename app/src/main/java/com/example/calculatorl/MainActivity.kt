package com.example.calculatorl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var result: EditText //Inicialización tardía
    private lateinit var newNumber: EditText
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    //Variables to hold the operands and type of calculation
    private var operando1: Double? = null //?=llamada segura a valor de la variable. esto hace que devuelva null en la operación que incluya esta variable
    private var operando2: Double = 0.0
    private var pendingOperation = "="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById(R.id.result)
        newNumber = findViewById(R.id.newNumber)

        //Data input buttons
        val button0: Button = findViewById(R.id.button0)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val buttonDot: Button = findViewById(R.id.buttonDec)

        //Operation buttons
        val buttonEquals = findViewById<Button>(R.id.buttonEq)
        val buttonDiv = findViewById<Button>(R.id.buttonDiv)
        val buttonMul = findViewById<Button>(R.id.buttonMul)
        val buttonMinus = findViewById<Button>(R.id.buttonMinus)
        val buttonPlus = findViewById<Button>(R.id.buttonPlus)

        //set listeners for values
        val listener = View.OnClickListener { v ->
            val selectedValue = v as Button
            newNumber.append(selectedValue.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        //set listeners for operators
        val opListener = View.OnClickListener{ v ->
            val selectedOperation = (v as Button).text.toString()
            val value = newNumber.text.toString()
            if (value.isNotEmpty()){
                performOperation(value,selectedOperation)
            }
            pendingOperation = selectedOperation
            displayOperation.text = pendingOperation
        }

        buttonEquals.setOnClickListener(opListener)
        buttonDiv.setOnClickListener(opListener)
        buttonMul.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)

    }

    private fun performOperation(value: String, operation: String){
        if ( operando1 == null){
            operando1 = value.toDouble()
        }else{
            operando2 = value.toDouble()

            if(pendingOperation == "="){
                pendingOperation = operation
            }
            when (pendingOperation){
                "=" -> operando1=operando2
                "/" -> if (operando2 == 0.0){
                            operando1 = Double.NaN
                    }else{
                            operando1 = operando1!! / operando2 //El !! convierte el tipo de la variable Double? a una que no pueda ser nula para la operación local. Esto lo aplico porque en el comienzo de la funcion, estoy entrando sólo si operando1 no es null
                    }
                "*" -> operando1 = operando1!! * operando2
                "+" -> operando1 = operando1!! + operando2
                "-" -> operando1 = operando1!! - operando2
            }
        }
        result.setText(operando1.toString())
        newNumber.setText("")
    }
}