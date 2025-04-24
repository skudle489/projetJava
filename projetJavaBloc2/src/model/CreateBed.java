package model;

import java.util.HashMap;

public class CreateBed {
    private static final HashMap<String,Bed> beds = new HashMap<>();

    public static Bed getBed(String type){
        Bed bed = beds.get(type);
        if(bed == null){
            bed = new Bed(type);
            beds.put(type, bed);
        }
        return bed;
    }

}
