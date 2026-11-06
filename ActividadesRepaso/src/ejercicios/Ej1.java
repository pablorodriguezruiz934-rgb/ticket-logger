/*Crear un método en JAVA que solicite dos números. Si el segundo número es
inferior al primero, hay que volver a solicitarlo. Una vez introducidos, para cada
número entre el límite inferior y el límite superior (ambos incluidos):
a. Imprimir “ByThree” si el número es divisible por 3.
b. Imprimir “ByFive” si el número es divisible por 5.
c. Imprimir “ByBoth” si el número es divisible por 3 y por 5.
d. Imprimir el número en caso contrario.*/

import java.lang.management.OperatingSystemMXBean;
import java.util.Scanner;

public static Scanner sc = new Scanner(System.in);
void main() {

    System.out.println("introduce un número");
    int num1=sc.nextInt();

    System.out.println("introduce otro número");
    int num2= sc.nextInt();

    numeros(num1, num2);



}

private void numeros(int num1, int num2) {

    if (num2<num1){
        System.out.println("introduzca de nuevo el segundo numero");
        int num3 = sc.nextInt();
        num2=num3;
    }

    System.out.println("introduce un num");
    int numero=sc.nextInt();

    if(numero>=num1||numero >=num2) {
        if (numero % 3 == 0 && numero % 5 != 0) {
            System.out.println("ByThree");
        }
        if (numero % 5 == 0 && numero % 3 != 0) {
            System.out.println("ByFive");
        }
        if (numero % 5 == 0 && numero % 3 == 0) {
            System.out.println("ByBoth");
        }
    }



}

public class Ej2 {
    public static Scanner sc  = new Scanner(System.in);
    /*Crear un método en JAVA que solicite números de forma ilimitada hasta que el
sumatorio de todos ellos llegue a 1.000. En ese caso se debe de devolver:
a. Cuál ha sido el total aculumado.
b. Cúantos números se han introducido.
c. Cuál es la media de todos ellos.
*/

    static void main() {
        final int MAX= 1000;
        int cont = 0;
        int contIntroducidos=0;
        int media = 0;

        do{
            System.out.println("introduce un num");
            int num = sc.nextInt();
            cont+=num;
            contIntroducidos++;
            media+=num/contIntroducidos;

        }while(cont<MAX);

        System.out.println("total acumulado= "+cont);
        System.out.println("números introducidos "+contIntroducidos);
        System.out.println("media "+media);

    }

}