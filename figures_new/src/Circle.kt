// TODO: дополнить определение класса размерами и позицией
import kotlin.math.PI

class Circle( x: Int, y: Int, val radius: Int) : Positioned(0, x, y){
    private var currentRadius: Int = radius

    override fun area(): Float  = (PI*currentRadius*currentRadius).toFloat()
    override fun resize(zoom: Int) {currentRadius*=zoom}
}