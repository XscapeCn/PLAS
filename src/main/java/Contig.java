import java.io.File;

public class Contig {

    public static void main(String[] args) {

        String file = "E:/WSL/task/testForFastaReader2/map_kmer.txt";

        File f = new File(file);
        File f2 = new File(f.getParentFile() + "/aaa.txt");
        System.out.println(f2.getPath());
    }

}
