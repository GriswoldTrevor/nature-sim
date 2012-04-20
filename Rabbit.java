import java.util.ArrayList;
import java.util.List;
import java.awt.Image;
import java.util.Collections;

public class Rabbit extends Animal {
    private static double calories;
    private static final int maxBreedingTime = 15; // max timesteps before it breeds

    private static final int sightDistance = 6;
    private static final int moveDistance = 2;
    private static double maxHunger;
    private static final int maxAge = 100;
    
    private static ArrayList<String> prey = new ArrayList<String>();
    private static ArrayList<String> predators = new ArrayList<String>();
    
    public Rabbit(Location loc){
        Debug.echo("Constructing a new Rabbit object");
        setLocation(loc);
        hunger = maxHunger / 4;
        age = 0;
        breedingTime = maxBreedingTime;
    }

    public void act(Grid grid){
        Debug.echo("Here is where the Rabbit would act");
        
        GridSquare mySquare = grid.get(getLocation());
        List<DistanceSquarePair> visibleSquares = grid.getAdjacentSquares(getLocation(), sightDistance);
        List<DistanceSquarePair> predatorSquares = grid.getOrganismSquares(visibleSquares, predators);
        List<DistanceSquarePair> preySquares = grid.getOrganismSquares(visibleSquares, prey);
        List<DistanceSquarePair> emptySquares = grid.getEmptySquares(visibleSquares);

        breed(grid);
        
        Plant myPlant = mySquare.getPlant();
        if(predatorSquares.size() > 0) {
            Debug.echo("OH SHIT RUN?");
        }
        if (myPlant != null && myPlant.isAlive() && prey.contains(myPlant.getClass().getName())){
            eat(myPlant.getCalories());
            return;
        } else {
            List<DistanceSquarePair> reachableSquares = grid.getAdjacentSquares(getLocation(), moveDistance);
            Collections.shuffle(reachableSquares);
            for(DistanceSquarePair pair: reachableSquares){
                if(emptySquares.contains(pair) && preySquares.contains(pair)){
                    move(grid, pair.gridSquare);
                    
                    mySquare = grid.get(getLocation());
                    myPlant = mySquare.getPlant();
                    eat(myPlant.getCalories());
                    return;
                }
            }
            List<DistanceSquarePair> emptyReachableSquares = emptySquares;
            emptyReachableSquares.retainAll(reachableSquares);
            if (emptyReachableSquares.size() > 0) {
                move(grid, emptyReachableSquares.get(Util.randInt(emptyReachableSquares.size())).gridSquare);
            }
        }
    }

    public void addMyType(Grid grid, GridSquare square) {
        grid.addAnimal(new Rabbit(square.getLocation()), square);
    }

    public static void addPrey(String p)     { prey.add(p);      }
    public static void addPredator(String p) { predators.add(p); }
    public static void setCalories(double c)    { 
        calories = c;
        maxHunger = c * 50;
    }
    
    protected double getMaxHunger(){
        return maxHunger;
    }

    protected int getMaxAge(){
        return maxAge;
    }

    protected int getSightDistance(){
        return sightDistance;
    }

    protected int getMoveDistance(){
        return moveDistance;
    }

    public int getMaxBreedingTime() {
        return maxBreedingTime;
    }
    
    public double getCalories()     { return calories;                          }
    public String toString()        { return "Rabbit";                          }
    public Image getImage()         { return Resources.imageByName("Rabbit");   }
}
