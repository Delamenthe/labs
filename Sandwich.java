package bsu.rfe.java.group6.lab1.Pomoz.varC16;

    public class Sandwich extends Food {

        public Sandwich(String component1, String component2)
        {
            super("Бутерброд");
            par1 = component1;
            par2 = component2;
            par = 2;
        }

        public boolean equals(Object arg0)
        {
            if (super.equals(arg0)) {
                if (!(arg0 instanceof Sandwich)) return false;
                if (!(par1.equals(((Sandwich) arg0).par1))) return false;
                return par2.equals(((Sandwich) arg0).par2);
            } else
                return false;
        }

        public Double calculateCalories()
        {
            calories = 0.0;

            if (this.par1.equals("Колбаса") && this.par2.equals("Сыр")) {
                calories += 164.0;
            }
            if (this.par1.equals("Помидор") && this.par2.equals("Сыр")) {
                calories += 214.0;
            }
            if (this.par1.equals("Шпроты") && this.par2.equals("Морковь")) {
                calories += 264.0;
            }
            if (this.par1.equals("Семга") && this.par2.equals("Масло")) {
                calories += 262.0;
            }
            if (this.par1.equals("Авокадо") && this.par2.equals("Яйцо")) {
                calories += 117.0;
            }
            if (this.par1.equals("Творог") && this.par2.equals("Зелень")) {
                calories += 252.0;
            }
            return calories;
        }

        public String getComponent1()
        {
            return par1;
        }

        public String getComponent2()
        {
            return par2;
        }

        public String toString()
        {
            return super.toString() + " с начинками '" + par1.toUpperCase() + "' и '" + par2.toUpperCase()+"'";
        }

        public void consume()
        {
            System.out.println(this + " съеден");
        }


    }
