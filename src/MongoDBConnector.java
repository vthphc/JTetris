import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoDBConnector {
    private static final String CONNECTION_STRING = "mongodb+srv://thinphcv5:dSj5Z2sitHEySrCL@cluster0.u5rccyd.mongodb.net/javaTetris?retryWrites=true&w=majority&appName=Cluster0";
    public static MongoClient getConnection() {
        return MongoClients.create(CONNECTION_STRING);
    }
}
