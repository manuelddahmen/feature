package one.empty3.feature.gui;

import javax.swing.*;
import net.miginfocom.swing.*;

import java.io.File;

public class ExploreResults extends JFrame {
    File directory;

    public ExploreResults(File directory) {
        this.directory = directory;
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        scrollPane1 = new JScrollPane();
        tree1 = new JTree();
        panel1 = new JPanel();
        button4 = new JButton();
        button5 = new JButton();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill,hidemode 3",
            // columns
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(tree1);
        }
        contentPane.add(scrollPane1, "cell 0 0");

        //======== panel1 ========
        {
            panel1.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));
        }
        contentPane.add(panel1, "cell 1 0");

        //---- button4 ----
        button4.setText("Single");
        contentPane.add(button4, "cell 0 1");

        //---- button5 ----
        button5.setText("Over");
        contentPane.add(button5, "cell 0 1");

        //---- button1 ----
        button1.setText("<<<");
        contentPane.add(button1, "cell 1 1");

        //---- button2 ----
        button2.setText("^^^");
        contentPane.add(button2, "cell 1 1");

        //---- button3 ----
        button3.setText(">>>");
        contentPane.add(button3, "cell 1 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

        tree1.setModel(new FileSystemViewModel(directory));

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JScrollPane scrollPane1;
    private JTree tree1;
    private JPanel panel1;
    private JButton button4;
    private JButton button5;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static void main(String [] args) {
        ExploreResults results = new ExploreResults(new File("."));
        results.setVisible(true);
    }
}
