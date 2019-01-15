package com.pingsStudio.crypt;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.springframework.security.crypto.keygen.KeyGenerators;

public class CryptorUI {
	private JFrame frmCryptator;
	private JTextField txtRaw;
	private JTextField txtSalt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CryptorUI window = new CryptorUI();
					window.frmCryptator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CryptorUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		URL iconURL = getClass().getResource("/com/pingsStudio/res/lock-128.png");
		ImageIcon icon = new ImageIcon(iconURL);
		
		frmCryptator = new JFrame();
		frmCryptator.setTitle("Cryptorgator");
		frmCryptator.setResizable(false);
		frmCryptator.setBounds(100, 100, 480, 310);
		frmCryptator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCryptator.getContentPane().setLayout(null);
		
		frmCryptator.setIconImage(icon.getImage());
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Raw Text", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(4, 12, 466, 43);
		frmCryptator.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		txtRaw = new JTextField();
		txtRaw.setBounds(6, 16, 454, 20);
		panel_1.add(txtRaw);
		txtRaw.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Hashed", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(4, 204, 466, 66);
		frmCryptator.getContentPane().add(panel);
		panel.setLayout(null);
		
		JTextArea txtHash = new JTextArea();
		txtHash.setBounds(6, 16, 454, 42);
		panel.add(txtHash);
		txtHash.setLineWrap(true);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "BCrypt", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(4, 55, 195, 124);
		frmCryptator.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnRawToBcrypt = new JButton("Encode");
		btnRawToBcrypt.setBounds(25, 66, 160, 23);
		panel_3.add(btnRawToBcrypt);
		
		JButton btnBcryptCheck = new JButton("Verify");
		btnBcryptCheck.setBounds(25, 90, 160, 23);
		panel_3.add(btnBcryptCheck);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "SHA1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(201, 55, 269, 124);
		frmCryptator.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnRawToSha = new JButton("Encode");
		btnRawToSha.setBounds(99, 66, 160, 23);
		panel_2.add(btnRawToSha);
		
		JButton btnShaCheck = new JButton("Verify");
		btnShaCheck.setBounds(99, 90, 160, 23);
		panel_2.add(btnShaCheck);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(10, 22, 259, 43);
		panel_2.add(panel_4);
		panel_4.setBorder(new TitledBorder(null, "Salt", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel_4.setLayout(null);
		
		txtSalt = new JTextField();
		txtSalt.setBounds(7, 16, 245, 20);
		panel_4.add(txtSalt);
		txtSalt.setColumns(10);
		
		JButton btnSalty = new JButton("Salty");
		btnSalty.setBounds(10, 66, 89, 23);
		panel_2.add(btnSalty);
		
		JButton btnSaltyMore = new JButton("Salty+");
		btnSaltyMore.setBounds(10, 90, 89, 23);
		panel_2.add(btnSaltyMore);
		
		JLabel lblInfo = new JLabel("");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setBounds(4, 179, 450, 14);
		frmCryptator.getContentPane().add(lblInfo);
		
		//Logic here...
		btnSalty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String t16=KeyGenerators.string().generateKey();
				txtSalt.setText(t16);
			}
		});
		btnSaltyMore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String t32=KeyGenerators.string().generateKey()+KeyGenerators.string().generateKey();
				txtSalt.setText(t32);
			}
		});
		
		btnRawToSha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(	txtRaw.getText()!=null && txtRaw.getText().trim().length()>0 &&
					txtSalt.getText()!=null && txtSalt.getText().trim().length()>0
				) {
					String hash = Cryptor.encodeToSHA(txtRaw.getText(), txtSalt.getText());
					txtHash.setText(hash);
				}
			}
		});
		
		btnShaCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(	txtRaw.getText()!=null && txtRaw.getText().trim().length()>0 &&
					txtSalt.getText()!=null && txtSalt.getText().trim().length()>0 &&
					txtHash.getText()!=null && txtHash.getText().trim().length()>0
				) {
					Boolean b = Cryptor.checkSHAPass(txtRaw.getText(), txtHash.getText(), txtSalt.getText());
					if(b) {
						lblInfo.setForeground(Color.GREEN);
						lblInfo.setText("Raw is MATCH with SHA");
					}else {
						lblInfo.setForeground(Color.RED);
						lblInfo.setText("Raw is UNMATCH with SHA");
					}
				}
			}
		});
		
		btnRawToBcrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtRaw.getText()!=null && txtRaw.getText().trim().length()>0) {
					String hash = Cryptor.encodeToBCrypt(txtRaw.getText());
					txtHash.setText(hash);
				}
			}
		});
		
		btnBcryptCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(	txtRaw.getText()!=null && txtRaw.getText().trim().length()>0 &&
					txtHash.getText()!=null && txtHash.getText().trim().length()>0
				) {
					Boolean b = Cryptor.checkBCryptPass(txtRaw.getText(), txtHash.getText());
					if(b) {
						lblInfo.setForeground(Color.GREEN);
						lblInfo.setText("Raw is MATCH with BCrypt");
					}else {
						lblInfo.setForeground(Color.RED);
						lblInfo.setText("Raw is UNMATCH with BCrypt");
					}
				}
			}
		});
		
		final JPopupMenu contextMenu = new JPopupMenu("cxMenu");
		JMenuItem ctxAbout = new JMenuItem("About");
		ctxAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmCryptator,"Ver 1.0.180828\n-= by KL =-","About",JOptionPane.PLAIN_MESSAGE);
			}
		});
		contextMenu.add(ctxAbout);
		frmCryptator.add(contextMenu);
		
		frmCryptator.addMouseListener(new MouseAdapter() {
			@Override
		     public void mouseClicked(MouseEvent e) {
		        if(e.getButton()==MouseEvent.BUTTON3) {//When is right-click
		        	contextMenu.show(frmCryptator , e.getX(), e.getY());  
		        }
		     }
		});
		
	}
}
