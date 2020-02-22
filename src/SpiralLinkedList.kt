import kotlin.reflect.KClass

class SpiralLinkedList {

    /**
     * Last node
     * */
    private var last: Node? = null

    /**
     * Store the last element of each type
     * */
    private val headNodes = hashMapOf<KClass<*>, Node>()

    /**
     * Add a new element to the list
     * */
    fun add(value: Any){
        if (last == null){
            putFirstElement(value)
        }else{
            addToLast(value)
        }
    }

    /**
     * Pop the last element
     * */
    fun popLast(): Any? {
        val lastNode = last?: return null
        sinkAt(lastNode)

        return lastNode.value
    }

    /**
     * Pop the last element matching the specified type
     *
     * @param kClass specified type
     * @return T
     * */
    fun <T : Any> popLast(kClass: KClass<T>): T? {
        if (last == null){
            return null
        }
        val nodeKey = headNodes.keys.find { it == kClass}?: return null
        val node = headNodes[nodeKey]

        requireNotNull(node)

        sinkAt(node)

        return node.value as T
    }

    /**
     * Extract the specified node and sink the list
     *
     * @param node specified node
     * */
    private fun sinkAt(node: Node){
        if (node.next == null){
            node.prev?.next = null
            last = node.prev
        }else{
            val next = node.next
            val prev = node.prev
            prev?.next = next
            next?.prev = prev
        }

        sinkHeadNode(node)

        node.next = null
        node.prev = null
        node.vprev = null
    }

    /**
     * Traversing the list
     *
     * @param block code block for each element
     * */
    fun foreach(block: (Any) -> Unit){
        var cursor = last
        while (cursor != null){
            block(cursor.value!!)
            cursor = cursor.prev
        }
    }

    /**
     * Returns the first element matching the given
     * [predicate], or `null` if no such element was
     * found.
     *
     * @param predicate
     *
     * @return
     * */
    fun find(predicate: (Any) -> Boolean): Any?{
        var findTarget: Any? = null
        var cursor = last
        while (cursor != null){
            if (cursor.value != null && predicate(cursor.value!!)){
                findTarget =  cursor.value
                break
            }else{
                cursor = cursor.prev
            }
        }

        return findTarget
    }

    fun length(): Int {
        var count = 0
        foreach { count ++ }
        return count
    }

    /**
     * Put the first element into list
     *
     * @param value first element
     * */
    private fun putFirstElement(value: Any){
        val newNode = Node(value.javaClass.kotlin, value, null, null, null)
        last = newNode
        riseHeadNode(newNode)
    }

    /**
     * Append a new element
     *
     * @param value
     * */
    private fun addToLast(value: Any){
        requireNotNull(last)
        val vprev = headNodes[value.javaClass.kotlin]
        val newNode = Node(value.javaClass.kotlin, value, last, null, vprev)
        last!!.next = newNode
        last = newNode
        riseHeadNode(newNode)
    }

    /**
     * Rise head node of match type
     *
     * @param node
     * */
    private fun riseHeadNode(node: Node){
        requireNotNull(node.value)
        headNodes[node.value!!::class] = node
    }

    /**
     * Sink head node of match type
     *
     * @param node
     * */
    private fun sinkHeadNode(node: Node){
        val vprev = node.vprev
        if (vprev == null){
            headNodes.remove(node.kClass)
        }else{
            headNodes[node.kClass] = vprev
        }
    }

    private class Node internal constructor(
        var kClass: KClass<*>,
        var value: Any?,
        var prev: Node?,
        var next: Node?,
        var vprev: Node?
    )

}