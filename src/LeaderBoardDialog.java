import javax.swing.*;

public class LeaderBoardDialog {
    public static void showLeaderBoard(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Leader Board", true);
        dialog.setSize(600, 600);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));

        LeaderBoardTable.displayLeaderBoard();
        JScrollPane scrollPane = new JScrollPane(LeaderBoardTable.leaderBoardTable);
        dialog.add(scrollPane);

        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
}
