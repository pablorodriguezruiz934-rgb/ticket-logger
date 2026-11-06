import java.util.Scanner;

public static Scanner sc = new Scanner(System.in);
void main() {

    int num = 0;
    int mayor=0;
    int segundoMayor=0;
    int menor=0;
    int cont =0;


    do {

        System.out.println("introduce un número");
        num=sc.nextInt();

        if(num>0){
            if ( esImpar(num)==0){
                System.out.println("par");
            }else{
                System.out.println("impar");
            }

        }else{
            System.out.println("número no válido");
        }

        if (cont==0){
            mayor=num;
            menor=num;
            cont++;
        }else{
            if (num>mayor){
                segundoMayor=mayor;
                mayor=num;

            }else if(num>segundoMayor&&num<mayor){
                segundoMayor=num;
            }

            if (num<menor&&num!=0){
                menor=num;
            }
        }


    }while(num!=0);

    System.out.println("El numero mas grande es: "+mayor);
    System.out.println("El segundo numero mas grande es: "+segundoMayor);
    System.out.println("El numero mas pequeño es: "+menor);

}

private int esImpar(int num) {
    int parImpar=0;
    if (num%2==0){
        parImpar=0;
    }else{
        parImpar=1;
    }
    return parImpar;
}