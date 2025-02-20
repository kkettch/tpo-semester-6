package task2

class SkewHeap<T : Comparable<T>> {
    private var root: Node<T>? = null

    private class Node<T>(val value: T, var left: Node<T>? = null, var right: Node<T>? = null) {
        override fun toString(): String {
            return "($value, L=${left.toString()}, R=${right.toString()})"
        }
    }

    private fun merge(h1: Node<T>?, h2: Node<T>?): Node<T>? {
        if (h1 == null) return h2
        if (h2 == null) return h1

        if (h1.value > h2.value) {
            h2.left = h2.right.also { h2.right = h2.left }
            h2.left = merge(h2.left, h1)

            return h2
        } else {
            h1.left = h1.right.also { h1.right = h1.left }
            h1.left = merge(h1.left, h2)
            return h1
        }
    }

    fun mergeWith(other: SkewHeap<T>) {
        root = merge(root, other.root)
        other.root = null
    }

    fun insert(value: T) {
        root = merge(root, Node(value))
    }

    fun insertList(heap: List<T>) {
        for (value in heap) {
            this.insert(value)
        }
    }

    fun extractMin(): T? {
        val minValue = root?.value
        root = merge(root?.left, root?.right)
        return minValue
    }

    override fun toString(): String {
        return root.toString()
    }
}

//fun main() {
//    val heap = SkewHeap<Int>()
//    val heap2 = SkewHeap<Int>()
//
//    heap.insertList(listOf(5, 12, 10))
//    println(heap)
//    heap2.insertList(listOf(3, 7, 8, 14))
//    println(heap2)
//
//    heap.mergeWith(heap2)
//    println(heap)
//}