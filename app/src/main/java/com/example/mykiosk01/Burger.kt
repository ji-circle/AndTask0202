package com.example.mykiosk01

class Burger(name : String, price : Double) : Food(name, price) {

    override fun displayInfo() {
        println("${name}    | W ${price} |  ")
    }
}