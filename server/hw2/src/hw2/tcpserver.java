package hw2;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;

public class tcpserver {

	public static int port = 10000;

	public static JFrame f;
	public static Container cp;
	public static JPanel p2 = new JPanel();
	public static JScrollPane s1 = new JScrollPane(p2);
	public static JTextField input1 = new JTextField(20);
	public static JTextField input2 = new JTextField(5);

	public static void main(String[] args) {
		try {
			//------------------------------
			//  �Ы�Thread
			//------------------------------
			serverThread thread1 = new serverThread();
			thread1.start();
			serverPictureThread thread2 = new serverPictureThread();
			thread2.start();

			//------------------------------
			//  ��������
			//------------------------------
			f = new JFrame("��ѫ�");
			cp = f.getContentPane();

			JPanel p1 = new JPanel();
			p1.setLayout(new GridLayout(2, 1));
			JLabel t1 = new JLabel("Server");
			p1.add(t1);

			JPanel p1_1 = new JPanel();
			p1_1.setLayout(new GridLayout(1, 4));
			JLabel t2 = new JLabel("Client IP:");
			p1_1.add(t2);
			p1_1.add(input1);

			JLabel t3 = new JLabel("Client Port:");
			p1_1.add(t3);
			p1_1.add(input2);
			p1.add(p1_1);
			cp.add(p1, BorderLayout.NORTH);

			p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));			
			cp.add(s1, BorderLayout.CENTER);

			JPanel p3 = new JPanel();
			p3.setLayout(new GridLayout(2, 1));
			JTextArea ta1 = new JTextArea(2, 30);
			JScrollPane s2 = new JScrollPane(ta1);
			p3.add(s2);

			JPanel p3_2 = new JPanel();
			p3_2.setLayout(new GridLayout(1, 3));
			JButton btn1 = new JButton("�Ϥ�");
			JButton btn2 = new JButton("�ǰe");
			JButton btn3 = new JButton("����");
			btn1.setSize(10, 20);
			p3_2.add(btn1);
			p3_2.add(btn2);
			p3_2.add(btn3);

			p3.add(p3_2);
			cp.add(p3, BorderLayout.SOUTH);

			f.setSize(400, 450);
			f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			f.setVisible(true);

			//------------------------------
			//  ���s(�Ϥ�)
			//------------------------------
			btn1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e3) {
					
					//�ɮ׿�ܱ�
					JFrame frame = new JFrame();
					JFileChooser chooser = new JFileChooser();
					int flag = chooser.showOpenDialog(frame);

					//���\���
					if (flag == JFileChooser.APPROVE_OPTION) {
						System.out.println("�ϥΪ̿�ܤF�ɮסG" + chooser.getSelectedFile().getAbsolutePath());
						ImageIcon imageIcon = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
						
						//��Server�ݨ�s
						JLabel t1 = new JLabel("Client>>");
						p2.add(t1);
						JLabel t2 = new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(-100, 180, java.awt.Image.SCALE_SMOOTH)));
						p2.add(t2);
						p2.repaint();
						p2.revalidate();
						
						//�s�uClient�öǰe�Ϥ�
						try {
							Socket sc = new Socket(input1.getText(), Integer.valueOf(input2.getText()) + 1);
							OutputStream outputStream = sc.getOutputStream();
							BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
							Image image = imageIcon.getImage();
							BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),BufferedImage.TYPE_INT_RGB);
							Graphics graphics = bufferedImage.createGraphics();
							graphics.drawImage(image, 0, 0, null);
							graphics.dispose();
							ImageIO.write(bufferedImage, "png", bufferedOutputStream);
							bufferedOutputStream.close();
							sc.close();
						} catch (Exception E3) {
						}
					}
					
					//�즲���b�̤ܳU
				     SwingUtilities.invokeLater(() -> {
				            JScrollBar bar = s1.getVerticalScrollBar();
				            bar.setValue(bar.getMaximum());
				     });
				}
			});

			//------------------------------
			//  ���s(�ǰe)
			//------------------------------
			btn2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e3) {
					String st = ta1.getText();
					
					//�s�u��client�öǰe
					try {
						Socket ss = new Socket(input1.getText(), Integer.valueOf(input2.getText()));
						OutputStream outputStream = ss.getOutputStream();
						PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, "GBK"), true);
						out.println(st);
						out.flush();
						ss.close();
					} catch (Exception E3) {
					}

					//��server�ݨ�s
					String[] split = st.split("\n");
					JLabel t2 = new JLabel("Server>> " + split[0]);
					p2.add(t2);
					for (int i = 1; i < split.length; i++) {
						JLabel t3 = new JLabel("                " + split[i]);
						p2.add(t3);
					}
					p2.repaint();
					p2.revalidate();
					ta1.setText("");
					
					//�즲���b�̤ܳU
				     SwingUtilities.invokeLater(() -> {
				            JScrollBar bar = s1.getVerticalScrollBar();
				            bar.setValue(bar.getMaximum());
				     });
				}
			});

			//------------------------------
			//  ���s(����)
			//------------------------------
			btn3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e3) {
					f.dispose();
					System.exit(0);
				}
			});
		} catch (Exception E1) {
		}

	}

}
