package model;

import exceptions.StarCreationException;

public class Star {
    private int starNumber;

    public Star(int starNumber) throws StarCreationException {
        setStarNumber(starNumber);
    }

    public void setStarNumber(int starNumber) throws StarCreationException {
        if (starNumber < 0 || starNumber > 5) {
            throw new StarCreationException("Le nombre d'étoiles doit etre en 1 et 5");
        } else {
            this.starNumber = starNumber;
        }
    }

    @Override
    public String toString(){
        return starNumber + "";
    }

    public int getStarNumber() {
        return starNumber;
    }
}
