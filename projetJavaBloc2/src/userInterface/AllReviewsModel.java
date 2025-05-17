package userInterface;

import model.*;

import javax.swing.table.AbstractTableModel;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class AllReviewsModel extends AbstractTableModel {
    private final ArrayList<String> columnNames;
    private ArrayList<Review> contents;

    public AllReviewsModel(ArrayList<Review> allReviews) {
        columnNames = new ArrayList<>();
        columnNames.add("Commentaire");
        columnNames.add("Dernière date de visite du pays de l'hotel");
        columnNames.add("Date de création");
        columnNames.add("Utilisateur");
        columnNames.add("Etoiles");
        columnNames.add("Est anonyme");
        columnNames.add("Titre");
        columnNames.add("Hotel");
        setContents(allReviews);
    }

    void setContents(ArrayList<Review> allReviews) {
        this.contents = allReviews;
    }


    public int getColumnCount() {
        return columnNames.size();
    }

    public int getRowCount() {
        return contents.size();
    }

    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    public Object getValueAt(int row, int column) {
        Review review = contents.get(row);
        switch (column) {
            case 0:
                return review.getComment();
                case 1:
                    if (review.getLastVisitDateHotelCountry() != null) {
                        return java.util.Date.from(review.getLastVisitDateHotelCountry().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    }
                    return null;
                    case 2:
                        return java.util.Date.from(review.getCreationDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        case 3:
                            return review.getCustomer();
                            case 4:
                                return review.getStar();
                                case 5:
                                    return review.getIsAnonymous();
                                    case 6:
                                        return review.getTitle();
                                        case 7:
                                            return review.getHotel();
                                            default:
                                                return null;
        }
    }

    public Class getColumnClass(int column) {
        switch (column) {
            case 0, 3, 6:
                return String.class;
            case 1, 2:
                return Date.class;
            case 4, 7:
                return Integer.class;
            case 5:
                return Boolean.class;
            default:
                return String.class;
        }
    }
}
