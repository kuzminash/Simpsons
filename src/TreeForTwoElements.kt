import java.util.*
import kotlin.collections.HashMap

/***
 * Хороший программист бы сделал такое дерево, что его можно было бы испольховать под произвольное
 * количество элементов в вершине, но я решила воспользоваться старым любимым методом копипасты
 * и чуть-чуть упростить себе жинзь
 * Это дерево нужно для выполнения второго задания, чтобы можно было посмотреть на орбиту элемента
 * составленного из пары.
 */

class TreeForTwoElements(
    private val root: Pair<Int, Int>,
    private val permutations: MutableList<Permutation>,
    val nodes: HashMap<Pair<Int, Int>, Node> = HashMap()) {

    init {
        createTree()
    }

    /***
     * Чтобы проверить, что мы пару не смогли перевести куда-то нам хватит информуции лишь о количестве
     * элементов в орбите так что не будем ничего хранить в вершине
     */

    class Node() {}

    private fun createTree() {

        val been = HashMap<Pair<Int, Int>, Boolean>()
        val queue: Queue<Pair<Int, Int>> = LinkedList()

        val id = MutableList(size) { 0 }
        for (i in 0 until size) {
            id[i] = i
        }

        queue.add(root)
        been[root] = true
        nodes[root] = Node()

        /***
         * dfs-ом ищем все элементы в которые мы можем попасть из root-а
         */

        while (queue.isNotEmpty()) {
            val element = queue.remove()
            for (i in 0 until permutations.size) {
                val newElementFirst = permutations[i].applyToElement(element.first)
                val newElementSecond = permutations[i].applyToElement(element.second)
                val newElement = Pair(newElementFirst, newElementSecond)
                if (!been.containsKey(newElement)) {
                    queue.add(newElement)
                    been[newElement] = true
                    nodes[newElement] = Node()
                }
            }
        }
    }
}