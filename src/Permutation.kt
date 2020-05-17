/***
 * в decomposition мы записываем образующие на которе раскладывается наша перестановка
 */
class Permutation(
    val permutation: MutableList<Int>,
    var decomposition: MutableList<Int> = ArrayList()) {

    /***
     * Разворачиваем перестановку
     */
    fun reverse(): Permutation {
        val newPermutationList = MutableList<Int>(size) { 0 }
        for (i in 0 until size)
            newPermutationList[permutation[i]] = i
        val newPermutation = Permutation(newPermutationList)
        newPermutation.decomposition.clear()
        for (i in 0 until decomposition.size) {
            newPermutation.decomposition.add(-(decomposition[decomposition.size - i - 1]))
        }
        return newPermutation
    }

    /***
     * Умножаем нашу перестановку на другую справа
     */
    fun multiply(otherPermutation: Permutation): Permutation {
        val newPermutationList = MutableList<Int>(size) { 0 }
        for (i in 0 until size)
            newPermutationList[i] = permutation[otherPermutation.permutation[i]]
        val newPermutation = Permutation(newPermutationList)
        newPermutation.decomposition = (decomposition + otherPermutation.decomposition) as MutableList<Int>
        return newPermutation
    }

    /***
     * смотрим куда элемент перешел при перестановке
     */
    fun applyToElement(element: Int): Int {
        return permutation[element]
    }

    /***
     * Сравниваем наши перестановки на равенство
     */
    fun isEqual(otherPermutation: Permutation): Boolean {
        for (i in 0 until size) {
            if (otherPermutation.permutation[i] != permutation[i]) return false
        }
        return true
    }

}