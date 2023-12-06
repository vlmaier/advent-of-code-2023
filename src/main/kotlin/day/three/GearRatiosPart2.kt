package day.three

import java.io.File

fun main() {
    val lines = File("src/main/resources/day/three/input.txt").readLines()
    val inputMap = lines.map { row -> row.toCharArray() }.toTypedArray()
    val numbers = mutableListOf<Pair<Int, List<String>>>()
    inputMap.forEachIndexed { rowIndex, row ->
        var number = 0
        row.forEachIndexed { columnIndex, value ->
            print("$value")
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
        print("$numbers ")
        println()
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
    println(gearMap)
    println(gearRatio)
}

private const val DOT = '.'
private const val GEAR = '*'
