import AdvCalc.Equation;
import AdvCalc.Matrix;
import AdvCalc.Trig;
import java.util.Scanner;

public class Cal {

    // Read matrix values
    public static Matrix inputMatrix(Scanner sc) {
        System.out.print("Enter rows: ");
        int rows = sc.nextInt();

        System.out.print("Enter columns: ");
        int cols = sc.nextInt();

        double[][] values = new double[rows][cols];

        System.out.println("Enter matrix elements:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                values[i][j] = sc.nextDouble();
            }
        }

        return new Matrix(values);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== ADVANCED CALCULATOR ===");
            System.out.println("1. Trigonometry");
            System.out.println("2. Matrix Operations");
            System.out.println("3. Equation");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("1. Sin  2. Cos  3. Tan");
                    System.out.print("Choose: ");
                    int op = sc.nextInt();

                    System.out.print("Enter angle in degrees: ");
                    double angle = sc.nextDouble();

                    if (op == 1)
                        System.out.println("Result: " + Trig.sin(angle));
                    else if (op == 2)
                        System.out.println("Result: " + Trig.cos(angle));
                    else if (op == 3)
                        System.out.println("Result: " + Trig.tan(angle));
                    else
                        System.out.println("Invalid choice!");
                    break;

                case 2:
                    System.out.println("Enter first matrix:");
                    Matrix m1 = inputMatrix(sc);

                    System.out.println("1. Add");
                    System.out.println("2. Subtract");
                    System.out.println("3. Multiply");
                    System.out.println("4. Scalar Multiply");
                    System.out.print("Choose: ");
                    op = sc.nextInt();

                    if (op == 4) {
                        System.out.print("Enter scalar: ");
                        double scalar = sc.nextDouble();
                        System.out.println(m1.multiply(scalar));
                    } else if (op >= 1 && op <= 3) {
                        System.out.println("Enter second matrix:");
                        Matrix m2 = inputMatrix(sc);

                        try {
                            if (op == 1)
                                System.out.println(m1.add(m2));
                            else if (op == 2)
                                System.out.println(m1.subtract(m2));
                            else
                                System.out.println(m1.multiply(m2));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid choice!");
                    }
                    break;

                case 3:
                    Equation.coeff();
                    break;

                case 4:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);

        sc.close();
    }
}