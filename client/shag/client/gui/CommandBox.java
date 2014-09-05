package shag.client.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;

public class CommandBox extends InputField {
	private static final long serialVersionUID = -5887673284740772236L;

	public CommandBox(MasterWindow window) {
		super("Enter manual command...", 12f, window);
		setBorder(border);
	}

	public void focusLost(FocusEvent e) {
		setForeground(Color.WHITE);
	}

	public void actionPerformed(ActionEvent e) {
		window.restoreFocus();
		window.enterCommand(getText());
	}
}
