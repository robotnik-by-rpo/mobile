fun main() {
    val parser = Parser("src/googleplaystore.csv")

    parser.readFile()

    parser.saveToJson("src/output.json")

    println("successful")
}