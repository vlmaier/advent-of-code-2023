package day.two

import java.io.File

fun main() {
    val lines = File("src/main/resources/day/two/input.txt").readLines()
    var sum = 0
    for (line in lines) {
        val game = Game(line)
        sum += if (game.isValidGame()) game.getId() else 0
    }
    println(sum)
}

data class Game(val input: String) {

    fun getId(): Int {
        val regex = Regex("""Game (\d+):""")
        val matchResult = regex.find(input)
        return matchResult?.groupValues?.get(1)?.toInt() ?: 0
    }

    fun getSets(): List<String> {
        val regex = Regex("""Game \d+: (.+)$""")
        val matchResult = regex.find(input)
        return matchResult?.groupValues?.get(1)?.split(";")?.map { it.trim() } ?: emptyList()
    }

    fun getMaxCounts(): Map<String, Int> {
        val colorCounts = mutableMapOf<String, Int>()
        getSets().forEach { set ->
            val counts = set.split(",").map { it.trim().split(" ") }
            counts.forEach { (count, color) ->
                val colorCount = count.toInt()
                colorCounts[color] = maxOf(colorCount, colorCounts[color] ?: 0)
            }
        }
        return colorCounts
    }

    fun isValidGame(): Boolean {
        val maxCounts = getMaxCounts()
        if (maxCounts.getOrDefault("red", 0) > 12) {
            return false
        }
        if (maxCounts.getOrDefault("green", 0) > 13) {
            return false
        }
        if (maxCounts.getOrDefault("blue", 0) > 14) {
            return false
        }
        return true
    }
}