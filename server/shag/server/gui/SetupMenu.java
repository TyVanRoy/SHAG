package shag.server.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SetupMenu extends JPanel implements ActionListener {
	private static final long serialVersionUID = 6688018889596848080L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final Dimension DIMENSIONS = new Dimension(WIDTH, HEIGHT);
	private Font titleFont = new Font("Times New Roman", Font.BOLD, 64);
	private Font mainFont = new Font("Times New Roman", Font.PLAIN, 32);
	private Font noteFont = new Font("Times New Roman", Font.PLAIN, 12);
	private String[] playerSelection = { "2", "4", "6", "8" };
	private JComboBox<String> playerField;
	private JTextField portField;
	private JButton newServerButton;
	private String note1;
	private String note2;
	private String note3;
	MasterServerWindow masterServerWindow;

	public SetupMenu(MasterServerWindow masterServerWindow) {
		this.masterServerWindow = masterServerWindow;
		writeNotes();
		format();
		requestFocus();
	}

	public void writeNotes() {
		note1 = "Note: If you want to make a LAN server, choose a random, four-digit port and make sure that the players connect to it.";
		note2 = "  Also make sure that the players connect to your local IP Address.";
		note3 = "  If you don't know how to find your local IP, google it.";
	}

	public void format() {
		setPreferredSize(DIMENSIONS);
		setMinimumSize(DIMENSIONS);
		setMaximumSize(DIMENSIONS);
		setLayout(null);
		portField = new JTextField();
		portField.setBounds((WIDTH / 2) - 115, (HEIGHT / 2) - 71, 200, 25);
		add(portField);
		playerField = new JComboBox<String>(playerSelection);
		playerField.setBounds((WIDTH / 2) - 80, (HEIGHT / 2), 200, 25);
		add(playerField);
		newServerButton = new JButton("New Server");
		newServerButton
				.setBounds((WIDTH / 2) - 199, (HEIGHT / 2) + 60, 398, 40);
		newServerButton.addActionListener(this);
		portField.addActionListener(this);
		add(newServerButton);
	}

	public void paintComponent(Graphics g) {
		Graphics2D gd = (Graphics2D) g;
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gd.setColor(Color.WHITE);
		gd.fillRect((WIDTH / 2) - 200, (HEIGHT / 2) - 100, 400, 200);
		gd.setColor(Color.BLACK);
		gd.drawRect((WIDTH / 2) - 200, (HEIGHT / 2) - 100, 400, 200);
		gd.setFont(mainFont);
		gd.drawString("Port:", (WIDTH / 2) - 180, (HEIGHT / 2) - 50);
		gd.drawString("Players:", (WIDTH / 2) - 180, (HEIGHT / 2) + 50 - 31);
		gd.setFont(titleFont);
		gd.drawString("SHAG SERVER SETUP", 50, 100);
		gd.drawLine(48, 102, 700, 102);
		gd.drawLine(48, 102, 48, 65);
		gd.setFont(noteFont);
		gd.drawString(note1, 20, HEIGHT - 150);
		gd.drawString(note2, 45, HEIGHT - 135);
		gd.drawString(note3, 45, HEIGHT - 120);
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public Dimension getDimensions() {
		return DIMENSIONS;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newServerButton || e.getSource() == portField) {
			try {
				initiateNewServer(fromString(portField.getText()),
						fromString((String) playerField.getSelectedItem()));
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(null,
						"Hey, Asshole; try numbers.", "Error",
						JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
			}
		}
	}

	public void initiateNewServer(int portNumber, int players) {
		masterServerWindow.initiateNewServer(portNumber, players);
	}

	public int fromString(String string) {
		return Integer.parseInt(string);
	}

	public String fromInt(int i) {
		return String.valueOf(i);
	}
}
