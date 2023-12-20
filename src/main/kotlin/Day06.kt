
fun main() {
    val input = readInput("day06")
    solveDay6Part1(input).let { println("Part 1: $it") }
    solveDay6Part2(input).let { println("Part 2: $it") }
}

fun solveDay6Part1(input: List<String>): Int {
    val times = extractLine(input[0])
    val distances = extractLine(input[1])
    val raceResults = times.zip(distances) { time, distance ->
        calculateWaysToBeatRecord(time, distance)
    }
    return raceResults.reduce(Int::times)
}

fun solveDay6Part2(input: List<String>): Int {
    val time = extractLine(input[0]).let { concatLine(it) }
    val distance = extractLine(input[1]).let { concatLine(it) }
    return calculateWaysToBeatRecord(time, distance)
}

private fun extractLine(input: String): List<Long> {
    return input.split("\\s+".toRegex()).filter { it.isNotBlank() }.drop(1).map { it.toLong() }
}

private fun concatLine(input: List<Long>): Long {
    return input.joinToString(" ").replace(" ", "").toLong()
}

private fun calculateWaysToBeatRecord(time: Long, distance: Long): Int {
    var ways = 0
    for (holdTime in 0..time) {
        val traveledDistance = holdTime * (time - holdTime)
        if (traveledDistance > distance) {
            ways++
        }
    }
    return ways
}
