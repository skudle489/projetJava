import model.Country;
import model.Customer;
import model.Hotel;
import model.Locality;

public class Main {
    public static void main(String[] args) {

        try {
            Country country = new Country("BE", "Belgique");
            Locality locality = new Locality("Limelette", country, "1342");
            Customer customer = new Customer("thounythea4@gmail.com", "Thouny", "Thea", "0498797605", "Rue de l'Europe", 1, 30, 9, 2003, false, locality);
            Hotel hotel = new Hotel(1, "Rue de la brochette", 8, 5, locality, "Briochoc");
            System.out.println(hotel);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
