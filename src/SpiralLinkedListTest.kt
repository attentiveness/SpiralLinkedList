class SpiralLinkedListTest {

    private val testTargetElement = Object3(data = 5)

    private val testLastElement = Object2(data = 6)

    private fun addDefault6ElementsTo(spiralLinkedList: SpiralLinkedList){
        spiralLinkedList.add(Object1(data = 1))
        spiralLinkedList.add(Object2(data = 2))
        spiralLinkedList.add(Object3(data = 3))
        spiralLinkedList.add(Object2(data = 4))
        spiralLinkedList.add(testTargetElement)
        spiralLinkedList.add(testLastElement)
    }

    @org.junit.Test
    fun add() {
        val spiralLinkedList = SpiralLinkedList()
        addDefault6ElementsTo(spiralLinkedList)
        assert(spiralLinkedList.length() == 6)
    }

    @org.junit.Test
    fun popLast() {
        val spiralLinkedList = SpiralLinkedList()
        addDefault6ElementsTo(spiralLinkedList)
        val last = spiralLinkedList.popLast()
        assert(last == testLastElement)
    }

    @org.junit.Test
    fun popLast1() {
        val spiralLinkedList = SpiralLinkedList()
        addDefault6ElementsTo(spiralLinkedList)
        val last = spiralLinkedList.popLast(testTargetElement::class)
        assert(last == testTargetElement)
    }

    @org.junit.Test
    fun foreach() {
        val spiralLinkedList = SpiralLinkedList()
        addDefault6ElementsTo(spiralLinkedList)
        spiralLinkedList.foreach {
            println(it)
        }
    }

    @org.junit.Test
    fun find() {
        val spiralLinkedList = SpiralLinkedList()
        addDefault6ElementsTo(spiralLinkedList)
        val find = spiralLinkedList.find { it is Object3 && it.data == testTargetElement.data }
        assert(find == testTargetElement)
    }

    data class Object1(val data: Int)

    data class Object2(val data: Int)

    data class Object3(val data: Int)

}