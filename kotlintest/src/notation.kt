fun main(args: Array<String>){
    println("Write the expression");

    fun mainCheck(line :Array<String>):Boolean {
        //Функция проверки введенного выражения
        if (line.size<3) {
            print("\n" + "not enough arguments")
            return false
        }

        for (lin in line) {
            for (l in lin) {
                if (l in '0'..'9') {
                    try {lin.toInt()} catch (e: NumberFormatException) {print ("\n" +
                            "The number is written together with another character, possibly a space is missing")
                        return false}
                } else {
                    if (lin.length > 1) {
                        print("The symbol is written together with another character, possibly a space is missing")
                        return false
                    } else {
                        if ((l == '-') || (l == '+') || (l == '*') || (l == '/')) {
                        } else {
                            print("Wrong character in string")
                            return false
                        }
                    }
                }
            }
        }
        return true
    }


    fun getData(expression: String): Array<String> {
        //Достаю данные в массив
        val parts = expression.split(' ')
        var line = emptyArray<String>()
        for (s in parts) {
            if (s!=" ") {
                line += s.toString()
            }
        }
        return line
    }

    //Все выражение может состоять из 3 групп - 
        // 1 - два числа и знак, которые мы берем сразу
        // 2 - число и знак, берем к ответу следующими
        // 3 - отдельные знаки, которые связывают различные части выражения, заключенные в скобки, например (1+1)
    
    fun checkExpress(arg1 :String, arg2:String, arg3:String):String {
        //Функция нахождения двух цифр и числа
        if ((arg1 in "0".."9")&&(arg2 in "0".."9")&&(!(arg3 in "0".."9"))) {
            arg1.toString()
            arg2.toString()
            arg3.toString()
            val str:String = " (" +arg1 + " " + arg3 + " " + arg2 + ") "
            return str
        }
        return " "
    }

    // Для правильного прохода по строке ненужные мне (уже кинула их в ответ) числа и знаки заменяю на #
    fun changeLine(line:Array<String>, i: Int):Array<String> {
        line[i] = "#"
        line[i-1] = "#"
        line[i-2] = "#"
        return line
    }

    fun Changeline2(line:Array<String>, i:Int):Array<String> {
        line[i] = "#"
        line[i-1] = "#"
        return line
    }

    fun ChangeOneLine(line:Array<String>, symb:String):Array<String> {
        //Замена определенного символа из строки на #
        var i = line.size-1
        while (line[i]!=symb) {
            i--
        }
        line[i] = "#"
        return line
    }

    fun getIndexes(line :Array<String>, len : Int):Array<Int> {
        //ЗДесь получаю индексы выражения, где у нас заканчиваются выражения в скобках
        var indexes = emptyArray<Int>()
        for (i in len downTo 1) {
            if ((line[i]=="#")&&(line[i-1]!="#")) {
                indexes += i-1
            }
        }
        return indexes
    }

    fun checkNumZnak(arg1 :String, arg2 :String):Boolean {
        //Ищем число + знак
        return ((arg1 in "0".."9")&&(!(arg2 in "0".."9")))
    }

    fun checkExp(line :Array<String>):Boolean {
        //Проверяем, остались ли символы в выражении
        for (l in line) {
            if (l!="#") return true
        }
        return false
    }

    fun checkRight(line: Array<String>):Int {
        //Кол-во нормальных символов в выражении
        var count = 0
        for (l in line) {
            if (l!="#") {
                count++
            }
        }
        return count
    }

    fun getIndex(line :Array<String>, len : Int):Int {
        //После того, как мы собрали сущности 2 числа+знак, нам нужно подсобрать к ним по порядку сущности число+знак

        var index = line.size-1

        while(index>=0 && line[index]=="#") {
            index--
        }

        while(index>=0&&line[index]!="#") {
            index--
        }

        if (index<0) index=0

        return index
    }

    fun findNum(index :Int, vararg line: String):String {
        //Смотрим, остались ли числа
        for (i in line.size-1 downTo index) {
            if (line[i] in "0".."9") {
                return line[i]
            }
        }
        return " "
    }

    fun findZnak(index :Int, vararg line: String):String {
        //Смотрим, остались ли знаки
        for (i in line.size-1 downTo index) {
            if (!(line[i] in "0".."9")&&line[i]!="#") {
                return line[i]
            }
        }
        return " "
    }

    fun getPairs(line: Array<String>, lines:Array<String>) {
        //Проходимся по строке, ищем все сущности цисло + знак
        val len = line.size - 1

        for (count in 0..lines.size-1) {
            val indexes = getIndexes(line, len)
            if (indexes.size>count+1) {
                for (i in indexes[count]..indexes[count + 1] + 4) {
                    if (line[i - 1] != null) {
                        if (checkNumZnak(line[i], line[i - 1])) {
                            val str: String = line[i].toString() + line[i - 1].toString()
                            lines[count] = str + lines[count]
                            Changeline2(line, i)
                        }
                    }
                }
            }
        }
    }


    fun checkRemainer(line : Array<String>, lines : Array<String>):String {
        //ищем остаток, то есть одиночные знаки или символы
        var result = " "

        var count = 0
        while (count != lines.size) {
            while (checkExp(line)&& (count < lines.size-1 || checkRight(line)!=1)) {
                val index = getIndex(line, line.size - 1)

                if (index >= 0) {
                    val num = findNum(index, *line)
                    val znak = findZnak(0, *line)

                    if (znak != " ") {
                        if (num != " ") {
                            val str: String = " " + num.toString() + " " + znak.toString() + " "
                            lines[count] = str + lines[count]
                            ChangeOneLine(line, num)
                            ChangeOneLine(line, znak)
                        } else {
                            if (count < lines.size-1) ChangeOneLine(line, znak)
                        }
                        if (lines.size > count + 1) {
                            lines[count + 1] += " " + znak.toString() + " " + lines[count] + " "
                            lines[count] = " "
                        } else {
                            if (count > 1) {
                                lines[count] = " " + znak.toString() + " " + lines[count - 1] + " "
                            }
                        }
                    }
                }
            }
            count ++
        }

        for (l in lines) {
            result+=l
        }

        return result
    }


    fun getResult(line: Array<String>): String {
        //собираем все в кучу
        val len = line.size - 1
        var lines = emptyArray<String>()

       for (i in len downTo 2) {
            val check_res = checkExpress(line[i], line[i-1], line[i-2])
                if (check_res!=" ")  {
                    changeLine(line, i)
                    lines += check_res
            }
        }

        getPairs(line, lines)

        val result = checkRemainer(line, lines)

        if (checkRight(line)==1) {
            print("Invalid expression entered, \n" + "extra character or number")
            return " "
        }

        return result
    }


    val expression = readLine()
    if (expression != null) {
        val line = getData(expression)
        if (mainCheck(line)) {
            val result = getResult(line)
            if (result != " ") {
                for (r in result) {
                    print(r)
                }
            }
        }
    } else {
        println("Expression does not match")
    }

}
