package ejercicios;

import java.util.Scanner;

public class Ej5 {
   public static void main() {
        Scanner sc = new Scanner(System.in);
        int x,y;
        do{
             x = sc.nextInt();
             y = sc.nextInt();
            if (x < 1 || y < 1){
                System.out.println("Los numeros deben ser validos");
            }
        }while(x < 1 || y < 1);
int [] [] m1 = new int[x][y];
boolean fin = false;
for (int i=0;i<x;i++){
    for (int j=0;j<y;j++){
        System.out.println("Introduce el numero de la fila " +(i+1)+" Introduce el numero de la columna "+(j+1));
        m1 [i][j] = sc.nextInt();
    }
}
int sum = 0;
for (int i =0;i<x;i++){
    for (int j =0;j<y;j++){
        sum +=m1[i][j];
    }
}
double med = sum /(x*y);


while (fin == false){

}
    }
}
