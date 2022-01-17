package grocerylist.application.service

import grocerylist.transport.http.dto.GroceryList

interface GroceryListRepository {
    suspend fun get(id: String): GroceryList?
    suspend fun save(groceryList: GroceryList) : GroceryList
}