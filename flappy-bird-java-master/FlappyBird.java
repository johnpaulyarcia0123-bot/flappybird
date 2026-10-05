import java.awt.*; // ngayon pre ito para sa Graphics, Color, Image, Font
import java.awt.event.*; // ito naman is para sa ActionListener tsaka KeyListener para gumalaw yung bird
import java.util.ArrayList; // ito pre para sa listahan ng pipes, kasi madami pipe diba
import java.util.Random; // ito naman is para sa random ng pwesto ng pipe
import javax.swing.*; // ito pre para sa JPanel, Timer, ImageIcon

// ngayon pre ito na yung pinaka main na game
// dito na lahat ng logic ng flappy bird
public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    
    // ito pre sukat ng game board
    int boardWidth = 360; // ngayon pre ito yung lapad 360 lang
    int boardHeight = 640; // ito naman is para sa taas 640 para portrait

    // ito pre mga pictures na gagamitin
    Image backgroundImg; // ito naman is para sa background na may clouds
    Image birdImg; // ngayon pre ito yung bird natin
    Image topPipeImg; // ito pre pang taas na pipe
    Image bottomPipeImg; // ito naman is para sa baba na pipe

    // ito pre pwesto ng bird sa umpisa
    int birdX = boardWidth/8; // ngayon pre ito yung x ng bird medyo nasa kaliwa
    int birdY = boardHeight/2; // ito naman is para sa y ng bird gitna sya
    int birdWidth = 34; // ito pre lapad ng bird
    int birdHeight = 24; // ito naman is para sa taas ng bird

    // NEEDS NI SIR NA OOP ENCAPSULATION
    // ngayon pre gumawa tayo ng class Bird para naka encapsulate lahat ng properties nya
    // ito yung OOP na sinasabi ni sir na dapat may sariling class
    class Bird {
        int x = birdX; // ito pre x nya
        int y = birdY; // ito naman is para sa y nya
        int width = birdWidth; // ito pre lapad nya
        int height = birdHeight; // ito naman is para sa taas nya
        Image img; // ito pre picture nya
        Bird(Image img) { this.img = img; } // ngayon pre ito yung constructor pag nag new Bird ka
    }

    // ito pre pwesto ng pipe
    int pipeX = boardWidth; // ngayon pre ito yung start ng pipe sa kanan
    int pipeY = 0; // ito naman is para sa y ng pipe 0 sa taas
    int pipeWidth = 64; // ito pre lapad ng pipe
    int pipeHeight = 512; // ito naman is para sa taas ng pipe mahaba

    // NEEDS NI SIR NA OOP ENCAPSULATION ULIT
    // ito naman pre class Pipe para naka hiwalay din properties ng pipe
    class Pipe {
        int x = pipeX; // ito pre x ng pipe
        int y = pipeY; // ito naman is para sa y ng pipe
        int width = pipeWidth; // ito pre lapad
        int height = pipeHeight; // ito naman is para sa taas
        Image img; // ito pre picture ng pipe
        boolean passed = false; // ngayon pre ito para malaman kung nalagpasan na ng bird para sa score
        Pipe(Image img) { this.img = img; } // ito pre constructor nya
    }

    // GAME VARIABLES
    Bird bird; // ngayon pre ito yung object ng bird
    int velocityX = -4; // ito naman is para sa bilis ng pipe papuntang kaliwa, negative kasi pa kaliwa
    int velocityY = 0; // ito pre bilis ng bird pag bumagsak o tumalon
    int gravity = 1; // ngayon pre ito yung gravity, pag 1 normal pag 2 hard pag 3 insane

    ArrayList<Pipe> pipes; // ito pre listahan ng pipes na nasa screen
    Random random = new Random(); // ito naman is para sa random para iba iba pwesto ng butas
    Timer gameLoop; // ngayon pre ito yung timer na 60 fps para gumalaw yung game
    Timer placePipeTimer; // ito naman is para sa timer na maglalagay ng bagong pipe kada 1.5 seconds
    boolean gameOver = false; // ito pre pag true patay ka na
    double score = 0; // ito naman is para sa score mo
    static int highScore = 0; // ngayon pre ito yung highscore, static para kahit mag restart ka di mawawala

    // CONSTRUCTOR - ONE TIME SETUP
    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight)); // ngayon pre ito yung sukat ng panel
        setFocusable(true); // ito naman is para gumana yung keyboard, kasi pag di mo nilagay to bingi yung panel
        addKeyListener(this); // ito pre para mabasa pag pinindot mo space
        loadHighScore(); // ngayon pre tawagin natin yung method na mag load ng highscore galing sa file

        // LOAD IMAGES - NEEDS NI SIR NA RESOURCE HANDLING
        // ngayon pre gagawin natin to para ma load yung mga pictures galing sa folder
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage(); // ito pre background
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage(); // ito naman is para sa bird
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage(); // ito pre taas na pipe
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage(); // ito naman is para sa baba na pipe

        bird = new Bird(birdImg); // ngayon pre gagawa tayo ng bagong bird object
        pipes = new ArrayList<Pipe>(); // ito naman is para gumawa ng bagong listahan ng pipes na wala pang laman

        // TIMER PARA SA PIPES
        placePipeTimer = new Timer(1500, e -> placePipes()); // ngayon pre kada 1500ms o 1.5 sec maglalagay ng pipe
        placePipeTimer.start(); // ito pre start mo na yung timer

        // GAME LOOP 60 FPS
        gameLoop = new Timer(1000/60, this); // ito naman is para 60 times per second gumalaw yung game, 1000 divided by 60
        gameLoop.start(); // ito pre start mo na din
    }

    // METHOD PARA MAGLAGAY NG PIPES
    void placePipes() {
        // ngayon pre mag rarandom tayo ng pwesto ng pipe para di pareho
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2)); // ito pre formula para random y
        int openingSpace = boardHeight/4; // ito naman is para sa butas sa gitna kung saan dadaan yung bird

        Pipe topPipe = new Pipe(topPipeImg); // ngayon pre gagawa tayo ng bagong top pipe
        topPipe.y = randomPipeY; // ito pre ilagay natin yung random y dun
        pipes.add(topPipe); // ito naman is para idagdag sa listahan

        Pipe bottomPipe = new Pipe(bottomPipeImg); // ito naman pre gagawa tayo ng bottom pipe
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace; // ito pre y nya is y ng top plus height plus butas
        pipes.add(bottomPipe); // ito naman is para idagdag din sa listahan
    }

    public void paintComponent(Graphics g) { 
        super.paintComponent(g); // tawagin muna yung original na paintComponent
        draw(g); // tapos tawagin yung draw natin
    } 

    // DRAW METHOD - DITO LAHAT NG DRAWING
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, this.boardWidth, this.boardHeight, null); // ngayon pre idraw natin background
        g.drawImage(birdImg, bird.x, bird.y, bird.width, bird.height, null); // ito naman is para idraw yung bird

        // ngayon pre i loop natin lahat ng pipes para idraw
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i); // kunin yung pipe sa list
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null); // idraw mo pre
        }

        // SCORE DISPLAY
        g.setColor(Color.white); // ito pre kulay puti ng text
        g.setFont(new Font("Arial", Font.BOLD, 20)); // ito naman is para sa font bold
        g.drawString("Score " + (int)score, 10, 35); // ngayon pre idraw yung score
        g.drawString("High " + highScore, 10, 60); // ito naman is para sa highscore

        // GAME OVER TEXT
        if (gameOver) { // pag patay ka na pre
            g.setColor(Color.RED); // ito pre kulay pula
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("GAME OVER", boardWidth/2 - 90, boardHeight/2 - 40); // ito naman is para sa game over text
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 14));
            g.drawString("Click RESTART Button Below!", boardWidth/2 - 95, boardHeight/2); // ito pre sabihin mo mag click ng restart
        }
    }

    // MOVE METHOD - DITO GUMAGALAW LAHAT
    public void move() {
        if(gameOver) return; // pag game over na wag na gumalaw pre

        velocityY += gravity; // ngayon pre dagdagan natin yung velocity ng gravity para bumagsak
        bird.y += velocityY; // ito naman is para gumalaw yung bird
        bird.y = Math.max(bird.y, 0); // ito pre para di lumagpas sa taas

        // ngayon pre i loop natin pipes para gumalaw
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX; // ito pre galaw ng pipe pa kaliwa

            // SCORE LOGIC
            if (!pipe.passed && bird.x > pipe.x + pipe.width) { // pag nalagpasan na ng bird yung pipe
                score += 0.5; // ngayon pre 0.5 lang kasi dalawang pipe yung isang set kaya 1 point lahat
                pipe.passed = true; // ito naman is para di na madoble score
                if((int)score > highScore) { // pag mas mataas na score mo kesa highscore
                    highScore = (int)score; // ito pre palitan mo highscore
                    saveHighScore(); // ito naman is para i save sa file
                }
            }

            // COLLISION CHECK
            if (collision(bird, pipe)) { // pag tumama bird sa pipe
                gameOver = true; // patay ka na pre
            }
        }

        if (bird.y > boardHeight) { // pag bumagsak bird sa baba
            gameOver = true; // patay ka na pre
        }
    }

    // COLLISION METHOD - NEEDS NI SIR NA LOGIC
    boolean collision(Bird a, Pipe b) {
        // ngayon pre ito yung formula ng rectangle collision
        return a.x < b.x + b.width && a.x + a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y;
    }

    // GAME LOOP - TATAWAGIN 60 TIMES PER SECOND
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); // ngayon pre galawin mo lahat
        repaint(); // ito naman is para idraw ulit
        if (gameOver) { // pag game over na
            placePipeTimer.stop(); // ito pre stop mo na timer ng pipes
            gameLoop.stop(); // ito naman is para stop mo na din game loop
        }
    }
