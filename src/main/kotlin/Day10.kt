import Direction.*

/**
 * Credit for solving to https://github.com/djleeds
 * This is merely an adopted version.
 */
fun main() {
    solveDay10Part1().let { println("Part 1: $it") }
}

fun solveDay10Part1(): Int {
    val maze = Maze.from(readInput("day10"))
    return maze.getFarthestPoint()
}

class Maze(val map: List<List<Tile>>) {
    private val start: Position = run {
        val y = map.indexOfFirst { it.contains(Tile.START) }
        val x = map[y].indexOfFirst { it == Tile.START }
        Position(x, y)
    }

    private val firstStepDirection = when {
        at(start.east).connections.contains(W) -> E
        at(start.south).connections.contains(N) -> S
        at(start.west).connections.contains(E) -> W
        at(start.north).connections.contains(S) -> N
        else -> error("Unknown direction")
    }

    private fun at(position: Position) = map[position.y][position.x]

    /**
     * Walk through entire loop and divide the path by 2
     */
    fun getFarthestPoint() = travel().count() / 2

    private fun travel(): Sequence<Triple<Position, Tile, Direction>> = sequence {
        var position = start
        var direction = firstStepDirection
        var tile: Tile
        do {
            position = position.go(direction)
            tile = at(position)
            yield(Triple(position, tile, direction))
            if (tile != Tile.START) {
                direction = tile.nextDirection(direction)
            }
        } while (tile != Tile.START)
    }

    companion object {
        fun from(input: List<String>) = Maze(input.map { line -> line.map { char -> Tile.from(char) } })
    }
}

data class Position(val x: Int, val y: Int) {
    val east get() = copy(x = x + 1)
    val south get() = copy(y = y + 1)
    val west get() = copy(x = x - 1)
    val north get() = copy(y = y - 1)

    fun go(direction: Direction) = when (direction) {
        N -> north
        S -> south
        E  -> east
        W  -> west
    }
}

enum class Tile(val char: Char, val connections: Set<Direction>) {
    NS('|', setOf(N, S)),
    EW('-', setOf(E, W)),
    NE('L', setOf(N, E)),
    NW('J', setOf(N, W)),
    SW('7', setOf(S, W)),
    SE('F', setOf(S, E)),
    START('S', emptySet()),
    EMPTY('.', emptySet());

    fun nextDirection(lastDirection: Direction) = connections.first { it != lastDirection.goBack() }

    companion object {
        fun from(char: Char) = entries.first { it.char == char }
    }
}

enum class Direction {
    N, E, S, W;

    fun goBack() = entries[(ordinal + 2).rem(entries.size)]
}