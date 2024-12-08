package utils

fun <T> permutationsForElements(operators: List<T>, length: Int): List<List<T>> {
    if (length == 1) return operators.map { listOf(it) }
    return permutationsForElements(operators, length - 1).flatMap { prefix ->
        operators.map { operator -> prefix + operator }
    }
}

fun <T> permutations(items: List<T>, length: Int): List<List<T>> {
    if (length == 0) return listOf(emptyList())

    val results = mutableListOf<List<T>>()
    val stack = ArrayDeque<Pair<List<T>, List<T>>>()

    stack.addLast(Pair(emptyList(), items))

    while (stack.isNotEmpty()) {
        val (current, available) = stack.removeLast()

        if (current.size == length) {
            results.add(current)
        } else {
            for (item in available) {
                val newCurrent = current + item
                val newAvailable = available - item
                stack.addLast(Pair(newCurrent, newAvailable))
            }
        }
    }

    return results
}

fun <T> combinations(items: List<T>, size: Int): List<List<T>> {
    if (size == 0) return listOf(emptyList())
    if (size > items.size) return emptyList()

    val results = mutableListOf<List<T>>()
    val stack = ArrayDeque<Pair<List<T>, Int>>()

    stack.addLast(Pair(emptyList(), 0))

    while (stack.isNotEmpty()) {
        val (current, index) = stack.removeLast()

        if (current.size == size) {
            results.add(current)
        } else {
            for (i in index..<items.size) {
                stack.addLast(Pair(current + items[i], i + 1))
            }
        }
    }

    return results
}