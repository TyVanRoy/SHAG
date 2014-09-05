package shag.client.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;

public class MessageField extends InputField {
	private static final long serialVersionUID = 7514770647658518181L;

	public MessageField(MasterWindow window) {
		super("", 12f, window);
		setBorder(null);
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(!getText().equals("")){
			window.sendMessage("000" + getText());
			setText("");
		}
		window.restoreFocus();
	}
	
	public void focusGained(FocusEvent e) {
		selectAll();
	}

	public void focusLost(FocusEvent e) {
		setBorder(null);
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
	}
}
