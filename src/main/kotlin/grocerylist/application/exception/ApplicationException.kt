package grocerylist.application.exception

data class ErrorResponse(val errorCode: String, val errorMessage: String)

class GroceryListNotFoundException(val errorCode: String, val errorMessage: String) : Exception(errorMessage) {}

class MongoDBException(val errorCode: String, val errorMessage: String) : Exception(errorMessage) {}