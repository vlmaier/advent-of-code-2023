
fun main() {
    solveDay5Part1().let { println("Part 1: $it") }
}

fun solveDay5Part1(): Int {
    val input = readInput("day05")
    val seeds = input
        .first { it.startsWith("seeds:") }
        .substringAfter(":")
        .split(" ")
        .filter { it.isNotBlank() }
        .map(String::toInt)

    val maps = input
        .dropWhile { !it.startsWith("seeds:") }
        .drop(1)
        .filter { line -> line.isNotEmpty() }
        // TODO


    return 0
}
