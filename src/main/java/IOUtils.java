/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author Fei Lu & Song Xu
 */
public class IOUtils {

    public static String getReverseCom(String s){
        StringBuilder sb = new StringBuilder();
        for (int i = s.length(); i > 0 ; i--) {
            if (s.charAt(i-1) == 'A'){
                sb.append("T");
            }else if (s.charAt(i-1) == 'T'){
                sb.append("A");

            }else if (s.charAt(i-1) == 'C'){
                sb.append("G");

            }else if (s.charAt(i-1) == 'G'){
                sb.append("C");
            }
        }
        return sb.toString();
    }

    public static BufferedReader getTextGzipReader (String infileS) {
        BufferedReader br = null;
        try {
            //br = new BufferedReader(new InputStreamReader(new MultiMemberGZIPInputStream(new FileInputStream(infileS))));
            br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(infileS), 65536)), 65536);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return br;
    }

    public static List<String> getFastqReader(String infileS){
        BufferedReader br = null;
        List<String> res = new ArrayList<>();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(infileS)), 65536);
            String str;
            while (br.readLine() != null){
                str = br.readLine();
                res.add(str);
                br.readLine();
                br.readLine();
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static <K,V> void hashMapToFile(HashMap<K,V> map, String outFiles){
        try {
            BufferedWriter ow = getTextWriter(outFiles);
            for (Map.Entry<K, V> entry : map.entrySet()) {
                ow.write("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                ow.newLine();}
            ow.close();
        }catch (Exception e){
            System.exit(1);
        }
    }


    public static List<String> getFastaReader(String infileS){
        BufferedReader br;
        List<String> res = new ArrayList<>();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(infileS)), 65536);
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = br.readLine()) != null){
                if (!str.startsWith(">")){
                    sb.append(str);

                }else{
                    res.add(sb.toString());
                    res.add(getReverseCom(sb.toString()));
                    sb = new StringBuilder();
                }
            }
            res.add(sb.toString());
            res.add(getReverseCom(sb.toString()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static BufferedReader getTextGzipReader (String infileS, int bufferSize) {
        BufferedReader br = null;
        try {
            //br = new BufferedReader(new InputStreamReader(new MultiMemberGZIPInputStream(new FileInputStream(infileS))));
            br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(infileS), bufferSize)), bufferSize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return br;
    }

    public static BufferedWriter getTextGzipWriter (String outfileS) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(outfileS), 65536)), 65536);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bw;
    }

    public static BufferedWriter getTextGzipWriter (String outfileS, int bufferSize) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(outfileS), bufferSize)), bufferSize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bw;
    }

    public static BufferedWriter getTextWriter (String outfileS) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter (new FileWriter(outfileS), 65536);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bw;
    }

    public static BufferedReader getTextReader (String infileS) {
        BufferedReader br = null;
        try {
            br = new BufferedReader (new FileReader(infileS), 65536);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return br;
    }

    public static DataOutputStream getBinaryGzipWriter (String outfileS) {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(outfileS), 65536), 65536));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dos;
    }

    public static DataOutputStream getBinaryWriter (String outfileS) {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outfileS), 65536));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dos;
    }

    public static DataOutputStream getBinaryWriter (String outfileS, int bufferSize) {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outfileS), bufferSize));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dos;
    }

    public static DataInputStream getBinaryGzipReader (String infileS) {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(infileS), 65536), 65536));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dis;
    }

    public static DataInputStream getBinaryReader (String infileS) {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(infileS), 65536));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dis;
    }

    public static DataInputStream getBinaryReader (String infileS, int bufferSize) {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(infileS), bufferSize));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dis;
    }

    public static ObjectOutputStream getObjectWriter (String outfileS) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(outfileS), 65536));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return oos;
    }

    public static ObjectInputStream getObjectReader (String infileS) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(infileS), 65536));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ois;
    }

    public static List<File> getVisibleFileListInDir (String inDirS) {
        File[] fs = new File(inDirS).listFiles();
        List<File> fList = new ArrayList<>();
        for (int i = 0; i < fs.length; i++) {
            if (fs[i].isHidden()) continue;
            fList.add(fs[i]);
        }
        Collections.sort(fList);
        return fList;
    }

    public static List<File> getDirListInDir (String inDirS) {
        File[] fs = new File(inDirS).listFiles();
        List<File> fList = new ArrayList<>();
        for (int i = 0; i < fs.length; i++) {
            if (fs[i].isDirectory()) {
                fList.add(fs[i]);
            }
        }
        Collections.sort(fList);
        return fList;
    }

    public static List<File> getDirListInDirStartsWith (String inDirS, String startStr) {
        List<File> fList = getDirListInDir(inDirS);
        List<File> nfList = new ArrayList<>();
        for (int i = 0; i < fList.size(); i++) {
            if (fList.get(i).getName().startsWith(startStr)) nfList.add(fList.get(i));
        }
        return nfList;
    }

    public static List<File> getDirListInDirEndsWith (String inDirS, String endStr) {
        List<File> fList = getDirListInDir(inDirS);
        List<File> nfList = new ArrayList<>();
        for (int i = 0; i < fList.size(); i++) {
            if (fList.get(i).getName().endsWith(endStr)) nfList.add(fList.get(i));
        }
        return nfList;
    }

    public static List<File> getFileListInDir (String inDirS) {
        File[] fs = new File(inDirS).listFiles();
        assert fs != null;
        List<File> fList = new ArrayList<>(Arrays.asList(fs));
        Collections.sort(fList);
        return fList;
    }

    public static List<File> getFileListInDirContains (String inDirS, String containStr) {
        File[] fs = new File(inDirS).listFiles();
        assert fs != null;
        fs = listFilesContains(fs, containStr);
        List<File> fList = new ArrayList<>(Arrays.asList(fs));
        Collections.sort(fList);
        return fList;
    }

    public static List<File> getFileListInDirStartsWith (String inDirS, String startStr) {
        File[] fs = new File(inDirS).listFiles();
        fs = listFilesStartsWith(fs, startStr);
        List<File> fList = new ArrayList<>(Arrays.asList(fs));
        Collections.sort(fList);
        return fList;
    }

    public static List<File> getFileListInDirEndsWith (String inDirS, String endStr) {
        File[] fs = new File(inDirS).listFiles();
        fs = listFilesEndsWith(fs, endStr);
        List<File> fList = new ArrayList<>(Arrays.asList(fs));
        Collections.sort(fList);
        return fList;
    }

    public static File[] listFilesContains (File[] fAll, String containStr) {
        List<File> al = new ArrayList();
        for (int i = 0; i < fAll.length; i++) {
            if (fAll[i].getName().contains(containStr)) al.add(fAll[i]);
        }
        return al.toArray(new File[al.size()]);
    }

    public static File[] listFilesStartsWith (File[] fAll, String startStr) {
        List<File> al = new ArrayList();
        for (int i = 0; i < fAll.length; i++) {
            if (fAll[i].getName().startsWith(startStr)) al.add(fAll[i]);
        }
        return al.toArray(new File[al.size()]);
    }

    public static File[] listFilesEndsWith (File[] fAll, String endStr) {
        List<File> al = new ArrayList();
        for (File file : fAll) {
            if (file.getName().endsWith(endStr)) al.add(file);
        }
        return al.toArray(new File[0]);
    }

    /**
     * List all the files in a directory
     * @param dir
     * @return
     */
    public static File[] listRecursiveFiles (File dir) {
        TreeSet<File> fSet = getRecursiveFiles (dir);
        return fSet.toArray(new File[fSet.size()]);
    }

    private static TreeSet<File> getRecursiveFiles (File dir) {
        TreeSet<File> fileTree = new TreeSet();
        for (File entry : dir.listFiles()) {
            if (entry.isFile()) fileTree.add(entry);
            else fileTree.addAll(getRecursiveFiles(entry));
        }
        return fileTree;
    }
}
