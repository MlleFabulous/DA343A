package laboration11;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ReadPictureFromServer {

	public static void main(String[] args) throws MalformedURLException {
		
		ImageIcon icon = new ImageIcon(new URL("http://ddwap.mah.se/af5204/Panda.jpg"));
		JOptionPane.showMessageDialog(null, icon);
	}

}
