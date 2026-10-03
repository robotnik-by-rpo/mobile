class Robot(var x: Int, var y: Int, var direction: Direction) {
    fun stepForward() {
        when(direction) {
            Direction.RIGHT -> x++
            Direction.LEFT -> x--
            Direction.UP -> y++
            Direction.DOWN -> y--
        }
    }

    fun turnLeft(){
        direction = when (direction) {
            Direction.LEFT -> Direction.DOWN
            Direction.RIGHT -> Direction.UP
            Direction.UP -> Direction.LEFT
            Direction.DOWN -> Direction.RIGHT
        }
    }

    fun turnRight(){
        direction = when (direction){
            Direction.LEFT -> Direction.UP
            Direction.RIGHT -> Direction.DOWN
            Direction.UP -> Direction.RIGHT
            Direction.DOWN -> Direction.LEFT
        }
    }

    override fun toString(): String {
        return "(${x}, ${y}), looks ${direction}"
    }
}