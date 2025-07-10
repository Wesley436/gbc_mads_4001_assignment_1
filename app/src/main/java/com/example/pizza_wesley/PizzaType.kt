package com.example.pizza_wesley

class PizzaType(private val name: String, private val price: Double) {
    /**
        getter for name attribute
        @return - name: String
    */
    fun getName(): String {
        return this.name
    }

    /**
        getter for price attribute
        @return - price: Double
    */
    fun getPrice(): Double {
        return this.price
    }
}