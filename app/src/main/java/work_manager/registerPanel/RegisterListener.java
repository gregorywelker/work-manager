package work_manager.registerPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import work_manager.user.User;

/**
 * This class's overridden actionPerformed method calls the User class which
 * performs an account creation
 */
class RegisterListener implements ActionListener {

    private final RegisterPanel registerPanel;

    RegisterListener(RegisterPanel registerPanel) {
        this.registerPanel = registerPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (User.registerUser(registerPanel.getUserNameField().getText(), registerPanel.getUserIDField().getText(),
                new String(registerPanel.getPasswordField().getPassword()),
                new String(registerPanel.getPasswordRepField().getPassword()))) {
            JOptionPane.showMessageDialog(null, "Sikeres regisztráció", null, JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Hibás adatok, a regisztráció nem sikerült.\nLehet, hogy a felhasználó név használt, vagy a jelszavak nem egyeznek.\nA jelszó minimum 3 karaktert kell, hogy tartalmazzon.",
                    null, JOptionPane.WARNING_MESSAGE);
        }
    }
}