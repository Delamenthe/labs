package bsu.rfe.java.group6.lab1.Pomoz.varC16;
import java.lang.reflect.Constructor;
import java.util.*;

public class Main
{
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception
    {
        Food[] breakfast = new Food[20];
        int i = 0;
        boolean flag1, flag2, flag3, flag4, flag5;
        flag1=flag2=flag3=flag4=flag5=false;

        for (String arg : args)
        {
            String[] parts = arg.split(("/"));
            try {
                Class myClass = Class.forName("bsu.rfe.java.group6.lab1.Pomoz.varC16." + parts[0]);
                if (parts.length == 1) {
                    Constructor constructor = myClass.getConstructor();
                    breakfast[i] = (Food) constructor.newInstance();
                    flag3=true;
                    i++;
                } else if (parts.length == 2) {
                    Constructor constructor = myClass.getConstructor(String.class);
                    breakfast[i] = (Food) constructor.newInstance(parts[1]);
                    flag4=true;
                    i++;
                } else if (parts.length == 3) {
                    Constructor constructor = myClass.getConstructor(String.class, String.class);
                    breakfast[i] = (Food) constructor.newInstance(parts[1], parts[2]);
                    flag5=true;
                    i++;
                }
            } catch (ClassNotFoundException e)
            {
                switch (parts[0])
                {
                    case "-sort":
                        flag1 = true;
                        break;
                    case "-calories":
                        flag2 = true;
                        break;
                    default:
                        System.out.println("Класс " + parts[0] + " не найден.");
                        break;
                }

            }
            catch (NoSuchMethodException e)
            {
                System.out.println("Метод класса " + parts[0] + " не был найден.");
            }
        }

        if (flag1)
        {
            Arrays.sort(breakfast, new Comparator() {
                public int compare(Object o1, Object o2)
                {
                    if (o1 == null || ((Food)o1).par < ((Food)o2).par)
                        return 1;
                    if (o2 == null || ((Food)o1).par > ((Food)o2).par)
                        return -1;
                    else return 0;
                }
            });

            System.out.println("Завтрак (отсортированный вариант):");
            for (Food item : breakfast)
            {
                if (item != null)
                {
                    if (item.calculateCalories()==0.0)
                        continue;
                    item.consume();
                }
                else
                    break;
            }
        }else
        {
            System.out.println("Завтрак: ");
            for (Food item : breakfast) {
                if (item != null)
                {
                    if (item.calculateCalories()==0.0)
                    {
                        System.out.print("Такой продукт не предусмотрен (" + item.name);
                        if(item.par1!=null)
                            System.out.print(", " + item.par1);
                        if(item.par2!=null)
                            System.out.print(", " + item.par2);
                        System.out.println(")");
                        continue;
                    }
                    item.consume();
                } else {
                    break;
                }
            }
        }

        System.out.println("Итого: ");
        if(flag3){
            int apple =0;
            Food Apple = new Apple("большое");
            Food Apple1 = new Apple("среднее");
            Food Apple2 = new Apple("маленькое");
            for (Food item: breakfast){
                if (item!=null) {
                    if(item.equals(Apple) || item.equals(Apple1) || item.equals(Apple2))
                        apple++;  }
            }
            System.out.println("Колличество съеденых яблок: " + apple + " шт.");
        }

        if(flag4){
            int cheese = 0;
            Food Cheese = new Cheese();
            for (Food item: breakfast){
                if (item!=null){
                    if(item.equals(Cheese))
                        cheese++;}
            }
            System.out.println("Колличество съеденого сыра: " + cheese + " шт.");
        }

        if(flag5){
            int sandwich = 0;
            Food Sandwich1 = new Sandwich("Колбаса","Сыр");
            Food Sandwich2 = new Sandwich("Помидор","Сыр");
            Food Sandwich3 = new Sandwich("Шпроты","Морковь");
            Food Sandwich4 = new Sandwich("Семга","Масло");
            Food Sandwich5 = new Sandwich("Авокадо","Яйцо");
            Food Sandwich6 = new Sandwich("Творог","Зелень");
            for (Food item: breakfast){
                if (item!=null) {
                    if(item.equals(Sandwich1) || item.equals(Sandwich2)|| item.equals(Sandwich3)||
                            item.equals(Sandwich4) || item.equals(Sandwich5) || item.equals(Sandwich6))
                        sandwich++;
                }
            }
            System.out.println("Колличество съеденых бутербродов: " + sandwich + " шт.");
        }

        if (flag2) {
            double CaloriesCounter = 0.0;
            for (Food item : breakfast) {
                if (item != null)
                    CaloriesCounter += item.calculateCalories();
                else
                    break;
            }
            System.out.println("Общее количество калорий: " + CaloriesCounter);

        }
    }
}