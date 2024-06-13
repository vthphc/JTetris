import com.mongodb.client.MongoClient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LeaderBoardTable {
    public static JTable leaderBoardTable = new JTable();

    public static void displayLeaderBoard() {
        String records = "";
        try (MongoClient mongoClient = MongoDBConnector.getConnection()) {
            records = QueryData.getRecords(mongoClient.getDatabase("javaTetris"));
            DefaultTableModel model = new DefaultTableModel();
            leaderBoardTable.setModel(model);

            model.addColumn("ID");
            model.addColumn("Player's Name");
            model.addColumn("Score");

            for (String record : records.split("\n\n")) {
                String[] recordFields = record.split("\n");
                model.addRow(recordFields);
            }

        }
    }
}
