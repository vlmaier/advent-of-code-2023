
fun main() {
    val input = readInput("day08")
    solveDay8Part1(input).let { println("Part 1: $it") }
}

fun solveDay8Part1(input: List<String>) = findStepsToTarget("AAA", "ZZZ", input[0], buildGraph(input))

data class Node(val left: String, val right: String)

fun buildGraph(input: List<String>): Map<String, Node> {
    val graph = mutableMapOf<String, Node>()
    for (line in input.subList(2, input.size)) {
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
