
fun main() {
    val input = readInput("day05")
    solveDay5Part1(input).let { println("Part 1: $it") }
}

fun solveDay5Part1(input: List<String>): Long {
    val seeds = input
        .first { it.startsWith(SEEDS) }
        .substringAfter(":")
        .split(" ")
        .filter { it.isNotBlank() }
        .map(String::toLong)
    val maps = getMaps(input)
    val seedToLocation = convertSeedToLocation(seeds, maps)
    return seedToLocation.min()
}

private fun getMaps(input: List<String>): MutableMap<String, List<List<Long>>> {
    val mapList = input
        .dropWhile { !it.startsWith(SEEDS) }
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
    return maps
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
        var isMapped = false
        for (line in map) {
            val (destinationStart, sourceStart, length) = line
            if (seed in sourceStart..<sourceStart + length) {
                val value = destinationStart + (seed - sourceStart)
                result.add(value)
                isMapped = true
                break
            }
        }
        if (!isMapped) {
            // if not mapped then 1:1 mapping
            result.add(seed)
        }
    }
    return result
}

private const val SEEDS = "seeds:"
