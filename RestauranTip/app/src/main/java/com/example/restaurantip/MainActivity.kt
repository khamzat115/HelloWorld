package com.example.restaurantip

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

//1. Wanna be able to edit the percentage straight away without dragging
//2. Create a list of things bought
//3. Split the list by N people
//4. Round total up or down?

private const val TAG = "MainActivity"
private const val  INITIAL_TIP_PERCENT = 15
var totalAmount = 0.0
var tipAmount = 0.0
var numPeople = 1.0
var Checked: Boolean = false
class MainActivity : AppCompatActivity() {
    private lateinit var BaseAmountet: EditText
    private lateinit var TipSeekBar: SeekBar
    private lateinit var TipPercenttv: TextView
    private lateinit var TipAmounttv: TextView
    private lateinit var TotalAmounttv: TextView
    private lateinit var TipDescriptiontv: TextView
    private lateinit var numPeopleet: EditText
    private lateinit var perPersontv: TextView
    private lateinit var roundupcb: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.show() //How can we show the RestauranTip
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BaseAmountet = findViewById(R.id.BaseAmountet)
        TipSeekBar = findViewById(R.id.TipseekBar)
        TipPercenttv = findViewById(R.id.TipPercenttv)
        TipAmounttv = findViewById(R.id.TipAmounttv)
        TotalAmounttv = findViewById(R.id.TotalAmounttv)
        TipDescriptiontv = findViewById(R.id.TipDescriptiontv)
        numPeopleet = findViewById(R.id.etnumPeople)
        perPersontv = findViewById(R.id.perpersontv)
        roundupcb = findViewById(R.id.roundupcb)

        TipSeekBar.progress = INITIAL_TIP_PERCENT //initialize to 15%
        TipPercenttv.text = "$INITIAL_TIP_PERCENT%" //make it a string
        updateTipDescription(INITIAL_TIP_PERCENT)
        TipSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                TipPercenttv.text = "$progress%"
                computeTipAndTotal(Checked)
                updateTipDescription(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        BaseAmountet.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal(Checked)
            }

        })
        numPeopleet.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                computeTipAndTotal(Checked)
            }
        })
        roundupcb.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                Log.i(TAG, "Checkbox $isChecked")
                if (isChecked) {
                    Checked = true
                    computeTipAndTotal(Checked)
                }else{
                    Checked = false
                    computeTipAndTotal(Checked)
                }
            }

        })

    }

//    private fun computePersonTotal() {
//        if(numPeopleet.text.isEmpty()) {
//            perPersontv.text = ""
//            TipAmounttv.text = "%.2f".format(tipAmount) //format to 2 decimal places
//            TotalAmounttv.text = "%.2f".format(totalAmount)
//            return
//        }
//        val numPeople = numPeopleet.text.toString().toDouble()
//        val perPerson = totalAmount/numPeople
//        perPersontv.text = "%.2f".format(perPerson)
//    }

    private fun updateTipDescription(tipPercent: Int) {
        val tipDescription = when(tipPercent) {
           in 0..9 -> "Poor😒"
           in 10..14 -> "Acceptable😐"
            in 15..19 -> "Good"
            in 20..24 -> "Great"
            else->"Amazing🤗"
        }
        TipDescriptiontv.text = tipDescription
        //UPDATE color based on tip percent
        val color = ArgbEvaluator().evaluate(
            tipPercent.toFloat()/TipSeekBar.max,
            ContextCompat.getColor(this, R.color.color_worst_tip),
            ContextCompat.getColor(this, R.color.color_best_tip)
        ) as Int
        TipDescriptiontv.setTextColor(color)
    }

    private fun computeTipAndTotal(Checked: Boolean) {
        //case where there's no base amount input
        if(BaseAmountet.text.isEmpty()) {
            TipAmounttv.text = ""
            TotalAmounttv.text = ""
            //perPersontv.text=""
            return
        }
        if(numPeopleet.text.isEmpty()) {
            perPersontv.text = ""
            TipAmounttv.text = "%.2f".format(tipAmount) //format to 2 decimal places
            TotalAmounttv.text = "%.2f".format(totalAmount)
            return
        }
        //1. get value of base amount and tip percent
        val baseAmount = BaseAmountet.text.toString().toDouble()
        val tipPercent = TipSeekBar.progress
        //2. Compute the tip and total
        tipAmount = baseAmount * tipPercent /100
        totalAmount = baseAmount + tipAmount
        //3. Update the UI
        //TipAmounttv.text = tipAmount.toString()
        TipAmounttv.text = "%.2f".format(tipAmount) //format to 2 decimal places
        numPeople = numPeopleet.text.toString().toDouble()
        if(Checked) {
            val roundedAmount = totalAmount.roundToInt().toDouble()
            TotalAmounttv.text = "%.2f".format(roundedAmount)
            perPersontv.text = "%.2f".format(roundedAmount/ numPeople)
        }else{
            TotalAmounttv.text = "%.2f".format(totalAmount)
            val perPerson = totalAmount/numPeople
            perPersontv.text = "%.2f".format(perPerson)
        }
    }
}