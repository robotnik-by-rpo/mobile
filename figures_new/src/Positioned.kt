abstract class Positioned(id: Int, var x: Int, var y: Int) : Figure(id), Transforming, Movable{
    override fun move(dx: Int, dy: Int) {
        x += dx
        y += dy
    }

    override fun rotate(direction: RotateDirection, centerX: Int, centerY: Int) {
        val dx = x - centerX
        val dy = y - centerY
        when (direction) {
            RotateDirection.Clockwise -> {
                x = centerX - dy
                y = centerY + dx
            }
            RotateDirection.CounterClockwise -> {
                x = centerX + dy
                y = centerY - dx
            }
        }
    }
}