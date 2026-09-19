import javax.swing.*; // import para sa JFrame, JButton, JLabel, JTextField
import java.awt.*; // import para sa Color, Font, Cursor
import java.awt.event.*; // import para sa ActionListener

/*
 * CLASS: Login
 * PARA SAAN: Ito yung unang window na lalabas pag ni run yung program
 * PAANO GUMAGANA: Gumagamit ng JFrame para maging window at ActionListener para mabasa yung click ng button
 * GINAWA NI: Flappy Bird Project
 */
public class Login extends JFrame implements ActionListener {

    // VARIABLES: Dito nilagay yung mga components na gagamitin sa buong class
    JTextField userField; // lagayan ng username na itatype ng user
    JPasswordField passField; // lagayan ng password, naka hide yung text
    JButton loginBtn; // pindutan para mag login

    /*
     * CONSTRUCTOR: Login()
     * PARA SAAN: One time setup lang to, dito binubuo yung itsura ng login form
     * KELAN TATAWAGIN: Pag nag new Login() sa main method
     */
    public Login() {
        // WINDOW SETTINGS
        setTitle("Flappy Bird - Login Form"); // title na makikita sa taas ng window
        setSize(400, 350); // sukat ng window, width 400 height 350
        setLocationRelativeTo(null); // para lumabas sa gitna ng screen, null means center
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // para pag pinindot yung X mag close talaga program
        setResizable(false); // bawal palakihin o paliitin ng user para di masira design
        setLayout(null); // manual layout, tayo maglalagay ng pwesto gamit setBounds
        getContentPane().setBackground(new Color(112, 197, 206)); // background color ng window, sky blue parang sa flappy bird game

        // WHITE PANEL - PARA SAAN: Parang card na puti para malinis tingnan
        JPanel panel = new JPanel(); // gumawa ng panel
        panel.setBounds(50, 30, 300, 250); // pwesto nya sa window
        panel.setBackground(Color.WHITE); // kulay puti
        panel.setLayout(null); // manual din
        add(panel); // ilagay sa main window

        // TITLE LABEL
        JLabel title = new JLabel("WELCOME BACK!"); // text na welcome
        title.setBounds(0, 15, 300, 25); // pwesto sa loob ng white panel
        title.setFont(new Font("Arial", Font.BOLD, 18)); // font, bold at malaki
        title.setForeground(new Color(0, 102, 204)); // kulay blue
        title.setHorizontalAlignment(SwingConstants.CENTER); // gitna yung text
        panel.add(title); // ilagay sa white panel

        // SUBTITLE LABEL
        JLabel sub = new JLabel("Please login to continue"); // maliit na text sa ilalim ng title
        sub.setBounds(0, 35, 300, 20);
        sub.setFont(new Font("Arial", Font.PLAIN, 12)); // plain lang, maliit
        sub.setForeground(Color.GRAY); // kulay gray
        sub.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(sub);

        // USERNAME LABEL
        JLabel userLabel = new JLabel("Username"); // label para alam ng user saan mag type
        userLabel.setBounds(25, 65, 100, 20); // pwesto
        userLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(userLabel);

        // USERNAME TEXTFIELD
        userField = new JTextField(); // textbox na pwede mag type
        userField.setBounds(25, 85, 250, 30); // pwesto at laki
        userField.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // border na gray para kita
        panel.add(userField); // ilagay sa panel

        // PASSWORD LABEL
        JLabel passLabel = new JLabel("Password"); // label ng password
        passLabel.setBounds(25, 120, 100, 20);
        passLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(passLabel);

        // PASSWORD FIELD
        passField = new JPasswordField(); // textbox na pag nag type puro bilog makikita para safe
        passField.setBounds(25, 140, 250, 30);
        passField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(passField);

        // LOGIN BUTTON
        loginBtn = new JButton("LOGIN & PLAY"); // button na may text
        loginBtn.setBounds(25, 185, 250, 40); // malaki para madali pindutin
        loginBtn.setBackground(new Color(115, 191, 46)); // kulay green parang go
        loginBtn.setForeground(Color.WHITE); // kulay ng text puti
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14)); // bold
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // pag tinapat mouse magiging kamay yung cursor
        loginBtn.addActionListener(this); // pag pinindot to, pupunta sa actionPerformed method sa baba
        panel.add(loginBtn);

        // HINT LABEL
        JLabel hint = new JLabel("Hint: admin / 1234"); // paalala sa user kung ano ilalagay
        hint.setBounds(0, 230, 300, 15);
        hint.setFont(new Font("Arial", Font.ITALIC, 10)); // italic at maliit
        hint.setForeground(Color.GRAY);
        hint.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(hint);

        setVisible(true); // pinaka importante, para makita yung window, pag wala to di lalabas
    }

    /*
     * METHOD: actionPerformed
     * PARA SAAN: Ito yung utak, dito lahat ng mangyayari pag pinindot yung login button
     * PAANO GUMAGANA: Kukunin yung laman ng textfield at icheck kung tama
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // KUNIN YUNG NILAGAY NG USER
        String username = userField.getText(); // kunin yung tinype sa username field
        String password = new String(passField.getPassword()); // kunin yung tinype sa password field

        // VALIDATION 1: Check kung walang laman
        if(username.equals("") || password.equals("")){
            // pag walang laman mag popup ng warning
            JOptionPane.showMessageDialog(this, 
                "Please enter your username and password!", 
                "Missing Information", 
                JOptionPane.WARNING_MESSAGE);
            return; // huminto, wag na ituloy sa baba
        }

        // VALIDATION 2: Check kung tama yung username at password
        // dito hardcoded lang admin at 1234 para simple, pwede palitan
        if(username.equals("admin") && password.equals("1234")){
            // PAG TAMA YUNG LOGIN
            // magpapakita ng welcome message na english
            JOptionPane.showMessageDialog(this,
                "Login Successful!\n\nWelcome " + username + "!\nYou are now ready to play Flappy Bird.\nGood luck and have fun!",
                "Welcome!",
                JOptionPane.INFORMATION_MESSAGE);
            
            dispose(); // isara yung login window para di dalawa yung window
            new App(); // buksan yung game window, yung App class yung may ari ng game JFrame
        } else {
            // PAG MALI YUNG LOGIN
            JOptionPane.showMessageDialog(this,
                "Invalid Username or Password!\n\nPlease try again.\n\nHint: Username is 'admin' and Password is '1234'",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
            passField.setText(""); // burahin yung password para mag type ulit
            passField.requestFocus(); // ilagay yung cursor sa password field ulit
        }
    }

    /*
     * MAIN METHOD
     * PARA SAAN: Dito magsisimula yung buong program
     * PAANO GUMAGANA: Pag ni run mo yung Login.java, ito unang tatawagin
     */
    public static void main(String[] args) {
        new Login(); // gumawa ng bagong Login window
    }
}