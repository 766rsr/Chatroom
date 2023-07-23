package hw2;

import java.net.*;
import java.io.*;
import javax.swing.*;

public class clientThread extends Thread {

	public void run() {
		//---------------------------------
		//    ������r���
		//---------------------------------
		while (true) {
			System.out.print("clientThread start\n");
			String st = "";
			try {
				//�s�u
				ServerSocket ss = new ServerSocket(tcpclient.port);
				Socket sc = ss.accept();
				InputStreamReader isr = new InputStreamReader(sc.getInputStream(), "GBK");
				BufferedReader br = new BufferedReader(isr);

				//�����è�s�Ĥ@�C
				st = br.readLine();
				JLabel t2 = new JLabel("Server>> " + st);
				tcpclient.p2.add(t2);

				//�����è�s�Ѿl�C
				while ((st = br.readLine()) != null) {
					JLabel t3 = new JLabel("                  " + st);
					tcpclient.p2.add(t3);
				}
				tcpclient.p2.repaint();
				tcpclient.p2.revalidate();

				sc.close();
				ss.close();
				
				//�즲���b�̤ܳU
				SwingUtilities.invokeLater(() -> {
			        JScrollBar bar = tcpclient.s1.getVerticalScrollBar();
			        bar.setValue(bar.getMaximum());
				});
			} catch (Exception E1) {
				System.out.print("clientThread error");
				System.out.print(E1 + "\n");
				break;
			}
		}
	}
}
