package com.pingsStudio.crypt;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import org.jasypt.util.text.BasicTextEncryptor;

public class SCSCoder extends JFrame {
	private static final long serialVersionUID = 3454260281778356823L;
	
	private JPanel contentPane;
	private BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SCSCoder frame = new SCSCoder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SCSCoder() {
		setResizable(false);
		textEncryptor.setPasswordCharArray("some-random-data".toCharArray());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		URL iconURL = getClass().getResource("/com/pingsStudio/res/lock-128.png");
		ImageIcon icon = new ImageIcon(iconURL);
		this.setIconImage(icon.getImage());
		
		JTextPane textPaneUp = new JTextPane();
		textPaneUp.setToolTipText("Seperate by newline or comma. will auto trim lead and tail space/empty char!");
		JScrollPane jspUp = new JScrollPane(textPaneUp);
		jspUp.setBounds(2, 25, 468, 86);
		contentPane.add(jspUp);
		
		JTextPane textPaneDown = new JTextPane();
		textPaneDown.setEditable(false);
		JScrollPane jspDown = new JScrollPane(textPaneDown);
		jspDown.setBounds(2, 138, 468, 148);
		contentPane.add(jspDown);
		
		JButton btnEnc = new JButton("SCS Encrypt");
		btnEnc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tts = textPaneUp.getText();
				String finalStr="";
				
				String[] lstts;
				if(tts.matches("(?s).*[\r\n].*")) {
					lstts=tts.split("\n");
				}else {
					lstts=tts.split(",");
				}
				for(String p : lstts) {
					if(p!=null && p.trim()!=null && !p.trim().equalsIgnoreCase("")){
						try {
							finalStr+=",\""+p.trim()+"\" <==> \""+textEncryptor.encrypt(p.trim())+"\"";
						}catch(Exception ex) {
							finalStr+=",\""+p.trim()+"\" <==> \"Fail with Error:"+ex.getMessage()+"\"";
						}
					}
				}
				finalStr=finalStr.substring(1);
				finalStr=finalStr.replaceAll(",", "\n");
				
				textPaneDown.setText(finalStr);
			}
		});
		btnEnc.setBounds(73, 112, 130, 23);
		contentPane.add(btnEnc);
		
		JButton btnDec = new JButton("SCS Decrypt");
		btnDec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tts = textPaneUp.getText();
				String finalStr="";
				
				String[] lstts;
				if(tts.matches("(?s).*[\r\n].*")) {
					lstts=tts.split("\n");
				}else {
					lstts=tts.split(",");
				}
				
				for(String p : lstts) {
					if(p!=null && p.trim()!=null && !p.trim().equalsIgnoreCase("")){
						try {
							finalStr+=",\""+p.trim()+"\" <==> \""+textEncryptor.decrypt(p.trim())+"\"";
						}catch(Exception ex) {
							finalStr+=",\""+p.trim()+"\" <==> \"Fail with Error:"+ex.getMessage()+"\"";
						}
					}
				}
				finalStr=finalStr.substring(1);
				finalStr=finalStr.replaceAll(",", "\n");
				
				textPaneDown.setText(finalStr);
			}
		});
		btnDec.setBounds(227, 112, 130, 23);
		contentPane.add(btnDec);
		
		JLabel lblTextToEncrypt = new JLabel("Text to Encrypt or Decrypt:");
		lblTextToEncrypt.setBounds(2, 11, 430, 14);
		contentPane.add(lblTextToEncrypt);
	}
}
