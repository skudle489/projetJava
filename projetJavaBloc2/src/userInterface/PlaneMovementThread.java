package userInterface;

public class PlaneMovementThread extends Thread {
    private Plane plane;

    public PlaneMovementThread(Plane plane) {
        this.plane = plane;
    }

    public void run() {
        try {

            long startTime = System.currentTimeMillis();
            long duration = 6000;

            while (System.currentTimeMillis() - startTime <= duration){
                plane.movePlane(2, -1);
                Thread.sleep(8);
                plane.repaint();
            }
        } catch (InterruptedException e) {
            System.out.println("Plane movement thread interrupted");
        }


    }
}
