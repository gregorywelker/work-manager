package work_manager.loginPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class's overridden actionPerformed method calls the mainFrame to display
 * the register panel
 */
class RegisterPanelListener implements ActionListener {

    private final LoginPanel loginPanel;

    RegisterPanelListener(LoginPanel loginPanel) {
        this.loginPanel = loginPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        loginPanel.getMainFrame().showRegisterPanel();
    }
}