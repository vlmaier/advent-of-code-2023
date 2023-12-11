/**
 * --- Day 8: Haunted Wasteland ---
 */
fun main() {
    solveDay8Part1().let { println("Part 1: $it") }
}

fun solveDay8Part1() = findStepsToTarget("AAA", "ZZZ", readInstructions(), buildGraph())

data class Node(val left: String, val right: String)

fun readInstructions() = readInput("day08")[0]

fun buildGraph(): Map<String, Node> {
    val lines = readInput("day08")
    val graph = mutableMapOf<String, Node>()
    for (line in lines.subList(2, lines.size)) {
        val parts = line.split(" = ")
        val nodeKey = parts[0]
        val nodeValues = parts[1].trim('(', ')').split(", ")
        val node = Node(nodeValues[0], nodeValues[1])
        graph[nodeKey] = node
    }
    return graph
}

fun findStepsToTarget(startNode: String, targetNode: String, instructions: String, graph: Map<String, Node>): Int {
    var currentNode = startNode
    var steps = 0
    while (currentNode != targetNode) {
        val instruction = instructions[steps++ % instructions.length]
        currentNode = graph[currentNode]?.let { if (instruction == 'L') it.left else it.right }
            ?: error("Invalid node: $currentNode")
    }
    return steps
}
