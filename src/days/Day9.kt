package days

import utils.execFileByLine

class Day9 {

    fun solve() {
        execFileByLine(9) {
            val digits = it.toCharArray().map { it.digitToInt() }
            val disk = ArrayDeque<Int>()

            for ((index, digit) in digits.withIndex()) {
                if (index % 2 == 0) {
                    repeat(digit) {
                        disk.addLast(index / 2)
                    }
                } else {
                    repeat(digit) {
                        disk.addLast(-1)
                    }
                }
            }
            part1(disk)
            disk.clear()
            for ((index, digit) in digits.withIndex()) {
                if (index % 2 == 0) {
                    repeat(digit) {
                        disk.addLast(index / 2)
                    }
                } else {
                    repeat(digit) {
                        disk.addLast(-1)
                    }
                }
            }
            part2(disk)
        }
    }

    private fun part1(disk: ArrayDeque<Int>) {
        val freeSpaceIndices = disk.withIndex()
            .filter { it.value == -1 }
            .map { it.index }

        val defragment = disk.toMutableList()
        for (index in freeSpaceIndices) {
            var nextLastValue = disk.removeLast()
            while (nextLastValue == -1) {
                nextLastValue = disk.removeLast()
            }
            defragment[index] = nextLastValue
        }
        repeat(freeSpaceIndices.count()) {
            defragment.removeLast()
        }
        var checksum = 0L
        defragment.forEachIndexed { index, i ->
            checksum += if (i == -1) 0 else index * i
        }
        println(checksum)
    }

    private fun part2(disk: ArrayDeque<Int>) {
        val freeMemory = freeMemoryLocations(disk)
        val filesInMemory = fileLocations(disk)
        val freeMemRanges = freeMemory.toMutableList()
        val filesInRanges = filesInMemory.toMutableList()
        val defragment = defragment(disk, filesInRanges, freeMemRanges)

        var checksum = 0L
        defragment.forEachIndexed { index, i ->
            if (i != -1) checksum += index * i
        }
        println(checksum)
    }

    private fun defragment(
        disk: ArrayDeque<Int>,
        filesInRanges: MutableList<Pair<Int, Int>>,
        freeMemRanges: MutableList<Pair<Int, Int>>
    ): List<Int> {
        val defragment = disk.toMutableList()
        while (filesInRanges.isNotEmpty()) {
            val number = filesInRanges.lastIndex
            val (start, end) = filesInRanges.last()
            freeMemRanges.firstOrNull { (it.second - it.first) >= (end - start) }?.let {
                val freeMemRangeIndex = freeMemRanges.indexOf(it)
                if (freeMemRanges[freeMemRangeIndex].second < filesInRanges[number].first) {
                    for (i in start..end) {
                        defragment[i] = -1
                    }
                    for (i in it.first..it.first + (end - start)) {
                        defragment[i] = number
                    }
                    freeMemRanges[freeMemRangeIndex] =
                        freeMemRanges[freeMemRangeIndex].first + (end - start) + 1 to freeMemRanges[freeMemRangeIndex].second
                }
            }
            filesInRanges.removeLast()
        }
        return defragment
    }

    private fun freeMemoryLocations(disk: ArrayDeque<Int>): List<Pair<Int, Int>> {
        val freeMemory = mutableListOf<Int>()
        disk.zipWithNext().forEachIndexed { index, pair ->
            if (pair.first == -1 && pair.second != -1) {
                freeMemory += index
            }
            if (pair.first != -1 && pair.second == -1) {
                freeMemory += index + 1
            }
        }
        return freeMemory.chunked(2).map { it.first() to it.last() }
    }

    private fun fileLocations(disk: ArrayDeque<Int>): List<Pair<Int, Int>> {
        val filesInMemory = mutableListOf<Int>()
        val first = 0
        val last = disk.last()

        for (number in first..last) {
            filesInMemory += (listOf(
                disk.indexOfFirst { it == number },
                disk.indexOfLast { it == number }))
        }
        return filesInMemory.chunked(2).map { it.first() to it.last() }
    }

}