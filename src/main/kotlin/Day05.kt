/**
 * --- Day 5: If You Give A Seed A Fertilizer ---
 */
fun main() {
    solveDay5Part1().let { println("Part 1: $it") }
}

fun solveDay5Part1(): Long {
    val input = readInput("day05")
    val seeds = input
        .first { it.startsWith("seeds:") }
        .substringAfter(":")
        .split(" ")
        .filter { it.isNotBlank() }
        .map(String::toLong)
    val mapList = input
        .dropWhile { !it.startsWith("seeds:") }
        .drop(2)
    val maps = mutableMapOf<String, List<List<Long>>>()
    var i = 0
    while (i < mapList.size) {
        val key = mapList[i].split(" ")[0]
        val value = mutableListOf<List<Long>>()
        var j = 1
        while (mapList.getOrNull(i + j)?.isNotEmpty() == true) {
            value.add(mapList[i + j].split(" ").map(String::toLong))
            j++
        }
        maps[key] = value
        i += j + 1
    }
    val seedToLocation = convertSeedToLocation(seeds, maps)
    return seedToLocation.min()
}

private fun convertSeedToLocation(seeds: List<Long>, maps: Map<String, List<List<Long>>>): List<Long> {
    var result = seeds
    for (map in maps) {
        result = convertCategory(result, map.value)
    }
    return result
}

private fun convertCategory(
    source: List<Long>,
    map: List<List<Long>>
): List<Long> {
    val result = mutableListOf<Long>()
    for (seed in source) {
        var isConverted = false
        for (line in map) {
            val (destinationStart, sourceStart, length) = line
            if (seed in sourceStart..<sourceStart + length) {
                val destValue = destinationStart + (seed - sourceStart)
                result.add(destValue)
                isConverted = true
                break
            }
        }
        if (!isConverted) {
            // if not mapped then 1:1 mapping
            result.add(seed)
        }
    }
    return result
}
