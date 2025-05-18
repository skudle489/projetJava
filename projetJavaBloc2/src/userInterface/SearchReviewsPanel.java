package userInterface;

import exceptions.GetCustomerException;
import exceptions.HotelException;
import exceptions.ReviewCreationException;
import exceptions.SearchReviewModelException;
import model.ReservationInvoiceModel;
import model.SearchReviewsModel;
import utils.AppControllers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class SearchReviewsPanel extends JPanel {
    private JPanel mainPanel, mainPanelContainer;
    private JSpinner ratingSpinner;
    private JLabel ratingLabel;

    private JPanel startDatePanel;
    private JSpinner startDaySpinner, startMonthSpinner, startYearSpinner;
    private JLabel startDateLabel;

    private JPanel endDatePanel;
    private JSpinner endDaySpinner, endMonthSpinner, endYearSpinner;
    private JLabel endDateLabel;

    private JPanel reviewsResultPanel;
    private JScrollPane scrollPane;

    private JButton searchButton;
    private AppControllers appControllers;
    private MainWindows mainWindows;


    private JTable reservationsTable;
    private DefaultTableModel tableModel;

    public SearchReviewsPanel(AppControllers appControllers) {
        this.appControllers = appControllers;
        setUpUI();
    }

    public void setUpUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        mainPanelContainer = new JPanel();
        reviewsResultPanel = new JPanel();
        reviewsResultPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        String[] columns = {"Commentaire", "Nom hotel", "Nombre d'étoiles de l'hotel", "Prénom", "Nom"};
        tableModel = new DefaultTableModel(columns, 0);
        reservationsTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(reservationsTable);


        searchButton = new JButton("Chercher les avis non anonymes entre les deux dates");

        mainPanel = new JPanel(new GridLayout(2, 2));
        ratingLabel = new JLabel("Étoile des avis");
        SpinnerNumberModel ratingModel = new SpinnerNumberModel(1, 1, 5, 1);
        ratingSpinner = new JSpinner(ratingModel);
        mainPanel.add(ratingLabel);
        mainPanel.add(ratingSpinner);

        startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startDateLabel = new JLabel("Date de début : ");
        startDaySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
        startMonthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        int currentYear = LocalDate.now().getYear();
        startYearSpinner = new JSpinner(new SpinnerNumberModel(currentYear, currentYear - 10, currentYear, 1));

        startDatePanel.add(startDateLabel);
        startDatePanel.add(new JLabel("Jour"));
        startDatePanel.add(startDaySpinner);
        startDatePanel.add(new JLabel("Mois"));
        startDatePanel.add(startMonthSpinner);
        startDatePanel.add(new JLabel("Année"));
        startDatePanel.add(startYearSpinner);
        mainPanel.add(startDatePanel);

        endDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        endDateLabel = new JLabel("Date de fin : ");
        endDaySpinner = new JSpinner(new SpinnerNumberModel(31, 1, 31, 1));
        endMonthSpinner = new JSpinner(new SpinnerNumberModel(12, 1, 12, 1));
        endYearSpinner = new JSpinner(new SpinnerNumberModel(currentYear, currentYear - 10, currentYear, 1));

        endDatePanel.add(endDateLabel);
        endDatePanel.add(new JLabel("Jour"));
        endDatePanel.add(endDaySpinner);
        endDatePanel.add(new JLabel("Mois"));
        endDatePanel.add(endMonthSpinner);
        endDatePanel.add(new JLabel("Année"));
        endDatePanel.add(endYearSpinner);
        mainPanel.add(endDatePanel);

        mainPanelContainer.setLayout(new BorderLayout());
        mainPanelContainer.add(mainPanel, BorderLayout.CENTER);
        mainPanelContainer.add(searchButton, BorderLayout.SOUTH);

        add(mainPanelContainer);
        add(tableScrollPane);

        searchButton.addActionListener(new SearchButtonListener());
    }

    public void setMainWindows(MainWindows mainWindows) {
        this.mainWindows = mainWindows;
    }

    public boolean areDatesValid(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        try {
            LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
            LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);
            return !startDate.isAfter(endDate);
        } catch (Exception e) {
            return false;
        }
    }

    public void displayResult() {
        tableModel.setRowCount(0);

        int starRating = (int) ratingSpinner.getValue();

        try {
            if (areDatesValid((int) startYearSpinner.getValue(), (int) startMonthSpinner.getValue(), (int) startDaySpinner.getValue(),
                    (int) endYearSpinner.getValue(), (int) endMonthSpinner.getValue(), (int) endDaySpinner.getValue())) {

                LocalDate startDate = LocalDate.of((int) startYearSpinner.getValue(), (int) startMonthSpinner.getValue(), (int) startDaySpinner.getValue());
                LocalDate endDate = LocalDate.of((int) endYearSpinner.getValue(), (int) endMonthSpinner.getValue(), (int) endDaySpinner.getValue());

                ArrayList<SearchReviewsModel> searchReviewsModels = appControllers.getReviewController().searchReviewsByRatingAndDates(starRating, startDate, endDate);

                if (!searchReviewsModels.isEmpty()) {
                    for (SearchReviewsModel review : searchReviewsModels) {

                        Object[] row = {review.getReviewComment(), review.getHotelName(), review.getHotelStars(), review.getCustomerFirstName(), review.getCustomerLastName()};
                        tableModel.addRow(row);

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Aucun avis non anonymes trouvés");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Dates invalides. La date de début doit être inférieure à la date de fin. Vérifiez aussi que la date de jour correspond au mois.");
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Erreur lors du traitement d'affichage des avis : " + exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class SearchButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            displayResult();
        }
    }
}
