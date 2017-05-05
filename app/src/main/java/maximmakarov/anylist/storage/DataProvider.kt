package maximmakarov.anylist.storage

import android.content.Context
import io.requery.Persistable
import io.requery.android.sqlite.DatabaseSource
import io.requery.kotlin.eq
import io.requery.query.Result
import io.requery.reactivex.KotlinReactiveEntityStore
import io.requery.sql.KotlinEntityDataStore
import io.requery.sql.TableCreationMode
import maximmakarov.anylist.item.Item
import maximmakarov.anylist.item.Models

/**
 * @author Maxim Makarov
 * @since 05.05.2017
 */
class DataProvider {
    val dbVersion: Int = 1

    val data: KotlinReactiveEntityStore<Persistable>

    constructor(context: Context) {
        val source = DatabaseSource(context, Models.DEFAULT, dbVersion)
        source.setTableCreationMode(TableCreationMode.DROP_CREATE)
        data = KotlinReactiveEntityStore<Persistable>(KotlinEntityDataStore(source.configuration))
    }

    fun loadAllItems(): Result<Item> {
        return (data select (Item::class)).get()
    }

    fun loadChilds(item: Item): Result<Item> {
        return (data select (Item::class) where (Item::parentId eq item.id)).get()
    }

    fun getPathToItem(item: Item) {

    }

    fun saveItem(item: Item) {
        data insert item
    }

    fun updateItem(item: Item) {
        data update item
    }

    fun removeItemSafe(item: Item) { //with all sub-hierarchy
        //todo rewrite
        data delete item
    }
}