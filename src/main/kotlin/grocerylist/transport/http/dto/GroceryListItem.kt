package grocerylist.transport.http.dto

data class GroceryItemAdd(
    var name: String?,
    var quantity: Int?,
    var isBought: Boolean?
)

data class GroceryItem(
    var id: String? = null,
    var name: String? = null,
    var quantity: Int? = null,
    var isBought: Boolean? = null
)