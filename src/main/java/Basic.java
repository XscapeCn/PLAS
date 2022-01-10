import java.io.*;
import java.util.*;

public class Basic {

    String inFile;
    File outFile;
    File simReadsFile;
    File kmerSequenceFile;
    File kmerCountFile;
    File mapFile;
    File simPosFile;
    List<String> rawReads = new ArrayList<>();
    HashMap<String, String> miniMap = new HashMap<>();
    List<List<String>> simReads = new ArrayList<>();
    List<List<Integer>> simPos = new ArrayList<>();
    List<String> kmer = new ArrayList<>();
    HashMap<String,Integer> kmerCount = new HashMap<>();
    HashMap<String,String> kmerSequence = new HashMap<>();
    HashMap<String, Integer> kmerIsBack = new HashMap<>();
    List<String> simContigs = new ArrayList<>();
    List<String> contigs = new ArrayList<>();
    int miniLength;
    double frequency;
    int boo = 0;

    public static void main(String[] args) {
        new Basic("E:/WSL/task/testForFastaReader2/reads-0.00.fa", 10, 0.0006, 1);
    }

    public Basic(String inFile, int miniLength, double frequency, int boo){
        this.inFile = inFile;
        this.miniLength = miniLength;
        this.frequency = frequency;
        this.boo = boo;
        initialize();
    }


    public void initialize(){
        long startTime=System.currentTimeMillis();   //获取开始时间

        //Fasta\q -> Reads
        this.rawReads = IOUtils.getFastaReader(inFile);
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("GetFasta running time："+(endTime-startTime)+"ms");
        //Generate minimizer
        GetMiniHashMap mn = new GetMiniHashMap(miniLength,frequency);
        miniMap = mn.getMini();


        List<String> tempRes = new ArrayList<>();
        List<Integer> tempPos = new ArrayList<>();
        int i= 0;
        System.out.println("===========");
//        System.out.println(testFa.get(3).length());
        List<String> testFa = IOUtils.getFastaReader("E:/WSL/task/testForFastaReader2/complete.fa");
        List<String> hifiFa = IOUtils.getFastaReader("E:/WSL/task/testForFastaReader2/hifi.fa");


        while (i < testFa.get(2).length()-miniLength){
            String Box = testFa.get(2).substring(i, i+miniLength);
            String s = miniMap.get(Box);
            if (s == null){
                i++;
            }else {
                tempPos.add(i);
                i += miniLength;
                tempRes.add(s);
            }
        }
        for (String a: tempRes
        ) {

            System.out.print(a);
        }
        System.out.println();


        while (i < hifiFa.get(2).length()-miniLength){
            String Box = hifiFa.get(2).substring(i, i+miniLength);
            String s = miniMap.get(Box);
            if (s == null){
                i++;
            }else {
                tempPos.add(i);
                i += miniLength;
                tempRes.add(s);
            }
        }
        for (String a: tempRes
        ) {

            System.out.print(a);
        }
        System.out.println();

        System.out.println("+++++++++++");



        //Convert Reads to simReads
        convertToSim();
        //simReads -> simKmer
        convertToKmer();
        //De Brujn
        long startTime2=System.currentTimeMillis();   //获取开始时间


        simContigs = euler(new ArrayList<>(kmerSequence.keySet()));
        long endTime2 = System.currentTimeMillis(); //获取结束时间
        System.out.println("euler running time："+(endTime2-startTime2)+"ms");
        //Convert to base level
        long startTime3 = System.currentTimeMillis();   //获取开始时间
        int ii = 1;
        for (String cont:simContigs
             ) {
            System.out.println("Contig"+ ii + ": ");
            System.out.println(cont);
            ii++;
        }

        contigs = convertToBaseLevel(simContigs, kmerSequence);
        long endTime3 = System.currentTimeMillis(); //获取结束时间
        System.out.println("converToBase running time：" + (endTime3-startTime3) + "ms");

        //init file name
        File inf = new File(inFile);
        File dic = inf.getParentFile();
        simReadsFile = new File(dic + "/simReads.txt");
        kmerSequenceFile = new File(dic + "/kmerSequence.txt");
        kmerCountFile = new File(dic + "/kmerCount.txt");
        outFile = new File(dic + "/song.fa");
        mapFile = new File(dic + "/miniMap.txt");
        simPosFile = new File(dic + "/simPos.txt");
        System.out.println(outFile.getPath());

        writeContig();
//        System.out.println(1);

        if (boo == 1){
            writeAllFile();
        }


//        List<String> fastaReader = IOUtils.getFastaReader("C:/Users/lenovo/Desktop/complete.fa");
//        convertToSim(fastaReader,miniLength,miniMap);



    }


