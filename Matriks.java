import java.util.*;
import java.io.*;

public class Matriks {
    public static Scanner in = new Scanner(System.in);
    public double [][] Matriks;
    public int baris;
    public int kolom;


    Matriks(){
        Matriks = new double[100][100];
    }

    Matriks(int n, int m){
        this.SetBrs(n);
        this.SetKol(m);
        Matriks = new double[n+1][m+1];
    }

    public int GetBrs(){
        return baris;
    }

    public int GetKol(){
        return kolom;
    }

    public double GetElmt(int n, int m){
        return Matriks[n][m];
    }

    public void SetBrs(int n){
        this.baris = n;
    }

    public void SetKol(int m){
        this.kolom = m;
    }

    public void SetElmt(int n, int m, double x){
        this.Matriks[n][m] = x;
    }

    public void isiMatriks(int n, int m){
        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= m; j++){
                this.Matriks[i][j] = in.nextDouble();
            }
        }
    }

    public void bacaFile(File inputfile){
        int brs = 0, kol = 0;

        try{
            Scanner in = new Scanner(inputfile);
            while(in.hasNextLine()){
                String baris = in.nextLine();
                brs++;

                Scanner inLine = new Scanner(baris);
                while(inLine.hasNextDouble() && brs == 1){
                    inLine.nextDouble();
                    kol++;
                }
            }

            in.close();
            this.SetBrs(brs);
            this.SetKol(kol);

            in = new Scanner(inputfile);
            for(int i = 1; i <= brs; i++){
                for(int j = 1; j <= kol; j++) SetElmt(i,j,in.nextDouble());
            }
        } 
        catch (FileNotFoundException ex){
            System.out.println("File tidak ditemukan");
        }
    }

    public void tulisMatriks(){
        for(int i=1; i<= this.GetBrs(); i++){
            for (int j=1; j<= this.GetKol(); j++){
                if((this.GetElmt(i,j)*10)%10 == 0) System.out.printf("%.0f ", this.GetElmt(i,j));
                else System.out.printf("%.2f ", this.GetElmt(i,j));
            }
            System.out.println();
        }
    }

    public void keyboardSPL(int n, int m){
        System.out.println();
        for(int i = 1; i <= this.GetBrs(); i++){
            System.out.println("Masukkan persamaan ke-" + (i+1));
            for(int j = 1; j <= this.GetKol(); j++){
                if(j == this.GetKol()){
                    System.out.print("Masukkan nilai konstanta: ");
                    this.SetElmt(i,j,in.nextDouble());
                } else{
                    System.out.print("Masukkan nilai koefisien x[" + (j) + "]: ");
                    this.SetElmt(i,j,in.nextDouble());
                } 
            }
            System.out.println();
        }
    }

    public void tukarBrs(int i1, int i2){
        double temp;
        for (int j = 1; j <= this.GetKol(); j++){
            temp = this.GetElmt(i1,j);
            SetElmt(i1, j, this.GetElmt(i2,j));
            SetElmt(i2, j, temp);
        }
    }

    public boolean isAllZero(int i){
        boolean allzero=true;
        for (int j = 1; j <= this.GetKol(); j++){
            if(this.GetElmt(i,j) != 0) allzero = false;
        }
        return allzero;
    }

    public boolean isKoefZero(int i){
        boolean koefZero = false;
        int jumlah = 0;
        if (!this.isAllZero(i)){
            for(int j = 1; j <= this.GetKol()-1; j++){
                if(this.GetElmt(i,j) == 0) jumlah+=1;
            }
        }
        if (jumlah == this.GetKol()-1) koefZero = true;
        return koefZero;
        
    }

    public void salinMatriks(Matriks Msal){
        for(int i = 1; i <= this.GetBrs(); i++){
            for(int j = 1; j <= this.GetKol(); j++){
                Msal.SetElmt(i,j,this.GetElmt(i,j));
            }
        }
    }

    public void isiIdentitas(Matriks M){
        for(int i = 1; i<=this.GetBrs(); i++){
            for(int j = this.GetKol()+1 ; j <= M.GetKol(); j++){
                if(j-i == this.GetKol()) M.SetElmt(i,j,1);
                else M.SetElmt(i,j,0);
            }
        }
    }

    public boolean cekDiagonalInvers(){
        boolean cek = true;
        for(int i = 1; i <= this.GetBrs(); i++){
            for (int j = 1; j <= this.GetKol(); j++){
                if(i==j){
                    if(this.GetElmt(i,j) != 1) cek = false;
                }
            }
        }
        return cek;
    }

    public void eliminasiGauss(){
        int brs = 1;
        int kol = 1;
        int indeksMaks;
        while(brs <= this.GetBrs() && kol<=this.GetKol()){
            indeksMaks = -1; 
            //Mencari baris yang tidak bernilai 0 dalam suatu kolom
            for(int i = brs; i <= this.GetBrs() && indeksMaks == -1; i++){
                if(this.GetElmt(i,kol) != 0) indeksMaks = i;
            }

            //Jika tidak ada baris bernilai nol di sebuah kolom, lanjut ke kolom selanjutnya
            if(indeksMaks == -1) kol++;

            //Jika ada akan diproses
            else{
                this.tukarBrs(brs,indeksMaks); //Tukar baris
                double lead = this.GetElmt(brs, kol); //Pembagi untuk membuat 1 utama

                //Membagi seluruh kolom pada baris dengan pembuat 1 utama
                for(int j = kol; j <= this.GetKol(); j++){
                    this.SetElmt(brs, j, this.GetElmt(brs,j)/lead);
                }

                //Mengurangi seluruh kolom di bawah 1 utama pada baris dengan rasio baris lain
                for(int i = brs+1; i <= GetBrs(); i++){
                    double rasio = (-1*this.GetElmt(i, kol)) / this.GetElmt(brs,kol);
                    for (int j = kol; j <= this.GetKol(); j++){
                        this.SetElmt(i, j , this.GetElmt(i,j) + rasio*this.GetElmt(brs,j));
                    }
                }

                //Lanjut ke baris dan pengecekan kolom selajuntnya
                brs++;
                kol++;
                
            }
        }
    }

    public void solusiEliminasiGauss(double solusi [][], int[] jumlahSolusi){
        boolean adaSolusi = true;   //Variabel penentu apakah sistem memiliki solusi
        int Brsnol = 0; //Jumlah baris seluruh elemen bernilai 0

        //Mengecek apakah terdapat solusi
        for(int i = 1; i <= this.GetBrs(); i++){
            if(this.isKoefZero(i)) adaSolusi = false;
            if(this.isAllZero(i)) Brsnol++;
        }

        //Jika tidak ada solusi, set nilai jumlahSolusi[0] menjadi 0
        if(!adaSolusi) jumlahSolusi[0] = 0; 

        //Jika ada, akan diproses
        else{
             if(this.GetBrs()-Brsnol == this.GetKol()-1) jumlahSolusi[0] = 1; //Jika solusi tunggal
             else jumlahSolusi[0] = 999;  //Jika solusi banyak

             boolean firstPivot = false; //apakah non-zero ditemukan
             int BrsPivot;  //indeks ditemukan elemen bukan 0 pertama pada baris
             int lastPivot = 0;

             //Mengisi array solusi
            for(int i = this.GetBrs(); i>=1; i--){
                if(this.isAllZero(i)) continue; //Jika isinya 0 semua, dilewatkan
                
                //Bukan 0, diproses
                else{
                   if(!firstPivot){
                       firstPivot = true;
                       int j = 1;

                       while(j <= this.GetKol()-1 && this.GetElmt(i,j) == 0) j++;
                       BrsPivot = j;
                       
                       for (int k = 1; k <= this.GetKol()-1; k++) solusi[BrsPivot][k] = 0;

                       j++;
                       while(j <= this.GetKol()-1){
                           if(this.GetElmt(i,j) != 0){
                               for (int k = 1; k <= this.GetKol()-1; k++){
                                   if (k!=j) solusi[j][k] = 0;
                                   else solusi[j][k] = 1;
                               }
                               solusi[j][this.GetKol()] = 0;
                               solusi[BrsPivot][j] = -1*this.GetElmt(i,j);
                           }
                           j++;
                       }
                       lastPivot = BrsPivot;
                       solusi[BrsPivot][this.GetKol()] = this.GetElmt(i, this.GetKol());
                   } else{
                        int j = 1;
                        while (j <= this.GetKol()-1 && this.GetElmt(i,j) == 0) j++;
                        BrsPivot = j;
                        for (int k =1; k <= this.GetKol()-1; k++) solusi[BrsPivot][k] = 0;
                        for(int k = BrsPivot+1; k <= lastPivot-1; k++){
                            for(int l=1; l <= this.GetKol()-1; l++){
                                if (l != k) solusi[k] [l] = 0;
                                else solusi[k][l] = 1;
                            }
                            solusi[k][this.GetKol()] = 0;
                        }
                        solusi[BrsPivot][this.GetKol()] = this.GetElmt(i, this.GetKol());
                        for (int k = BrsPivot+1; k <= this.GetKol()-1; k++){
                            for(int l = 1; l <= this.GetKol(); l++){
                                solusi[BrsPivot][l] += -1*this.GetElmt(i,k)*solusi[k][l];
                            }
                        }
                        lastPivot = BrsPivot;
                   } 
                }
            }
        } 
    }

    public void eliminasiGaussJordan(){
        int brs = 1;
        int kol = 1;

        
        while(brs <= this.GetBrs() && kol <= this.GetKol()){
            int indeksMaks = -1;
            //Mencari baris yang tidak bernilai 0 dalam suatu kolom
            for(int i = brs; i <= this.GetBrs() && indeksMaks == -1; i++){
                if(this.GetElmt(i, kol) != 0) {
                    indeksMaks = i;
                }
            }

            //Jika tidak ada baris bernilai nol di sebuah kolom, lanjut ke kolom selanjutnya
            if(indeksMaks == -1) kol++;

            //Jika ada akan diproses
            else{
                this.tukarBrs(brs, indeksMaks); //Tukar Baris
                double lead = this.GetElmt(brs, kol);
                //Membagi seluruh kolom pada baris dengan pembuat 1 utama
                for(int j = kol; j <= this.GetKol(); j++){
                    this.SetElmt(brs, j, this.GetElmt(brs,j)/lead);
                }
                //Mengurangi seluruh kolom pada baris dengan rasio baris lain
                for(int i = 1; i <= this.GetBrs(); i++){
                    if(i != brs){
                        double rasio = (-1*this.GetElmt(i, kol)) / this.GetElmt(brs,kol);
                        for(int j = kol; j <= this.GetKol(); j++){
                            this.SetElmt(i,j,this.GetElmt(i,j) + rasio*this.GetElmt(brs,j));
                        }
                    }
                }

                //Lanjut ke baris dan pengecekan kolom selajuntnya
                brs++;
                kol++;
            }
        }
    }

    public void solusiEliminasiGaussJordan(double solusi [][], int[] jumlahSolusi){
        int brsnol = 0;
        boolean adaSolusi = true;

        //Mengecek apakah terdapat solusi
        for (int i = this.GetBrs(); i >= 1; i--){
            if (this.isAllZero(i)) brsnol++;
            if (this.isKoefZero(i)) adaSolusi = false;
        }

        //Jika tidak ada solusi, set nilai jumlahSolusi[0] menjadi 0
        if(!adaSolusi) jumlahSolusi[0] = 0;

        //Jika ada, akan diproses
        else{
            if(this.GetBrs() - brsnol == this.GetKol() - 1) jumlahSolusi[0] = 1; //Jika solusi tunggal
            else jumlahSolusi[0] = 999; //Jika solusi banyak

            //Mengisi Array solusi
            for (int i = 1; i <= this.GetKol()-1; i++){
                for (int j=1;j <= this.GetKol(); j++){
                    if(i == j)  solusi[i][j] = 1;   //Isi dengan 1 setiap, sebagai koefisien dari setiap elemen X
                    else solusi[i][j] = 0;  //Isi dengan 0 bukan diagonal
                }
            }
            
            int BrsPivot = 0;
            for(int i = this.GetBrs(); i>=1; i--){
                if(this.isAllZero(i)) continue;
                else{
                    int j=1;
                    while(j <= this.GetKol()-1 && this.GetElmt(i,j) == 0) j++;
                    BrsPivot = j;   //Nilai x-sekian yang akan diproses
                    solusi[BrsPivot][BrsPivot] = 0; //Meng-0-kan elemen diagonal tersebut
                    j++;
                    while(j <= this.GetKol()-1){
                        solusi[BrsPivot][j] = -1*this.GetElmt(i,j);
                        j++;
                    }
                    solusi[BrsPivot][this.GetKol()] = this.GetElmt(i,this.GetKol()); //Hasil x-sekian

                }
            }

        }
    }

    public void tulisSolusi(double[][] solusi){
        int variabel = this.GetKol()-1;
        for (int i = 1; i <= variabel; i++){
            System.out.print("x[" + i + "] = ");
            boolean tulisDulu = true;

            //Mengecek solusi banyak dari x-sekian
            for (int j = 1; j <= variabel; j++){
                if(solusi[i][j] != 0){
                    if(tulisDulu){
                        tulisDulu = false;
                        if((solusi[i][j]*10)%10 == 0) System.out.printf("(%.0f)T[%d]", solusi[i][j],j); 
                        else System.out.printf("(%.2f)T[%d]", solusi[i][j],j); 
                    } else {
                        if ((solusi[i][j]*10)%10 == 0) System.out.printf(" + (%.0f)T[%d]", solusi[i][j],j);
                        else System.out.printf(" + (%.2f)T[%d]", solusi[i][j],j);
                    }
                }
            }
            //Jika solusi di kolom ujung tidak 0
            if(solusi[i][variabel+1] != 0){
                if(tulisDulu){ //Jika solusi x-sekian tunggal
                    if ((solusi[i][variabel+1]*10)%10 == 0) System.out.printf("%.0f\n",solusi[i][variabel+1]);
                    else System.out.printf("%.2f\n",solusi[i][variabel+1]);
                }   
                else { //Konstanta x-sekian banyak
                    if ((solusi[i][variabel+1]*10)%10 == 0) System.out.printf(" + (%.0f)%n", solusi[i][variabel+1]);
                    else System.out.printf(" + (%.2f)%n", solusi[i][variabel+1]);
                }
            } else{
                if(tulisDulu) System.out.println("0"); //Jika solusi x-sekian bernilai 0, dengan satu baris hanya berisi 1 elemen
                else System.out.println();
            }
        }
    }

    public void ubahKol(double konstanta [], int j ){
        for(int i = 1; i<= this.GetBrs(); i++){
            this.SetElmt(i,j, konstanta[i]);
        }
    }

    public void segitigaAtas(int jmlTukarBrs){
        if(this.GetKol() == this.GetBrs()){
            int brs = 1;
            int kol = 1;
            int indeksMaks;
            while(brs <= this.GetBrs() && kol<=this.GetKol()){
                indeksMaks = -1; 
                //Mencari baris yang tidak bernilai 0 dalam suatu kolom
                for(int i = brs; i <= this.GetBrs() && indeksMaks == -1; i++){
                    if(this.GetElmt(i,kol) != 0) indeksMaks = i;
                }
    
                //Jika tidak ada baris bernilai nol di sebuah kolom, lanjut ke kolom selanjutnya
                if(indeksMaks == -1) kol++;
    
                //Jika ada akan diproses
                else{                    
                    if(brs != indeksMaks){
                        jmlTukarBrs = jmlTukarBrs + 1;   //Menghitung jumlah tukar baris
                        this.tukarBrs(brs,indeksMaks); //Tukar baris
                    } 
                    
                    //Mengurangi seluruh kolom di bawah 1 utama pada baris dengan rasio baris lain
                    for(int i = brs+1; i <= this.GetBrs(); i++){
                        double rasio = (-1*this.GetElmt(i, kol)) / this.GetElmt(brs,kol);
                        for (int j = kol; j <= this.GetKol(); j++){
                            this.SetElmt(i, j , this.GetElmt(i,j) + rasio*this.GetElmt(brs,j));
                        }
                    }
    
                    //Lanjut ke baris dan pengecekan kolom selajuntnya
                    brs++;
                    kol++;
                    
                }
            }
        }
    }

    public void kaliMatriksKonstanta(double[] M2, Matriks Mhasil){
        for(int i = 1; i <= this.GetBrs(); i++){
            for(int j = 1; j<=1; j++){
                for(int k = (this.GetKol()/2)+1; k <= this.GetKol(); k++){
                    Mhasil.SetElmt(i,j,Mhasil.GetElmt(i,j)+this.GetElmt(i,k)*M2[k-(this.GetKol()/2)]);
                }
            }
        }
    }

    public void tulisHasilSPLIvnvers(){
        System.out.println("Solusi Sistem Persamaan: ");
        for(int i = 1; i <= this.GetBrs(); i++){
            if((this.GetElmt(i,1)*10)%10 == 0) System.out.printf("x[%d] = %.0f\n", i, this.GetElmt(i,1));
            else System.out.printf("x[%d] = %.2f\n", i, this.GetElmt(i,1));
        }
    }

    public void bacaKoefKeyboard(double[] konstanta, int n, int m){
        for(int i=1; i<= n; i++){
            System.out.println();
            System.out.println("Masukkan persamaan ke-" + i + " :");
            for (int j = 1; j<= m; j++){
                if (j == m) {
                    System.out.print("Masukkan konstanta: ");
                    double x = in.nextDouble();
                    konstanta[i] = x;
                }else {
                    System.out.print("Masukkan koefisien x[" + j + "]: ");
                    double x = in.nextDouble();
                    this.SetElmt(i,j,x);
                }
            }
        }
    }

    public void bacaKoefFile(File inputfile, double konstanta[]){
        int brs = 0, kol = 0;

        try{
            Scanner in = new Scanner(inputfile);
            while(in.hasNextLine()){
                String baris = in.nextLine();
                brs++;

                Scanner inLine = new Scanner(baris);
                while(inLine.hasNextDouble() && brs == 1){
                    inLine.nextDouble();
                    kol++;
                }
            }

            in.close();
            this.SetBrs(brs);
            this.SetKol(kol-1);

            in = new Scanner(inputfile);
            for(int i = 1; i <= brs; i++){
                for(int j = 1; j <= kol; j++) {
                    if(j == kol){
                        konstanta[i] = in.nextDouble();
                    } else this.SetElmt(i,j,in.nextDouble());
                }
            }
        } 
        catch (FileNotFoundException ex){
            System.out.println("File tidak ditemukan");
        }
    }

}