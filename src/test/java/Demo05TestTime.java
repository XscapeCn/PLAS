import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo05TestTime {
    public static void main(String[] args) throws IOException {

        long startTime=System.currentTimeMillis();   //获取开始时间
        List<String> res = IOUtils.getFastaReader("E:/WSL/task/asb/reads-0.00.fa");
        BufferedWriter bw = IOUtils.getTextWriter("E:/WSL/task/asb/test2.txt");

        int length = 10;
        double frequency = 0.0003;

        Demo04Mini mn = new Demo04Mini(length,frequency);
        HashMap<String, String> miniMap = mn.getMini();

        BufferedWriter bw2 = new BufferedWriter(new FileWriter("E:/WSL/task/asb/map_kmer.txt"));

        for (Map.Entry<String, String> entry : miniMap.entrySet()) {
            bw2.write("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            bw2.newLine();
        }
        bw2.close();

        List<List<String>> Res = new ArrayList<>();
        for (String tmp:res) {
            int i = 0;
            List<String> tempRes = new ArrayList<>();
            while (i < tmp.length()-length){
                String Box = tmp.substring(i,i+length);
                String s = miniMap.get(Box);
                if (s == null){
                    i++;
                }else {
//                    i += length;
                    i++;
                    tempRes.add(s);
                }
            }
            Res.add(tempRes);
        }

        for (List<String> re : Res) {
            for (String s : re) {
                bw.write(s);
//                bw.write(",");
            }
            bw.newLine();
        }

        bw.close();
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
    }

}

