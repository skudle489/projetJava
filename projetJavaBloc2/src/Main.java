import model.*;

import java.time.LocalDate;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        try {
            Country country = new Country("BE", "Belgique");
            Locality locality = new Locality("Limelette", country, "1342");
            Customer customer = new Customer("thounythea4@gmail.com", "Thouny", "Thea", "0498797605", "Rue de l'Europe", 1, 30, 9, 2003, false, locality);
            Hotel hotel = new Hotel(1, "Rue de la brochette", 8, 5, locality, "Briochoc");
            System.out.println(hotel);
            Bed bed = new Bed("King");
            Amenity amenity = new Amenity("Chauffage");
            BedroomType bedroomType = new BedroomType("Familliale");
            Bedroom bedroom = new Bedroom(32, 5, 100, 50, null, true, null, bedroomType, hotel);
            BedroomOwnsAmenity bedroomOwnsAmenity = new BedroomOwnsAmenity(amenity, bedroom);

            Star star = CreateStar.getStar(1);
            Star star2 = CreateStar.getStar(2);
            Star star3 = CreateStar.getStar(3);
            Star star4 = CreateStar.getStar(4);
            Star star5 = CreateStar.getStar(5);
            Star star6 = CreateStar.getStar(5);

            BedroomOwnsBed bedroomOwnsBed = new BedroomOwnsBed(bedroom, bed);
            Review review = new Review("Trop la classe", hotel, "A recommender", true, star, customer, LocalDate.now(), null);



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
