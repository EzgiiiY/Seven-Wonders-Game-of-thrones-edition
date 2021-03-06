package backend.models;

import backend.app.Main;

import java.util.ArrayList;

import static backend.app.constants.*;
import static backend.models.Numbers.countPrimes;
import static backend.models.Numbers.gcd;

public class House {
    public String name;
    public ArrayList<Integer> resourcesList; //1 means no resources
    public ArrayList<Wonder> wonders;
    public ArrayList<Wonder> playedWonders;
    public int militaryShields;
    public int coins;
    public int buff;
    public int nerf;
    public int victoryPoints;



    public House(String name){
        this.name = name;
        resourcesList = new ArrayList<>();
        //1 represents no resources
        resourcesList.add(1);
        militaryShields = 0;
        addCoins(3);

        if(name.equalsIgnoreCase("lannister")) //House buff lannister
            addCoins(3);

        if(name.equalsIgnoreCase("targaryen")) //House buff targaryen
            militaryShields++;
    }

    public House(String name, ArrayList<Integer> resourcesList, ArrayList<Wonder> wonders, int militaryShields, int buff, int nurf) {
        this.name = name;
        this.resourcesList = resourcesList;
        this.wonders = wonders;
        this.playedWonders = new ArrayList<>();
        this.militaryShields = militaryShields;
        this.coins = 3;
        this.buff = buff;
        this.nerf = nerf;
        this.victoryPoints = 0;


    }

    public void addResource(ArrayList<Integer> newRes){
        if(newRes.size() > 0){
            ArrayList<Integer> tempRes = new ArrayList<Integer>(newRes.size() * resourcesList.size());
            for(int i = 0; i < newRes.size(); i++){
                for(int j = 0; j < resourcesList.size(); j++){
                    tempRes.add(resourcesList.get(j) * newRes.get(i));
                }
            }
            resourcesList = tempRes;
        }
    }

    public void addResource(int[] newRes){
        if(newRes.length > 0){
            ArrayList<Integer> tempRes = new ArrayList<Integer>(newRes.length * resourcesList.size());
            for(int i = 0; i < newRes.length; i++){
                for(int j = 0; j < resourcesList.size(); j++){
                    tempRes.add(resourcesList.get(j) * newRes[i]);
                }
            }
            resourcesList = tempRes;
        }
    }

    public void printResources(){
        System.out.println("Printing possible resources:");
        for(int i = 0; i < resourcesList.size(); i++){
            System.out.println("" + i + ": " + factorResources(resourcesList.get(i)));
        }
        System.out.println();
    }

    private String factorResources(int res){
        String string = "";
        //7 primes for 7 resources
        int factors[] = {RES1, RES2, RES3, RES4, RES5, RES6, RES7};
        for(int i = 0; i < factors.length; i++){
            int counter = 0;
            //int tempres = res;
            while (res % factors[i] == 0){
                counter++;
                res/=factors[i];
            }
            string = string + counter;
            if (i != factors.length -1){
                string = string + ", ";
            }
        }
        return string;
    }

    private ArrayList<String> getPlayedStructures(){
        ArrayList<String> result = new ArrayList<>();
        if(Main.gameEngine.getPlayedCards() != null) {
            for (Card card : Main.gameEngine.getPlayedCards()) {
                result.add(card.name);
            }
        }
        //TODO Implement this
        return result;
    }

    //returns 0,- if can't, 1,- if it can be built without trading, 2,remaining
    // if it requires trading for the remaining resources (using CostResult class)
    public CostResult canAfford(Cost cost){
        CostResult result = new CostResult(0,1);
        System.out.println("Required: " + cost.getCoins() + " money and ["
                + factorResources(cost.getResources()) + "] or " + cost.getPrereq());

        //check cost.getMoney()
        boolean canAffordMoney = cost.getCoins() <= coins;

        //check cost.getPrereq()
        ArrayList<String> availStructs = getPlayedStructures();
        for(int i = 0; i < availStructs.size(); i++){
            if(cost.getPrereq().equals(availStructs.get(i))){
                System.out.println("Can be built because " + availStructs.get(i) + " is already built.");
                result.code = 1;
                return result;
            }
        }


        //checking cost.getResources().
        for(int i = 0; i <resourcesList.size(); i++){
            if (canAffordMoney &&(resourcesList.get(i) % cost.getResources() == 0)){
                System.out.println("Can build with " + factorResources(resourcesList.get(i)));
                pay(cost);
                result.code = 1;
                return result;
            } else {
                int gcd = gcd(resourcesList.get(i), cost.getResources());
                int remaining = cost.getResources() / gcd;

                //check if neighbors have it
                System.out.println("To use [" + factorResources(resourcesList.get(i)) + "], it requires " + countPrimes(remaining)
                    +" more resource(s) which is/are " + remaining +" = [" + factorResources(remaining) +"]");
                result.set(2, remaining);
            }
            System.out.println("Resources index: " + i + "\nTotal resources: " + resourcesList.size());
        }
        return result;
    }

    public void buildWonder() {
        for (Wonder wonder : wonders) {
            if (!wonder.isBuilt()) {
                this.addResource(wonder.getResources());
                this.militaryShields += wonder.getMilitaryShields();
                this.victoryPoints += wonder.getVictoryPoints();
                this.coins += wonder.getCoins();
                wonder.build();
                getPlayedWonders().add(wonder);
                break;
            }
        }
    }

    private void pay(Cost cost) {
        //coins = coins - cost.getCoins();
    }

    public void addCoins(int coins){
        this.coins += coins;
    }


    public ArrayList<Integer> getResourcesList() {
        return resourcesList;
    }

    public int getMilitaryShields() {
        return militaryShields;
    }


    public int getCoins() {
        return coins;
    }

    public int getBuff() {
        return buff;
    }

    public int getNerf() {
        return nerf;
    }

    public ArrayList<Wonder> getWonders() {
        if( wonders == null)
            wonders = new ArrayList<>();
        return wonders;
    }

    public ArrayList<Wonder> getPlayedWonders() {
        if( playedWonders == null)
            playedWonders = new ArrayList<>();
        return playedWonders;
    }

}