    public void writeAllFile(){
        IOUtils.hashMapToFile(this.miniMap,mapFile.getPath());
        IOUtils.hashMapToFile(kmerSequence, kmerSequenceFile.getPath());
        IOUtils.hashMapToFile(kmerCount, kmerCountFile.getPath());
        BufferedWriter simplifyWriter = IOUtils.getTextWriter(simReadsFile.getPath());
        BufferedWriter posWriter = IOUtils.getTextWriter(simPosFile.getPath());

        try{
            System.out.println(simReads.size());
            for (List<String> re : simReads) {
                for (String s : re) {
                    simplifyWriter.write(s);
//                    System.out.println(s);
                }
                simplifyWriter.newLine();
//                System.out.println();
            }
            simplifyWriter.close();

            for (List<Integer> pos: simPos) {
                for (Integer in:pos) {
                    posWriter.write(in + "\t");
                }
                posWriter.newLine();
            }
            posWriter.close();
        }catch (Exception e){
            System.exit(1);
        }
    }
    public void convertToSim(){
        long startTime=System.currentTimeMillis();   //获取开始时间
        System.out.println(rawReads.size());
        for (String read:rawReads) {
            int i = 0;
            List<String> tempRes = new ArrayList<>();
            List<Integer> tempPos = new ArrayList<>();
            while (i < read.length()-miniLength){
                String Box = read.substring(i, i+miniLength);
                String s = miniMap.get(Box);
                if (s == null){
                    i++;
                }else {
                    tempPos.add(i);
                    i += miniLength;
                    tempRes.add(s);
                }
            }
            simReads.add(tempRes);
            simPos.add(tempPos);
        }
        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("ConvertToSim running time："+ (endTime - startTime) + "ms");

    }

    public static void  convertToSim(List<String> fs, int miniLength, Map<String,String> miniMap){
        long startTime = System.currentTimeMillis();   //获取开始时间
        StringBuilder sb = new StringBuilder();

        for (String read:fs) {
            System.out.println(read);
            int i = 0;
            while (i < read.length()-miniLength){
                String Box = read.substring(i, i+miniLength);
                String s = miniMap.get(Box);
                if (s == null){
                    i++;
                }else {
                    sb.append(s);
                    i += miniLength;
                }
            }
        }
        System.out.println(sb);
        long endTime=System.currentTimeMillis(); //获取结束时间

        System.out.println("ConvertToSim running time：" + (endTime-startTime) + "ms");
    }

