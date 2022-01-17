package grocerylist.application.config


import grocerylist.application.service.GroceryListItemRepository
import grocerylist.application.service.GroceryListItemService
import grocerylist.application.service.GroceryListRepository
import grocerylist.application.service.GroceryListService
import grocerylist.persistence.mongo.GroceryListItemRepoMongoDBImpl
import grocerylist.persistence.mongo.GroceryListRepoMongoDBImpl
import grocerylist.persistence.mongo.MongoConnection
import org.koin.dsl.module

val groceryListModule = module {
    single {
        MongoConnection().mongoDatabase
    }
    single<GroceryListRepository> {
        GroceryListRepoMongoDBImpl(get())
    }
    single<GroceryListItemRepository> {
        GroceryListItemRepoMongoDBImpl(get())
    }

    single {
        GroceryListItemService(get(), get())
    }
    single {
        GroceryListService(get())
    }

}