
public class Main {
    public static void main(String[] args) {
            int N1 = 1541624489 ;  // Примерное значение
        int N2 = 31190802;  // Примерное значение
        int[] key = {21, 8, 29, 17, 0, 0, 0, 0};
        int[][] Sbox= {
                {0,1,6,7,2,8,15,3,7,5,7,11,14,10,6,9},
                {3,14,0,7,6,8,2,1,11,15,0,2,3,10,7,8},
                {9,13,2,4,3,6,6,10,4,3,7,1,10,11,0,3},
                {14,5,14,7,8,11,0,10,3,4,3,9,6,7,12,15},
                {2,6,2,0,1,8,9,14,7,13,12,10,7,9,0,4},
                {7,3,5,15,3,12,10,3,6,9,7,3,7,0,14,6},
                {0,6,4,0,7,6,8,1,11,13,10,6,3,6,3,9},
                {1,12,0,10,6,9,3,15,2,12,7,8,9,4,3,0}};


//

        int result=CM1(N1,key[1]);
        System.out.println(result);
        int[] mas=SBOX(is_desetechniy_v_dvoichni(result),Sbox);
        int[] mas2=shiftLeft(mas);
        long S=is_dvoichni_v_desyatichni(mas);
        System.out.println(S);
        S=is_dvoichni_v_desyatichni(mas2);
        System.out.println(S);
        mas2=sum_modul_2(mas2,is_desetechniy_v_dvoichni(N2));
        S=is_dvoichni_v_desyatichni(mas2);
        System.out.println(S);










    }
    public static int[] SBOX(int[] cm,int[][] sbox){
        int[][] mus=new int[8][4];
        int[] result= new int[32];
        int j=0;
        for(int i=0;i<cm.length;)
        {
            for (int n=0;n<4;n++){
                mus[j][n]=cm[i];
                i++;
            }
            j++;
        }
        for (int k=0;k<result.length;){
        for(int i=0;i<8;i++) {
            int[] bit = new int[4];
            for (int n = 0; n < 4; n++) {
                bit[n] = mus[i][n];
            }
            long chislo = is_dvoichni_v_desyatichni(bit);
            chislo = sbox[7-i][ (int)chislo];
            bit = is_desetechniy_v_dvoichni( (int)chislo);
            for (int p = 28; p < 32; p++) {
                result[k] = bit[p];
                k++;
            }
        }
        }

        return result;
    }
    public static int CM1(int a,int b){
        return a+b;
    }
    public static int[] sum_modul_2(int [] mus,int [] mus2)
    {
        for (int i =0;i<mus.length;i++)
        {
            if(mus[i]==mus2[i])
            {
                mus[i]=0;
            }
            else mus[i]=1;
        }
        return mus;

    }
    public static int[] is_desetechniy_v_dvoichni(int mus)
    {
        int[] dvoika=new int[32];
        int j=0;
        String mas124=Integer.toBinaryString(mus);
        char[] str=mas124.toCharArray();
        if(mas124.length()<dvoika.length)j=dvoika.length-mas124.length();
        for (int i=0;i<mas124.length();i++)
        {

            dvoika[j]=Character.getNumericValue(str[i]);
            j++;
        }

        return dvoika;

    }
    public static long is_dvoichni_v_desyatichni(int[] mus)
    {
        long desyatichni=0;
        for(int i=mus.length-1;i>=0;i--)
        {
            desyatichni+=mus[i]*((long) Math.pow(2,mus.length-1-i));

        }

        return desyatichni;

    }

    public static int[] shiftLeft(int[] a) {
        if (a != null) {
            int length = a.length;
            int[] b = new int[length];
            // шаг 1
            System.arraycopy(a, 11, b, 0, length - 11);
            // шаг 2
            System.arraycopy(a, 0, b, length - 11, 11);
            return b;
        } else {
            return null;
        }

}
}
