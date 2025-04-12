package userInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Plane extends JPanel {
    private Rectangle rectangle;
    private ArrayList<Rectangle> windows;
    private int planeHeight;
    private Polygon face;
    private Polygon topWing;
    private Polygon bottomWing;
    private Polygon tail;

    private static final int NUM_WINDOWS = 10;
    private static final int WINDOW_WIDTH_RATIO = 15;
    private static final int WINDOW_HEIGHT_RATIO = 3;


    public Plane(int x, int y, int width, int height) {
        rectangle = new Rectangle(x, y, width, height);
        planeHeight = rectangle.height/3;
        face = createFace();
        topWing = createTopWing();
        bottomWing = createBottomWing();
        tail = createTail();
        windows = createWindows();
        startMovement();
    }

    private void startMovement() {
        PlaneMovementThread planeMovementThread = new PlaneMovementThread(this);
        planeMovementThread.start();
    }

    private Polygon createFace(){
        int [] xPointsFace = {rectangle.x + rectangle.width, rectangle.x + rectangle.width, rectangle.x + rectangle.width + rectangle.width/4};
        int[] yPointsFace = {rectangle.y, rectangle.y + planeHeight, rectangle.y + planeHeight};
        return new Polygon(xPointsFace, yPointsFace, 3);
    }

    private Polygon createTopWing(){
        int[] xPointsTopWing = {rectangle.x + rectangle.width/2, rectangle.x + rectangle.width/2, rectangle.x + rectangle.width/2 + rectangle.width/7};
        int[] yPointsTopWing = {rectangle.y-rectangle.height/2, rectangle.y, rectangle.y};
        return new Polygon(xPointsTopWing, yPointsTopWing, 3);
    }

    private Polygon createBottomWing(){
        int[] xPointsBottomWing = {rectangle.x + rectangle.width/2, rectangle.x + rectangle.width/2, rectangle.x + rectangle.width/2 + rectangle.width/7};
        int[] yPointsBottomWing = {rectangle.y + planeHeight, rectangle.y + rectangle.height/2 + planeHeight, rectangle.y+planeHeight};
        return new Polygon(xPointsBottomWing, yPointsBottomWing, 3);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Polygon getFace() {
        return face;
    }

    public Polygon getTopWing() {
        return topWing;
    }

    public Polygon getBottomWing() {
        return bottomWing;
    }

    public Polygon createTail(){
        int[] xPoints = {rectangle.x, rectangle.x, rectangle.x + rectangle.width/10};
        int[] yPoints = {rectangle.y - planeHeight, rectangle.y, rectangle.y};
        return new Polygon(xPoints, yPoints, 3);
    }

    public Polygon getTail() {
        return tail;
    }

    public ArrayList<Rectangle> createWindows(){
        ArrayList<Rectangle> windows = new ArrayList<>();
        int windowWidth = rectangle.width/WINDOW_WIDTH_RATIO;
        int windowsSpacing = windowWidth/2;
        int windowY = rectangle.y + planeHeight/3;
        int windowHeight = planeHeight/WINDOW_HEIGHT_RATIO;

        for (int windowIndex = 0; windowIndex < NUM_WINDOWS; windowIndex++) {
            int windowX = rectangle.x + windowWidth + (windowWidth * windowIndex) + windowsSpacing * windowIndex;
            windows.add(new Rectangle(windowX, windowY, windowWidth, windowHeight));
        }
        return windows;
    }

    public ArrayList<Rectangle> getWindows() {
        return windows;
    }

    public void movePolygone(Polygon polygon, int dx, int dy) {
        for (int iPoint = 0; iPoint < polygon.npoints; iPoint++) {
            polygon.xpoints[iPoint]+=dx;
            polygon.ypoints[iPoint]+=dy;
        }
    }

    public void movePlane(int dx, int dy){
        movePolygone(face, dx, dy);
        movePolygone(topWing, dx, dy);
        movePolygone(bottomWing, dx, dy);
        movePolygone(tail, dx, dy);

        rectangle.x += dx;
        rectangle.y += dy;

        for (Rectangle window : windows) {
            window.x += dx;
            window.y += dy;
        }

    }

    public void drawPlane(Graphics g) {
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, planeHeight);
        g.fillPolygon(face);
        g.fillPolygon(topWing);
        g.fillPolygon(bottomWing);
        g.fillPolygon(tail);
        g.setColor(Color.WHITE);
        for (Rectangle window : windows) {
            g.fillRect(window.x, window.y, window.width, window.height);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawPlane(g);
    }
}
