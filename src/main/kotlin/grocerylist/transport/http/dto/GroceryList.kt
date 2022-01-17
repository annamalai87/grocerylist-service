package grocerylist.transport.http.dto

import org.bson.codecs.pojo.annotations.BsonProperty

data class GroceryListCreate(
    var createdBy: String?
)

data class GroceryList(
    var id: String? = null,
    var groceryItems: List<GroceryItem>? = null,
    var createdAt: String? = null,
    var createdBy: String? = null
)