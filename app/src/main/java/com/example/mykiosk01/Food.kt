package com.example.mykiosk01

open class Food(eachName:String, eachPrice:Double) {
    var name:String = ""
    var price:Double = 0.0

    init{
        name = eachName
        price = eachPrice

    }

    open fun displayInfo(){

    }




}