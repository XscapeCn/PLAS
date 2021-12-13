import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo08TestKmer {
    public static void main(String[] args) throws IOException {
        BufferedReader textReader = IOUtils.getTextReader("E:/WSL/task/testForFastaReader/tail2.txt");
        List<String> kmer = new ArrayList<>();
        HashMap<String,Integer> countTable = new HashMap<>();

        String str;
        while ((str =textReader.readLine())!=null){
            for (int i = 0; i < str.length() - 15; i += 3) {
                String tmp = str.substring(i,i+15);
                if (kmer.contains(tmp)){
                    Integer value = countTable.get(tmp);
                    value += 1;
                    countTable.put(tmp,value);
                }else {
                    kmer.add(tmp);
                    countTable.put(tmp,1);
                }
            }

        }

        for (int i = 0; i < kmer.size(); i++) {
            System.out.println(kmer.get(i));

        }

        HashMap<String, Integer> res1 = new HashMap<>();
//        BufferedWriter bw3 = new BufferedWriter(new FileWriter("E:/WSL/task/testForFastaReader/map.txt"));


        for (Map.Entry<String, Integer> entry : countTable.entrySet()) {
//            if (entry.getValue() > 5){
            res1.put(entry.getKey(),entry.getValue());
//            }
//            bw3.write("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//            bw3.newLine();
        }


//        for (Map.Entry<String, Integer> entry : res1.entrySet()) {
//            if (entry.getValue() < 5){
//                res1.put(entry.getKey(),entry.getValue());
//            }
//            bw3.write("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//            bw3.newLine();
//        }

//        bw3.close();

        List<String> strings = new ArrayList<>(res1.keySet());
        euler(strings);
    }

    public static void getForSequence(String str, List<String> dic, StringBuilder sb){
        dic.remove(str);

        for (int i = 0; i < dic.size(); i++) {
            if (str.substring(3,15).equals(dic.get(i).substring(0,12))){
//                res = res + strr.substring(12,15);
                sb.append(dic.get(i).substring(12,15));
                getForSequence(dic.get(i),dic,sb);
            }
        }
    }

    public static void getBackSequence(String str, List<String> dic, StringBuilder res){
        dic.remove(str);

        for (int i = 0; i < dic.size(); i++) {
            if (str.substring(0,12).equals(dic.get(i).substring(3,15))){
//                System.out.println(dic.get(i));
                res.insert(0,dic.get(i).substring(0,3));
//                res = dic.get(i).substring(3,15) + res;
//                System.out.println(res);
                getBackSequence(dic.get(i),dic,res);
            }
        }
    }

    public static void euler(List<String> strings){
        int i = 1;
        while (!strings.isEmpty()){
//            System.out.println(strings.size());
            String next = strings.get(0);
            System.out.println("Contig" + i +": ");
            System.out.println("Initail: " + next);

            StringBuilder sb = new StringBuilder();
            sb.append(next);

            getForSequence(next, strings,sb);

            System.out.println("Front: " + sb);

            String back = null;
//            System.out.println(strings.size());

            StringBuilder sb2 = new StringBuilder(next);

            getBackSequence(next,strings,sb2);
            System.out.println("Back: " + sb2);
//            System.out.println(strings.size());

            System.out.println(sb2 + sb.toString().substring(next.length()));



            i++;
        }
    }
}
