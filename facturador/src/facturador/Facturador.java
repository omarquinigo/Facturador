package facturador;

import javax.swing.UIManager;
import vista.JFrameSplashScreen;

public class Facturador {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrameSplashScreen ss = new JFrameSplashScreen();//declaro frameLogin
        ss.setVisible(true);//mostrar el frame
    }

}
