# Демонстрации работы

Инициализация классов
```kotlin
val c = Circle(0, 0, 5)
val r = Rect(0, 0, 4, 6)
val s = Square(1, 1, 3)
```

Методы у каждого из классов
```kotlin
move()
resize()
rotate()
```
Применение каждго из классов

```kotlin
// Circle
c.move(3, 4)
c.resize(2)
c.rotate(RotateDirection.Clockwise, 0, 0)

// Square
s.move(1, -1)
s.resize(2)
s.rotate(RotateDirection.Clockwise, 0, 0)

// Rectangle
r.move(2, 2)
r.resize(3)
r.rotate(RotateDirection.CounterClockwise, 0, 0)
```

Вывод из консоли при запуске `main.kt`
```terminaloutput
=== Circle ===
Создан: x=0, y=0, area=78.53982
move(3,4): x=3, y=4
resize(2): area=314.15927  (радиус стал 10)
rotate(CW,0,0): x=-4, y=3
rotate(CCW,0,0): x=3, y=4  (вернулись в исходную точку)

=== Rect ===
Создан: x=0, y=0, area=24.0
move(2,2): x=2, y=2
resize(3): area=216.0  (4x6 -> 12x18)
rotate(CCW,0,0): x=2, y=-2
rotate(CW,0,0): x=2, y=2  (вернулись в исходную точку)

=== Square ===
Создан: x=1, y=1, area=9.0
move(1,-1): x=2, y=0
resize(2): area=36.0  (3x3 -> 6x6)
rotate(CW,0,0): x=0, y=2

=== Наследование от Figure ===
Circle: area = 3.1415927
Rect: area = 6.0
Square: area = 16.0

=== Реализация интерфейса Movable ===
Все Movable сдвинуты на (10, 10)

=== Реализация интерфейса Transforming ===
Все Transforming увеличены в 2 раза и повёрнуты на 90° по часовой

=== Поворот вокруг точки (10, 10) ===
До: x=10, y=20
CW: x=0, y=10
CCW: x=10, y=20  (вернулись)

Process finished with exit code 0
```

