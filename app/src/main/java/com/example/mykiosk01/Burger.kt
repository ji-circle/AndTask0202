package com.example.mykiosk01

class Burger(parameters : List<Any>) : Food(parameters) {

    override fun displayInfo() {
        println("${name}    | W ${price} |  ")
    }
}