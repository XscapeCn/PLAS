import java.util.HashMap;
import java.util.Stack;

import static java.lang.Math.abs;

public class Demo04Mini {
    int cnt = 0;
    Stack<Character> s = new Stack<>();
    int i = 0;
    HashMap<String,String> mini = new HashMap<>();
    double count;

    public Demo04Mini(int len, double d){
        char[] acid = new char[]{'A','C','G','T'};
        count =d;
//        count = (int) ((int)Math.pow(4,len)*d);
        this.randomMini(acid, 0, len);
    }

    public HashMap<String, String> getMini() {
        return mini;
    }

    public void randomMini(char[] acid, int curnum, int maxnum) {
        if (curnum == maxnum) {
            StringBuilder sb = new StringBuilder();
            for (Character str:s) {sb.append(str);}
            cnt++;
//            System.out.println(abs(sb.toString().hashCode()));
            if (abs(sb.toString().hashCode()) < Integer.MAX_VALUE*this.count){

//                System.out.println(sb);
//                this.mini.put(sb.toString(),"m"+i);

                if (i < 10){
                    this.mini.put(sb.toString(), "00" + i);
                }else if (i < 100) {
                    this.mini.put(sb.toString(), "0" + i);
                }else {
                    this.mini.put(sb.toString(), "" + i);
                }
//                System.out.println(i);
                i++;

            }
            return;
        }
        for (char str : acid) {
            s.push(str);
            randomMini(acid, curnum + 1, maxnum);
            s.pop();
        }
    }

//    public void randomMini(char[] acid, int curnum, int maxnum) {
//        if (curnum == maxnum) {
//            StringBuilder sb = new StringBuilder();
//            cnt++;
//            if (i < this.count){
//                for (Character str:s) {sb.append(str);}
////                System.out.println(sb);
////                this.mini.put(sb.toString(),"m"+i);
//
//                if (i < 10){
//                    this.mini.put(sb.toString(), "00" + i);
//                }else if (i < 100) {
//                    this.mini.put(sb.toString(), "0" + i);
//                }else {
//                    this.mini.put(sb.toString(), "" + i);
//                }
////                System.out.println(i);
//            }
//            i++;
//            return;
//        }
//        for (char str : acid) {
//            s.push(str);
//            randomMini(acid, curnum + 1, maxnum);
//            s.pop();
//        }
//    }


}
