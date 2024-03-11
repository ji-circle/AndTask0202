package com.example.mykiosk01

import kotlinx.coroutines.selects.select

fun main(){
//    var majSelect :Int = 0
//    var isSelect0 : Boolean = false
//    var majOptionList = listOf('1','2','3','4','0')
//    var subOptionList = listOf('1','2','3','4','5','0')

//    lateinit var subSelect :String

    var numMiss : Boolean = true

    var baskets = mutableListOf<Food>()
    //var tempFood = Food(~~)
    //baskets.add(tempFood)

    baskets.add(selection())

//    while (!isSelect0) {
//        displayMajorMenu()
//        try{
//            majSelect = readLine()!!.toInt()
//            if(majSelect == 0) {
//                println("종료")
//                isSelect0 = true
//                break
//            } else {
//                subSelect = majChoiceDist(majSelect)
//            }
//
//            while(subSelect == "retry"){
//                println("선택지 중에서 다시 선택해주세요")
//                displayMajorMenu()
//                majSelect = readLine()!!.toInt()
//            }
//            while(subSelect == "return"){
//                println("뒤로가기 선택!")
//                displayMajorMenu()
//                majSelect = readLine()!!.toInt()
//                if(majSelect == 0) {
//                    println("종료")
//                    isSelect0 = true
//                    break
//                } else {
//                    subSelect = majChoiceDist(majSelect)
//                }
//            }
//        } catch(e:Exception){
//            println("다시 선택해주세요!!")
//        }
//
//
//    }
//
//    println("while 탈출")
//

}



fun displayMajorMenu(){
    println("[ SHAKESHACK MENU ]")
    println("1. Burgers     | 앵거스 비프 통살을 다져만든 버거")
    println("2. Frozen Custard  | 매장에서 신선하게 만드는 아이스크림")
    println("3. Drinks      | 매장에서 직접 만드는 음료")
    println("4. Beer        | 뉴욕 브루클린 브루어리에서 양조한 맥주")
    println("0. 종료      | 프로그램 종료")
}

fun selection() : Food {

    var majSelect :Int = 0
    var isSelect0 : Boolean = false
    lateinit var subSelect :Any

    var foods : Food


    while (!isSelect0) {
        displayMajorMenu()
        try{
            majSelect = readLine()!!.toInt()
            if(majSelect == 0) {
                println("종료")
                isSelect0 = true
                break
            } else {
                subSelect = majChoiceDist(majSelect)!!
            }

            if(subSelect == "retry"){
                println("대분류 선택지 중에서 다시 선택해주세요")
//                displayMajorMenu()
//                majSelect = readLine()!!.toInt()
                selection()

                //WHILE 들에 갇힌 거 아닌지 확인하기. 아니면 IF로 바꾸기!
            }
            if(subSelect == "return"){
                println("뒤로가기 선택!")
                selection()
            }
        } catch(e:Exception){
            println("올바른 형식으로 다시 선택해주세요!!")
        }

    }

    println("while 탈출")

    if(subSelect is Food){
        foods = subSelect
    } else {
        foods = Food("1", 2.0)
    }

    return foods
}

fun majChoiceDist(choice:Int?):Any?{
    var numMiss : Boolean = true


    return when(choice){
        1 -> {
            var b1list = listOf("ShackBurger",6.9,"토마토, 양상추, 쉑소스가 토핑된 치즈버거")
            var b2list = listOf("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거")
            var b3list = listOf("Shroom Burger", 9.4, "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거")
            var b4list = listOf("Cheeseburger", 6.9,"포테이토 번과 비프패티, 치즈가 토핑된 치즈버거")
            var b5list = listOf("Hamburger",5.4, "비프패티를 기반으로 야채가 들어간 기본버거")
            var burgerLists = listOf(b1list, b2list, b3list, b4list, b5list)

            println("[ Burgers MENU ]")
            for(i in burgerLists.indices){
                println("%s. %-15s | W %-5s | %s".format(i+1, burgerLists[i][0], burgerLists[i][1], burgerLists[i][2]))
            }
            println("0. 뒤로가기        | 뒤로가기")

            try{
                var subSelect = readLine()

                numMiss = numMissSelect(subSelect, burgerLists)

                if(numMiss == true){
                    println("Burger 선택지 중 다시 골라주세요")
                    majChoiceDist(1)
                } else{
                    if(subSelect?.toInt()==0){
                        return "return"
                    }
                    else{
                        return subSelect
                    }
                }
            } catch(e:Exception) {
                println("올바른 형식으로 버거 종류를 선택해주세요")
            }


            //물어보기
            //var addCheck = askCheck(burgerLists, subSelect.toInt())
            //if(addCheck == 1){
            //    var tempFood = Food()
            //    return
        //  }

            //이 부분도 위랑 비슷하게 변경하기... 함수 따로 빼서



        }
        else -> {
//            println("다시 선택해주세요")
//            displayMajorMenu()
            return "retry"
        }

    }


}

fun numMissSelect(subSelect : String?, subList : List<Any>) : Boolean{
    var numMiss : Boolean = true

     for(i in 0..subList.size){
         if(subSelect?.toInt()==i){
             numMiss = false
         }
     }

    return numMiss
}
