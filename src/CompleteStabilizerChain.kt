
class CompleteStabilizerChain(
    private val base: MutableList<Int>,
    generatorsSet: MutableList<Permutation>,
    private val chain: MutableList<SubgroupG> = ArrayList())  {

    private val id = Permutation(MutableList(size) { it })

    /***
     * Прямо в конструкторе сразу строим полную цепочку стабилизаторов
     * по алгоритму
     */

    init {

        var helpPermutations = generatorsSet
        for (b in 0 until base.size) {

            val root = base[b]
            val tree = Tree(root, helpPermutations)

            val newG = SubgroupG(root, helpPermutations, tree)
            chain.add(newG)
            val temp: MutableList<Permutation> = ArrayList()

            for (y in 0 until size) {

                /***
                 * Заходим только если наш элемент принадлежит орбите
                 */

                if (tree.nodes.containsKey(y)) {

                    val restored = restorePermutation(root, y, tree)

                    for (s in newG.formings) {

                        var myS = s
                        myS = myS.multiply(restored)

                        val element = s.applyToElement(y)
                        var ToSY = restorePermutation(root, element, tree)
                        var reverseToSY = ToSY.reverse()

                        reverseToSY = reverseToSY.multiply(myS)

                        temp.add(reverseToSY)
                    }
                }
            }
            helpPermutations = normalize(temp)
        }
    }

    /***
     * Просеиваем чтобы работало быстрее
     */

    private fun normalize(S: MutableList<Permutation>): MutableList<Permutation> {
        val permutations: MutableList<Permutation> = ArrayList()
        val been: HashMap<Pair<Int, Int>, Permutation> = HashMap()
        for (s in S) {
            var myS = s
            for (x in 0 until size) {
                if (myS.applyToElement(x) != x) {
                    if (been.containsKey(Pair(x, myS.applyToElement(x)))) {
                        val newMyS = myS
                        myS = myS.reverse()
                        myS = myS.multiply(been[Pair(x, newMyS.applyToElement(x))]!!)
                    } else {
                        been[Pair(x, myS.applyToElement(x))] = myS
                        permutations.add(myS)
                        break
                    }
                }
            }
        }
        return permutations
    }


    /***
     * Восстанавливаем по дереву перестановку идя до root-а
     */
    private fun restorePermutation(root: Int, to: Int, tree: Tree): Permutation {

        var position = to
        var permutation = id

        while (position != root) {
            permutation = permutation.multiply(tree.nodes[position]!!.permutation)
            position = tree.nodes[position]!!.parent
        }
        return permutation
    }

    /***
     * Функция считающая порядок группы так как написано в конспекте
     */
    fun groupOrder(): Long {
        var order: Long = 1
        for (i in 0 until chain.size) {
            order *= (chain[i].tree.nodes.size)
        }
        return order
    }

    /***
     * А тут мы проверяем лежит ли в нашей группе перестановка или нет воспользовавшись алгоритмом
     */
    fun belongs(permutation: Permutation): Boolean {
        var myPermutation = permutation
        for (i in 0 until base.size) {
            val u = myPermutation.applyToElement(base[i])
            if (!chain[i].tree.nodes.containsKey(u)) return false
            var restore = restorePermutation(base[i], u, chain[i].tree)
            restore = restore.reverse()
            myPermutation = restore.multiply(myPermutation)
        }
        if (myPermutation.isEqual(id)) {
            return true
        }
        return false
    }
}