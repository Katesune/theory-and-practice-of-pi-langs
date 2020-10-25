import java.io.File

fun readBF(path: String):String{
    val file = File(path)
    val lines = file.readLines()
    return lines.joinToString(" ")
}

fun getLoopBlocks(s: ArrayList<String>): HashMap<Int,Int> {
    val blocks = HashMap<Int,Int>()
    val stack = mutableListOf<Int>()

    for ((i, char) in s.withIndex()){
        if (getIndex(char) == 6){//конец цикла
            stack.add(i)
        }
        if(getIndex(char) == 5){//начало цикла
            blocks[i] = stack[stack.lastIndex]
            blocks[stack.removeAt(stack.lastIndex)] = i
        }
    }

    return blocks
}

fun setCommands():HashMap<String, Int> { //список команд
    val commands= HashMap<String, Int>()

    commands["MoO"] = 1
    commands["MOo"] = 2
    commands["moO"] = 3
    commands["mOo"] = 4
    commands["moo"] = 5
    commands["MOO"] = 6
    commands["OOM"] = 7
    commands["oom"] = 8
    commands["mOO"] = 9
    commands["Moo"] = 10
    commands["OOO"] = 11

    return commands
}

fun getIndex(c:String):Int { //отдает индекс команды из списка команд
    val commands = setCommands()
    try {
        val index = commands.get(c)!!
        return index
    } catch (e: KotlinNullPointerException) {
        return 0
    }
}

fun changePtr(ptr:Int, index:Int):Int { //изменяет номер ячейки
    when(index) {
        3 -> return ptr + 1
        4 -> return ptr - 1
    }
    return -3
}

fun changeBuffer(ptr:Int, b:Array<Char>, index:Int):Boolean{//изменяет значение ячейки
    when (index) {
        1 -> b[ptr] = b[ptr] + 1
        2 -> b[ptr] = b[ptr] - 1
        7 -> print(b[ptr].toInt())
        9 -> b[ptr] == readLine()?.toCharArray()?.get(0)
        10 ->
            if (b[ptr]==(0).toChar()) {
            //print(">>> ")
            b[ptr] == readLine()?.toCharArray()?.get(0)
        } else print(b[ptr])
        11 -> b[ptr] = (0).toChar()
        else -> return false
    }
    return true
}

fun changeBlocks(ch: Char, i:Int, index:Int):Int { //Функция изменения конца и начала цикла
    when (index) {
        5 -> if (ch != (0).toChar()) {
            return i
        }
        6 -> if (ch == (0).toChar()){
            print(ch)
            return i
        }
    }
    return -3
}

fun go(source: ArrayList <String>){
    val buffer = Array<Char> (100) {_ -> 0.toChar()}
    var ptr = 0
    var i = 0

    val blocks=getLoopBlocks(source)

    while (i < source.size){
        val index = getIndex(source[i])
        when (index) {
            in 3..4-> ptr = changePtr(ptr, index)
            in 5..6 ->
                if (changeBlocks(buffer[ptr],blocks[i]!!, index)!=-3) {
                    i = changeBlocks(buffer[ptr],blocks[i]!!, index)
                }
            9 -> if (changeBlocks(buffer[ptr],blocks[i]!!, index)!=-3) {
                i = changeBlocks(buffer[ptr],blocks[i]!!, index)
            } else if (changePtr(ptr, index)!=-3) {
                ptr = 3
            } else changeBuffer(ptr,buffer,index)
            else -> changeBuffer(ptr,buffer,index)
        }
        i+=1
    }

}

fun main(args: Array<String>){
    val source = readBF("hello.cow").replace("\\s+".toRegex(), " ").trim()
    val s  = ArrayList <String> (source.split(" "))
    go(s)
    println()
    println("Done")
}
