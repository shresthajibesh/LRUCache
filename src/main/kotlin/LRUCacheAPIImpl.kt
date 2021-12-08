class LRUCacheAPIImpl(private val size:Int = 20): LRUCacheAPI{

    init {
        if(size<1){
            throw Throwable("Cache needs to eba ble to hold atleast one item")
        }
    }
    private val myItemsMap = mutableMapOf<Int,Item>()
    override fun readAll(): List<Item> {
        return myItemsMap.values.toList()
    }

    override fun readOne(withID: Int): Item? {
        var item  = myItemsMap[withID]
        if(item!=null){
            item = item.copy(lastAccessedOn = System.currentTimeMillis())
            myItemsMap[withID] = item
        }
        return item
    }

    override fun write(item: Item) {
        if(myItemsMap.size<size){
            myItemsMap[item.id] = item
        }else{
            val lestUsedItem = myItemsMap.values.minByOrNull { it.lastAccessedOn }!!
            delete(lestUsedItem)
            myItemsMap[item.id] = item
        }
    }

    override fun delete(item: Item) {
        myItemsMap.remove(item.id)
    }

    override fun clearAll() {
        myItemsMap.clear()
    }
}