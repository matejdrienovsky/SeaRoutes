package Database;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.LoggerFactory;
import OwnException.*;

/**
 * DatabaseFacade class
 * This class is used to connect to the database and get the collection
 */
public class DatabaseFacade {
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    static Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    static {
        root.setLevel(Level.ERROR);
    }

    /**
     * connect method to connect to the database
     * @param uri
     * @param databaseName
     * @param collectionName
     */
    public void connect(String uri, String databaseName, String collectionName) {
        try{
            client = MongoClients.create(uri);
            database = client.getDatabase(databaseName);
            collection = database.getCollection(collectionName);
        } catch (Exception e) {
            throw new OwnException("Error connecting to database");
        }
    }

    /**
     * find method to find all the documents in the collection
     * @return
     */
    public FindIterable<Document> find() {
        return collection.find();
    }

    /**
     * getCollection method to get the collection
     * @return
     */
    public MongoCollection<Document> getCollection() {
        return collection;
    }
}
