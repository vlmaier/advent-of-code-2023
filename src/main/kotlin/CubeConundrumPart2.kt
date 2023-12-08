import java.io.File

fun main() {
    val lines = File("src/main/resources/day/two/day02.txt").readLines()
    var sum = 0
    for (line in lines) {
        val game = Game(line)
        sum += game.getPower()
    }
    println(sum)
}
