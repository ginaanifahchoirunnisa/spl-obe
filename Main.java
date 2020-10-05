import java.util.*;
import java.io.*;

public class Main {

    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args){
        utama();
    }

    static void utama(){
        System.out.println("\n=== SELAMAT DATANG DI PROGRAM SISTEM PERSAMAAN LINIER DENGAN OPERASI BARIS ELEMENTER ===");
        System.out.println("1. Eliminasi Gauss");
        System.out.println("2. Eliminasi Gauss-Jordan");
        System.out.println("3. Metode Matriks Balikan");
        System.out.println("4. Kaidah Cramer");
        System.out.println("5. Keluar\n");
        System.out.print("Masukkan pilihan: ");
        int pilihan = in.nextInt();
        while(true){
            if (pilihan == 1) gauss();
            else if (pilihan == 2) gaussjordan();
            // else if (pilihan == 3) balikan();
            // else if (pilihan == 4) cramer();
            else if(pilihan == 5) System.exit(0);
            else{
                System.out.println("Masukan salah. Silakan masukkan ulang!");
                pilihan = in.nextInt();
            }
        }
    }

    static void masukan(Matriks M){
        System.out.println("1. Masukan dari keyboard");
        System.out.println("2. Masukan dari file");
        System.out.print("Masukkan pilihan: ");
        int masukan = in.nextInt();
        System.out.println();
       
        
        while(true){
            if(masukan == 1){
                //Meminta input jumlah persamaan dan jumlah peubah
                System.out.print("Masukkan jumlah persamaan: ");
                int n = in.nextInt();
                System.out.print("Masukkan jumlah peubah: ");
                int m = in.nextInt();
                M.SetBrs(n);
                M.SetKol(m+1);
                M.keyboardSPL(n,m+1);

                break;

            } else if (masukan == 2) {
                //Membaca matriks yang ada di file
                String namaFile = in.nextLine();
                File file  = new File(namaFile);

                while(!file.exists()){
                    System.out.print("Masukkan nama file: ");
                    namaFile = in.nextLine();
                    file = new File(namaFile);
                }

                M.bacaFile(file);
                System.out.println();
                break;

            }else{
                System.out.print("Masukan salah. Silakan masukkan ulang! ");
                masukan = in.nextInt();   
            }
        }
    }

    static void tulisMatriksAugmented(Matriks M){
        System.out.println("Matriks Augmented: ");
        M.tulisMatriks();
        System.out.println();
    }

    static void gauss(){
        System.out.println("\n=== Eliminasi Gauss ===");
        Matriks M = new Matriks();
        masukan(M);
        tulisMatriksAugmented(M);
        Matriks Mmanip = new Matriks(M.GetBrs(),M.GetKol());
        M.salinMatriks(Mmanip);

        M.eliminasiGauss();
        System.out.println("Matriks Eselon Baris: ");
        M.tulisMatriks();  
        double[][] solusi = new double[100][100];
        int[] jumlahSolusi = new int[1];

        Mmanip.eliminasiGaussJordan();
        System.out.println();                          
        //Menuliskan solusi
        Mmanip.solusiEliminasiGaussJordan(solusi, jumlahSolusi);
        Mmanip.tulisSolusi(solusi);
        System.out.println();
        System.out.println("Menuju menu utama......");
        utama();

    }

    static void gaussjordan(){
        System.out.println("\n=== Eliminasi Gauss-Jordan ===");
        Matriks M = new Matriks();
        masukan(M);
        tulisMatriksAugmented(M);

        M.eliminasiGaussJordan();
        System.out.println("Matriks Eselon Baris Tereduksi: ");
        M.tulisMatriks();  
        double[][] solusi = new double[100][100];
        int[] jumlahSolusi = new int[1];

        System.out.println();                          
        //Menuliskan solusi
        M.solusiEliminasiGaussJordan(solusi, jumlahSolusi);
        M.tulisSolusi(solusi);
        System.out.println();
        System.out.println("Menuju menu utama......");
        utama();
    }

        
}