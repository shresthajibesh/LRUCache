val cache:LRUCacheAPI = LRUCacheAPIImpl(3)

fun main(args: Array<String>) {
    repeat(4){
        cache.write(Item(it,"Item $it"))
    }

    cache.readAll().forEach {
        println(it)
    }
}