import jdk.jfr.Name
import org.junit.Before
import org.junit.Test
import kotlin.test.*

internal class LRUCacheAPIImplTest {

    private val cacahe: LRUCacheAPI = LRUCacheAPIImpl(4)
    private val itemOne = Item(1, "Item 1", 1L)
    private val itemTwo = Item(2, "Item 2", 2L)
    private val itemThree = Item(3, "Item 3", 3L)
    private val itemFour = Item(4, "Item 4", 4L)
    private val itemFive = Item(5, "Item 5", 5L)


    @Before
    fun setUpForTest() {
        cacahe.clearAll()
        cacahe.write(itemOne)
        cacahe.write(itemTwo)
        cacahe.write(itemThree)
        cacahe.write(itemFour)
        cacahe.write(itemFive)
    }

    @Test
    @Name("Cache has fixed Size")
    fun cacheCanHandleOnlyFixedNoOfItems() {
        assertEquals(4, cacahe.readAll().size)
    }

    @Test
    @Name("Cache read one gives proper item")
    fun cacheReadOne() {
        val item = cacahe.readOne(itemFour.id)
        assertNotNull(item)
        assertEquals(item.id, itemFour.id)
        assertEquals(item.name, itemFour.name)
    }

    @Test
    @Name("Cache read reading updates the item")
    fun cacheReadOneUpdate() {
        val item = cacahe.readOne(itemFour.id)
        assertNotNull(item)
        assertEquals(item.id, itemFour.id)
        assertEquals(item.name, itemFour.name)
        assertTrue { item.lastAccessedOn > itemFour.lastAccessedOn }
    }

    @Test
    @Name("Cache read one with invalud id gives null")
    fun cacheReadInvalidOne() {
        val item = cacahe.readOne(500)
        assertNull(item)
    }

    @Test
    @Name("Cache holds latest data")
    fun cacheHoldsLatestData() {
        val items = cacahe.readAll().map { it.id }
        assertContains(items, itemFive.id)
        assertFalse(items.contains(itemOne.id))
    }

    @Test
    @Name("Cache retains proper data")
    fun cacheDataValidity() {
        val latestItem = cacahe.readAll().maxByOrNull { it.lastAccessedOn }!!
        assertEquals(itemFive, latestItem)
    }

    @Test
    @Name("Cache items are removed properly")
    fun cacheItemDeletion() {
        cacahe.delete(itemThree)
        val items = cacahe.readAll()
        assertEquals(3, items.size)
        assertFalse(items.contains(itemThree))
    }
}

// email address


