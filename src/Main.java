import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Random;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        // Create the base frame
        JFrame frame = new JFrame("Password & UUID Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.getContentPane().setBackground(Color.decode("#f0f0f0"));
        frame.setLayout(null);

        // Add a header text for password length
        JLabel lengthLabel = new JLabel("Length");
        lengthLabel.setBounds(350, 20, 100, 20);
        frame.add(lengthLabel);

        // Add the field for password length
        JTextField sizeField = new JTextField("16");
        sizeField.setBounds(350, 50, 100, 30);
        sizeField.setBorder(new LineBorder(Color.lightGray));
        frame.add(sizeField);

        // Add the actual password field
        JTextField passwordField = new JTextField();
        passwordField.setBounds(50, 110, 400, 30);
        passwordField.setBorder(new LineBorder(Color.lightGray));
        frame.add(passwordField);

        // Add label that informs the password was copied
        JLabel copyPasswordLabel = new JLabel();
        copyPasswordLabel.setBounds(50, 140, 400, 20);
        frame.add(copyPasswordLabel);

        // Add checkbox for password options
        JCheckBox includeSpecials = new JCheckBox("include #$%&");
        includeSpecials.setSelected(true);
        includeSpecials.setFont(new Font("Arial", Font.PLAIN, 12));
        includeSpecials.setBounds(50, 80, 150, 30);
        frame.add(includeSpecials);

        // Add button to generate and copy a password
        JButton passwordButton = new JButton("Generate password");
        passwordButton.setBounds(50, 50, 290, 30);
        passwordButton.setBackground(Color.decode("#ffffff"));
        passwordButton.setBorder(new LineBorder(Color.lightGray));
        passwordButton.addActionListener(e -> {
            int passwordLength = Integer.parseInt(sizeField.getText());
            String generatedPassword = generate(passwordLength, includeSpecials.isSelected());
            passwordField.setText(generatedPassword);
            copyPasswordLabel.setText("Password copied to clipboard.");
            copyToClipboard(generatedPassword);
        });
        frame.add(passwordButton);

        // Add field for the UUID
        JTextField uuidField = new JTextField();
        uuidField.setBounds(50, 260, 400, 30);
        uuidField.setBorder(new LineBorder(Color.lightGray));
        frame.add(uuidField);

        // Add a label that notifies the UUID has been copied to the clipboard
        JLabel copyUUIDLabel = new JLabel();
        copyUUIDLabel.setBounds(50, 290, 400, 20);
        frame.add(copyUUIDLabel);

        // Add a checkbox for shorter version of the UUID
        JCheckBox shortUUID = new JCheckBox("shorter version");
        shortUUID.setSelected(true);
        shortUUID.setBounds(50, 235, 400, 20);
        shortUUID.setFont(new Font("Arial", Font.PLAIN, 12));
        frame.add(shortUUID);

        // Add a field for name input
        JTextField nameField = new JTextField();
        nameField.setBounds(350, 200, 100, 30);
        nameField.setBorder(new LineBorder(Color.lightGray));
        frame.add(nameField);

        // Add a header text for name input
        JLabel nameLabel = new JLabel("Name (optional)");
        nameLabel.setBounds(350, 170, 100, 20);
        frame.add(nameLabel);

        // Add the button to generate and copy the UUID to the clipboard
        JButton UUIDButton = new JButton("Generate UUID");
        UUIDButton.setBounds(50, 200, 290, 30);
        UUIDButton.setBackground(Color.decode("#ffffff"));
        UUIDButton.setBorder(new LineBorder(Color.lightGray));
        UUIDButton.addActionListener(e -> {
            String name = nameField.getText();
            String generatedUUID = generateUUID(shortUUID.isSelected(), name);
            uuidField.setText(generatedUUID);
            copyUUIDLabel.setText("UUID copied to clipboard.");
            copyToClipboard(generatedUUID);
        });
        frame.add(UUIDButton);

        // Have the frame be visible
        frame.setVisible(true);
    }

    // Method to generate a random password String of specified length
    private static String generate(int length, boolean includeCharacters) {
        StringBuilder str = new StringBuilder(length);
        Random random = new Random();

        int[][] ranges = {{48, 57}, {65, 90}, {97, 122}};
        char[] specialChars = {'!', '@', '#', '$', '%', '^', '&', '*'};

        // adjust ranges if no characters should be included, characters being the fourth option
        int totalOptions = ranges.length + (includeCharacters ? 1 : 0);
        char c;

        while (str.length() < length) {
            int choice = random.nextInt(totalOptions);
            if (choice < ranges.length) {
                int[] selectedRange = ranges[choice];
                c = (char) (selectedRange[0] + random.nextInt(selectedRange[1] - selectedRange[0] + 1));
            } else {
                c = specialChars[random.nextInt(specialChars.length)];
            }
            str.append(c);
        }
        return str.toString();
    }

    // Method to copy string to clipboard
    private static void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    // Method to generate a UUID and convert to String
    private static String generateUUID(boolean shortVersion, String name) {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        if (shortVersion) {
            String[] parts = uuidString.split("-");
            uuidString = parts[1] + "-" + parts[2] + "-" + parts[3];
        }
        if (!name.isBlank()) {
            uuidString = name + "-" + uuidString;
        }
        return uuidString;
    }
}


