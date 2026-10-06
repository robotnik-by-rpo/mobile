// сочетание определения класса и конструктора одновременно объявляет переменные и задаёт их значения
class Rect(x: Int, y: Int, val width: Int, val height: Int) : Positioned(0, x, y) {

    var color: Int = -1 // при объявлении каждое поле нужно инициализировать
    private var currentWidth: Int = width
    private var currentHeight: Int = height

    lateinit var name: String // значение на момент определения неизвестно (только для объектных типов)
    // дополнительный конструктор вызывает основной
    constructor(rect: Rect) : this(rect.x, rect.y, rect.currentWidth, rect.currentHeight)

    // для каждого класса area() определяется по-своему
    override fun area(): Float {
        return (currentWidth*currentHeight).toFloat() // требуется явное приведение к вещественному числу
    }
    override fun resize(zoom: Int) {currentHeight*=zoom; currentWidth*=zoom}
}