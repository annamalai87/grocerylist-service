package grocerylist.transport.http.routes

import com.mongodb.reactivestreams.client.MongoDatabase
import grocerylist.application.exception.MongoDBException
import grocerylist.application.util.awaitFirstOrNullWithExceptionHandling
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent.inject
import org.slf4j.LoggerFactory


fun Route.actuatorRoutes() {
    val mongoDatabase by inject<MongoDatabase>(MongoDatabase::class.java)
    val log = LoggerFactory.getLogger(this::class.java)

    route("/actuator") {
        get("/health") {
            val collections = mongoDatabase.listCollectionNames().awaitFirstOrNullWithExceptionHandling() {
                MongoDBException("MONGO_DB_FAILURE", "Mongo health check failed")
            }
            call.respondText("OK")
        }
    }
}