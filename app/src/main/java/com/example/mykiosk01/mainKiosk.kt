package com.example.mykiosk01

fun main(){
    var majSelect :String
    var isSelect0 : Boolean = false
    var optionList =

    while (!isSelect0) {
        displayMajorMenu()
        try{
            majSelect = readLine()
            if(majSelect?.first() != '0'&& majSelect?.first()!='1'&&maj)
        }


        if(majSelect == "0") {
            isSelect0 = true
        }

    }




}



fun displayMajorMenu(){
    println("[ SHAKESHACK MENU ]")
    println("1. Burgers     | 앵거스 비프 통살을 다져만든 버거")
    println("2. Frozen Custard  | 매장에서 신선하게 만드는 아이스크림")
    println("3. Drinks      | 매장에서 직접 만드는 음료")
    println("4. Beer        | 뉴욕 브루클린 브루어리에서 양조한 맥주")
    println("0. 종료      | 프로그램 종료")
}