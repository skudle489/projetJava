package userInterface;

import exceptions.BedroomTypeCreationException;
import model.BedroomInformationsModel;
import model.BedroomType;
import utils.AppControllers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchBedroomPanel extends JPanel {
    private JPanel mainPanel;
    private JLabel labelsBedroomsLabel;
    private JList<String> labelsBedroomsList;
    private JTable informationsBedroomsTable;
    private JButton sendRequestButton;
    private AppControllers appControllers;
    private DefaultTableModel tableModel;

    public SearchBedroomPanel(AppControllers appControllers) throws BedroomTypeCreationException {
        this.appControllers = appControllers;
        setUpUI();
    }


    public void setUpUI() throws BedroomTypeCreationException {
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 3));

        labelsBedroomsLabel = new JLabel("Type de chambre recherché :");
        labelsBedroomsList = new JList<>();
        sendRequestButton = new JButton("Recherche");
        sendRequestButton.addActionListener(new SearchButtonListener());

        loadBedroomTypesInJList();

        mainPanel.add(labelsBedroomsLabel);
        mainPanel.add(new JScrollPane(labelsBedroomsList));
        mainPanel.add(sendRequestButton);

        String[] columnNames = {"Numéro de chambre", "Prix par jour", "Nom hotel", "Numéro de rue", "Rue", "Ville", "Pays"};
        tableModel = new DefaultTableModel(columnNames, 0);
        informationsBedroomsTable = new JTable(tableModel);

        for (int i = 0; i < columnNames.length; i++) {
            informationsBedroomsTable.getColumnModel().getColumn(i).setPreferredWidth(100);
        }

        JScrollPane tableScrollPane = new JScrollPane(informationsBedroomsTable);

        add(mainPanel, BorderLayout.CENTER);
        add(tableScrollPane, BorderLayout.SOUTH);
    }

    private void loadBedroomTypesInJList() throws BedroomTypeCreationException {
        ArrayList<BedroomType> bedroomTypes = appControllers.getBedroomTypeController().getAllTypesBedroom();
        DefaultListModel<String> model = new DefaultListModel<>();

        for (BedroomType bedroomType : bedroomTypes) {
            model.addElement(bedroomType.getLabel());
        }

        labelsBedroomsList.setModel(model);
        labelsBedroomsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (labelsBedroomsList.getSelectedValue() != null) {
                    tableModel.setRowCount(0);

                    ArrayList<BedroomInformationsModel> bedroomInfos = appControllers.getBedroomController().getAllBedroomsInformationsByType(labelsBedroomsList.getSelectedValue());

                    for (BedroomInformationsModel info : bedroomInfos) {
                        Object[] row = {
                                info.getBedroomNumber(),
                                info.getCostPerDay(),
                                info.getHotelName(),
                                info.getStreetNumber(),
                                info.getStreet(),
                                info.getCity(),
                                info.getCountryName()
                        };
                        tableModel.addRow(row);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}