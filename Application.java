import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;

public class Application extends JPanel {

    public static final int screenWidth = 1000;
    public static final int screenHeight = 700;
    
    private static final long serialVersionUID = 1L;
    
    public static String fileName = "./mazes/seven.txt";
    private JFrame frame;

    private Maze maze;

    private boolean show3d = true;
    public static boolean showCompass = true;

    private boolean usingServer = true;
    private HttpServer server;
    private static final int PORT = 8000;

    private Process process;

    public Application() {

        if (usingServer) {
            HashMap<String, HttpServer.Receiver> receivers = new HashMap<>();
            receivers.put("move", new HttpServer.Receiver() {

                @Override
                public String call(String params) {
                    if (params.equals("front")) {
                        maze.getExplorer().move();
                        maze.flip = !maze.flip;
                    } else if (params.equals("back")) {
                        maze.getExplorer().moveBack();
                        maze.flip = !maze.flip;
                    } else if (params.equals("left")) {
                        maze.getExplorer().turnLeft();
                    } else if (params.equals("right")) {
                        maze.getExplorer().turnRight();
                    }
                    repaint();
                    return maze.getExplorer().getLocation().getX() + " " + maze.getExplorer().getLocation().getY() + " "
                            + maze.getExplorer().getDirection() + " " + (maze.isDone() ? 1 : 0);
                }
            });
            receivers.put("file", new HttpServer.Receiver() {

                @Override
                public String call(String params) {
                    return "." + fileName;
                }
            });
            receivers.put("controller", new HttpServer.Receiver() {

                @Override
                public String call(String params) {
                    File file = new File("./html/index.html");
                    String html = "";
                    try {
                        List<String> list = Files.readAllLines(file.toPath());
                        for(String s: list)
                            html += s + "\n";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return html;
                }
            });
            server = new HttpServer(PORT, receivers);
            server.start();
        }

        frame = new JFrame();

        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenWidth*5/4, screenHeight*5/4);
        setBoard();
        frame.setVisible(true);

        frame.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent event) {
                // System.out.println("Pressed: " + event.getKeyCode());
                switch(event.getKeyCode()) {
                    case 38:
                        maze.getExplorer().move();
                        maze.flip = !maze.flip;
                        break;
                    case 37:
                        // maze.getExplorer().turnLeft();
                        break;
                    case 39:
                        // maze.getExplorer().turnRight();
                        break;
                    case 40:
                        maze.getExplorer().moveBack();
                        maze.flip = !maze.flip;
                        break;
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent event) {
                // System.out.println("Released: " + event.getKeyCode());
                switch(event.getKeyCode()) {
                    case 38:
                        // maze.getExplorer().move();
                        break;
                    case 37:
                        maze.getExplorer().turnLeft();
                        break;
                    case 39:
                        maze.getExplorer().turnRight();
                        break;
                    case 32:
                        show3d = !show3d;
                        break;
                    case 67:
                        showCompass = !showCompass;
                        break;
                    case 80:
                        try {
                            if(process != null && process.isAlive()) {
                                process.destroy();
                            } else {
                                Runtime.getRuntime().exec("python ./python/maze_ai.py");
                                ProcessBuilder builder = new ProcessBuilder("python", "maze_ai.py");
                                builder.directory(new File("python"));
                                builder.redirectError();
                                process = builder.start();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                repaint();
            }

            @Override
            public void keyTyped(KeyEvent event) {
                // System.out.println("Typed: " + event.getKeyCode());
            }

        });
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);

        if(maze.isDone()) {
            g.setColor(Color.WHITE);
            g.drawString("Rishav is cool not", 20, 20);
        } else if(maze.getExplorer().isDead()) {
            g.setColor(Color.WHITE);
            g.drawString("Rishav is deaded", 20, 20);
        } else {
            if(show3d)
                maze.draw3d(g);
            else
                maze.draw(g);
        }
        
    }

    public void setBoard() {
        maze = Maze.fromFile(fileName);
    }

    public static void main(String[] args) {
        if(args.length > 0)
            fileName = "./mazes/" + args[0] + ".txt";
        Application app = new Application();
    }

}