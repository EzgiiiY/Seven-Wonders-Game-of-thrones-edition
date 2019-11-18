package backend.models;

import javafx.scene.paint.Color;

public class Civic extends Card {
    private int victoryPoint;
    private int seasonalEffect;

    public Civic(String name, int[] cardFreq, int age, Cost cost, Color color, String imagePath, int victoryPoint, int seasonalEffect){
        super(name, cardFreq, age, cost, color, imagePath);
        this.victoryPoint = victoryPoint;
        this.seasonalEffect = seasonalEffect;
    }

    public int getSeasonalEffect() {
        return seasonalEffect;
    }
    public int getVictoryPoint() {
        return victoryPoint;
    }
}