package com.example.mykiosk01

import kotlinx.coroutines.selects.select
import java.util.InputMismatchException
import java.util.Scanner

fun main(){
    var isSelect0 : Boolean = false
    var isFirstOrder : Int = 0
    var baskets = mutableListOf<Food>()
    //var price : Double = 0.0
    var currentMoney : Double = 0.0

    currentMoney = 100.0

    while(!isSelect0){
        var tempFood = selection(isFirstOrder)

        if (tempFood?.name == "zero"){
            println("진행중인 주문을 취소합니다")
            break
        } else if(tempFood?.name == "wrong" ||tempFood?.name == "retry") {
            println("처음부터 다시 선택하세요")
            //continue //확인하기
        }else if(tempFood?.name == "order"){

            var receipt = orderChoice(tempFood, baskets, currentMoney)
            if(receipt == -200.0){
                break
            } else {
                currentMoney = receipt
            }
        } else {
            baskets.add(tempFood!!)
            println("정상 추가 완료")
            isFirstOrder += 1
        }
        println("")

    }

//    for(i in 0..baskets.size-1){
//        println("${baskets[i].name}")
//        println("${baskets[i].price}")
//        println("${baskets[i].explanation}")
//    }



}



fun displayMajorMenu(){
    println("\"SHAKESHACK BURGER 에 오신걸 환영합니다\"")
    println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요")
    println("")
    println("[ SHAKESHACK MENU ]")
    println("1. Burgers     | 앵거스 비프 통살을 다져만든 버거")
    println("2. Frozen Custard  | 매장에서 신선하게 만드는 아이스크림")
    println("3. Drinks      | 매장에서 직접 만드는 음료")
    println("4. Beer        | 뉴욕 브루클린 브루어리에서 양조한 맥주")
}
fun displayOrderMenu(){
    println("[ ORDER MENU ]")
    println("5. Order       | 장바구니를 확인 후 주문합니다.")
    println("6. Cancel      | 진행중인 주문을 취소합니다.")
}

fun selection(_count: Int) : Food {

    var majSelect :Int = 0
    var subSelect :Any = ""
    //var isSelect0 : Boolean = false

    var foods : Food


    displayMajorMenu()
    if(_count > 0){
        displayOrderMenu()
    }
    try{
        if(_count>0){
            majSelect = checkNumNull(6, 1)
        } else {
            majSelect = checkNumNull(4,1)
        }

        if(majSelect == 6) {
            println("진행중인 주문을 취소합니다.")
            subSelect = 0
        }else if(majSelect == 5) {
            subSelect = "order"
        }else {
            subSelect = majChoiceDist(majSelect)!!
        }
        //while(subSelect == "retry"){
        //    println("대분류 선택지 중에서 다시 선택해주세요")
//            displayMajorMenu()
//            majSelect = readLine()!!.toInt()
        //    selection(_count)

        //WHILE 들에 갇힌 거 아닌지 확인하기. 아니면 IF로 바꾸기!
        //}
//
//        if(subSelect == "return"){
//            println("뒤로가기 선택!")
//            selection(_count)
//        }
    }catch(e:Exception){
        println("올바른 형식으로 다시 선택해주세요!!")
        e.printStackTrace()
        println(e)
    }



    println("selection() 탈출")

    if(subSelect is Food) {
        foods = subSelect
        println("foods 추가")
    } else if(subSelect == 0) {
        foods = Food(listOf("zero", 0.0, "zero"))
    } else if(subSelect == "order") {
        foods = Food(listOf("order", 0.0, "order"))
        println("order 추가")
    } else if(subSelect == "retry"){
        foods = Food(listOf("retry", 0.0, "retry"))
        println("뒤로가기 선택 2")
    } else {
        foods = Food(listOf("wrong", 0.0, "wrong"))
    }
    return foods
}

