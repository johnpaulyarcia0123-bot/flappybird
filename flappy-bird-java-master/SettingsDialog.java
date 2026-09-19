import java.awt.*; // ngayon pre ito para sa Color, Font
import javax.swing.*; // ito naman is para sa JDialog, JButton, JLabel, JOptionPane

// ngayon pre ito yung SettingsDialog na GUI
// bakit kelangan to pre kasi dati pinipindot pa S tapos 1 2 3 sa keyboard ang hirap
// ngayon pipindutin na lang na parang button sa cp
public class SettingsDialog extends JDialog {

    // constructor pre pag tinawag mo new SettingsDialog bubukas yung window na maliit
    public SettingsDialog(JFrame parent, FlappyBird game) {
        super(parent, "Settings - Difficulty", true); // ito pre tawagin mo yung JDialog na may title, yung true ibig sabihin modal pag bukas to di mo magagalaw yung game hanggat di mo sinasara to
        setSize(300, 250); // ngayon pre ito yung laki ng settings window maliit lang
        setLocationRelativeTo(parent); // ito naman is para nasa gitna ng game pag bumukas
        setLayout(null); // ito pre manual layout tayo para hawak natin pwesto

        // title ng settings
        JLabel title = new JLabel("SELECT DIFFICULTY"); // ito pre text sa taas
        title.setFont(new Font("Arial", Font.BOLD, 16)); // ito naman is para bold at malaki
        title.setBounds(60, 10, 200, 30); // ito pre pwesto nya sa window
        add(title); // ngayon pre ilagay natin sa dialog

        // BUTTON 1 NORMAL
        // ngayon pre gagawa tayo ng button para sa normal mode
        JButton normalBtn = new JButton("1 - Normal (Gravity 1)"); // ito pre button na may text na normal
        normalBtn.setBounds(30, 50, 220, 30); // ito naman is para sa pwesto at laki nya
        // pag pinindot to pre ano mangyayari
        normalBtn.addActionListener(e -> {
            setGravity(game, 1); // ngayon pre tatawagin natin yung setGravity para palitan gravity ng game to 1
            JOptionPane.showMessageDialog(this, "Normal Mode Set!"); // ito naman is para mag popup na naset na normal
            dispose(); // ito pre isara na natin settings window
            game.requestFocus(); // ito naman is para bumalik focus sa game para gumana ulit keyboard at buttons
        });
        add(normalBtn); // ngayon pre ilagay natin yung button sa dialog

        // BUTTON 2 HARD
        // ito naman pre para sa hard mode
        JButton hardBtn = new JButton("2 - Hard (Gravity 2)"); // ito pre button na hard
        hardBtn.setBounds(30, 90, 220, 30); // ito naman is para sa pwesto nya
        hardBtn.addActionListener(e -> {
            setGravity(game, 2); // ngayon pre palitan natin gravity to 2 mas mabilis bumagsak bird
            JOptionPane.showMessageDialog(this, "Hard Mode Set!"); // ito pre popup na hard na
            dispose(); // isara settings pre
            game.requestFocus(); // balik focus sa game pre
        });
        add(hardBtn); // ilagay sa dialog pre

        // BUTTON 3 INSANE
        // ito pre yung pinaka mahirap na mode
        JButton insaneBtn = new JButton("3 - INSANE (Gravity 3)"); // ito pre insane button
        insaneBtn.setBounds(30, 130, 220, 30); // ito naman is para sa pwesto
        insaneBtn.setBackground(Color.RED); // ngayon pre kulay red para mukhang delikado
        insaneBtn.setForeground(Color.WHITE); // ito naman is para puti yung text para kita sa red
        insaneBtn.addActionListener(e -> {
            setGravity(game, 3); // ngayon pre palitan gravity to 3 sobrang bilis bumagsak
            JOptionPane.showMessageDialog(this, "INSANE MODE!"); // ito pre popup na insane na
            dispose(); // isara na pre
            game.requestFocus(); // balik focus sa game pre
        });
        add(insaneBtn); // ilagay sa dialog pre

        setVisible(true); // ito pre pinaka importante pag wala to di lalabas yung settings kahit anong gawin mo
    }

    // BAKIT KELANGAN TONG METHOD NA TO PRE
    // ngayon pre ito yung nag papalit ng gravity
    // ginawa ko may dalawang way para sure gumana
    private void setGravity(FlappyBird game, int gravity) {
        try {
            // way 1 pre tawagin yung setGravity method sa FlappyBird.java gamit reflection
            // reflection pre para kahit private yung method matawag pa din
            game.getClass().getMethod("setGravity", int.class).invoke(game, gravity);
        } catch (ReflectiveOperationException e) {
            try {
                // way 2 pre pag wala talagang setGravity method, diretso na natin palitan yung variable na gravity
                java.lang.reflect.Field field = game.getClass().getDeclaredField("gravity"); // hanapin mo yung variable na gravity
                field.setAccessible(true); // gawin mo accessible kahit private
                field.setInt(game, gravity); // palitan mo value pre
            } catch (ReflectiveOperationException ignored) {
                // pag wala talaga hindi na mapapalitan pre pero di mangyayari to kasi meron naman tayo setGravity method
            }
        }
    }
}