import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * Login
 * Ito yung unang window na lalabas pag ni run yung program
 * Gumagamit ng JFrame para maging window at ActionListener para mabasa yung click ng button
 * Flappy Bird Project
 */
public class Login extends JFrame implements ActionListener {
    // VARIABLES
    JTextField userField;
    JPasswordField passField;
    JButton loginBtn;

    public Login() {
        setTitle("Flappy Bird - Login Form");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(112, 197, 206));

        JPanel panel = new JPanel();
        panel.setBounds(50, 30, 300, 250);
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        add(panel);

        JLabel title = new JLabel("WELCOME BACK!");
        title.setBounds(0, 15, 300, 25);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(0, 102, 204));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title);

        JLabel sub = new JLabel("Please login to continue");
        sub.setBounds(0, 35, 300, 20);
        sub.setFont(new Font("Arial", Font.PLAIN, 12));
        sub.setForeground(Color.GRAY);
        sub.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(sub);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(25, 65, 100, 20);
        userLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(userLabel);

        userField = new JTextField();
        userField.setBounds(25, 85, 250, 30);
        userField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(userField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(25, 120, 100, 20);
        passLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(25, 140, 250, 30);
        passField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(passField);

        loginBtn = new JButton("LOGIN & PLAY");
        loginBtn.setBounds(25, 185, 250, 40);
        loginBtn.setBackground(new Color(115, 191, 46));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        
        
        // Itong addActionListener "this" ang nagkokonekta sa button papunta sa actionPerformed method sa baba
        // Yung 'this' ay yung Login class mismo na naka-implements ng ActionListener
        // so kapag pinindot yung button, tatawagin yung actionPerformed method sa Login class
        // Kaya dito natin nilalagay yung logic kung ano ang gagawin kapag pinindot yung button
        // Sa actionPerformed method, kinukuha natin yung username at password mula sa text fields
        // at kino compare sa hardcoded values para malaman kung successful ang login o hindi
        // Kung successful magdi dispose yung login window at magbubukas ng bagong App window
        loginBtn.addActionListener(this);
        panel.add(loginBtn);

        JLabel hint = new JLabel("");
        hint.setBounds(0, 230, 300, 15);
        hint.setFont(new Font("Arial", Font.ITALIC, 10));
        hint.setForeground(Color.GRAY);
        hint.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(hint);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        if(username.equals("") || password.equals("")){
            JOptionPane.showMessageDialog(this, "Please enter your username and password!", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(username.equals("admin") && password.equals("1234")){

            JOptionPane.showMessageDialog(this, "Login Successful!\n\nWelcome " + username + "!\nYou are now ready to play Flappy Bird.\nGood luck and have fun!", "Welcome!", JOptionPane.INFORMATION_MESSAGE);


            

            // 1. dispose()  Ito yung nagpapasara / nagbubura ng Login window.
            //    Method ito na minana INHERITANCE natin galing sa JFrame.
            //    Pag tinawag mo to, sinisira na ng Java yung Login object sa memory
            //    kaya nawawala yung login form sa screen.
            //
            // 2. new App()  Ito yung nagbubukas ng game.
            //    Gumagawa tayo ng bagong OBJECT ng App class (OBJECT INSTANTIATION).
            //    Sa loob ng App class mayroong bagong JFrame na may Flappy Bird game.
            //    Kaya pag na-construct yung App(), automatic lalabas yung game window.
            //
            // FLOW: Login Window (dispose - isara) -> App Window (new - buksan)
            // Ito yung tinatawag na Window Navigation sa Java Swing
            

            dispose(); // so pag binura natin tong dalawa na to lalabas lang yung log in form 
            new App(); // pero hindi lilipat sa game kasi wala pa tayong App.java na naglalaman ng game logic at GUI

        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password!\n\nPlease try again.\n\nHint: Username is 'admin' and Password is '1234'", "Login Failed", JOptionPane.ERROR_MESSAGE);
            passField.setText("");
            passField.requestFocus();
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}