// space
   @Override
     public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) flap();
        // so pag pinalitan ko ng ganto yan mawawala yung space niya
        // @Override
        // public void keyPressed(KeyEvent e) {

    }
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    // GUI METHODS - NEEDS NI SIR NA INTERACTION
    // ngayon pre ito yung tinatawag ng button na FLAP
    public void flap() {
        if(gameOver) { // pag patay ka na pag nag flap ka mag restart
            restartGame(); // ito pre restart
        } else {
            velocityY = -9; // ito naman is para tumalon yung bird pataas
        }
    }

    // ito naman is para sa settings na gravity
    public void setGravity(int newGravity) {
        this.gravity = newGravity; // ngayon pre palitan natin gravity
        this.requestFocus(); // ito naman is para bumalik focus sa game
    }

    // RESTART METHOD - FIX NA TO PRE
    public void restartGame() {
        // ngayon pre i stop muna natin para di mag doble
        if(placePipeTimer != null) placePipeTimer.stop();
        if(gameLoop != null) gameLoop.stop();

        bird.y = birdY; // ito pre balik mo bird sa gitna
        velocityY = 0; // ito naman is para reset mo bilis nya
        pipes.clear(); // ngayon pre burahin mo lahat ng pipes sa list
        score = 0; // ito naman is para reset mo score to zero
        gameOver = false; // ito pre di ka na patay

        // start ulit timers
        placePipeTimer.start(); // ito pre start mo ulit pipe timer
        gameLoop.start(); // ito naman is para start mo ulit game loop
        
        repaint(); // idraw ulit pre
        requestFocus(); // balik focus pre
    }

    // FILE HANDLING - NEEDS NI SIR NA FILE HANDLING
    // ngayon pre gagawin natin to para ma save yung highscore kahit isara mo game
    public void loadHighScore() {
        try {
            java.io.File file = new java.io.File("highscore.txt"); // ito pre hanapin mo yung file
            if (file.exists()) { // pag meron file
                java.util.Scanner sc = new java.util.Scanner(file); // basahin mo pre
                if (sc.hasNextInt()) highScore = sc.nextInt(); // kunin mo number sa loob
                sc.close(); // isara mo scanner pre
            }
        } catch (Exception e) { highScore = 0; } // pag wala file zero lang
    }

    // ito naman is para mag save ng highscore
    public void saveHighScore() {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("highscore.txt"); // gumawa ka ng writer pre
            writer.write(String.valueOf(highScore)); // sulat mo highscore dun
            writer.close(); // isara mo pre para ma save
        } catch (Exception e) {}
    }
}