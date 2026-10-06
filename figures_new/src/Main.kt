fun main() {
    println("=== Circle ===")
    val c = Circle(0, 0, 5)
    println("Создан: x=${c.x}, y=${c.y}, area=${c.area()}")

    c.move(3, 4)
    println("move(3,4): x=${c.x}, y=${c.y}")

    c.resize(2)
    println("resize(2): area=${c.area()}  (радиус стал 10)")

    c.rotate(RotateDirection.Clockwise, 0, 0)
    println("rotate(CW,0,0): x=${c.x}, y=${c.y}")

    c.rotate(RotateDirection.CounterClockwise, 0, 0)
    println("rotate(CCW,0,0): x=${c.x}, y=${c.y}  (вернулись в исходную точку)")

    println()
    println("=== Rect ===")
    val r = Rect(0, 0, 4, 6)
    println("Создан: x=${r.x}, y=${r.y}, area=${r.area()}")

    r.move(2, 2)
    println("move(2,2): x=${r.x}, y=${r.y}")

    r.resize(3)
    println("resize(3): area=${r.area()}  (4x6 -> 12x18)")  

    r.rotate(RotateDirection.CounterClockwise, 0, 0)
    println("rotate(CCW,0,0): x=${r.x}, y=${r.y}")

    r.rotate(RotateDirection.Clockwise, 0, 0)
    println("rotate(CW,0,0): x=${r.x}, y=${r.y}  (вернулись в исходную точку)")

    println()
    println("=== Square ===")
    val s = Square(1, 1, 3)
    println("Создан: x=${s.x}, y=${s.y}, area=${s.area()}")

    s.move(1, -1)
    println("move(1,-1): x=${s.x}, y=${s.y}")

    s.resize(2)
    println("resize(2): area=${s.area()}  (3x3 -> 6x6)")

    s.rotate(RotateDirection.Clockwise, 0, 0)
    println("rotate(CW,0,0): x=${s.x}, y=${s.y}")

    println()
    println("=== Наследование от Figure ===")
    val figures: List<Figure> = listOf(
        Circle(0, 0, 1),
        Rect(0, 0, 2, 3),
        Square(0, 0, 4)
    )
    for (f in figures) {
        println("${f::class.simpleName}: area = ${f.area()}")
    }

    println()
    println("=== Реализация интерфейса Movable ===")
    val movables: List<Movable> = listOf(
        Circle(0, 0, 1),
        Rect(0, 0, 2, 3),
        Square(0, 0, 4)
    )
    for (m in movables) {
        m.move(10, 10)
    }
    println("Все Movable сдвинуты на (10, 10)")


    println()
    println("=== Реализация интерфейса Transforming ===")
    val transformables: List<Transforming> = listOf(
        Circle(0, 0, 1),
        Rect(0, 0, 2, 3),
        Square(0, 0, 4)
    )
    for (t in transformables) {
        t.resize(2)
        t.rotate(RotateDirection.Clockwise, 0, 0)
    }
    println("Все Transforming увеличены в 2 раза и повёрнуты на 90° по часовой")

    println()
    println("=== Поворот вокруг точки (10, 10) ===")
    val c2 = Circle(10, 20, 3)
    println("До: x=${c2.x}, y=${c2.y}")
    c2.rotate(RotateDirection.Clockwise, 10, 10)
    println("CW: x=${c2.x}, y=${c2.y}")
    c2.rotate(RotateDirection.CounterClockwise, 10, 10)
    println("CCW: x=${c2.x}, y=${c2.y}  (вернулись)")

}