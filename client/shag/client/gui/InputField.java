package shag.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import shag.client.main.Shag;

public class InputField extends JTextField implements ActionListener,
		FocusListener {
	private static final long serialVersionUID = -8291457133005470295L;
	private String fonts = Shag.getFonts();
	private Font mainFont;
	private float fontSize;
	protected Border border;
	protected MasterWindow window;

	public InputField(String text, float fontSize, MasterWindow window) {
		super(text, JTextField.CENTER);
		this.fontSize = fontSize;
		this.window = window;
		setText(text);
		format();
	}

	public void format() {
		loadFont();
		border = new LineBorder(Color.WHITE, 1);
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		if (getText().trim().equals("")) {
			setBorder(border);
		} else {
			setBorder(null);
		}
		setFont(mainFont.deriveFont(fontSize));
		addActionListener(this);
		addFocusListener(this);
	}

	public void loadFont() {
		try {
			mainFont = Font.createFont(Font.TRUETYPE_FONT, new File(fonts
					+ "PressStart2P.ttf"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		window.restoreFocus();
	}

	public void focusGained(FocusEvent e) {
		setForeground(Color.RED);
		setBorder(border);
		selectAll();
	}

	public void focusLost(FocusEvent e) {
		setForeground(Color.WHITE);
		if (getText().trim().equals("")) {
			setBorder(border);
		} else {
			setBorder(null);
		}
	}
}
