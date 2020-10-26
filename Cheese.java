package bsu.rfe.java.group6.lab1.Pomoz.varC16;

public class Cheese extends Food {
    public Cheese()
    {
        super("Сыр");
        par = 0;
    }
    public Double calculateCalories()
    {
        calories = 30.0;
        return calories;
    }
    public void consume()   {
        System.out.println(this + " съеден");
    }
}