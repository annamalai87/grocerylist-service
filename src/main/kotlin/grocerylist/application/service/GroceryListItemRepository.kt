package grocerylist.application.service

import grocerylist.transport.http.dto.GroceryItem


interface GroceryListItemRepository {
    suspend fun save(groceryListId: String, groceryItem: GroceryItem): GroceryItem
    suspend fun update(groceryListId: String, groceryItem: GroceryItem): GroceryItem
    suspend fun delete(groceryListId: String, groceryItemId: String)
}
