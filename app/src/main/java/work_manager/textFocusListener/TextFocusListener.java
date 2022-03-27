package work_manager.textFocusListener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.text.JTextComponent;

/**
 * This is a class for input text fields, making it easier to use text fields.
 * It lets the program display the type of information that the user needs to
 * write into the text field, after the user clicks on the field, it gets empty
 * and the user can easily write without having to delete the content. If the
 * text field stays empty or all the characters get deleted then the original
 * text comes up prompting again what information the given field is for
 */
public class TextFocusListener implements FocusListener {
    private final JTextComponent textField;
    private final String baseText;
    private boolean dirty;

    @Override
    public void focusGained(FocusEvent e) {
        if (textField.getText().equals(baseText) && !dirty) {
            textField.setText("");
        }
        dirty = true;
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (textField.getText().length() <= 0) {
            textField.setText(baseText);
            dirty = false;
        }
    }

    public TextFocusListener(JTextComponent textField) {
        this.textField = textField;
        baseText = textField.getText();
        dirty = false;
    }

}
