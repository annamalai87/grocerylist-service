package grocerylist

import com.google.gson.FieldNamingPolicy
import grocerylist.application.config.groceryListModule
import grocerylist.application.exception.ErrorResponse
import grocerylist.application.exception.GroceryListNotFoundException
import grocerylist.application.exception.MongoDBException
import grocerylist.transport.http.routes.actuatorRoutes
import grocerylist.transport.http.routes.groceryListItemRoutes
import grocerylist.transport.http.routes.groceryListRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.server.netty.EngineMain
import io.ktor.gson.gson
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.core.context.startKoin

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    install(CallLogging)
    install(CORS)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        }
    }

    install(StatusPages) {
        exception<MongoDBException> { cause ->
            call.respond(HttpStatusCode.FailedDependency, ErrorResponse(cause.errorCode, cause.errorMessage))
        }
        exception<GroceryListNotFoundException> { cause ->
            call.respond(HttpStatusCode.NotFound, ErrorResponse(cause.errorCode, cause.errorMessage))
        }
    }


    startKoin {
        modules(groceryListModule)
    }

    install(Routing) {
        route("/grocery_list/v1") {
            actuatorRoutes()
            groceryListRoutes()
            groceryListItemRoutes()
        }
    }
}


