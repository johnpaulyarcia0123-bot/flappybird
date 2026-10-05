import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class App {
    public App() {
        int boardWidth = 360;
        int boardHeight = 640;

        // GUMAWA NG WINDOW PARA SA GAME
        JFrame frame = new JFrame("Flappy Bird");
        frame.setResizable(false); // bawal i-resize para di masira layout ng game
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // pag pinindot X, close lahat
        frame.setLayout(new BorderLayout()); // para may CENTER (game) at SOUTH (buttons)

        // GAME PANEL MISMO - DITO YUNG BIRD AT PIPES
        FlappyBird flappyBird = new FlappyBird();

        // MENU BAR - NEEDS NI SIR RJAY PARA MUKHANG PROFESSIONAL APP
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem settingsItem = new JMenuItem("Settings GUI");
        JMenuItem logoutItem = new JMenuItem("Logout");

        // PAG PININDOT SETTINGS, BUBUKAS YUNG DIALOG NG DIFFICULTY
        settingsItem.addActionListener(e -> new SettingsDialog(frame, flappyBird));

        // =================================================================
        // DITO KUMOKONEKTA PABALIK SA LOGIN.JAVA - LOGOUT FEATURE
        // =================================================================
        // BAKIT KONEKTADO SA LOGIN?
        // Kasi pag nag-logout sa game, kailangan bumalik sa login form.
        // Hindi na kailangan ng import kasi same folder/package lang sila.
        // FLOW: frame.dispose() = isara yung game window
        //       new Login() = gumawa ng bagong Login object = lalabas ulit login form
        // Ito yung reverse ng ginawa natin sa Login.java na new App()
        // =================================================================
        logoutItem.addActionListener(e -> {
            frame.dispose(); // isara muna game para di dumami window
            new Login(); // buksan ulit login - DITO KUMOKONEKTA SA LOGIN.JAVA
        });

        menu.add(settingsItem);
        menu.add(logoutItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        // BOTTOM PANEL - NEEDS NI SIR RJAY NA LAHAT MAY GUI BUTTON, HINDI PURO KEYBOARD
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout()); // sunod-sunod lang buttons
        bottomPanel.setBackground(Color.DARK_GRAY);

        JButton flapBtn = new JButton("FLAP (SPACE)");
        flapBtn.setBackground(Color.YELLOW);
        flapBtn.setFont(new Font("Arial", Font.BOLD, 12));

        JButton restartBtn = new JButton("RESTART");
        restartBtn.setBackground(Color.GREEN);
        restartBtn.setFont(new Font("Arial", Font.BOLD, 14));

        JButton settingsBtn = new JButton("SETTINGS");
        settingsBtn.setBackground(Color.CYAN);
        settingsBtn.setFont(new Font("Arial", Font.BOLD, 14));


// space
        flapBtn.addActionListener(e -> {
            flappyBird.requestFocus(); // ibalik focus sa game, kasi napunta sa button yung focus
            // pag binura koto dina gagana yung space at yung method na flap() sa FlappyBird.java
            // kunwari pinindot yung SPACE key para isang method lang tinatawagan
            flappyBird.dispatchEvent(new KeyEvent(
                    flappyBird, KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
                    0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED));
                    // so pag pinalitan koto ng ganto dina gagana yung space per
                    // gagana yung nasa GUI

                    // ito papalit dapat
                    // flapBtn.addActionListener(e -> {
                    // flappyBird.flap();
                    // flappyBird.requestFocus();
        });

        // restart
        restartBtn.addActionListener(e -> {
            flappyBird.restartGame(); // tawagin yung method na nagre-reset ng score, bird, pipes
            flappyBird.requestFocus(); // ibalik focus sa game
        });

        // SETTINGS BUTTON - GUI NA PAGPILI NG DIFFICULTY
        settingsBtn.addActionListener(e -> {
            new SettingsDialog(frame, flappyBird); // buksan yung GUI ng Normal/Hard/Insane
        });

        bottomPanel.add(flapBtn);
        bottomPanel.add(restartBtn);
        bottomPanel.add(settingsBtn);

        // dito kona  ilagay yung game panel sa gitna at buttons sa baba
        frame.add(flappyBird, BorderLayout.CENTER); // game sa gitna
        frame.add(bottomPanel, BorderLayout.SOUTH); // buttons sa baba


        // BAKIT DITO DAPAT YUNG PACK AT SETLOCATIONRELATIVETO?

        // 1. frame.pack()  inaayos yung sukat ng frame base sa laki ng flappyBird + bottomPanel
        //    Pag di mo to ginawa, putol o maliit yung game kasi di nasama sukat ng bottomPanel
        // 2. setLocationRelativeTo(null) DAPAT PAGKATAPOS NG PACK
        //    Kasi pag nilagay mo sa taas bago mag-pack, mali yung gitna.
        //    Lalaki pa kasi frame pag nadagdag bottomPanel, kaya mapupunta sa baba yung game.
        //    Pag dito nilagay pagkatapos ng pack, final na laki na ng frame kaya sakto gitna.

        frame.pack();
        frame.setLocationRelativeTo(null); // para gitna talaga buong window

        flappyBird.requestFocus(); // para pag bukas pa lang gumagana na keyboard agad
        // so pag binura ko tong dalawa na to mag blablack screen lang siya kahit tama yung code sa Login.java
        frame.setVisible(true); // ipakita window, pag wala to di lalabas
    }
}