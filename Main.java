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
            else if (pilihan == 3) balikan();
            else if (pilihan == 4) cramer();
            else if(pilihan == 5) System.exit(0);
            else{
                System.out.println("Masukan salah. Silakan masukkan ulang!");
                pilihan = in.nextInt();
            }
        }
    }

    static void masukanelim(Matriks M){
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

    static void masukanlain(Matriks M, double[] konstanta){
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
                M.SetKol(m);
                M.bacaKoefKeyboard(konstanta, n, m+1);
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

                M.bacaKoefFile(file, konstanta);
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
        masukanelim(M);
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
        masukanelim(M);
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

    static void balikan(){
        System.out.println("\n=== Metode Matriks Balikan ===");
        Matriks M = new Matriks();
        Matriks Mtemp = new Matriks();
        Matriks Mhasil = new Matriks();
        double[] konstanta = new double[100];
        masukanlain(M, konstanta);

        if(M.GetBrs() == M.GetKol()){
        //Menerapkan eliminasi Gauss-Jordan untuk memindahkan matriks identitas ke kiri
            Mtemp.SetBrs(M.GetBrs());
            Mtemp.SetKol(2*M.GetKol());
            M.salinMatriks(Mtemp);
            M.isiIdentitas(Mtemp);
            Mtemp.eliminasiGaussJordan();

            if (Mtemp.cekDiagonalInvers()){
                System.out.println("\nMatriks hasil operasi baris elementer: ");
                Mtemp.tulisMatriks();
                System.out.println();
            
                //Menuliskan hasil sistem persamaan
                Mhasil.SetBrs(Mtemp.GetBrs());
                Mhasil.SetKol(1);
                Mtemp.kaliMatriksKonstanta(konstanta, Mhasil);
                Mhasil.tulisHasilSPLIvnvers();
            }else {
                System.out.println("\nMetode Matriks Balikan tidak dapat menyelesaikan persamaan.");
            }
        }else {
            System.out.println("\nMetode Matriks Balikan tidak dapat menyelesaikan persamaan.");
        }

        System.out.println();
        System.out.println("Menuju menu utama......");
        utama();
    }

    static void cramer(){
        System.out.println("\n=== Kaidah Cramer ===");
        Matriks M = new Matriks();
        Matriks Mtemp = new Matriks();
        double[] konstanta = new double[100];
        masukanlain(M, konstanta);

        Mtemp.SetBrs(M.GetBrs());
        Mtemp.SetKol(M.GetKol());
        double[] detMi = new double[M.GetKol()+1];

        //Menghitung deteminan M
        M.salinMatriks(Mtemp);
        double detM = M.determinanM();
        System.out.println();

        if (M.GetBrs() == M.GetKol()){
            if ((detM*10)%10 == 0)  System.out.printf("det(M) = %.0f\n", detM);
            else System.out.printf("det(M) = %.2f\n", detM);

            //Menghitung Determinan D(i)
            for(int j=1; j<=M.GetKol(); j++){
                Mtemp.salinMatriks(M);
                M.ubahKol(konstanta,j);
                detMi[j] = M.determinanM();
                if((detMi[j]*10)%10 == 0) System.out.printf("det(M%d) = %.0f\n", j, detMi[j]);
                else System.out.printf("det(M%d) = %.2f\n", j, detMi[j]);
            }

            //Menuliskan solusi tunggal jika ada
            if (detM != 0){
                System.out.println("\nSolusi Sistem Persamaan: ");
                for(int j=1; j<=M.GetKol(); j++){
                    if(((detMi[j]/detM)*10)%10 == 0) System.out.printf("x[%d] = %.0f\n", j, (detMi[j]/detM)); 
                    else System.out.printf("x[%d] = %.2f\n", j, (detMi[j]/detM));
                }
            }else {
                System.out.println("\nKaidah Cramer tidak dapat menyelesaikan persamaan");
            } 
        } else {
            System.out.println("Kaidah Cramer tidak dapat menyelesaikan persamaan");
        } 

        System.out.println();
        System.out.println("Menuju menu utama......");
        utama();
    }
}