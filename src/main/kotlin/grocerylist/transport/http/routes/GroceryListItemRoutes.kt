package grocerylist.transport.http.routes

import grocerylist.application.service.GroceryListItemService
import grocerylist.application.service.GroceryListService
import grocerylist.application.service.toGroceryItem
import grocerylist.transport.http.dto.GroceryItem
import grocerylist.transport.http.dto.GroceryItemAdd
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.groceryListItemRoutes() {
    val groceryListItemService by inject<GroceryListItemService>(GroceryListItemService::class.java)

    post("/{groceryListId}/grocery_item") {
        val groceryListId = call.parameters["groceryListId"].orEmpty()
        val groceryItemAdd = call.receive<GroceryItemAdd>()
        call.respond(
            HttpStatusCode.Created,
            groceryListItemService.addGroceryListItem(groceryListId, toGroceryItem(groceryItemAdd))
        )
    }

    put("/{groceryListId}/grocery_item") {
        val groceryListId = call.parameters["groceryListId"].orEmpty()
        val groceryItem = call.receive<GroceryItem>()
        call.respond(HttpStatusCode.OK, groceryListItemService.updateGroceryListItem(groceryListId, groceryItem))
    }

    delete("/{groceryListId}/grocery_item/{groceryItemId}") {
        val groceryListId = call.parameters["groceryListId"].orEmpty()
        val groceryItemId = call.parameters["groceryItemId"].orEmpty()
        groceryListItemService.deleteGroceryListItem(groceryListId, groceryItemId)
        call.respond(HttpStatusCode.NoContent)
    }
}