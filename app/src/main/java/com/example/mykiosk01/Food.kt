package com.example.mykiosk01

open class Food(parameters : List<Any>) {
    var name:String = ""
    var price:Double = 0.0
    var explanation : String = ""

    init{
        name = parameters[0] as String
        price = parameters[1] as Double
        explanation = parameters[2] as String


    }

    open fun displayInfo(){
        println("%-15s | W %-5s | %s ".format(name, price, explanation))
    }




}