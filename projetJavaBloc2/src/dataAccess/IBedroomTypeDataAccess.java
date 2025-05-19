package dataAccess;

import exceptions.BedroomTypeCreationException;
import model.BedroomType;

import java.util.ArrayList;

public interface IBedroomTypeDataAccess {
    ArrayList<BedroomType> getAllTypesBedroom() throws BedroomTypeCreationException;
}
