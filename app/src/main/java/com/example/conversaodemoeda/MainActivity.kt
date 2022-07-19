package com.example.conversaodemoeda

import android.icu.number.CurrencyPrecision
import android.icu.number.Precision.currency
import android.icu.util.Currency
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var result: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        result = findViewById<TextView>(R.id.txt_result)
        val buttonConverter = findViewById<Button>(R.id.btn_converter)


        buttonConverter.setOnClickListener {
            converter()
        }
    }

    private fun converter() {
        val selectedCurrency = findViewById<RadioGroup>(R.id.radio_group)

        val checked = selectedCurrency.checkedRadioButtonId

        val currency = when (checked) {
            R.id.radio_usd -> "USD"
            R.id.radio_eur -> "EUR"
            else -> "CLP"
        }

        val editField = findViewById<EditText>(R.id.edit_field)

        val value = editField.text.toString()

        if (value.isEmpty())
            return

        result.text = value
        result.visibility = View.VISIBLE

        Thread{
            // aqui acontece em paralelo

            val url = URL("https://economia.awesomeapi.com.br/last/${currency}")
            val conn = url.openConnection() as HttpURLConnection
            //Log.i("USD",url.toString())


            try {
                val data = conn.inputStream.bufferedReader().readText()

                val obj = JSONObject(data)

                runOnUiThread{

                    val res = obj.getDouble()

                    result.text = "${value.toDouble()} * res"
                    result.visibility = View.VISIBLE
                }


            }finally {
                conn.disconnect()
            }
        }.start()

    }

}

private operator fun Double.times(res: Unit) {

}

private fun JSONObject.getDouble() {

}




