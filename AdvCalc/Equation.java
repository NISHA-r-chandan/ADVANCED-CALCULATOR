package AdvCalc;
import java.util.Scanner;
class Equation{ 
    
    //menu based
    public static void coeff(){
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Simul Equation\n2. Polynomial\n3.Exit");
        while(true){
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            if(choice == 1){
                //input coefficients of the equations
                System.out.println("Equation1: a1x +b1y = c1 ");
                System.out.println("Enter value of a1: ");
                double a1 = sc.nextDouble();
                
                System.out.println("Enter value of b1: ");
                double b1 = sc.nextDouble();

                System.out.println("Enter value of c1: ");
                double c1 = sc.nextDouble();

                System.out.println("Equation1: a2x +b2y = c2 ");
                System.out.println("Enter value of a2: ");
                double a2 = sc.nextDouble();

                System.out.println("Enter value of b2: ");
                double b2 = sc.nextDouble();

                System.out.println("Enter value of c2: ");
                double c2 = sc.nextDouble();
                
                //Return soution
                System.out.println("solution: "+ Calculation.simuleq(a1, b1, c1, a2, b2, c2));
            }
            else if(choice == 2){
                System.out.println("Polynomial: Equation : ax² + bx + c = 0");
                System.out.println("Enter value of a:");
                double a = sc.nextDouble();

                System.out.println("Enter value of b:");
                double b = sc.nextDouble();

                System.out.println("Enter the value of c:");
                double c = sc.nextDouble();
                
                //print solution
                System.out.println("Solution: "+ Calculation.polyeq(a, b, c));
            }
            else if(choice == 3){
                System.out.println("Program terminating---");
                System.exit(0);
            }
            else{
                System.out.println("Invalid input");
                break;
            }
        }
    sc.close();
    }
    public static void main(String[] args){
        Equation.coeff();
    }    
}     
    
//calculation
class Calculation{
    //final constant to avoid errors due to values in double
    public static final double EPSILON = 1e-9;

    //logic for simul equations
    public static String simuleq(double a1, double b1, double c1, double a2, double b2, double c2){
        double determinant = (a1*b2) - (a2*b1);
        if(Math.abs(determinant)<EPSILON){
            return "No unique solution exists";            
        }

        double x = ((c1 * b2) - (c2 * b1) / determinant);
        double y = ((c1 * a2) - (c2 * a1) / determinant);

        //return string
        return "x = "+x+" y = "+y;
    }

    //logic for polynomial equations
    public static String polyeq(double a, double b, double c){
        if(Math.abs(a) < EPSILON){
            return " Not a quadratic equation";
        }

        double determinant = b * b - 4 * a * c;
         
        if(determinant > 0){
            double r1 = (-b + Math.sqrt(determinant))/(2*a);
            double r2 = (-b - Math.sqrt(determinant))/(2*a);
            return("Two real and distinct values: root1 = "+r1+" root2 = "+r2);
        }
        else if(determinant < EPSILON){
            double r1 = (-b)/(2*a);
            double r2 = (-b)/(2*a);
            return("Two real and equal values: root1 = "+r1+" root2 = "+r2);
        }
        else{
            double real = -b/(2*a);
            double imag = Math.sqrt(-determinant)/(2*a);
            return("Two imaginary values: root1 = "+real+"+i"+imag+" root2 = "+real+"-i"+imag);

        }
    }

}
