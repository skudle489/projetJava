package userInterface;

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
    private JPanel mainPanel, mainPanelContainer, buttonPanelContainer, starPanel;
    private JSpinner ratingSpinner;
    private JLabel ratingLabel;

    private JPanel startDatePanel;
    private JSpinner startDaySpinner, startMonthSpinner, startYearSpinner;
    private JLabel startDateLabel;

    private JPanel endDatePanel;
    private JSpinner endDaySpinner, endMonthSpinner, endYearSpinner;
    private JLabel endDateLabel;

    private JPanel reviewsResultPanel;

    private JButton searchButton;
    private AppControllers appControllers;


    private JTable reservationsTable;
    private DefaultTableModel tableModel;

    public SearchReviewsPanel(AppControllers appControllers) {
        this.appControllers = appControllers;
        setUpUI();
    }

    public void setUpUI() {
        setLayout(new BorderLayout());

        mainPanelContainer = new JPanel();

        String[] columns = {"Commentaire", "Nom hotel", "Nombre d'étoiles de l'hotel", "Prénom", "Nom"};
        tableModel = new DefaultTableModel(columns, 0);
        reservationsTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(reservationsTable);

        buttonPanelContainer = new JPanel();
        searchButton = new JButton("Chercher les avis non anonymes entre les deux dates");
        searchButton.setPreferredSize(new Dimension(350, 25));



        mainPanel = new JPanel(new GridLayout(2, 2));
        ratingLabel = new JLabel(" Étoile des avis");
        mainPanel.add(ratingLabel);
        starPanel = new JPanel();
        starPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        starPanel.setBorder(BorderFactory.createEmptyBorder(30, 250, 0, 0));
        mainPanel.add(starPanel);


        SpinnerNumberModel ratingModel = new SpinnerNumberModel(1, 1, 5, 1);
        ratingSpinner = new JSpinner(ratingModel);
        ratingSpinner.setPreferredSize(new Dimension(400, 70));
        starPanel.add(ratingSpinner);

        startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startDatePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0 ));
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
        endDatePanel.setBorder(BorderFactory.createEmptyBorder(30, 250, 0, 0 ));
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
        mainPanelContainer.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        mainPanelContainer.add(mainPanel, BorderLayout.CENTER);
        mainPanelContainer.add(buttonPanelContainer, BorderLayout.SOUTH);
        buttonPanelContainer.add(searchButton);

        add(mainPanelContainer, BorderLayout.CENTER);
        add(tableScrollPane, BorderLayout.SOUTH);

        searchButton.addActionListener(new SearchButtonListener());
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
                JOptionPane.showMessageDialog(null, "Dates invalides. La date de début doit être inférieure à la date de fin. Vérifiez aussi que la date de jour correspond au mois. (Exemple : 30 jours dans avril, et 31 dans mai)");
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