fun majChoiceDist(choice:Int?):Any?{
    var numMiss : Boolean = true
    var subSelect : Any? = 0
    var foodType : Int = 0


    return when(choice){
        1 -> {
            numMiss = true
            subSelect = 0
            foodType = 0

            var b1list = listOf("ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거")
            var b2list = listOf("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거")
            var b3list = listOf("Shroom Burger", 9.4, "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거")
            var b4list = listOf("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거")
            var b5list = listOf("Hamburger", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거")
            var burgerLists = listOf(b1list, b2list, b3list, b4list, b5list)

            println("[ Burgers MENU ]")
            for (i in burgerLists.indices) {
                println(
                    "%s. %-15s | W %-5s | %s".format(
                        i + 1,
                        burgerLists[i][0],
                        burgerLists[i][1],
                        burgerLists[i][2]
                    )
                )
            }
            println("0. 뒤로가기        | 뒤로가기")

            try {
 //               //subSelect = readLine()
//                foodType = subSelect?.toInt()!!

                foodType = checkNumNull(burgerLists.size+1, 0)

//                numMiss = numMissSelect(foodType, burgerLists)

 //               while (numMiss == true) {
   //                 println("Burger 선택지 중 다시 골라주세요")
                    //foodType = checkNumNull()
//                    foodType = readLine()?.toInt()!!
     //               numMiss = numMissSelect(foodType, burgerLists)


 //               }
                if (foodType == 0) {
                    return "return"
                }

            } catch (e: Exception) {
                println("올바른 형식으로 버거 종류를 선택해주세요")
            }

            foodType = foodType - 1
            //물어보기
            askCheck(burgerLists[foodType])

            var tempFood = Burger(burgerLists[foodType])
            println("$foodType")
            println("tempfood 추가")
            return tempFood


            //이 부분도 위랑 비슷하게 변경하기... 함수 따로 빼서

        }
        //5 -> {
        //    println("5 입력")
        //    return "order"
        //}
        2 -> {
            numMiss = true
            subSelect = 0
            foodType = 0

            var fc1list = listOf("frozen custard 1", 1.2, "첫번째 fc")
            var fc2list = listOf("frozen custard 2", 1.3, "두번째 fc")
            var fc3list = listOf("frozen custard 3", 1.4, "세번째 fc")
            var fc4list = listOf("frozen custard 4", 1.5, "네번째 fc")
            var fc5list = listOf("frozen custard 5", 1.6, "다섯번째 fc")
            var fc6list = listOf("frozen custard 6", 1.7, "여섯번째 fc")
            var fcsLists = listOf(fc1list, fc2list, fc3list, fc4list, fc5list, fc6list)

            println("[ FROZEN CUSTARD MENU ]")
            for (i in fcsLists.indices) {
                println(
                    "%s. %-15s | W %-5s | %s".format(
                        i + 1,
                        fcsLists[i][0],
                        fcsLists[i][1],
                        fcsLists[i][2]
                    )
                )
            }
            println("0. 뒤로가기        | 뒤로가기")

            try {
                //               //subSelect = readLine()
//                foodType = subSelect?.toInt()!!

                foodType = checkNumNull(fcsLists.size+1, 0)

//                numMiss = numMissSelect(foodType, burgerLists)

                //               while (numMiss == true) {
                //                 println("Burger 선택지 중 다시 골라주세요")
                //foodType = checkNumNull()
//                    foodType = readLine()?.toInt()!!
                //               numMiss = numMissSelect(foodType, burgerLists)


                //               }
                if (foodType == 0) {
                    return "return"
                }

            } catch (e: Exception) {
                println("올바른 형식으로 맥주 종류를 선택해주세요")
            }

            foodType = foodType - 1
            //물어보기
            askCheck(fcsLists[foodType])

            var tempFood = FrozenCustard(fcsLists[foodType])
            println("$foodType")
            println("frozen custard tempfood 추가")
            return tempFood

        }
        3 -> {
            numMiss = true
            subSelect = 0
            foodType = 0

            var dr1list = listOf("drink 1", 1.2, "첫번째 drink")
            var dr2list = listOf("drink 2", 1.3, "두번째 drink")
            var dr3list = listOf("drink 3", 1.4, "세번째 drink")
            var dr4list = listOf("drink 4", 1.5, "네번째 drink")
            var drsLists = listOf(dr1list, dr2list, dr3list, dr4list)

            println("[ DRINK MENU ]")
            for (i in drsLists.indices) {
                println(
                    "%s. %-15s | W %-5s | %s".format(
                        i + 1,
                        drsLists[i][0],
                        drsLists[i][1],
                        drsLists[i][2]
                    )
                )
            }
            println("0. 뒤로가기        | 뒤로가기")

            try {
                //               //subSelect = readLine()
//                foodType = subSelect?.toInt()!!

                foodType = checkNumNull(drsLists.size+1, 0)

//                numMiss = numMissSelect(foodType, burgerLists)

                //               while (numMiss == true) {
                //                 println("Burger 선택지 중 다시 골라주세요")
                //foodType = checkNumNull()
//                    foodType = readLine()?.toInt()!!
                //               numMiss = numMissSelect(foodType, burgerLists)


                //               }
                if (foodType == 0) {
                    return "return"
                }

            } catch (e: Exception) {
                println("올바른 형식으로 맥주 종류를 선택해주세요")
            }

            foodType = foodType - 1
            //물어보기
            askCheck(drsLists[foodType])

            var tempFood = Drink(drsLists[foodType])
            println("$foodType")
            println("drink tempfood 추가")
            return tempFood
        }

        else -> { //4
            numMiss = true
            subSelect = 0
            foodType = 0

            var be1list = listOf("beer1", 1.2, "첫번째 맥주")
            var be2list = listOf("beer2", 1.3, "두번째 맥주")
            var be3list = listOf("beer3", 1.4, "세번째 맥주")
            var be4list = listOf("beer4", 1.5, "네번째 맥주")
            var be5list = listOf("beer5", 1.6, "다섯번째 맥주")
            var beersLists = listOf(be1list, be2list, be3list, be4list, be5list)

            println("[ BEER MENU ]")
            for (i in beersLists.indices) {
                println(
                    "%s. %-15s | W %-5s | %s".format(
                        i + 1,
                        beersLists[i][0],
                        beersLists[i][1],
                        beersLists[i][2]
                    )
                )
            }
            println("0. 뒤로가기        | 뒤로가기")

            try {
                //               //subSelect = readLine()
//                foodType = subSelect?.toInt()!!

                foodType = checkNumNull(beersLists.size+1, 0)

//                numMiss = numMissSelect(foodType, burgerLists)

                //               while (numMiss == true) {
                //                 println("Burger 선택지 중 다시 골라주세요")
                //foodType = checkNumNull()
//                    foodType = readLine()?.toInt()!!
                //               numMiss = numMissSelect(foodType, burgerLists)


                //               }
                if (foodType == 0) {
                    return "return"
                }

            } catch (e: Exception) {
                println("올바른 형식으로 맥주 종류를 선택해주세요")
            }

            foodType = foodType - 1
            //물어보기
            askCheck(beersLists[foodType])

            var tempFood = Beer(beersLists[foodType])
            println("$foodType")
            println("beer tempfood 추가")
            return tempFood
        }

    }


}

fun numMissSelect(subSelect : Int?, subList : List<Any>) : Boolean{
    var numMiss : Boolean = true

     for(i in 0..subList.size){
         if(subSelect==i){
             numMiss = false
         }
     }

    return numMiss
}

fun askCheck(typeLists:List<Any?>){


    println("%-15s | W %-5s | %s".format(typeLists[0], typeLists[1], typeLists[2]))

    println("")
    println("위 메뉴를 장바구니에 추가하시겠습니까?")
    println("1. 확인 \t 2. 취소")

    var temp = readLine()?.toInt()


    when(temp){
        1 -> {
            println("%s 가 장바구니에 추가되었습니다".format(typeLists[0]))
        }
        2 -> {
            println("Burger 선택지 중 다시 골라주세요")
            majChoiceDist(1)
        }
        else -> {
            println("1, 2 중에서 다시 선택해주세요")
            askCheck(typeLists)
        }

    }
}

fun checkNumNull(_size : Int, _startNum : Int): Int {
    val scanner = Scanner(System.`in`)
    var numNullResult: Int? = null
    var isRangeOk : Boolean = false

    while (numNullResult == null) {
        try {
            val input = scanner.next()      //공백 생기면 그 뒤는 무시... 아마 코틀린도 똑같겠지. 입력버퍼 안 비워도 되나?? 비워야하면 readLine이나 nextLine 해주기
            numNullResult = input.toInt()
            for(i in _startNum.._size+_startNum-1){
                if(numNullResult == i){
                    isRangeOk = true
                }
            }
            if(!isRangeOk){
                println("범위 밖 선택지를 골랐습니다.")
                numNullResult = null //이렇게 말고 다른 방법 없나
            }
        } catch (e: NumberFormatException) {
            println("숫자를 입력해주세요.")
        }finally{
            if (numNullResult == null) {
                println("다시 입력해주세요")
            } else {
                println("입력한 숫자는 $numNullResult 입니다.")
            }
        }
    }

    return numNullResult
}

fun orderChoice(_tempFood : Food, _baskets : List<Food>, _currentMoney:Double) : Double {
    var price : Double = 0.0
    println("아래와 같이 주문 하시겠습니까?")
    println("")
    println("[ Orders ]")
    for(i in _baskets.indices){
        _baskets[i].displayInfo()
    }
    for(i in 0.._baskets.size-1){
        price += _baskets[i].price
    }
    println("")
    println("[ Total ]")
    println("W %.1f".format(price))

    fun askOrder() : Int{
        println("1. 주문 \t 2. 메뉴판")
        var userOrderChoice = checkNumNull(2,1)
        return userOrderChoice
    }

    var userOrderChoice = askOrder()
    if(userOrderChoice == 1){
        //가격 비교, 구매 함수 or 가격 리턴하기
        if(_currentMoney > price){
            println("구매 완료, 현재 잔액 : %.1f".format(_currentMoney-price))
            return _currentMoney-price
        } else{
            println("현재 잔액은 %.1f으로 %.1f이 부족해서 주문할 수 없습니다.".format(_currentMoney, price-_currentMoney))
            return -200.0
        }

    } else{
        return _currentMoney
    }



}