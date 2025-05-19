package dataAccess;

import exceptions.GetAllBedsFromBedroomException;
import model.BedroomOwnsBed;

import java.util.ArrayList;

public interface IBedroomOwnsBedDataAccess {
    ArrayList<BedroomOwnsBed> getAllBedsFromBedroom(int bedroom, int hotel) throws GetAllBedsFromBedroomException;
}
