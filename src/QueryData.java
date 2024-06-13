import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.types.ObjectId;

public class QueryData {
    public static String getRecords(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("records");
        FindIterable<Document> records = collection.find().sort(Sorts.descending("score"));

        StringBuilder result = new StringBuilder();
        for (Document record : records) {
            result.append(record.getObjectId("_id")).append("\n");
            result.append(record.getString("playerName")).append("\n");
            result.append(record.getInteger("score")).append("\n");
            result.append("\n");
        }

        return result.toString();
    }
}
