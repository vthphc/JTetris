import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class GameOverDialog extends JDialog {
    private JTextField nameField;
    private JLabel scoreLabel;
    private JButton submitButton;
    private int score;

    public GameOverDialog(JFrame parent, int score) {
        super(parent, "Game Over", true);
        this.score = score;
        initUI();
    }

    private void initUI() {
        setLayout(new GridLayout(3, 1));

        scoreLabel = new JLabel("Your Score: " + score, SwingConstants.CENTER);
        add(scoreLabel);

        nameField = new JTextField("Enter your name");
        add(nameField);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = nameField.getText().trim();
                if (!playerName.isEmpty()) {
                    saveScoreToDatabase(playerName, score);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(GameOverDialog.this, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(submitButton);

        setSize(300, 200);
        setLocationRelativeTo(getParent());
    }

    private void saveScoreToDatabase(String playerName, int score) {
        try (MongoClient mongoClient = MongoDBConnector.getConnection()) {
            MongoDatabase database = mongoClient.getDatabase("javaTetris");
            MongoCollection<Document> collection = database.getCollection("records");

            Document record = new Document("playerName", playerName)
                    .append("score", score);
            collection.insertOne(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
