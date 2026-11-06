package ejercicios;

import java.util.Random;

public class Ej4 {

    public static void generarMat(){

        int filas = 5;
        int columnas = 5;

        int[][] matriz = new int[5][5];
        Random random = new Random();

        for (int i = 0; i < filas; i++){
            for (int j = 0; j < columnas; j++){
                matriz[i][j] = random.nextInt(-100, 100);
            }
        }


        for (int i = 0; i < filas; i++){
            for (int j = 0; j < columnas; j++){
                System.out.println(matriz[i][j]+" ");
            }
            System.out.println();
        }

        int [][] matriz2 = new int[6][6];
        int suma = 0;

        for (int i = 0; i< 6; i++){
            for (int j = 0; j< 6; j++){
                if (i< matriz.length){
                    matriz2[i][j]=matriz[i][j];
                }else{

                }
            }
        }
    }
}
