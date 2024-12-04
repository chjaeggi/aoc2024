package utils

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import kotlin.math.min


fun execFileByLine(number: Int, f: (str: String) -> Unit) =
    File("./src/inputs/input$number.txt").forEachLine { f(it) }

fun execFileByLineIndexed(number: Int, f: (index: Int, str: String) -> Unit) {
    val arr = mutableListOf<String>()
    File("./src/inputs/input$number.txt").forEachLine {
        arr.add(it)
    }
    arr.forEachIndexed { index, value ->
        f(index, value)
    }
}

fun execFileByLineInGroups(number: Int, groupSize: Int, f: (str: List<String>) -> Unit) {
    val arr = ArrayDeque<String>()
    File("./src/inputs/input$number.txt").forEachLine {
        arr.addLast(it)
    }
    while (arr.isNotEmpty()) f((0..<min(groupSize, arr.size)).map { arr.removeFirst() })
}

fun numberOfLinesPerFile(number: Int): Int {
    val reader = BufferedReader(FileReader("./src/inputs/input$number.txt"))
    var lines = 0
    while (reader.readLine() != null) lines++
    reader.close()
    return lines
}

fun numberOfCharsPerLine(number: Int): Int {
    val reader = BufferedReader(FileReader("./src/inputs/input$number.txt"))
    val line = reader.readLine()
    reader.close()
    return line.length
}