package grocerylist.persistence.mongo

import com.mongodb.client.model.Filters.eq
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.MongoDatabase
import grocerylist.application.exception.MongoDBException
import grocerylist.application.service.GroceryListRepository
import grocerylist.application.util.awaitFirstOrNullWithExceptionHandling
import grocerylist.transport.http.dto.GroceryList
import org.bson.types.ObjectId

class GroceryListRepoMongoDBImpl(private val mongoDatabase: MongoDatabase) : GroceryListRepository {

    val mongoCollection: MongoCollection<GroceryListDocument> =
        mongoDatabase.getCollection("grocerylist", GroceryListDocument::class.java)

    override suspend fun get(id: String): GroceryList? {
        val getResult = mongoCollection.find(eq("_id", ObjectId(id))).awaitFirstOrNullWithExceptionHandling {
            MongoDBException("GROCERY_LIST_FAILURE", "Fail to get grocery list $id")
        }

        return getResult?.let { toGroceryList(it) }
    }

    override suspend fun save(groceryList: GroceryList): GroceryList {
        val groceryListDocument = toGroceryListDocument(groceryList)
        val insertOneResult = mongoCollection.insertOne(groceryListDocument).awaitFirstOrNullWithExceptionHandling() {
            MongoDBException("GROCERY_LIST_FAILURE", "Failed to save grocery list $groceryListDocument")
        }
        return groceryList.copy(id = groceryListDocument.id.toString())
    }
}



