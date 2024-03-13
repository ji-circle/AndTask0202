package com.example.mykiosk01

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.InputMismatchException
import java.util.Scanner



//질문 1) java처럼 scanner을 사용했는데, readLine에서 nextInt처럼 int만 받게 하는 방법이 있나요?
//질문 2) 객체를 리턴하기도 하고 문자열을 리턴하기도 해서 Any 를 많이 사용했는데, 이렇게 하는 것과
//          selection() 함수의 마지막 부분처럼 객체에 메세지를 넣어서 만든 뒤 리턴해 판별하는 것 중에
//              뭐가 더 나은 방법인가요? (사실 둘 다 비효율적인 것 같아서요... 더 효율적인 방법을 알고 싶습니다)

fun main(){
    var isSelect0 : Boolean = false
    var isFirstOrder : Int = 0
    var baskets = mutableListOf<Food>()
    var currentMoney : Double = 0.0

    currentMoney = 100.0

    while(!isSelect0){
        var tempFood = selection(isFirstOrder)

        if (tempFood?.name == "zero"){
            println("진행중인 주문을 취소합니다")
            break
        } else if(tempFood?.name == "wrong" ||tempFood?.name == "retry") {
            println("처음부터 다시 선택하세요")
        }else if(tempFood?.name == "order"){

            var receipt = orderChoice(tempFood, baskets, currentMoney)
            if(receipt == -200.0){
                break
            } else {
                if(currentMoney != receipt) {
                    isFirstOrder = 0
                    for(j in 0..baskets.size-1){
                        baskets.removeAt(0)
                    }
                }
                currentMoney = receipt
            }
        } else {
            baskets.add(tempFood!!)
            //println("정상 추가 완료")
            isFirstOrder += 1
        }
        println("")
    }
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
    }catch(e:Exception){
        println("올바른 형식으로 다시 선택해주세요!!")
        e.printStackTrace()
        println(e)
    }

    //println("selection() 탈출")

    if(subSelect is Food) {
        foods = subSelect
        //println("foods 추가")
    } else if(subSelect == 0) {
        foods = Food(listOf("zero", 0.0, "zero"))
    } else if(subSelect == "order") {
        foods = Food(listOf("order", 0.0, "order"))
        //println("order 추가")
    } else if(subSelect == "retry"){
        foods = Food(listOf("retry", 0.0, "retry"))
        //println("뒤로가기 선택 2")
    } else {
        foods = Food(listOf("wrong", 0.0, "wrong"))
    }
    return foods
}

fun majChoiceDist(choice:Int?):Any?{
    var foodType : Int = 0

    return when(choice){
        1 -> {
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
                        i + 1, burgerLists[i][0], burgerLists[i][1], burgerLists[i][2]
                    )
                )
            }
            println("0. 뒤로가기        | 뒤로가기")

            try {
                foodType = checkNumNull(burgerLists.size+1, 0)

                if (foodType == 0) {
                    return "return"
                }

            } catch (e: Exception) {
                println("올바른 형식으로 버거 종류를 선택해주세요")
            }

            foodType = foodType - 1
            if(askCheck(burgerLists[foodType])=="retry"){
                //println("burger 장바구니 직전 취소")
                return "retry"
            }

            var tempFood = Burger(burgerLists[foodType])
            //println("$foodType")
            //println("tempfood 추가")
            return tempFood

        }
        2 -> {
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
                        i + 1, fcsLists[i][0], fcsLists[i][1], fcsLists[i][2]
                    )
                )
            }
            println("0. 뒤로가기        | 뒤로가기")

            try {
                foodType = checkNumNull(fcsLists.size+1, 0)

                if (foodType == 0) {
                    return "return"
                }

            } catch (e: Exception) {
                println("올바른 형식으로 custard 종류를 선택해주세요")
            }

            foodType = foodType - 1
            if(askCheck(fcsLists[foodType])=="retry"){
                //println("custard 장바구니 직전 취소")
                return "retry"
            }

            var tempFood = FrozenCustard(fcsLists[foodType])
            //println("$foodType")
            //println("frozen custard tempfood 추가")
            return tempFood
        }
        3 -> {
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
                        i + 1, drsLists[i][0], drsLists[i][1], drsLists[i][2]
                    )
                )
            }
            println("0. 뒤로가기        | 뒤로가기")

            try {
                foodType = checkNumNull(drsLists.size+1, 0)

                if (foodType == 0) {
                    return "return"
                }

            } catch (e: Exception) {
                println("올바른 형식으로 drink 종류를 선택해주세요")
            }

            foodType = foodType - 1
            if(askCheck(drsLists[foodType])=="retry"){
                //println("drink 장바구니 직전 취소")
                return "retry"
            }

            var tempFood = Drink(drsLists[foodType])
            //println("$foodType")
            //println("drink tempfood 추가")
            return tempFood
        }

        else -> { //4
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
                        i + 1, beersLists[i][0], beersLists[i][1], beersLists[i][2]
                    )
                )
            }
            println("0. 뒤로가기        | 뒤로가기")

            try {
                foodType = checkNumNull(beersLists.size+1, 0)
                if (foodType == 0) {
                    return "return"
                }

            } catch (e: Exception) {
                println("올바른 형식으로 맥주 종류를 선택해주세요")
            }

            foodType = foodType - 1
            if(askCheck(beersLists[foodType])=="retry"){
                //println("beer 장바구니 직전 취소")
                return "retry"
            }

            var tempFood = Beer(beersLists[foodType])
            //println("$foodType")
            //println("beer tempfood 추가")
            return tempFood
        }
    }
}

