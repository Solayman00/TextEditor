
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;

public class TexteEditor extends JFrame implements ActionListener {

	JFrame frame;
	Label label, label1, fontSize, fontColor, fontType;
	JTextArea textarea;
	JScrollPane scrollPane;
	JSpinner fontSizeSpinner;
	JButton fontColorBtn, importBtn, exportBtn, exitBtn;
	JComboBox fontBox;
	
	
	Font titleFont = new Font("Ink Free",Font.BOLD,22);
	Font title1Font = new Font("Arial",Font.BOLD,19);
	Font labelFont = new Font("Ink Free",Font.PLAIN,18);
	Font btnFont = new Font("Ink Free",Font.PLAIN,16);
	String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	
	TexteEditor() {
		frame = new JFrame("Text Editor By Elden");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLayout(null);
		//frame.getContentPane().setBackground(Color.white);
		frame.setLocationRelativeTo(null);
		
		label = new Label("Simple Text Editor");
		label.setBounds(280, 20, 300, 30);
		label.setFont(titleFont);
		frame.add(label);
		
		label1 = new Label("Edit your Text here");
		label1.setBounds(15, 100, 180, 30);
		label1.setFont(title1Font);
		frame.add(label1);
		
		fontSize = new Label("Size ");
		fontSize.setBounds(20, 150, 60, 30);
		fontSize.setFont(labelFont);
		frame.add(fontSize);
		
		fontSizeSpinner = new JSpinner();
		fontSizeSpinner.setBounds(100, 150, 70, 30);
		fontSizeSpinner.setValue(20);
		fontSizeSpinner.addChangeListener( new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				textarea.setFont(new Font(textarea.getFont().getFamily(),Font.PLAIN,(int) fontSizeSpinner.getValue())); 
				
			}
			
		});
		frame.add(fontSizeSpinner);
		
		fontColor = new Label("Color ");
		fontColor.setBounds(20, 200, 60, 30);
		fontColor.setFont(labelFont);
		frame.add(fontColor);
		
		fontColorBtn = new JButton("Color");
		fontColorBtn.setBounds(100, 200, 70, 30);
		fontColorBtn.setBackground(Color.white);
		fontColorBtn.addActionListener(this);
		
		frame.add(fontColorBtn);
		
		
		
		textarea = new JTextArea();
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		textarea.setFont(new Font("Arial", Font.PLAIN,20));
		
		scrollPane = new JScrollPane(textarea);
		scrollPane.setBounds(200, 70, 570, 470);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	
		
		frame.add(scrollPane);
		
		fontType = new Label("Text Type ");
		fontType.setBounds(20, 250, 100, 30);
		fontType.setFont(labelFont);
		frame.add(fontType);
		
		
		fontBox = new JComboBox(fonts);
		fontBox.setBounds(25, 290, 150, 30);
		fontBox.setBackground(Color.white);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Arial");
		
		frame.add(fontBox);
		
		importBtn = new JButton("Import a Text");
		importBtn.setBounds(20, 400, 160, 40);
		importBtn.setBackground(Color.white);
		importBtn.setFocusable(false);
		importBtn.setFont(btnFont);
		importBtn.addActionListener(this);
		frame.add(importBtn);
		
		exportBtn = new JButton("Export a Text");
		exportBtn.setBounds(20, 450, 160, 40);
		exportBtn.setBackground(Color.white);
		exportBtn.setFocusable(false);
		exportBtn.setFont(btnFont);
		exportBtn.addActionListener(this);
		frame.add(exportBtn);
		
		exitBtn = new JButton("Exit");
		exitBtn.setBounds(20, 500, 160, 40);
		exitBtn.setBackground(Color.white);
		exitBtn.setFocusable(false);
		exitBtn.setFont(btnFont);
		exitBtn.addActionListener(this);
		frame.add(exitBtn);
		
		
		
		frame.setVisible(true);
		
	}
	public static void main(String[] args) {
		
		new TexteEditor();

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == fontColorBtn) {
			JColorChooser coloChooser = new JColorChooser();
			
			Color color = coloChooser.showDialog(null, "Choose a Color ", Color.black);
			
			textarea.setForeground(color);
			
			
		}
		
		if(e.getSource() == fontBox) {
			textarea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textarea.getFont().getSize()));
		}
		
		if(e.getSource() == importBtn) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
			fileChooser.setFileFilter(filter);
			
			int response = fileChooser.showOpenDialog(null);
			
			if (response == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				
				try {
					fileIn = new Scanner(file);
					if (file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine() + "\n";
							textarea.append(line);
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					fileIn.close();
				}
			}
			
		}
		
        if(e.getSource() == exportBtn) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			int response = fileChooser.showSaveDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				File file;
				PrintWriter fileOut = null;
				
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(textarea.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					fileOut.close();
				}
			}
		}

        if(e.getSource() == exitBtn) {
	         System.exit(0);
        }
		
	}

}
