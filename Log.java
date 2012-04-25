import java.awt.Image;

public class Log extends Plant {
    public Log(Location loc) {
        Debug.echo("Creating a log at location "+loc);
        setLocation(loc);
        alive = false;
        amount = 0;
        stepsUntilEdible = 0;
    }

    public void step(Grid grid){
    }

    protected int getMaxAmount() {
        return 0;
    }

    protected int getMaxStepsUntilEdible() {
        return 0;
    }

    public double getCalories() {
        return 0;
    }

    public Image getImage() {
        return Resources.imageByName("Log");
    }

    public String toString() {
        return "Log";
    }
}
