package grocerylist.application.service

import grocerylist.application.exception.GroceryListNotFoundException
import grocerylist.transport.http.dto.GroceryList
import grocerylist.transport.http.dto.GroceryListCreate
import java.util.*

class GroceryListService(private val groceryListRepository: GroceryListRepository) {
    suspend fun createGroceryList(groceryList: GroceryList): GroceryList {
        return groceryListRepository.save(groceryList)
    }

    suspend fun getGroceryList(id: String): GroceryList {
        return groceryListRepository.get(id) ?: throw GroceryListNotFoundException(
            "GROCERY_LIST_NOT_FOUND",
            "Grocery list is not found $id"
        )
    }
}

fun toGroceryList(groceryListCreate: GroceryListCreate): GroceryList {
    return GroceryList(
        createdAt = Date().toString(),
        createdBy = groceryListCreate.createdBy
    )
}