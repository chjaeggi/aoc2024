package utils

import java.io.BufferedReader
import java.io.File
import java.io.FileReader


fun execFileByLine(number: Int, f: (str: String) -> Unit) =
    File("./src/inputs/input$number.txt").forEachLine { f(it) }

fun execFileByLineIndexed(number: Int, f: (str: String, index: Int) -> Unit) {
    val arr = mutableListOf<String>()
    File("./src/inputs/input$number.txt").forEachLine {
        arr.add(it)
    }
    arr.forEachIndexed { index, value ->
        f(value, index)
    }
}

fun execFileByLineInGroups(number: Int, groupSize: Int, f: (str: List<String>) -> Unit) {
    val arr = mutableListOf<String>()
    File("./src/inputs/input$number.txt").forEachLine {
        arr.add(it)
    }
    val groupList = mutableListOf<String>()
    var index = 0
    while (index * groupSize < arr.size) {
        for (line in (index * groupSize)..<(index + 1) * groupSize) {
            groupList.add(arr[line])
        }
        index++
        f(groupList)
        groupList.clear()
    }

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