package work_manager.passwordFocusListener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;

/**
 * This is a class for input password fields, making it easier to use password
 * fields. It lets the program display the type of information that the user
 * needs to write into the password field, after the user clicks on the field,
 * it gets empty and the user can easily write without having to delete the
 * content and also the character display of the field changes to password type,
 * meaning instead of characters only * are displayed. If the password field
 * stays empty or all the characters get deleted then the original text comes up
 * prompting again what information the given field is for
 */
public class passwordFocusListener implements FocusListener {
    private final JPasswordField passwordField;
    private String baseText;

    @Override
    public void focusGained(FocusEvent e) {
        String actualText = new String(passwordField.getPassword());
        if (actualText.equals(baseText) && passwordField.getEchoChar() == (char) 0) {
            passwordField.setText("");
        }
        passwordField.setEchoChar('\u25CF');
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (passwordField.getPassword().length <= 0) {
            passwordField.setText(baseText);
            passwordField.setEchoChar((char) 0);
        }
    }

    public passwordFocusListener(JPasswordField passwordField) {
        this.passwordField = passwordField;
        passwordField.setEchoChar((char) 0);
        baseText = new String(passwordField.getPassword());
    }
}
