interface LRUCacheAPI {
    fun readAll():List<Item>
    fun readOne(withID:Int):Item?
    fun write(item:Item)
    fun delete(item:Item)
    fun clearAll()
}