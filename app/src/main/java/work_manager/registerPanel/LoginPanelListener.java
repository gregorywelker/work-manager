package work_manager.registerPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class's overridden actionPerformed method calls the mainFrame to display
 * the login panel
 */
class LoginPanelListener implements ActionListener {

	private final RegisterPanel registerPanel;

	LoginPanelListener(RegisterPanel registerPanel) {
		this.registerPanel = registerPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.registerPanel.getMainFrame().showLoginPanel();
	}
}