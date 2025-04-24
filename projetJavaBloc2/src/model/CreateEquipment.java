package model;

import exceptions.EquipmentCreationException;

import java.util.HashMap;

public class CreateEquipment {
    private static final HashMap<String, Equipment> equipments = new HashMap<>();

    public static Equipment getEquipment(String equipmentName) throws EquipmentCreationException {
        Equipment equipment = equipments.get(equipmentName);
        if (equipment == null) {
            equipment = new Equipment(equipmentName);
            equipments.put(equipmentName, equipment);
        }
        return equipment;
    }
}
