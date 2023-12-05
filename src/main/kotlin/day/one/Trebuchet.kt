package day.one

import java.io.File

fun main() {
    val lines = File("src/main/resources/day/one/input.txt").readLines()
    var sum = 0
    for (line: String in lines) {
        val firstDigit = line.findFirstDigit()
        val lastDigit = line.reversed().findFirstDigit()
        val combinedNumber = "$firstDigit$lastDigit".toIntOrNull()
        sum += combinedNumber ?: 0
    }
    println(sum)
}

fun String.findFirstDigit(): Int {
    var charsSoFar = ""
    forEach { char ->
        charsSoFar = (charsSoFar + char).takeLast(5)
        digitMap.forEach { if (charsSoFar.contains(it.key)) return it.value }
        if (char.isDigit()) return char.digitToInt()
    }
    return 0
}

val digitMap = mapOf(
    "one" to 1,
    "eno" to 1,
    "two" to 2,
    "owt" to 2,
    "three" to 3,
    "eerht" to 3,
    "four" to 4,
    "ruof" to 4,
    "five" to 5,
    "evif" to 5,
    "six" to 6,
    "xis" to 6,
    "seven" to 7,
    "neves" to 7,
    "eight" to 8,
    "thgie" to 8,
    "nine" to 9,
    "enin" to 9,
)