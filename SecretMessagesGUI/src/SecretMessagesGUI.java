import javax.swing.JFrame;


import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SecretMessagesGUI extends JFrame {
	private JTextField txtKey;
	private JTextArea txtIn;
	private JTextArea txtOut;
	private JSlider slider;
	private JButton btnCopy;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JLabel lblTxtCopy;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	
	public String encode(String message, int keyVal){
		String output = "";
		
		char key = (char)keyVal;				// Ключ пользователя для шифрования
		
		
		for(int i = 0; i <= message.length()-1; i++) {
			char input = message.charAt(i);
			
			if(input >= 'A' && input <= 'Z'){ // ПРОВЕРКА для заглавных букв
				input += key;
				
				if(input > 'Z')
					input -= 26;
				
				if(input < 'A')
					input += 26;				
			}
			
			if(input >= 'a' && input <= 'z'){ // ПРОВЕРКА для строчных букв
				input += key;
				
				if(input > 'z')
					input -= 26;
				
				if(input < 'a')
					input += 26;
				
			}
			
			if(input >= '0' && input <= '9') {
				input += (keyVal % 10);
				
				if(input > '9')
					input-=10;
				
				if(input < '0')
					input+=10;				
			}			
			output+=input;
		}
		
		return output;
	}
	
	
	public SecretMessagesGUI() {
		setResizable(false);
		getContentPane().setBackground(new Color(0, 255, 204));
		setTitle("Cryptographer by Redixan");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("cryptography.png")));
		
		JLabel lblTxtCopy = new JLabel("Copied");
		lblTxtCopy.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTxtCopy.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTxtCopy.setBounds(378, 206, 104, 18);
		getContentPane().add(lblTxtCopy);
		lblTxtCopy.setVisible(false);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 28, 540, 101);
		getContentPane().add(scrollPane);
		
		txtIn = new JTextArea();
		scrollPane.setViewportView(txtIn);
		txtIn.setWrapStyleWord(true);
		txtIn.setLineWrap(true);
		txtIn.setFont(new Font("Arial", Font.PLAIN, 16));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(24, 228, 540, 114);
		getContentPane().add(scrollPane_1);
		
		txtOut = new JTextArea();
		scrollPane_1.setViewportView(txtOut);
		txtOut.setWrapStyleWord(true);
		txtOut.setLineWrap(true);
		txtOut.setFont(new Font("Arial", Font.PLAIN, 16));
		
		txtKey = new JTextField();
		txtKey.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {					
					 slider.setValue(Integer.parseInt(txtKey.getText()));
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Please enter the KEY");
				}
			}
		});
		txtKey.setFont(new Font("RomanT", Font.BOLD, 14));
		txtKey.setHorizontalAlignment(SwingConstants.CENTER);
		txtKey.setBounds(239, 166, 83, 30);
		getContentPane().add(txtKey);
		txtKey.setColumns(10);
		
		JButton btn = new JButton("Encode/Decode");
		btn.setBackground(new Color(255, 153, 204));
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
					String message = txtIn.getText();
					int key = Integer.parseInt(txtKey.getText());
					String output = encode(message, key);
					txtOut.setText(output);
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Please enter the KEY");
					txtKey.requestFocus();
					txtKey.selectAll();
					lblTxtCopy.setVisible(false);
				}
			}
		});
		btn.setBounds(340, 172, 150, 23);
		getContentPane().add(btn);
		
		JLabel lblNewLabel = new JLabel("Key:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(168, 172, 59, 19);
		getContentPane().add(lblNewLabel);
		
	    slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txtKey.setText("" + slider.getValue());
				String message = txtIn.getText();
				int key = slider.getValue();
				String output = encode(message, key);
				txtOut.setText(output);
				lblTxtCopy.setVisible(false);
			}
		});
		slider.setToolTipText("");
		slider.setPaintTicks(true);
		slider.setValue(3);
		slider.setMinorTickSpacing(13);
		slider.setMinimum(-26);
		slider.setMaximum(26);
		slider.setPaintLabels(true);
		slider.setBackground(new Color(0, 255, 204));
		slider.setBounds(24, 163, 160, 38);
		getContentPane().add(slider);
		
	    btnCopy = new JButton("Copy!");
	    btnCopy.setBackground(new Color(204, 204, 255));
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = txtIn.getText();
				int key = slider.getValue();
				String output = encode(message, key);
				txtOut.setText(output);
				StringSelection stringSelection = new StringSelection(output);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
				lblTxtCopy.setVisible(true);
			}
		});
		btnCopy.setBounds(489, 201, 75, 23);
		getContentPane().add(btnCopy);
		
		lblNewLabel_1 = new JLabel("26");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(152, 208, 48, 14);
		getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("-26");
		lblNewLabel_2.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 208, 48, 14);
		getContentPane().add(lblNewLabel_2);
		

	}

	public static void main(String[] args) {
		SecretMessagesGUI theApp = new SecretMessagesGUI();
		theApp.setSize(new java.awt.Dimension(600,400));
		theApp.setVisible(true);
	}
}

