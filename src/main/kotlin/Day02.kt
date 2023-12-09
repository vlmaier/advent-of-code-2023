/**
 * --- Day 2: Cube Conundrum ---
 */
fun main() {
    solveDay2Part1().let { println("Part 1: $it") }
    solveDay2Part2().let { println("Part 2: $it") }
}

fun solveDay2Part1(): Int {
    val input = readInput("day02")
    var sum = 0
    for (line in input) {
        val game = Game(line)
        sum += if (game.isValidGame()) game.getId() else 0
    }
    return sum
}

fun solveDay2Part2(): Int {
    val input = readInput("day02")
    var sum = 0
    for (line in input) {
        val game = Game(line)
        sum += game.getPower()
    }
    return sum
}

data class Game(val input: String) {

    fun getId(): Int {
        val regex = Regex("""Game (\d+):""")
        val matchResult = regex.find(input)
        return matchResult?.groupValues?.get(1)?.toInt() ?: 0
    }

    fun isValidGame(): Boolean = listOf("red" to 12, "green" to 13, "blue" to 14)
        .all { (color, maxCount) -> getMaxCounts().getOrDefault(color, 0) <= maxCount }

    fun getPower(): Int = getMaxCounts().values.reduce(Int::times)

    private fun getSets(): List<String> {
        val regex = Regex("""Game \d+: (.+)$""")
        val matchResult = regex.find(input)
        return matchResult?.groupValues?.get(1)?.split(";")?.map { it.trim() } ?: emptyList()
    }

    private fun getMaxCounts(): Map<String, Int> {
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
}