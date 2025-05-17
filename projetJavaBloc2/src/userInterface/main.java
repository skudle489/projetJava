package userInterface;


public class main {
    public static void main(String[] args) {
        try {
            MainWindows mainWindows = new MainWindows();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
