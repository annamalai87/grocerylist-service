package grocerylist.transport.http.routes

import grocerylist.application.service.GroceryListItemService
import grocerylist.application.service.GroceryListService
import grocerylist.application.service.toGroceryItem
import grocerylist.application.service.toGroceryList
import grocerylist.transport.http.dto.GroceryItemAdd
import grocerylist.transport.http.dto.GroceryListCreate
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.inject
import org.slf4j.LoggerFactory


fun Route.groceryListRoutes() {
    val groceryListService by inject<GroceryListService>(GroceryListService::class.java)

    get("/{groceryListId}") {
        val groceryListId = call.parameters["groceryListId"].orEmpty()
        call.respond(HttpStatusCode.OK, groceryListService.getGroceryList(groceryListId))
    }

    post {
        val groceryListCreate = call.receive<GroceryListCreate>()
        val groceryList = toGroceryList(groceryListCreate)
        call.respond(HttpStatusCode.Created, groceryListService.createGroceryList(groceryList))
    }

}