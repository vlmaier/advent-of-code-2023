/**
 * --- Day 3: Gear Ratios ---
 *
 * You and the Elf eventually reach a gondola lift station; he says the gondola lift will take you up to the water source, but this is as far as he can bring you. You go inside.
 *
 * It doesn't take long to find the gondolas, but there seems to be a problem: they're not moving.
 *
 * "Aaah!"
 *
 * You turn around to see a slightly-greasy Elf with a wrench and a look of surprise. "Sorry, I wasn't expecting anyone! The gondola lift isn't working right now; it'll still be a while before I can fix it." You offer to help.
 *
 * The engineer explains that an engine part seems to be missing from the engine, but nobody can figure out which one. If you can add up all the part numbers in the engine schematic, it should be easy to work out which part is missing.
 *
 * The engine schematic (your puzzle input) consists of a visual representation of the engine. There are lots of numbers and symbols you don't really understand, but apparently any number adjacent to a symbol, even diagonally, is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)
 *
 * Here is an example engine schematic:
 *
 * 467..114..
 * ...*......
 * ..35..633.
 * ......#...
 * 617*......
 * .....+.58.
 * ..592.....
 * ......755.
 * ...$.*....
 * .664.598..
 *
 * In this schematic, two numbers are not part numbers because they are not adjacent to a symbol: 114 (top right) and 58 (middle right). Every other number is adjacent to a symbol and so is a part number; their sum is 4361.
 *
 * Of course, the actual engine schematic is much larger. What is the sum of all of the part numbers in the engine schematic?
 *
 * Your puzzle answer was 530849.
 *
 * --- Part Two ---
 *
 * The engineer finds the missing part and installs it in the engine! As the engine springs to life, you jump in the closest gondola, finally ready to ascend to the water source.
 *
 * You don't seem to be going very fast, though. Maybe something is still wrong? Fortunately, the gondola has a phone labeled "help", so you pick it up and the engineer answers.
 *
 * Before you can explain the situation, she suggests that you look out the window. There stands the engineer, holding a phone in one hand and waving with the other. You're going so slowly that you haven't even left the station. You exit the gondola.
 *
 * The missing part wasn't the only issue - one of the gears in the engine is wrong. A gear is any * symbol that is adjacent to exactly two part numbers. Its gear ratio is the result of multiplying those two numbers together.
 *
 * This time, you need to find the gear ratio of every gear and add them all up so that the engineer can figure out which gear needs to be replaced.
 *
 * Consider the same engine schematic again:
 *
 * 467..114..
 * ...*......
 * ..35..633.
 * ......#...
 * 617*......
 * .....+.58.
 * ..592.....
 * ......755.
 * ...$.*....
 * .664.598..
 *
 * In this schematic, there are two gears. The first is in the top left; it has part numbers 467 and 35, so its gear ratio is 16345. The second gear is in the lower right; its gear ratio is 451490. (The * adjacent to 617 is not a gear because it is only adjacent to one part number.) Adding up all of the gear ratios produces 467835.
 *
 * What is the sum of all of the gear ratios in your engine schematic?
 *
 * Your puzzle answer was 84900879.
 */
fun main() {
    solveDay3Part1().let { println("Part 1: $it") }
    solveDay3Part2().let { println("Part 2: $it") }
}

fun solveDay3Part1(): Int {
    val input = readInput("day03")
    val inputMap = input.map { row -> row.toCharArray() }.toTypedArray()
    val numbers = mutableListOf<Pair<Int, List<Char>>>()
    inputMap.forEachIndexed { rowIndex, row ->
        var number = 0
        row.forEachIndexed { columnIndex, value ->
            // print("$value")
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
        // print("$numbers ")
        // println()
    }
    var sum = 0
    numbers.forEach { (number, neighbors) ->
        if (neighbors.any { !it.isDigit() && it != DOT }) {
            sum += number
        }
    }
    return sum
}

fun solveDay3Part2(): Int {
    val input = readInput("day03")
    val inputMap = input.map { row -> row.toCharArray() }.toTypedArray()
    val numbers = mutableListOf<Pair<Int, List<String>>>()
    inputMap.forEachIndexed { rowIndex, row ->
        var number = 0
        row.forEachIndexed { columnIndex, value ->
            // print("$value")
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
        // print("$numbers ")
        // println()
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
    // println(gearMap)
    return gearRatio
}

private const val DOT = '.'
private const val GEAR = '*'
