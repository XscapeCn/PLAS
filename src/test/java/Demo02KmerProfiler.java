import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Demo02KmerProfiler {
    public StringBuilder sb = new StringBuilder();

    public Demo02KmerProfiler(String str){
        List<String> strings = strToKmer(str,30);
        String ab = strings.get(0);
        sb.append(ab.substring(0,10-1));
        getForSequence(ab,strings);
        System.out.println(sb.toString());
    }

    public Demo02KmerProfiler(List<String> str, int mer){
        List<String> strings = strToKmer(str,mer);
        String ab = strings.get(0);
        sb.append(ab, 0, mer-1);
        getForSequence(ab,strings);
        System.out.println(sb.toString());
    }

    public static List<String> strToKmer(String str, int mer){
        int len = str.length();
        List<String> kmer = new ArrayList<>();
//        String[] kmer = new String[len - mer + 1];
        for (int i = 0; i < len - mer + 1; i++) {
            kmer.add(str.substring(i,i+mer));
        }
        return kmer;
    }

    public static List<String> strToKmer(List<String> str, int mer){
        int len = str.get(0).length();
        List<String> kmer = new ArrayList<>();
//        String[] kmer = new String[len - mer + 1];
        for (int i = 0; i < str.size(); i++) {
            for (int j = 0; j < len - mer + 1; j++) {
                kmer.add(str.get(i).substring(j,j+mer));
            }
        }

//        for (int i = 0; i < len - mer + 1; i++) {
//            kmer.add(str.substring(i,i+mer));
//        }
        return kmer;
    }


    public void getForSequence(String str, List<String> dic){
        dic.remove(dic.indexOf(str));

//        sb.append(str);
        for (int i = 0; i < dic.size(); i++) {
            if (dic.get(i).substring(0,str.length()-1).equals(str.substring(1))){
                sb.append(dic.get(i).charAt(str.length()-1));
                getForSequence(dic.get(i), dic);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> res = new ArrayList<>();
//        BufferedReader br = new BufferedReader(new FileReader("src/Box.fq"));
//        String str;
//        while ((str = br.readLine())!=null){
//            res.add(str);
//        }

        res.add("TGTCGCGGTGCCATACCAGTTTTTTAGCAACTGCCATCATGCCGTTAGCCTGATAGTCGCCGAACTGAACTTTTGCTGACTGACGAACCTGCGGTTCGCA");
        res.add("TGTCGCGGTGCCATACCAGTTTTTAGCAACTGCCATCATGCCGTTAGCCTGATAGTCGCCGAACTGAACTTTTGCTGACTGACGAACCTGCGGTTCGCA");
        Demo02KmerProfiler kmerProfiler = new Demo02KmerProfiler(res,20);
    }

    
}
