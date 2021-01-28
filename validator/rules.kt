import java.io.File
import java.io.BufferedReader
import java.lang.Math.abs
import kotlin.math.log2

class RulesException(message: String): Exception(message)

class rules(var pass: String = "") {

    var length = 8

    public fun setPassword(password: String) {
        pass = password
    }

    public fun checkOrder(number: Int) {
        when (number) {
            0 -> if (!checkPasswordLength()) println("Слишком короткий пароль")
            1 -> if (!checkLetters()) println("Пароль должен содержать минимум по одной букве в верхнем и нижнем регистре")
            2 -> if (!checkNumbers()) println("В пароле нет цифр")
            3 -> if (!chechSpecialSymbols()) println("Пароль должен содержать минимум два спец. символа")
            4 -> if (!checkInDict()) println("Слишком простой пароль")
            5 -> if (!checkEntropy()) println("Низкий уровень энтропии")
            else -> throw RulesException("Такого правила не существует")
        }
    }

    private fun checkPasswordLength(): Boolean {
        return pass.split(" ").size > length
    }

    private fun chechSpecialSymbols(): Boolean {
        val matchResult = Regex("[/./^&/$#;]+").findAll(pass)
        return matchResult.toList().size > 1
    }

    private fun checkNumbers(): Boolean {
        val matchResult = Regex("[0-9]+").findAll(pass)
        return matchResult.toList().isNotEmpty()
    }

    private fun checkLetters(): Boolean {
        val matchResult = Regex("[A-Z]+").findAll(pass)
        val matchResult2 = Regex("[a-z]+").findAll(pass)
        return (matchResult.toList().isNotEmpty() && matchResult2.toList().isNotEmpty())
    }

    private fun getDict(): List<String> {
        val bufferedReader: BufferedReader = File("pswd-dict.txt").bufferedReader()
        val dict = bufferedReader.use { it.readText() }.split("\n")

        return dict
    }

    private fun checkInDict(): Boolean {
        val dict = getDict()
        for (d in dict) {
            if (d.equals(pass)) return false
        }
        return true
    }

    private fun checkEntropy(): Boolean {
        val dict = getDictForEntropy()

        var entropy = 0.0
        for (key in dict.keys) {
            val chast = dict.getValue(key).toDouble()/pass.length.toDouble()
            entropy += chast*log2(chast)
        }

        return abs(entropy) > 2
    }

    private fun getDictForEntropy(): MutableMap<Char, Int>{
        var dict: MutableMap<Char, Int> = mutableMapOf(' ' to 0)


        for (p in pass) {
            if (p in dict) {
                dict.replace(p, dict.getValue(p)+1)
            } else dict.put(p, 1)
        }

        dict.remove(' ')
        return dict
    }

}
