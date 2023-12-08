
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
    val mapList = input
        .dropWhile { !it.startsWith("seeds:") }
        .drop(2)
    val maps = mutableMapOf<String, List<List<Int>>>()
    var i = 0
    while (i < mapList.size) {
        val key = mapList[i].split(" ")[0]
        val value = mutableListOf<List<Int>>()
        var j = 1
        while (mapList.getOrNull(i + j)?.isNotEmpty() == true) {
            value.add(mapList[i + j].split(" ").map(String::toInt))
            j++
        }
        maps[key] = value
        i += j + 1
    }
    return 0
}
