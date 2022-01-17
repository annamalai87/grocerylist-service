package grocerylist.persistence.mongo

import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.*
import com.mongodb.reactivestreams.client.MongoDatabase
import grocerylist.application.exception.MongoDBException
import grocerylist.application.service.GroceryListItemRepository
import grocerylist.application.util.awaitFirstOrNullWithExceptionHandling
import grocerylist.transport.http.dto.GroceryItem
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory

class GroceryListItemRepoMongoDBImpl(private val mongoDatabase: MongoDatabase) : GroceryListItemRepository {

    private val mongoCollection = mongoDatabase.getCollection("grocerylist", GroceryListDocument::class.java)
    private val log = LoggerFactory.getLogger(GroceryListItemRepoMongoDBImpl::class.java)

    override suspend fun save(groceryListId: String, groceryItem: GroceryItem): GroceryItem {

        val groceryListItemDocument = toGroceryListItemDocument(groceryItem)

        val updateResult = mongoCollection.updateOne(
            eq("_id", ObjectId(groceryListId)),
            push("grocery_items", groceryListItemDocument)
        ).awaitFirstOrNullWithExceptionHandling() {
            MongoDBException("GROCERY_ITEM_FAILURE", "Failed to save grocery list item $groceryListItemDocument")
        }


        return groceryItem.copy(id = groceryListItemDocument.id.toString())

    }

    override suspend fun update(groceryListId: String, groceryItem: GroceryItem): GroceryItem {
        val groceryListItemDocument = toGroceryListItemDocument(groceryItem)

        val updateResult = mongoCollection.updateOne(
            and(
                eq("_id", ObjectId(groceryListId)),
                eq("grocery_items._id", ObjectId(groceryItem.id))
            ),
            set("grocery_items.$", groceryListItemDocument)
        ).awaitFirstOrNullWithExceptionHandling() {
            MongoDBException("GROCERY_ITEM_FAILURE", "Failed to update grocery list item $groceryListItemDocument")
        }

        return groceryItem
    }

    override suspend fun delete(groceryListId: String, groceryItemId: String) {

        mongoCollection.updateOne(
            eq("_id", ObjectId(groceryListId)),
            pull("grocery_items", eq("_id", ObjectId(groceryItemId)))
        ).awaitFirstOrNullWithExceptionHandling() {
            MongoDBException("GROCERY_ITEM_FAILURE", "Failed to delete grocery list item $groceryItemId")
        }
    }
}
