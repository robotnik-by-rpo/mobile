class Square(x: Int, y: Int, val side: Int) : Positioned(0, x, y) {
    private var currentSide: Int = side

    override fun area(): Float = (currentSide*currentSide).toFloat()
    override fun resize(zoom: Int) {currentSide *= zoom}
}