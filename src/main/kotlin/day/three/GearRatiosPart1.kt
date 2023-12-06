package day.three

import java.io.File

private const val DOT = '.'

fun main() {
    val lines = File("src/main/resources/day/three/input.txt").readLines()

    val matrix = lines.map { row -> row.toCharArray() }.toTypedArray()
    val numbers = mutableListOf<Pair<Int, List<Char>>>()

    matrix.forEachIndexed { rowIndex, row ->
        var number = 0
        row.forEachIndexed { columnIndex, value ->
            print("$value")
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
                            listOfNeighbors.add(matrix[previousRow].getOrNull(k) ?: DOT)
                        }
                    }
                    if (rowIndex != matrix.size - 1) {
                        // neighbors bottom
                        for (k in columnIndex - numberLength..nextColumn) {
                            val nextRow = rowIndex + 1
                            listOfNeighbors.add(matrix[nextRow].getOrNull(k) ?: DOT)
                        }
                    }
                    numbers.add(number to listOfNeighbors)
                    number = 0
                }
            }
        }
        print("$numbers ")
        println()
    }

    var sum = 0
    numbers.forEach { (number, neighbors) ->
        if (neighbors.any { !it.isDigit() && it != DOT }) {
            sum += number
        }
    }
    println(sum)
}