    public void convertToKmer(){
        long startTime = System.currentTimeMillis();   //获取开始时间

        List<String> tmpList = new ArrayList<>();
        for (List<String> re : simReads) {
            StringBuilder sb = new StringBuilder();
            for (String s : re) {
                sb.append(s);
            }
            tmpList.add(sb.toString());
        }

        int row = 0;
        for (String str: tmpList) {
            for (int i = 0; i < str.length() - 15; i += 3) {
                String tmp = str.substring(i,i+15);
                int i1 = i/3;
                int i2;
                if (i1 != 0){
                    i2 = simPos.get(row).get(i1);
                }else {
                    i2 = 0;
                }
//                    String end = tmp.substring(12);
                int e1 = i1 + 6 ;
                if (simPos.get(row).size() == e1){
                    e1 = e1 - 1;
                }
                if (kmer.contains(tmp)){
                    Integer value = kmerCount.get(tmp);
                    value += 1;
                    kmerCount.put(tmp,value);
                    if ((e1-i1) > kmerIsBack.get(tmp)){
                        Integer e2 = simPos.get(row).get(e1);
                        kmerSequence.put(tmp, rawReads.get(row).substring(i2,e2));
                    }
                }else {
                    Integer e2 = simPos.get(row).get(e1);
                    kmer.add(tmp);
                    kmerIsBack.put(tmp, e1-i1);
                    kmerSequence.put(tmp, rawReads.get(row).substring(i2,e2));
                    kmerCount.put(tmp,1);
                }
            }
            row++;
        }
        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("ConvertToKmer running time：" + (endTime-startTime) + "ms");
    }


//    public static void main(String[] args) throws IOException {
//        long startTime=System.currentTimeMillis();   //获取开始时间
//        List<String> rawReads = IOUtils.getFastaReader("src/Box.fq");
//        int length = 10;
//        double frequency = 0.0006;
//
//        GetMiniHashMap mn = new GetMiniHashMap(length,frequency);
//        HashMap<String, String> miniMap = mn.getMini();
//        System.out.println("Counts of minimizer: " + miniMap.size());
//
//        IOUtils.hashMapToFile(miniMap,"E:/WSL/task/testForFastaReader2/map_kmer.txt");
//
//
//        List<List<String>> simReads = new ArrayList<>();
//        List<List<Integer>> simPos = new ArrayList<>();
//        for (String read:rawReads) {
//            int i = 0;
//            List<String> tempRes = new ArrayList<>();
//            List<Integer> tempPos = new ArrayList<>();
//            while (i < read.length()-length){
//                String Box = read.substring(i, i+length);
//                String s = miniMap.get(Box);
//                if (s == null){
//                    i++;
//                }else {
//                    tempPos.add(i);
//                    i += length;
//                    tempRes.add(s);
//                }
//            }
//            simReads.add(tempRes);
//            simPos.add(tempPos);
//        }
//
//
//        BufferedWriter simplifyWriter = IOUtils.getTextWriter("E:/WSL/task/testForFastaReader2/simplify-reads.fa");
//        for (List<String> re : simReads) {
//            for (String s : re) {
//                simplifyWriter.write(s);
//            }
//            simplifyWriter.newLine();
//        }
//        simplifyWriter.close();
//
////        BufferedWriter posWriter = IOUtils.getTextWriter("E:/WSL/task/testForFastaReader/minimize_pos.txt");
////        for (List<Integer> pos: simPos) {
////            for (Integer in:pos) {
////                posWriter.write(in + "\t");
////            }
////            posWriter.newLine();
////        }
////        posWriter.close();
//        long endTime=System.currentTimeMillis(); //获取结束时间
//        System.out.println("Program running time："+(endTime-startTime)+"ms");
//
//
//        long startTime2=System.currentTimeMillis();   //获取开始时间
//
//        BufferedReader textReader = IOUtils.getTextReader("E:/WSL/task/testForFastaReader2/simplify-reads.fa");
//        List<String> kmer = new ArrayList<>();
//        HashMap<String,Integer> kmerCount = new HashMap<>();
//        HashMap<String,String> kmerSequence = new HashMap<>();
//        HashMap<String, Integer> kmerIsBack = new HashMap<>();
//
//        String str;
//        int row = 0;
//        while ((str =textReader.readLine())!=null){
//            for (int i = 0; i < str.length() - 15; i += 3) {
//                String tmp = str.substring(i,i+15);
//
//                int i1 = i/3;
//                int i2;
//                if (i1 != 0){
//                    i2 = simPos.get(row).get(i1);
//                }else {
//                    i2=0;
//                }
////                    String end = tmp.substring(12);
//                int e1 = i1 + 6 ;
//                if (simPos.get(row).size() == e1){
//                    e1 = e1 -1;
//                }
//                if (kmer.contains(tmp)){
//                    Integer value = kmerCount.get(tmp);
//                    value += 1;
//                    kmerCount.put(tmp,value);
//                    if ((e1-i1) > kmerIsBack.get(tmp)){
//                        Integer e2 = simPos.get(row).get(e1);
//                        kmerSequence.put(tmp, rawReads.get(row).substring(i2,e2));
//                    }
//                }else {
//
//                    Integer e2 = simPos.get(row).get(e1);
//
//                    kmer.add(tmp);
//                    kmerIsBack.put(tmp, e1-i1);
//
//                    kmerSequence.put(tmp, rawReads.get(row).substring(i2,e2));
//                    kmerCount.put(tmp,1);
//                }
//
//            }
//            row++;
//        }
//
////        System.out.println(kmer.size());
//
//        for (String mer: kmer) {
//            for (int i = 0; i < simReads.size(); i++) {
//                if (simReads.get(i).contains(mer)){
//
//                }
//            }
//        }
//
//        IOUtils.hashMapToFile(kmerSequence, "E:/WSL/task/testForFastaReader2/kmerSequence.txt");
//        IOUtils.hashMapToFile(kmerCount, "E:/WSL/task/testForFastaReader2/kmerCount.txt");
//
//        HashMap<String, Integer> res1 = new HashMap<>();
//        BufferedWriter countWriter = new BufferedWriter(new FileWriter("E:/WSL/task/testForFastaReader2/kmerCount.txt"));
//        for (Map.Entry<String, Integer> entry : kmerCount.entrySet()) {
//            res1.put(entry.getKey(),entry.getValue());
//            countWriter.write("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//            countWriter.newLine();
//        }
//        countWriter.close();
//
//        List<String> strings = new ArrayList<>(res1.keySet());
//        List<String> simContigs = euler(strings);
//        List<String> contigs = convertToBaseLevel(simContigs, kmerSequence);
//
////        getReadsInContigs(simContigs, simReads);
//        writeContig(contigs, "E:/WSL/task/testForFastaReader2/song.fa");
//
//        long endTime2=System.currentTimeMillis(); //获取结束时间
////        System.out.println(rawReads.size());
//        System.out.println("Program running time："+(endTime2-startTime2)+"ms");
//
//    }