fun askCheck(typeLists:List<Any?>) : String?{

    var sentence : String = ""
    println("%-15s | W %-5s | %s".format(typeLists[0], typeLists[1], typeLists[2]))

    println("")
    println("위 메뉴를 장바구니에 추가하시겠습니까?")
    println("1. 확인 \t 2. 취소")

    var temp = checkNumNull(2,1)

    when(temp){
        1 -> {
            sentence = "%s 가 장바구니에 추가되었습니다".format(typeLists[0])
            println(sentence)
        }
        2 -> {
            //println("장바구니 담기 취소")
            sentence = "retry"
        }
    }
    return sentence
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
                numNullResult = null //이렇게 null 넣는 거 말고 다른 방법 없나
            }
        } catch (e: NumberFormatException) {
            println("숫자를 입력해주세요.")
        }finally{
            if (numNullResult == null) {
                println("다시 입력해주세요")
            } else {
                //println("입력한 숫자는 $numNullResult 입니다.")
            }
        }
    }
    return numNullResult
}

fun orderChoice(_tempFood : Food, _baskets : List<Food>, _currentMoney:Double) : Double {
    var price : Double = 0.0
    var returnValue : Double = 0.0
    var numBanned : Int = 0

    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val formattedDateTime = currentDateTime.format(formatter)

    while(true){
        price = 0.0 //초기화해야 중복적용 안 됨
        println("아래와 같이 주문 하시겠습니까? (현재 주문 대기수 : $numBanned)")
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

            if(checkTimeBanned()){
                numBanned += 1
                continue
            }
            else{
                if(_currentMoney > price){
                    //3초 기다리기
                    var job = CoroutineScope(Dispatchers.Default).launch {
                        delay(3000)
                        println("결제중")
                    }
                    runBlocking {
                        job.join()
                    }
                    println("구매 완료, 현재 잔액 : %.1f (%s)".format(_currentMoney-price, formattedDateTime))
                    returnValue = _currentMoney-price
                    //println("3초 딜레이 후 결제 완료")
                    job.cancel()
                    break
                } else{
                    println("현재 잔액은 %.1f으로 %.1f이 부족해서 주문할 수 없습니다.".format(_currentMoney, price-_currentMoney))
                    returnValue = -200.0
                    break
                }
            }
        } else{
            returnValue= _currentMoney
            break
        }
    }
    return returnValue
}

fun checkTimeBanned() : Boolean{
    val currentTime = LocalTime.now()
    var formatter = DateTimeFormatter.ofPattern("a HH시 mm분")
    var formatted = currentTime.format(formatter)

    val start = LocalTime.of(23, 10) // 11:10 PM
    val end = LocalTime.of(23, 59) // 11:59 PM

    var formatted2 = start.format(formatter)
    var formatted3 = end.format(formatter)

    var result = when {
        currentTime.compareTo(start) >= 0 && currentTime.compareTo(end) <= 0 -> true
                //금지된 시간이라면 true, 1(횟수)를 반환
        else -> false   //금지된 시간이 아니면 false
    }
    if(result){
        println("현재 시각은 $formatted 입니다. ")
        println("은행 점검 시간은 $formatted2 ~ $formatted3 이므로 결제할 수 없습니다.")
    }
    return result
}