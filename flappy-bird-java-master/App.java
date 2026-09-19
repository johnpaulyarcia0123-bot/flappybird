import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class App {

    public App() {
        int boardWidth = 360; // ngayon pre ito yung lapad ng game 360 lang para sakto
        int boardHeight = 640; // ito naman is para sa taas para makuha yung haba na 640

        // ngayon pre gagawa tayo ng window para dun lalabas yung flappy bird
        JFrame frame = new JFrame("Flappy Bird");
        // DITO DATI MAY setLocationRelativeTo(null) KAYA NASA BABA YUNG GAME PAG BUKAS
        // TANGGALIN NATIN DITO PRE KASI MAG PAPACK PA TAYO SA BABA LALAKI PA FRAME
        frame.setResizable(false); // ito naman pre para di ma resize kasi pag na resize masisira yung game
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ito pre para pag pinindot yung X close talaga lahat
        frame.setLayout(new BorderLayout()); // ngayon pre gagamit tayo ng borderlayout para may gitna at baba tayo

        // ngayon pre gagawin natin to para sa mismong game panel yung may bird at pipes
        FlappyBird flappyBird = new FlappyBird();

        // ito naman pre gagawa tayo ng menu sa taas para mukhang legit na app
        // NEEDS NI SIR RJAY TO PRE KASI GUSTO NI SIR MAY MENU BAR NA PROFESSIONAL TINGNAN
        JMenuBar menuBar = new JMenuBar(); // ito pre lalagyan natin ng menu
        JMenu menu = new JMenu("Menu"); // ito yung menu na word pre pag pinindot may lalabas
        JMenuItem settingsItem = new JMenuItem("Settings GUI"); // ito naman is para sa settings na pipindutin
        JMenuItem logoutItem = new JMenuItem("Logout"); // ito naman is para sa logout para bumalik sa login

        // ngayon pre pag pinindot yung settings bubukas yung bintana ng difficulty
        settingsItem.addActionListener(e -> new SettingsDialog(frame, flappyBird));

        // ito naman pre pag pinindot yung logout isasara natin game tapos bubuksan login ulit
        // NEEDS NI SIR TO PRE PARA MAY LOGOUT FEATURE YUNG APP HINDI LANG CLOSE
        logoutItem.addActionListener(e -> { 
            frame.dispose(); // ngayon pre isasara muna natin yung game para di dumami window
            new Login(); // ito naman is para buksan ulit yung login
        });

        menu.add(settingsItem); // ngayon pre ilalagay natin yung settings sa loob ng menu
        menu.add(logoutItem); // ito naman is para malagay yung logout sa loob ng menu
        menuBar.add(menu); // ito pre ilalagay natin yung menu sa menubar
        frame.setJMenuBar(menuBar); // ngayon pre ilalagay natin yung menubar sa taas ng frame

        // ngayon pre gagawin natin to para sa baba na may buttons puro GUI na pipindutin na lang
        // NEEDS NI SIR RJAY TO PRE KASI AYAW NA NI SIR NG PURO KEYBOARD GUSTO NYA LAHAT MAY BUTTON NA GUI
        // DATI PRESS S PARA SETTINGS NGAYON BUTTON NA LANG
        JPanel bottomPanel = new JPanel(); // ito pre panel sa baba
        bottomPanel.setLayout(new FlowLayout()); // ito naman is para sunod sunod lang yung buttons
        bottomPanel.setBackground(Color.DARK_GRAY); // ito pre kulay dark gray para astig tingnan

        JButton flapBtn = new JButton("FLAP (SPACE)"); // ngayon pre gagawa tayo ng button na pang flap
        flapBtn.setBackground(Color.YELLOW); // ito naman is para sa kulay yellow para kita agad
        flapBtn.setFont(new Font("Arial", Font.BOLD, 12));

        JButton restartBtn = new JButton("RESTART"); // ito naman is para sa restart pag na game over ka
        restartBtn.setBackground(Color.GREEN); // ito pre kulay green para parang go ulit
        restartBtn.setFont(new Font("Arial", Font.BOLD, 14));

        JButton settingsBtn = new JButton("SETTINGS"); // ito naman is para sa settings na button
        settingsBtn.setBackground(Color.CYAN); // ito pre kulay cyan para iba naman
        settingsBtn.setFont(new Font("Arial", Font.BOLD, 14));

        // ngayon pre gagawin natin to para kahit button pinindot mo parang space din sa keyboard
        // NEEDS NI SIR TO PRE PARA KAHIT WALANG KEYBOARD MAKAKALARO KA SA BUTTON LANG
        flapBtn.addActionListener(e -> {
            flappyBird.requestFocus(); // ito pre para bumalik focus sa game
            flappyBird.dispatchEvent(new KeyEvent(
                flappyBird, 
                KeyEvent.KEY_PRESSED, 
                System.currentTimeMillis(), 
                0, 
                KeyEvent.VK_SPACE, 
                KeyEvent.CHAR_UNDEFINED)); // ito naman is para kunwari pinindot mo yung space
        });

        // ito naman pre para sa restart pag pinindot tatawagin yung restartGame dun sa flappybird
        // NEEDS NI SIR TO PRE DATI SPACE PARA MAG RESTART NGAYON BUTTON NA LANG
        restartBtn.addActionListener(e -> {
            flappyBird.restartGame(); // ngayon pre tatawagin natin yung method na nag rereset ng lahat
            flappyBird.requestFocus(); // ito naman is para bumalik focus sa game para gumana pa din keyboard
        });

        // ngayon pre pag pinindot yung settings button bubukas yung may normal hard insane
        // NEEDS NI SIR TO PRE PARA GUI NA YUNG PAG PILI NG DIFFICULTY HINDI NA YUNG S AT 1 2 3
        settingsBtn.addActionListener(e -> {
            new SettingsDialog(frame, flappyBird); // ito pre bubukas yung settings na GUI
        });

        bottomPanel.add(flapBtn); // ngayon pre ilalagay natin yung flap button sa baba
        bottomPanel.add(restartBtn); // ito naman is para malagay yung restart sa baba
        bottomPanel.add(settingsBtn); // ito naman is para malagay yung settings sa baba

        // FINAL LAGAY NA SA FRAME - DITO YUNG FIX NG NASA BABA NA GAME
        frame.add(flappyBird, BorderLayout.CENTER); // ito pre yung game sa gitna
        frame.add(bottomPanel, BorderLayout.SOUTH); // ito naman is para sa baba yung may buttons
        
        // BAKIT KELANGAN TONG PACK PRE
        // ngayon pre yung pack para ayusin yung sukat ng frame base sa laki ng game at nung bottomPanel
        // pag di mo ginawa to putol yung game
        frame.pack();

        // BAKIT DITO KELANGAN ILAGAY YUNG setLocationRelativeTo
        // NGAYON PRE DITO DAPAT ILAGAY PAGKATAPOS NG PACK
        // KASI PAG NILAGAY MO SA TAAS BAGO MAG PACK MALI YUNG GITNA
        // LALAKI PA KASI YUNG FRAME PAG NADAGDAG YUNG BOTTOM PANEL KAYA MAPUPUNTA SA BABA YUNG GAME
        // PAG DITO MO NILAGAY PAGKATAPOS NG PACK SAKTO NA GITNA TALAGA KASI FINAL NA LAKI NA NG FRAME
        frame.setLocationRelativeTo(null); // ito pre para gitna talaga yung buong window

        flappyBird.requestFocus(); // ito naman is para pag bukas pa lang gumagana na agad yung keyboard
        frame.setVisible(true); // ngayon pre ipapakita na natin yung window pag wala to di lalabas kahit anong gawin mo
    }
}