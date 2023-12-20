
fun main() {
    val input = readInput("day03")
    solveDay3Part1(input).let { println("Part 1: $it") }
    solveDay3Part2(input).let { println("Part 2: $it") }
}

fun solveDay3Part1(input: List<String>): Int {
    val inputMap = input.map { row -> row.toCharArray() }.toTypedArray()
    val numbers = mutableListOf<Pair<Int, List<Char>>>()
    inputMap.forEachIndexed { rowIndex, row ->
        var number = 0
        row.forEachIndexed { columnIndex, value ->
            if (value.isDigit()) {
                number = "$number$value".toInt()
                val nextColumn = columnIndex + 1
                if (nextColumn == row.size || !row[nextColumn].isDigit()) {
                    val numberLength = "$number".length
                    val listOfNeighbors = mutableListOf<Char>()
                    // neighbor right
                    listOfNeighbors.add(if (nextColumn == row.size) DOT else row[nextColumn])
                    // neighbor left
                    listOfNeighbors.add(
                        if (columnIndex - 1 == -1) DOT else row.getOrNull(columnIndex - numberLength) ?: DOT
                    )
                    if (rowIndex != 0) {
                        // neighbors top
                        for (k in columnIndex - numberLength..nextColumn) {
                            val previousRow = rowIndex - 1
                            listOfNeighbors.add(inputMap[previousRow].getOrNull(k) ?: DOT)
                        }
                    }
                    if (rowIndex != inputMap.size - 1) {
                        // neighbors bottom
                        for (k in columnIndex - numberLength..nextColumn) {
                            val nextRow = rowIndex + 1
                            listOfNeighbors.add(inputMap[nextRow].getOrNull(k) ?: DOT)
                        }
                    }
                    numbers.add(number to listOfNeighbors)
                    number = 0
                }
            }
        }
    }
    var sum = 0
    numbers.forEach { (number, neighbors) ->
        if (neighbors.any { !it.isDigit() && it != DOT }) {
            sum += number
        }
    }
    return sum
}

fun solveDay3Part2(input: List<String>): Int {
    val inputMap = input.map { row -> row.toCharArray() }.toTypedArray()
    val numbers = mutableListOf<Pair<Int, List<String>>>()
    inputMap.forEachIndexed { rowIndex, row ->
        var number = 0
        row.forEachIndexed { columnIndex, value ->
            if (value.isDigit()) {
                number = "$number$value".toInt()
                val nextColumn = columnIndex + 1
                if (nextColumn == row.size || !row[nextColumn].isDigit()) {
                    val numberLength = "$number".length
                    /**
                     * save all neighbors for every found number
                     * '.' (dot) is used as a default neighbor in case you are out of bounds
                     * if you find a '*' (gear) also save the location [row][column],
                     * so you can validate later which numbers are sharing the same gear
                     * and so finding your gear ratios
                     */
                    val listOfNeighbors = mutableListOf<String>()
                    // neighbor right
                    listOfNeighbors.add(
                        if (nextColumn == row.size) DOT.toString()
                        else row[nextColumn].let { if (it == GEAR) "*[$rowIndex][$nextColumn]" else it.toString() }
                    )
                    // neighbor left
                    listOfNeighbors.add(
                        if (columnIndex - 1 == -1) DOT.toString()
                        else row.getOrNull(columnIndex - numberLength)
                            ?.let { if (it == GEAR) "*[$rowIndex][${columnIndex - numberLength}]" else it.toString() }
                            ?: DOT.toString()
                    )
                    // neighbors top (only if available)
                    if (rowIndex != 0) {
                        listOfNeighbors.addAll((columnIndex - numberLength..nextColumn).map { k ->
                            val previousRow = rowIndex - 1
                            val neighbor = inputMap.getOrNull(previousRow)?.getOrNull(k) ?: DOT
                            if (neighbor == GEAR) "*[$previousRow][$k]" else neighbor.toString()
                        })
                    }
                    // neighbors bottom (only if available)
                    if (rowIndex != inputMap.size - 1) {
                        listOfNeighbors.addAll((columnIndex - numberLength..nextColumn).map { k ->
                            val nextRow = rowIndex + 1
                            val neighbor = inputMap.getOrNull(nextRow)?.getOrNull(k) ?: DOT
                            if (neighbor == GEAR) "*[$nextRow][$k]" else neighbor.toString()
                        })
                    }
                    numbers.add(number to listOfNeighbors)
                    number = 0
                }
            }
        }
    }
    val gearMap = mutableMapOf<String, MutableList<Int>>()
    numbers.forEach { (number, neighbors) ->
        val gearNeighbors = neighbors.filter { it.startsWith(GEAR.toString()) }
        gearNeighbors.forEach {
            val gearNumbers = gearMap.getOrDefault(it, mutableListOf())
            gearNumbers.add(number)
            gearMap[it] = gearNumbers
        }
    }
    var gearRatio = 0
    gearMap.filter { it.value.size == 2 }.values.forEach {
        gearRatio += it.reduce(Int::times)
    }
    return gearRatio
}

private const val DOT = '.'
private const val GEAR = '*'
