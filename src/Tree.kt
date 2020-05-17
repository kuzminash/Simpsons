import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/***
 * В алгоритме ребра направлены к root-у, но таким образом с деревом очень неудобно работать
 * так что в моем дереве ребра направлены от него
 */

class Tree(
    private val root: Int,
    private val permutations: MutableList<Permutation>,
    val nodes: HashMap<Int, Node> = HashMap()) {

    init {
        createTree()
    }

    /***
     * В вершинке чтобы было удобно работать также храним от какой перестановке к нему пришли
     * чтобы было удобно восстанавливать после перестановку
     */

    class Node(val parent: Int, val permutation: Permutation) {}

    private fun createTree() {

        val been = MutableList (size) { false }
        val queue: Queue<Int> = LinkedList ()
        val id = MutableList (size) { 0 }
        for (i in 0 until size) {
            id[i] = i
        }

        queue.add(root)
        been[root] = true
        nodes[root] = Node(root, Permutation(id))

        /***
         * dfs-ом ищем все элементы в которые мы можем попасть из root-а
         */

        while (queue.isNotEmpty()) {
            val element: Int = queue.remove()
            for (i in 0 until permutations.size) {
                val newElement = permutations[i].applyToElement(element)
                if (!been[newElement]) {
                    queue.add(newElement)
                    been[newElement] = true
                    nodes[newElement] = Node(element, permutations[i])
                }
            }
        }
    }
}