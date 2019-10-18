import java.awt.Graphics;

public class PhaseObject extends GameObject{

    private GameObject holding;

    public PhaseObject(int x, int y) {
        super(x, y);
    }

    public void put(GameObject obj) {
        holding = obj;
    }

    public void draw(Graphics g) {
        if(holding != null)
            holding.draw(g);
    }

}