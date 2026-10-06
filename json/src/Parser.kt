import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class Parser(val fileJson: String) {

    private val data = mutableMapOf<String, MutableMap<String, MutableMap<String, Any?>>>()

    private val androidApiMap = mapOf(
        "1.0" to 1, "1.1" to 2, "1.5" to 3, "1.6" to 4,
        "2.0" to 5, "2.0.1" to 6, "2.1" to 7, "2.2" to 8, "2.3" to 9, "2.3.1" to 9,
        "2.3.2" to 9, "2.3.3" to 10, "2.3.4" to 10, "2.3.5" to 10, "2.3.6" to 10, "2.3.7" to 10,
        "3.0" to 11, "3.1" to 12, "3.2" to 13,
        "4.0" to 14, "4.0.1" to 14, "4.0.2" to 14, "4.0.3" to 15, "4.0.4" to 15,
        "4.1" to 16, "4.1.1" to 16, "4.1.2" to 16,
        "4.2" to 17, "4.2.1" to 17, "4.2.2" to 17,
        "4.3" to 18, "4.3.1" to 18,
        "4.4" to 19, "4.4.1" to 19, "4.4.2" to 19, "4.4.3" to 19, "4.4.4" to 19,
        "4.4W" to 20, "4.4W.1" to 20, "4.4W.2" to 20,
        "5.0" to 21, "5.0.1" to 21, "5.0.2" to 21,
        "5.1" to 22, "5.1.1" to 22,
        "6.0" to 23, "6.0.1" to 23,
        "7.0" to 24, "7.1" to 25, "7.1.1" to 25, "7.1.2" to 25,
        "8.0" to 26, "8.1" to 27,
        "9" to 28,
        "10" to 29,
        "11" to 30,
        "12" to 31,
        "13" to 33,
        "14" to 34,
        "15" to 35
    )

    public fun readFile() {
        var header: List<String>? = null

        File(fileJson).forEachLine { line ->
            if (line.isBlank()) return@forEachLine

            if (header == null) {
                header = splitCsvLine(line)
                return@forEachLine
            }

            val parts = splitCsvLine(line)
            if (parts.size < header!!.size) return@forEachLine

            val row = header!!.zip(parts).toMap()
            val category = row["Category"]?.trim().orEmpty()
            if (category.isEmpty()) return@forEachLine

            addCategory(category, row)
        }
    }


    private fun splitCsvLine(line: String): List<String> {
        val result = mutableListOf<String>()
        val sb = StringBuilder()
        var inQuotes = false
        var i = 0
        while (i < line.length) {
            val c = line[i]
            when {
                c == '"' -> {
                    if (inQuotes && i + 1 < line.length && line[i + 1] == '"') {
                        sb.append('"'); i++
                    } else {
                        inQuotes = !inQuotes
                    }
                }
                c == ',' && !inQuotes -> {
                    result.add(sb.toString()); sb.clear()
                }
                else -> sb.append(c)
            }
            i++
        }
        result.add(sb.toString())
        return result
    }

    private fun parse(row: Map<String, String>): MutableMap<String, Any?> {
        val tempData = mutableMapOf<String, Any?>()

        tempData["app"] = row["App"]?.trim()
        tempData["rating"] = row["Rating"]?.trim()?.toDoubleOrNull()
        tempData["reviews"] = row["Reviews"]?.trim()?.toLongOrNull()
        tempData["size"] = row["Size"]?.trim()
        tempData["installs"] = parseInstalls(row["Installs"])
        tempData["type"] = row["Type"]?.trim()
        tempData["price"] = parsePrice(row["Price"])
        tempData["contentRating"] = row["Content Rating"]?.trim()
        tempData["genres"] = row["Genres"]
            ?.split(";")
            ?.map { it.trim() }
            ?.filter { it.isNotEmpty() }
            ?: emptyList<String>()
        tempData["lastUpdated"] = timeToFormatISO8601(row["Last Updated"]?.trim().orEmpty())
        tempData["currentVer"] = row["Current Ver"]?.trim()
        tempData["androidVer"] = parseAndroidVer(row["Android Ver"])

        return tempData
    }

    private fun parseInstalls(value: String?): Long? {
        if (value == null) return null
        val cleaned = value.replace(",", "").replace("+", "").trim()
        return cleaned.toLongOrNull()
    }

    private fun parsePrice(value: String?): Boolean {
        if (value == null) return false
        val v = value.trim().removePrefix("$")
        return when {
            v.equals("Free", ignoreCase = true) -> false
            v.isEmpty() -> false
            else -> (v.toDoubleOrNull() ?: 0.0) > 0.0
        }
    }


    private fun parseAndroidVer(value: String?): Int? {
        if (value == null) return null
        val v = value.trim()
        if (v.startsWith("Varies", ignoreCase = true)) return null
        val version = v.substringBefore(" and up").trim()
        return androidApiMap[version]
    }

    private fun timeToFormatISO8601(lineTime: String): String? {
        if (lineTime.isBlank()) return null
        return try {
            val input = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)
            val date = LocalDate.parse(lineTime, input)
            date.toString()
        } catch (e: Exception) {
            null
        }
    }

    private fun addCategory(key: String, row: Map<String, String>) {
        val appName = row["App"]?.trim().orEmpty()
        if (appName.isEmpty()) return
        val categoryMap = data.getOrPut(key) { mutableMapOf() }
        categoryMap[appName] = parse(row)
    }

    public fun saveToJson(path: String) {
        val sb = StringBuilder()
        sb.append("{\n")
        val catEntries = data.entries.toList()
        catEntries.forEachIndexed { ci, (category, apps) ->
            sb.append("  ").append(quote(category)).append(": {\n")
            val appEntries = apps.entries.toList()
            appEntries.forEachIndexed { ai, (appName, fields) ->
                sb.append("    ").append(quote(appName)).append(": {\n")
                val fieldEntries = fields.entries.toList()
                fieldEntries.forEachIndexed { fi, (k, v) ->
                    sb.append("      ").append(quote(k)).append(": ").append(toJsonValue(v))
                    if (fi < fieldEntries.size - 1) sb.append(",")
                    sb.append("\n")
                }
                sb.append("    }")
                if (ai < appEntries.size - 1) sb.append(",")
                sb.append("\n")
            }
            sb.append("  }")
            if (ci < catEntries.size - 1) sb.append(",")
            sb.append("\n")
        }
        sb.append("}\n")
        File(path).writeText(sb.toString())
    }

    private fun quote(s: String): String {
        val escaped = s.replace("\\", "\\\\").replace("\"", "\\\"")
        return "\"$escaped\""
    }

    private fun toJsonValue(v: Any?): String = when (v) {
        null -> "null"
        is String -> quote(v)
        is Boolean -> v.toString()
        is Number -> v.toString()
        is List<*> -> v.joinToString(prefix = "[", postfix = "]") { toJsonValue(it) }
        is Map<*, *> -> v.entries.joinToString(prefix = "{", postfix = "}") {
            "${quote(it.key.toString())}: ${toJsonValue(it.value)}"
        }
        else -> quote(v.toString())
    }
}