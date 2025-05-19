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
    private JPanel mainPanel, mainPanelContainer, listLabelsPanel, buttonPanel ;
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

        mainPanelContainer = new JPanel();
        mainPanelContainer.setLayout(new BorderLayout());


        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));
        mainPanelContainer.add(mainPanel);


        labelsBedroomsLabel = new JLabel("  Type de chambre recherché :");
        listLabelsPanel = new JPanel();
        listLabelsPanel.setLayout(new BorderLayout());
        listLabelsPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 15, 10));


        labelsBedroomsList = new JList<>();

        buttonPanel = new JPanel();
        sendRequestButton = new JButton("Recherche");
        sendRequestButton.setPreferredSize(new Dimension(200, 25));
        sendRequestButton.addActionListener(new SearchButtonListener());

        loadBedroomTypesInJList();

        mainPanel.add(labelsBedroomsLabel);
        listLabelsPanel.add(new JScrollPane(labelsBedroomsList), BorderLayout.CENTER);
        mainPanel.add(listLabelsPanel);
        buttonPanel.add(sendRequestButton);
        mainPanelContainer.add(buttonPanel, BorderLayout.SOUTH);

        String[] columnNames = {"Numéro de chambre", "Prix par jour", "Nom hotel", "Numéro de rue", "Rue", "Ville", "Pays"};
        tableModel = new DefaultTableModel(columnNames, 0);
        informationsBedroomsTable = new JTable(tableModel);

        for (int i = 0; i < columnNames.length; i++) {
            informationsBedroomsTable.getColumnModel().getColumn(i).setPreferredWidth(100);
        }

        JScrollPane tableScrollPane = new JScrollPane(informationsBedroomsTable);

        this.add(mainPanelContainer, BorderLayout.CENTER);
        this.add(tableScrollPane, BorderLayout.SOUTH);
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
                JOptionPane.showMessageDialog(mainPanelContainer, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}