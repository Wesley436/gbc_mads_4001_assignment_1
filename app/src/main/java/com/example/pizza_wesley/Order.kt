package com.example.pizza_wesley

class Order {
    private val type: PizzaType
    private val numSlices: Int
    private val needsDelivery: Boolean
    private val orderNumber: String

    /**
     * constructor to assign type, numSlices, needsDelivery and generate a random orderNumber
     */
    constructor(type: PizzaType, numSlices: Int, needsDelivery: Boolean) {
        this.type = type
        this.numSlices = numSlices
        this.needsDelivery = needsDelivery
        this.orderNumber = (1000 .. 9999).random().toString()
    }

    /**
     * gets subtotal for the order cost from pizza type's price, number of slices, and whether delivery is needed
     * @return Double
     */
    private fun getSubtotal(): Double {
        return (this.type.getPrice() * numSlices) + (if (needsDelivery) 10.5 else 0.0)
    }

    /**
     * gets the amount of tax from the amount of subtotal
     * @return Double
     */
    private fun getTaxes(): Double {
        return this.getSubtotal() * 0.13
    }

    /**
     * adds tax and subtotal to return total order cost
     * @return Double
     */
    private fun getTotal(): Double {
        return this.getSubtotal() + this.getTaxes()
    }

    /**
     * prints details of all the order information
     * @return String
     */
    fun generateReceipt(): String {
        return """
            RECEIPT #${this.orderNumber}
            Pizza Type: ${this.type.getName()}
            Num Slices: ${this.numSlices}
            Delivery?: ${if (this.needsDelivery) "Yes" else "No"}
            Subtotal: $${"%.2f".format(this.getSubtotal())}
            Taxes (13%): $${"%.2f".format(this.getTaxes())}
            Total: $${"%.2f".format(this.getTotal())}
       """.trimIndent()
    }

    /**
     * prints only the order number and total cost of the order
     * @return String
     */
    override fun toString(): String {
        return "Order #: ${this.orderNumber}, Total: ${"%.2f".format(this.getTotal())}"
    }
}