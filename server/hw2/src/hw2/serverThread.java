package hw2;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;

public class serverThread extends Thread {

	public void run() {
		while (true) {
			System.out.print("serverThread start\n");
			String st = "";
			
			//------------------------------
			//  ������r���
			//------------------------------			
			try {
				//�s�u
				ServerSocket ss = new ServerSocket(tcpserver.port);
				Socket sc = ss.accept();
				InputStreamReader isr = new InputStreamReader(sc.getInputStream(), "GBK");
				BufferedReader br = new BufferedReader(isr);
				
				//��s�ӷ�IP
				st = "" + sc.getInetAddress();
				st = "" + st.substring(1);
				tcpserver.input1.setText(st);
				tcpserver.input2.setText("10000");
				
				//�����è�s�Ĥ@�C
				st = br.readLine();
				JLabel t2 = new JLabel("Client>>  " + st);
				tcpserver.p2.add(t2);

				//�����è�s�Ѿl�C
				while ((st = br.readLine()) != null) {
					JLabel t3 = new JLabel("                " + st);
					tcpserver.p2.add(t3);
				}
				tcpserver.p2.repaint();
				tcpserver.p2.revalidate();

				sc.close();
				ss.close();
				
				//�즲���b�̤ܳU
			     SwingUtilities.invokeLater(() -> {
			            JScrollBar bar = tcpserver.s1.getVerticalScrollBar();
			            bar.setValue(bar.getMaximum());
			     });
			} catch (Exception E1) {
				System.out.print("serverThread error");
				System.out.print(E1 + "\n");
				break;
			}
		}

	}

}
