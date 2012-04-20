import java.awt.Image;

public abstract class Animal extends Organism {
    protected int age;
    protected double hunger;
    
    protected abstract double getMaxHunger();
    protected abstract int getMaxAge();
    protected abstract int getSightDistance();
    protected abstract int getMoveDistance();
    public abstract double getCalories();
    public abstract void act(Grid grid);
    public abstract Image getImage();
    
    public void step(Grid grid){
        age++;
        hunger+= getMaxHunger()/50;
        if(isOld() || isStarving()) {
            if(isOld()){
                Debug.echo("Animal at "+getLocation()+" died due to old age");
            } else {
                Debug.echo("Animal at "+getLocation()+" died due to hunger");
            }
            grid.removeAnimal(getLocation());
        } else {
            act(grid);
        }
    }
    public boolean isOld(){
        return age >= getMaxAge();
    }
    public boolean isStarving(){
        return hunger >= getMaxHunger();
    }
    protected void eat(double amount){
        hunger -= amount;
        if(hunger < 0) {
            hunger = 0;
        }
    }
    protected void move(Grid grid, Location newLocation){
        grid.removeAnimal(getLocation());
        grid.addAnimal(this, newLocation);
        setLocation(newLocation);
    }
    protected void move(Grid grid, GridSquare newGridSquare){
        move(grid, newGridSquare.getLocation());
    }
}
