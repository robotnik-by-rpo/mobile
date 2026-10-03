enum class Direction {
    UP, DOWN, LEFT, RIGHT
}
fun moveRobot(r: Robot, toX: Int, toY: Int) {
    while (r.x != toX) {
        if (r.x < toX){
            when (r.direction) {
                Direction.UP -> r.turnRight()
                Direction.LEFT -> {r.turnLeft(); r.turnLeft() }
                Direction.RIGHT -> r.stepForward()
                Direction.DOWN -> r.turnLeft()
            }
        } else{
            when (r.direction) {
                Direction.LEFT -> r.stepForward()
                Direction.RIGHT -> { r.turnLeft(); r.turnLeft() }
                Direction.UP -> r.turnLeft()
                Direction.DOWN -> r.turnRight()
            }
        }
    }

    while (r.y != toY) {
        if (r.y < toY){
            when (r.direction) {
                Direction.UP -> r.stepForward()
                Direction.DOWN -> { r.turnLeft(); r.turnLeft() }
                Direction.LEFT -> r.turnRight()
                Direction.RIGHT -> r.turnRight()
            }
        } else {
            when (r.direction) {
                Direction.DOWN -> r.stepForward()
                Direction.LEFT -> r.turnLeft()
                Direction.RIGHT -> r.turnRight()
                Direction.UP -> {r.turnLeft(); r.turnLeft() }
            }
        }
    }
}

fun main() {

    val r1 = Robot(3, 4, Direction.RIGHT)
    println("${r1.x}, ${r1.y}, ${r1.direction}")
    r1.stepForward()
    println("${r1.x}, ${r1.y}, ${r1.direction}")
    println(r1)

    val robot = Robot(0, 0, Direction.UP)
    println("До: $robot")
    moveRobot(robot, 3, 2)
    println("После: $robot")

}