    public static void getReadsInContigs(List<String> simContigs, List<List<String>> simReads){
        for (String contig : simContigs) {
            List<Integer> tempReads  = new ArrayList<>();
            for (List<String> sim:simReads) {
                StringBuilder s = new StringBuilder();
                for (String value : sim) {
                    s.append(value);
                }
//                System.out.println(s);
                if (contig.contains(s)){
                    int i = simReads.indexOf(sim);
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
    }


    public static void getForSequence(String str, List<String> dic,StringBuilder sb){
        dic.remove(str);

        for (int i = 0; i < dic.size(); i++) {
            if (str.substring(3,15).equals(dic.get(i).substring(0,12))){
//                res = res + strr.substring(12,15);
                sb.append(dic.get(i), 12, 15);
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


    public static List<String> euler(List<String> arr){
        int i = 1;
        List<String> res = new ArrayList<>();
        while (!arr.isEmpty()){
//            System.out.println(arr.size());
            String next = arr.get(0);
//            System.out.println("Contig" + i +": ");
//            System.out.println("Initail: " + next);
            StringBuilder frontSB = new StringBuilder();
            frontSB.append(next);
            getForSequence(next, arr, frontSB);
//            System.out.println("Front: " + frontSB);
//            String back = null;
//            System.out.println(arr.size());
            StringBuilder backSB = new StringBuilder(next);
            getBackSequence(next, arr, backSB);
//            System.out.println("Back: " + backSB);
//            System.out.println(arr.size());
//            System.out.println(backSB + frontSB.toString().substring(next.length()));
            res.add(backSB + frontSB.toString().substring(next.length()));
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


    public static List<String> convertToBaseLevel(List<String> simContigs, HashMap<String,String> kmerSequence){
        List<String> res = new ArrayList<>();
        for (String contig: simContigs) {
            System.out.println(contig);
            StringBuilder sbRes = new StringBuilder(kmerSequence.get(contig.substring(0,15)));
            for (int i = 3; i < contig.length()-15; i += 3) {
                int end = i + 15;
//                System.out.println(i);
//                System.out.println(contig.substring(i,end));
                String tmp = sBind(sbRes.toString(),kmerSequence.get(contig.substring(i,end)),1);
                if (tmp.equals("0")){
                    System.out.println("SB");
                    System.exit(1);
                }else {
                    sbRes = new StringBuilder(tmp);
                }
            }
            res.add(sbRes.toString());
        }
        return res;
    }

//    public static void writeContig(List<String> contigs, String outFile){
//        try{
//            BufferedWriter cWriter = IOUtils.getTextWriter(outFile);
//            for (int i = 0; i < contigs.size(); i++) {
//                cWriter.write(">"+ i + "\n");
//                cWriter.write(contigs.get(i));
//                cWriter.newLine();
//            }
//        }catch (Exception e){
//            System.exit(1);
//        }
//    }

    public void writeContig(){
        try{
            BufferedWriter cWriter = IOUtils.getTextWriter(outFile.getPath());
            for (int i = 0; i < contigs.size(); i++) {
//                System.out.println(1);
                cWriter.write(">"+ i+1 + "\n");
                cWriter.write(contigs.get(i));
                cWriter.newLine();
            }
            cWriter.close();
        }catch (Exception e){
            System.exit(1);
        }
    }
}
