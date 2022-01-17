package grocerylist.application.service

import grocerylist.application.exception.GroceryListNotFoundException
import grocerylist.transport.http.dto.GroceryItem
import grocerylist.transport.http.dto.GroceryItemAdd

class GroceryListItemService(
    private val groceryListItemRepository: GroceryListItemRepository,
    private val groceryListService: GroceryListService
) {
    suspend fun addGroceryListItem(groceryListId: String, groceryItem: GroceryItem): GroceryItem {

        groceryListService.getGroceryList(groceryListId)
        return groceryListItemRepository.save(groceryListId, groceryItem)
    }

    suspend fun updateGroceryListItem(groceryListId: String, groceryItem: GroceryItem): GroceryItem {
        return groceryListItemRepository.update(groceryListId, groceryItem)
    }

    suspend fun deleteGroceryListItem(groceryListId: String, groceryItemId: String) {
        groceryListItemRepository.delete(groceryListId, groceryItemId)
    }
}

fun toGroceryItem(groceryItemAdd: GroceryItemAdd): GroceryItem {
    return GroceryItem(
        name = groceryItemAdd.name,
        quantity = groceryItemAdd.quantity,
        isBought = groceryItemAdd.isBought
    )
}