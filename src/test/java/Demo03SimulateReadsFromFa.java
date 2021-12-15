import java.io.*;

public class Demo03SimulateReadsFromFa {

    public static void main(String[] args) throws IOException {
        String inFile = "E:/WSL/task/testForFastaReader/complete.fa";
        String outFile = "src/Box.fq";
        int X = 40;
        int readsLength = 10000;


        int refLength = 0;
        BufferedReader br = new BufferedReader(new FileReader(inFile));
        StringBuilder ref = new StringBuilder();

        String str;
        while ((str =br.readLine())!=null ){
            if (!str.contains(">")){
                ref.append(str);
                refLength+=str.length();
            }
        }
        br.close();
        System.out.println(refLength);

        BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));

        String reference = ref.toString();


        int readsCount = refLength*X/readsLength;
        System.out.println(readsCount);

        int len = ref.length();
        for (int i = 0; i < readsCount; i++) {
            int slice = (int) (Math.random()*(len -readsLength));
            bw.write(">contig" + i);
            bw.newLine();
            bw.write(reference.substring(slice,slice+readsLength));
            bw.newLine();
        }
        bw.close();
    }
}
