import scala.Int;

import java.io.*;
import java.util.*;

public class Demo06Graph {

//    StringBuilder sb;

    public static void main(String[] args) throws IOException {
        long startTime=System.currentTimeMillis();   //获取开始时间
        List<String> res = IOUtils.getFastaReader("src/test.fq");
//        System.out.println(res.get(res.size()-1));

        BufferedWriter simplifyWriter = IOUtils.getTextWriter("E:/WSL/task/testForFastaReader2/simplify-reads.fa");
//        System.out.println(Integer.MAX_VALUE);
        int length = 10;
        double frequency = 0.0006;

        Demo04Mini mn = new Demo04Mini(length,frequency);
        HashMap<String, String> miniMap = mn.getMini();
        System.out.println(miniMap.size());

        BufferedWriter mapKmerWriter = new BufferedWriter(new FileWriter("E:/WSL/task/testForFastaReader2/map_kmer.txt"));

        for (Map.Entry<String, String> entry : miniMap.entrySet()) {
            mapKmerWriter.write("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            mapKmerWriter.newLine();
        }
        mapKmerWriter.close();

        List<List<String>> Res = new ArrayList<>();
        List<List<Integer>> Pos = new ArrayList<>();
        for (String tmp:res) {
            int i = 0;
            List<String> tempRes = new ArrayList<>();
            List<Integer> tempPos = new ArrayList<>();
            while (i < tmp.length()-length){
                String test = tmp.substring(i, i+length);
                String s = miniMap.get(test);
                if (s == null){
                    i++;
                }else {
                    tempPos.add(i);

                    i += length;
//                    i++;
                    tempRes.add(s);
                }
            }
            Res.add(tempRes);
            Pos.add(tempPos);
        }

        for (List<String> re : Res) {
            for (String s : re) {
                simplifyWriter.write(s);
//                simplifyWriter.write(",");
            }
            simplifyWriter.newLine();
        }

//        BufferedWriter posWriter = IOUtils.getTextWriter("E:/WSL/task/testForFastaReader/minimize_pos.txt");
//        for (List<Integer> pos: Pos) {
//            for (Integer in:pos) {
//                posWriter.write(in + "\t");
//            }
//            posWriter.newLine();
//        }
//        posWriter.close();


        simplifyWriter.close();
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");


        long startTime2=System.currentTimeMillis();   //获取开始时间

        BufferedReader textReader = IOUtils.getTextReader("E:/WSL/task/testForFastaReader2/simplify-reads.fa");
        List<String> kmer = new ArrayList<>();
        HashMap<String,Integer> countTable = new HashMap<>();
        HashMap<String,String> kmerSequence = new HashMap<>();
        HashMap<String, Integer> kmerIsBack = new HashMap<>();

        String str;
        int row = 0;
        while ((str =textReader.readLine())!=null){
            for (int i = 0; i < str.length() - 15; i += 3) {
                String tmp = str.substring(i,i+15);

                int i1 = i/3;
                int i2;
                if (i1 != 0){
                    i2 = Pos.get(row).get(i1);
                }else {
                    i2=0;
                }
//                    String end = tmp.substring(12);
                int e1 = i1 + 6 ;
                if (Pos.get(row).size() == e1){
                    e1 = e1 -1;
                }
                if (kmer.contains(tmp)){
                    Integer value = countTable.get(tmp);
                    value += 1;
                    countTable.put(tmp,value);
                    if ((e1-i1) > kmerIsBack.get(tmp)){
                        Integer e2 = Pos.get(row).get(e1);
                        kmerSequence.put(tmp, res.get(row).substring(i2,e2));
                    }


                }else {

                    Integer e2 = Pos.get(row).get(e1);

                    kmer.add(tmp);
                    kmerIsBack.put(tmp, e1-i1);

                    kmerSequence.put(tmp, res.get(row).substring(i2,e2));
                    countTable.put(tmp,1);
                }

            }
            row++;
        }

//        System.out.println(kmer.size());

        for (String mer: kmer) {
            for (int i = 0; i < Res.size(); i++) {
                if (Res.get(i).contains(mer)){

                }
            }
        }


        BufferedWriter posWriter = new BufferedWriter(new FileWriter("E:/WSL/task/testForFastaReader2/baseTable.txt"));
        for (Map.Entry<String, String> entry : kmerSequence.entrySet()) {
            posWriter.write("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            posWriter.newLine();
        }
        posWriter.close();

        HashMap<String, Integer> res1 = new HashMap<>();
        BufferedWriter countWriter = new BufferedWriter(new FileWriter("E:/WSL/task/testForFastaReader2/countTable.txt"));
        for (Map.Entry<String, Integer> entry : countTable.entrySet()) {
            res1.put(entry.getKey(),entry.getValue());
            countWriter.write("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            countWriter.newLine();
        }
        countWriter.close();

        List<String> strings = new ArrayList<>(res1.keySet());
        List<String> contigs = euler(strings);
//        contigs.add("668527706589771808771704707700467665659960750494714413770945947926718471731748595774613692386223975730544796620927974928536975926664850540916512916509720693527465714847783727847649866716728912590817751361718667566192592656898631053693660626664641493676704799702718590783783596252444734861607641720715650736525733817716325717654812943783684639716660719824515564936768052510967966773975835950963595783783975775847845847942771969594696129590874969719639211680049848693758494715771701282251464714719843846967828669831703619914399495627925925799690781914668575");

        for (String contig: contigs) {
            List<Integer> tempReads  = new ArrayList<>();
            for (List<String> sim:Res) {
                StringBuilder s = new StringBuilder();
                for (String value : sim) {
                    s.append(value);
                }
//                System.out.println(s);
                if (contig.contains(s)){
                    int i = Res.indexOf(sim);
                    if (!tempReads.contains(i)){
                        tempReads.add(i);
                    }
                }
            }
            for (Integer iii:tempReads) {
                System.out.print(iii);
                System.out.print(",");
            }
            System.out.println();}

        BufferedWriter faWriter = new BufferedWriter(new FileWriter("E:/WSL/task/testForFastaReader2/song.fa"));
        for (String contig: contigs) {
            StringBuilder sbRes = new StringBuilder(kmerSequence.get(contig.substring(0,15)));
            for (int i = 3; i < contig.length()-15; i += 3) {
                int end = i + 15;
                String tmp = sBind(sbRes.toString(),kmerSequence.get(contig.substring(i,end)),1);
//                System.out.println(contig.substring(0,15));
//                System.out.println(contig.substring(i,end));
                if (tmp == "0"){
                    System.out.println("SB");
                    System.exit(1);
                }else {
                    sbRes = new StringBuilder(tmp);
                }
            }
            faWriter.write(">"+"\n");
            faWriter.write(sbRes.toString());
            faWriter.newLine();
//            sbRes);

        }
        faWriter.close();


        List<String> fastaReader = IOUtils.getFastaReader("E:/WSL/task/testForFastaReader2/complete.fa");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fastaReader.size(); i++) {
            stringBuilder.append(fastaReader.get(i));

        }
        System.out.println(stringBuilder);

        String simFromSeq = getSimFromSeq(stringBuilder.toString(), miniMap);
        System.out.println(simFromSeq);


        long endTime2=System.currentTimeMillis(); //获取结束时间
//        System.out.println(res.size());
        System.out.println("程序运行时间： "+(endTime2-startTime2)+"ms");

    }


    public static void getForSequence(String str, List<String> dic,StringBuilder sb){
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


    public static List<String> euler(List<String> strings){
        int i = 1;
        List<String> res = new ArrayList<>();
        while (!strings.isEmpty()){
//            System.out.println(strings.size());
            String next = strings.get(0);
            System.out.println("Contig" + i +": ");
//            System.out.println("Initail: " + next);

            StringBuilder sb = new StringBuilder();
            sb.append(next);

            getForSequence(next, strings,sb);

//            System.out.println("Front: " + sb);
            String back = null;
//            System.out.println(strings.size());
            StringBuilder sb2 = new StringBuilder(next);
            getBackSequence(next,strings,sb2);
//            System.out.println("Back: " + sb2);
//            System.out.println(strings.size());
            System.out.println(sb2 + sb.toString().substring(next.length()));
            res.add(sb2 + sb.toString().substring(next.length()));
            i++;
        }
        return res;
    }


    public static String sBind(String a, String b, int gap){
        int ind = Math.min(a.length(),b.length());
        for (int i = 5; i < ind; i+=gap) {
            if (a.substring(a.length() - i).equals(b.substring(0,i))){
//                System.out.println(i);
                return a+b.substring(i);
            }
        }
        return "0";
    }

//    public static String rBind(List<String> reads, String tmp, StringBuilder sb){
//        reads.remove(tmp);
//        for (int i = 0; i < reads.size(); i++) {
//            String s = sBind(tmp, reads.get(i), 1);
//            if (!s.equals("0")){
//                sb = new StringBuilder(s);
//            }
//        }
//    }

    public static String getSimFromSeq(String seq, HashMap<String, String> minimap){
        StringBuilder res = new StringBuilder();
        Set<String> kmer = minimap.keySet();
        for (int i = 0; i < seq.length() - 10; i += 1) {
            String tmp = seq.substring(i,i+10);

            if (kmer.contains(tmp)){
                res.append(minimap.get(tmp));
            }
        }
        return res.toString();
    }
}
