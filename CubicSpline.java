import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Calculates a cubic spline given a set of data points.
 * Based on the 9th edition of the book "Numerical Analysis" by Richard :. Burden and J. Douglas Faires.
 * Program written for Java 7 and up.
 * Written for a Numerical Analysis course.
 *
 * @author DavisGoulet
 */
public class CubicSpline {
    /* Example:
     * Given the input "3;1,2,3,4;0,2,6,4" corresponding to the points (1,0), (2,2), (3,6), and (4,4).
     * The program will return:
     * j          a[j]       b[j]       c[j]       d[j]
     * 0          0          1.0667     0          0.9334
     * 1          2          3.8667     2.8        -2.6666
     * 2          6          1.4667     -5.2       1.7334
     *
     * This corresponds to the equations:
     * y = 0 + 1.0667*(x-1) + 0.0*(x-1)^2 + 0.9334*(x-1)^3 for [1,2]
     * y = 2 + 3.8667*(x-2) + 2.8*(x-2)^2 - 2.6666*(x-2)^3 for [2,3]
     * y = 6 + 1.4667*(x-3) - 5.2*(x-3)^2 + 1.7334*(x-3)^3 for [3,4]
     */

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Please enter the data if the format of n;x0,x1,x2,...,xn+1;y0,y1,y2,...,yn+1 where: \n"
                + "n is the number of splines.\n"
                + "xi is the x value for the ith point.\n"
                + "yi is f(xi) for the corresponding point.");

        //Waits for user input.
        String input = sc.nextLine();
        calcCubicSpline(input);
    }

    //Method to calculate the values for a cubic spline.
    public static void calcCubicSpline(String s) {
        //Splitting the input up into values.
        String[] input = s.split(";");
        String[] xInput = input[1].split(",");
        String[] aInput = input[2].split(",");
        int numElements = Integer.parseInt(input[0]);

        double[] xValues = new double[numElements + 1];
        double[] aValues = new double[numElements + 1];
        double[] bValues = new double[numElements];
        double[] cValues = new double[numElements + 1];
        double[] dValues = new double[numElements];

        //Returns error if the n value given is not 1 less then the number of x values.
        if (numElements + 1 != xInput.length || xInput.length != aInput.length) {
            System.out.println("Invalid input. Please try again.");
            return;
        }

        //Parsing the input into numbers.
        for (int i = 0; i <= numElements; i++) {
            try {
                xValues[i] = Double.parseDouble(xInput[i]);
                aValues[i] = Double.parseDouble(aInput[i]);
            } catch (NumberFormatException e) {
                //Returns error if one of the inputs is not a number.
                System.out.println("Invalid input. Please try again.");
                return;
            }
        }


        //Step #1

        double[] h = new double[numElements];
        for (int i = 0; i <= numElements - 1; i++) {
            h[i] = xValues[i + 1] - xValues[i];
        }

        //Step #2

        double[] α = new double[numElements];
        for (int i = 1; i <= numElements - 1; i++) {

            α[i] = (((3 / h[i]) * (aValues[i + 1] - aValues[i])) - ((3 / h[i - 1]) * (aValues[i] - aValues[i - 1])));
        }

        //Step #3

        double[] l = new double[numElements + 1];
        double[] u = new double[numElements + 1];
        double[] z = new double[numElements + 1];

        l[0] = 1;
        u[0] = 0;
        z[0] = 0;

        //Step #4

        for (int i = 1; i <= numElements - 1; i++) {
            l[i] = ((2 * (xValues[i + 1] - xValues[i - 1])) - (h[i - 1] * u[i - 1]));
            u[i] = h[i] / l[i];
            z[i] = (α[i] - (h[i - 1] * z[i - 1])) / l[i];
        }

        //Step #5

        l[numElements] = 1;
        z[numElements] = 0;
        cValues[numElements] = 0;

        //Step #6

        for (int j = numElements - 1; j >= 0; j--) {
            cValues[j] = (z[j] - (u[j] * cValues[j + 1]));
            bValues[j] = (((aValues[j + 1] - aValues[j]) / h[j]) - ((h[j] * (cValues[j + 1] + 2 * cValues[j])) / 3));
            dValues[j] = ((cValues[j + 1] - cValues[j]) / (3 * h[j]));
        }

        //Step #7

        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);

        System.out.println("Results (Rounded to 4 decimal places): \n");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s \n", "j", "a[j]", "b[j]", "c[j]", "d[j]");

        for (int j = 0; j <= numElements - 1; j++) {
            System.out.printf("%-10d %-10s %-10s %-10s %-10s \n", j, df.format(aValues[j]), df.format(bValues[j]), df.format(cValues[j]), df.format(dValues[j]));
        }

    }
}
