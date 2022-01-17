package grocerylist.persistence.mongo

import com.mongodb.AuthenticationMechanism
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoDatabase
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import java.util.concurrent.TimeUnit

class MongoConnection {

    val mongoDatabase: MongoDatabase = connectDB()

    private fun connectDB(): MongoDatabase {

        val pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build())
        val codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        return MongoClients.create(
            MongoClientSettings.builder()
                .applyToClusterSettings { builder ->
                    builder.hosts(listOf(ServerAddress("localhost", 27017)))
                }
                .credential(
                    MongoCredential.createCredential("root", "admin", "rootpassword".toCharArray())
                )
                .codecRegistry(codecRegistry)
                .applyToSocketSettings { builder ->
                    builder.connectTimeout(3, TimeUnit.SECONDS)
                    builder.readTimeout(3, TimeUnit.SECONDS)
                }
                .applyToConnectionPoolSettings { builder ->
                    builder.maxSize(16)
                    builder.maxWaitTime(3, TimeUnit.SECONDS)
                    builder.minSize(16)
                }
                .applyToClusterSettings { builder ->
                    builder.serverSelectionTimeout(3, TimeUnit.SECONDS)
                }
                .build()
        ).getDatabase("grocery")
    }

}