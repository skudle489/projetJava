package dataAccess;

import exceptions.BedroomTypeCreationException;
import model.BedroomType;

import java.util.ArrayList;

public interface IBedroomTypeDataAccess {
    public ArrayList<BedroomType> getAllTypesBedroom() throws BedroomTypeCreationException;
}
