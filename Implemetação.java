import java.util.Arrays;

public class Implemetação {
    public static void main(String[] args) {
        double[][] matriz = {
                { 3, 1, 1, 0, 0, 800 }, // 2°
                { 1, 1, 0, 1, 0, 80 }, // 3°
                { 1, 0, 0, 0, 3, 80 }, // 4°
                { -10, -3, 0, 0, 0, 0 } // 1°
                // {X1, X2, F1, F2, F3, Lado Direito}
        };
        simplex(matriz);
    }

    public static void simplex(double[][] matriz) {
        int numeroLinhas = matriz.length;
        int numeroColunas = matriz[0].length;

        while (true) {
            int ColunaPivo = ColunasDeEntrada(matriz);
            if (ColunaPivo == -1) {
                break;
            }

            int linhaPivo = linhaDeSaida(matriz, ColunaPivo);

            if (linhaPivo == -1) {
                System.out.println("O problema é ilimitado.");
                return;
            }

            performPivot(matriz, linhaPivo, ColunaPivo);
        }

        double[] solucao = new double[numeroColunas - 1];
        for (int i = 0; i < numeroLinhas; i++) {
            if (matriz[i][numeroColunas - 1] >= 0) {
                int varIndex = AcharOPrincipal(matriz[i]);
                if (varIndex != -1) {
                    solucao[varIndex] = matriz[i][numeroColunas - 1];
                }
            }
        }

        System.out.println("ÓTIMA:");
        System.out.println(Arrays.toString(solucao));
        System.out.println("Os primeiros números são as variáveis independentes seguidas do valor de Z");
    }

    public static int ColunasDeEntrada(double[][] matriz) {
        int numeroColunas = matriz[0].length;
        int ColunaPivo = -1;

        for (int j = 0; j < numeroColunas - 1; j++) {
            if (matriz[matriz.length - 1][j] < 0) {
                if (ColunaPivo == -1 || matriz[matriz.length - 1][j] < matriz[matriz.length - 1][ColunaPivo]) {
                    ColunaPivo = j;
                }
            }
        }

        return ColunaPivo;
    }

    public static int linhaDeSaida(double[][] matriz, int pivotCol) {
        int numRows = matriz.length;
        int linhaPivo = -1;
        double minRatio = Double.MAX_VALUE;

        for (int i = 0; i < numRows - 1; i++) {
            if (matriz[i][pivotCol] > 0) {
                double ratio = matriz[i][matriz[i].length - 1] / matriz[i][pivotCol];
                if (ratio < minRatio) {
                    minRatio = ratio;
                    linhaPivo = i;
                }
            }
        }

        return linhaPivo;
    }

    public static void performPivot(double[][] matriz, int linhaPivo, int ColunaPivo) {
        int numeroLinhas = matriz.length;
        int numeroColunas = matriz[0].length;
        double ElementoPivo = matriz[linhaPivo][ColunaPivo];

        for (int j = 0; j < numeroColunas; j++) {
            matriz[linhaPivo][j] /= ElementoPivo;
        }

        for (int i = 0; i < numeroLinhas; i++) {
            if (i != linhaPivo) {
                double fator = matriz[i][ColunaPivo];
                for (int j = 0; j < numeroColunas; j++) {
                    matriz[i][j] -= fator * matriz[linhaPivo][j];
                }
            }
        }
    }

    public static int AcharOPrincipal(double[] row) {
        for (int i = 0; i < row.length; i++) {
            if (row[i] == 1) {
                return i;
            }
        }
        return -1;
    }
}
