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
			//  接收文字資料
			//------------------------------			
			try {
				//連線
				ServerSocket ss = new ServerSocket(tcpserver.port);
				Socket sc = ss.accept();
				InputStreamReader isr = new InputStreamReader(sc.getInputStream(), "GBK");
				BufferedReader br = new BufferedReader(isr);
				
				//刷新來源IP
				st = "" + sc.getInetAddress();
				st = "" + st.substring(1);
				tcpserver.input1.setText(st);
				tcpserver.input2.setText("10000");
				
				//接收並刷新第一列
				st = br.readLine();
				JLabel t2 = new JLabel("Client>>  " + st);
				tcpserver.p2.add(t2);

				//接收並刷新剩餘列
				while ((st = br.readLine()) != null) {
					JLabel t3 = new JLabel("                " + st);
					tcpserver.p2.add(t3);
				}
				tcpserver.p2.repaint();
				tcpserver.p2.revalidate();

				sc.close();
				ss.close();
				
				//拖曳卷軸至最下
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
