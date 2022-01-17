package grocerylist.persistence.mongo

import grocerylist.transport.http.dto.GroceryItem
import grocerylist.transport.http.dto.GroceryList
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class GroceryListDocument(
    @BsonProperty("_id")
    var id: ObjectId? = null,
    @get:BsonProperty("grocery_items") @set:BsonProperty("grocery_items")
    var groceryItems: List<GroceryItemDocument>? = null,
    var createdAt: String? = null,
    var createdBy: String? = null
)

data class GroceryItemDocument(
    @BsonProperty("_id")
    var id: ObjectId? = null,
    var name: String? = null,
    var quantity: Int? = null,
    @get:BsonProperty("is_bought") @set:BsonProperty("is_bought")
    var isBought: Boolean? = null
)

fun toGroceryListDocument(groceryList: GroceryList): GroceryListDocument {
    return GroceryListDocument(
        id = groceryList.id?.let { ObjectId(it) } ?: ObjectId(),
        groceryItems = groceryList.groceryItems?.map { it -> toGroceryListItemDocument(it) },
        createdAt = groceryList.createdAt,
        createdBy = groceryList.createdBy
    )
}

fun toGroceryList(groceryListDocument: GroceryListDocument): GroceryList {
    return GroceryList(
        id = groceryListDocument.id.toString(),
        groceryItems = groceryListDocument.groceryItems?.map { it -> toGroceryItem(it) }.orEmpty(),
        createdAt = groceryListDocument.createdAt,
        createdBy = groceryListDocument.createdBy
    )
}

fun toGroceryListItemDocument(groceryItem: GroceryItem): GroceryItemDocument {
    return GroceryItemDocument(
        id = groceryItem.id?.let { ObjectId(it) } ?: ObjectId(),
        name = groceryItem.name,
        quantity = groceryItem.quantity,
        isBought = groceryItem.isBought
    )
}

fun toGroceryItem(groceryItemDocument: GroceryItemDocument): GroceryItem {
    return GroceryItem(
        id = groceryItemDocument.id.toString(),
        name = groceryItemDocument.name,
        quantity = groceryItemDocument.quantity,
        isBought = groceryItemDocument.isBought
    )
}