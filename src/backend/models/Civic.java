package backend.models;

import javafx.scene.paint.Color;

public class Civic extends Card {
    private int victoryPoint;
    private int seasonalEffect;

    public Civic(String name, int cardFreq, int age, Cost cost, String imagePath,
                 String iconPath, String backPath, String chain1, String chain2, int victoryPoint, int seasonalEffect){

        super(name, cardFreq, age, cost, "civic.jpg", iconPath, "blue.jpg", chain1, chain2);
        this.victoryPoint = victoryPoint;
        this.seasonalEffect = seasonalEffect;
    }

    public int getSeasonalEffect() {
        return seasonalEffect;
    }
    public int getVictoryPoints() {
        return victoryPoint;
    }

    @Override
    public boolean isCivic(){
        return true;
    }
}
