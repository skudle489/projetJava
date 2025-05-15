package userInterface;

import model.Customer;

import javax.swing.table.AbstractTableModel;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class AllCustomersModel extends AbstractTableModel {
    private final ArrayList<String> columnNames;
    private ArrayList<Customer> contents;

    public AllCustomersModel(ArrayList<Customer> contents) {
        columnNames = new ArrayList<>();
        columnNames.add("Adresse mail");
        columnNames.add("Prénom");
        columnNames.add("Nom");
        columnNames.add("Numéro de téléphone");
        columnNames.add("Adresse de rue");
        columnNames.add("Numéro de rue");
        columnNames.add("Date de naissance");
        columnNames.add("Est vegan");
        columnNames.add("Numéro de téléphone secondaire");
        columnNames.add("Ville");
        columnNames.add("Code postal");
        columnNames.add("Pays");
        setContents(contents);
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


    public void setContents(ArrayList<Customer> contents) {
        this.contents = contents;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer customer = contents.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return customer.getMailAdress();
            case 1:
                return customer.getFirstName();
            case 2:
                return customer.getLastName();
                case 3:
                    return customer.getPhone();
                    case 4:
                        return customer.getStreet();
                        case 5:
                            return customer.getStreetNumber();
                            case 6:
                                if (customer.getBirthDate() != null) {
                                    return java.util.Date.from(customer.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
                                } else {
                                    return null;
                                }
                                case 7:
                                    return customer.getIsVegan();
                                    case 8:
                                        return customer.getSecondaryPhone();
                                        case 9:
                                            return customer.getCity();
                                            case 10:
                                                return customer.getPostalCode();
                                                case 11:
                                                    return customer.getCountry();
                                                    default:
                                                        return null;

        }
    }

    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 5:
                return Integer.class;
            case 6:
                return Date.class;
            case 7:
                return Boolean.class;
            default:
                return String.class;

        }
    }

}
