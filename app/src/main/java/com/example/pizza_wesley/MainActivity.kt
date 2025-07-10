package com.example.pizza_wesley

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val orderList = mutableListOf<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rootElement: LinearLayout = findViewById<LinearLayout>(R.id.main)
        val btnViewHistory = findViewById<Button>(R.id.btn_view_history)
        val rgPizzaType = findViewById<RadioGroup>(R.id.rg_pizza_type)
        val rbMeat = findViewById<RadioButton>(R.id.rb_meat)
        val rbVegetarian = findViewById<RadioButton>(R.id.rb_vegetarian)
        val etNumSlices = findViewById<EditText>(R.id.et_num_slices)
        val sEntirePizza = findViewById<Switch>(R.id.s_entire_pizza)
        val cbNeedDelivery = findViewById<CheckBox>(R.id.cb_need_delivery)
        val btnSubmitOrder = findViewById<Button>(R.id.btn_submit_order)
        val tvReceiptAndHistory = findViewById<TextView>(R.id.tv_receipt_and_history)

        /**
         * replaces tvReceiptAndHistory's text with all orders' short toString() texts if orderList is not empty
         */
        btnViewHistory.setOnClickListener {
            if (this.orderList.isEmpty()) {
                val snackbar: Snackbar = Snackbar.make(rootElement, "No orders!", Snackbar.LENGTH_SHORT)
                snackbar.show()
            } else {
                var orderHistory =  ""

                for(order in this.orderList) {
                    orderHistory+= order.toString()
                    orderHistory+="\n"
                }

                tvReceiptAndHistory.text = orderHistory
            }
        }

        /**
         * sets etNumSlices's text to 8 if checked, 0 otherwise
         */
        sEntirePizza.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                etNumSlices.setText("8")
            } else {
                etNumSlices.setText("0")
            }
        }

        /**
         * check if number of slices inputted is larger than 0 or if sEntirePizza is switched
         * if all inputs are valid, creates an Order object, add it to the orderList, resets the form, then replaces tvReceiptAndHistory's text with the Order's receipt
         */
        btnSubmitOrder.setOnClickListener{
            val numSlicesInput = etNumSlices.text
            var numSlices = 0
            if (numSlicesInput.isNotEmpty()) {
                numSlices = numSlicesInput.toString().toInt()
            }

            if (sEntirePizza.isChecked) {
                numSlices = 8
            }

            if (numSlices == 0) {
                val snackbar: Snackbar = Snackbar.make(rootElement, "Must enter a number", Snackbar.LENGTH_SHORT)
                snackbar.show()
            } else {
                val rbPizzaType = findViewById<RadioButton>(rgPizzaType.checkedRadioButtonId)
                val pizzaType =
                    when (rbPizzaType.id) {
                        rbMeat.id -> "Meat"
                        rbVegetarian.id -> "Vegetarian"
                        else -> "Meat"
                    }
                val pricePerSlice = if (pizzaType == "Meat") 6.7 else 4.25

                val order = Order(PizzaType(pizzaType, pricePerSlice), numSlices, cbNeedDelivery.isChecked)
                this.orderList.add(order)
                etNumSlices.setText("0")
                rgPizzaType.check(rbMeat.id)
                sEntirePizza.isChecked = false
                cbNeedDelivery.isChecked = false
                tvReceiptAndHistory.text = order.generateReceipt()
            }
        }
    }
}