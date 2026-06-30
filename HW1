fun main() {
    analyzeIntList(listOf(67, -14, 22, -8, 15))
    val password = "someTestPassword"
    val pass = checkPasswordStrength(password)
    println(pass)
}

fun analyzeIntList(input: List<Int>) {
    var min = input[0]
    var max = input[0]
    var sum = 0
    var even = 0
    var odd = 0

    for (number in input) {
        if (number < min) {
            min = number
        }
        if (number > max) {
            max = number
        }
        sum += number
        if (number % 2 == 0) {
            even++
        }
        if (number % 2 != 0) {
            odd++
        }
    }
    println("Min: $min")
    println("Max: $max")
    println("Sum: $sum")
    println("Even count: $even")
    println("Odd count: $odd")
}

fun checkPasswordStrength(password: String): String {
    var cnt = 0

    if (password.length >= 8) {
        cnt++
    }

    if (password.any { it.isDigit() }) {
        cnt++
    }

    if (password.any { it.isUpperCase() }) {
        cnt++
    }

    if (password.any { it.isLowerCase() }) {
        cnt++
    }

    if (password.any { !it.isLetterOrDigit() }) {
        cnt++
    }

    return when (cnt) {
        5 -> "Best"
        4 -> "Good"
        2, 3 -> "Normal"
        else -> "Bad"
    }
}
