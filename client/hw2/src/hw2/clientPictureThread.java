package hw2;

import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.*;
import java.awt.image.BufferedImage;

public class clientPictureThread extends Thread {

	public void run() {
		//---------------------------------
		//    接收圖片資料
		//---------------------------------
		while (true) {
			System.out.print("clientPictureThread start\n");
			try {
				//連線
				ServerSocket ss = new ServerSocket(tcpclient.port + 1);
				Socket sc = ss.accept();

				//接收圖片
				InputStream in = sc.getInputStream();
				BufferedInputStream bi = new BufferedInputStream(in);
				BufferedImage bim = ImageIO.read(bi);
				bi.close();

				//刷新圖片
				JLabel t3 = new JLabel("Server>> ");
				tcpclient.p2.add(t3);
				JLabel t2 = new JLabel(new ImageIcon(new ImageIcon(bim).getImage().getScaledInstance(-100, 200, java.awt.Image.SCALE_SMOOTH)));
				tcpclient.p2.add(t2);
				tcpclient.p2.repaint();
				tcpclient.p2.revalidate();

				ss.close();
				sc.close();
				
				//拖曳卷軸至最下
				SwingUtilities.invokeLater(() -> {
			        JScrollBar bar = tcpclient.s1.getVerticalScrollBar();
			        bar.setValue(bar.getMaximum());
				});
			} catch (Exception E2) {
				System.out.print("clientPictureThread error");
				System.out.print(E2 + "\n");
				break;
			}
		}
	}
}
