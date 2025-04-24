package model;


import exceptions.EquipmentCreationException;

public class Equipment {
    private String label;

    public Equipment(String label) throws EquipmentCreationException {
        setLabel(label);
    }

    public void setLabel(String label) throws EquipmentCreationException {
        if (label == null){
            throw new EquipmentCreationException("Le label pour l'équipement ne peut etre null");
        } else {
            if (label.isEmpty()){
                throw new EquipmentCreationException("Le label pour l'équipement ne peut etre vide");
            } else {
                this.label = label;
            }
        }
    }
}
