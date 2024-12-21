package utils

fun <T> permutationsForElements(operators: List<T>, length: Int): List<List<T>> {
    if (length == 1) return operators.map { listOf(it) }
    return permutationsForElements(operators, length - 1).flatMap { prefix ->
        operators.map { operator -> prefix + operator }
    }
}

fun <T> List<T>.combinations(size: Int): List<List<T>> {
    if (size == 0) return listOf(emptyList())
    if (size > this.size) return emptyList()
    if (size == this.size) return listOf(this)

    val result = mutableListOf<List<T>>()
    val remaining = this.drop(1)

    result.addAll(remaining.combinations(size - 1).map { listOf(this.first()) + it })
    result.addAll(remaining.combinations(size))

    return result
}

fun <T> List<T>.permutations(): List<List<T>> {
    if (this.size <= 1) return listOf(this)

    val result = mutableListOf<List<T>>()
    for (i in this.indices) {
        val remaining = this.toMutableList().apply { removeAt(i) }
        for (perm in remaining.permutations()) {
            result.add(listOf(this[i]) + perm)
        }
    }
    return result
}