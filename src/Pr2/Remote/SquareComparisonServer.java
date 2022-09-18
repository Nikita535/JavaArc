package Pr2.Remote;

import java.rmi.RemoteException;

public class SquareComparisonServer implements SquareComparison{
    double D=0;
    double x1=0, x2=0;

    @Override
    public String squareComparison(int a, int b, int c) throws RemoteException {
        D = b * b - 4 * a * c;
        if (D > 0) {
            x1 = (-b - Math.sqrt(D)) / (2 * a);
            x2 = (-b + Math.sqrt(D)) / (2 * a);
            return "Корни уравнения: x1 = " + x1 + ", x2 = " + x2;
        }
        else if (D == 0) {
            double x;
            x = -b / (2 * a);
            return "Уравнение имеет единственный корень: x = " + x;
        }
        else {
            return "Уравнение не имеет действительных корней!";
        }
    }
}
