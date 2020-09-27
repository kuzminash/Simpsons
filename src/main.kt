import kotlin.system.exitProcess

const val size = 54

fun firstTask(Permutations: MutableList<Permutation>) {

    /***
     * пусть базой будут просто все элементы
     */
    val base: MutableList<Int> = ArrayList()
    for (i in 0 until size) {
        base.add(i)
    }

    val myChain = CompleteStabilizerChain(base, Permutations)

    val transpositionList: MutableList<Int> = ArrayList()
    transpositionList.add(1)
    transpositionList.add(0)
    for (i in 2 until size) {
        transpositionList.add(i)
    }
    val transposition = Permutation(transpositionList)
    /***
     * S_n порождается транспозицией и циклом, если в нашей группе нет элемента (01)
     * то мы автоматически проиграли и Костя  плохо перемешает карты
     */
    if (!myChain.belongs(transposition)) {
        println("Костя, ты проиграл, ты не можешь произвольным образом перемешать карты, нужно было лучше учиться:(")
    }
}

fun main() {
    val myPermutation: MutableList<Permutation> = ArrayList()

    for (i in 0 until size - 1) {
        val last = size - i - 1
        val listPermutation: MutableList<Int> = ArrayList()
        for (y in last until size) {
            listPermutation.add(y)
        }
        for (y in 0 until last) {
            listPermutation.add(y)
        }

        val permutation = Permutation(listPermutation)
        Formings.formings[permutation] = i + 1
        permutation.decomposition.add(i + 1)

        val reversePermutation = permutation.reverse()
        permutation.decomposition.add(-(i + 1))

        myPermutation.add(permutation)
        myPermutation.add(reversePermutation)
    }

    val listPermutation: MutableList<Int> = ArrayList()
    for (i in size/3 until size/3*2) {
        listPermutation.add(i)
    }
    for (i in 0 until size/3) {
        listPermutation.add(i)
    }
    for (i in size/3*2 until size) {
        listPermutation.add(i)
    }
    val permutation = Permutation(listPermutation)
    Formings.formings[permutation] = 54
    permutation.decomposition.add(54)

    val reversePermutation = permutation.reverse()
    permutation.decomposition.add(-54)

    myPermutation.add(permutation)
    myPermutation.add(reversePermutation) //закидываем все образующие которые у нас есть

    firstTask(myPermutation)
}