import java.util.HashMap;
import java.util.Stack;

import static java.lang.Math.abs;

public class GetMiniHashMap {
    HashMap<String,String> mini = new HashMap<>();
    Stack<Character> s = new Stack<>();
    int i = 0;
    int cnt = 0;
    double count;

    public GetMiniHashMap(int len, double d){
        char[] acid = new char[]{'A','C','G','T'};
        count =d;
        this.randomMini(acid, 0, len);
    }

    public HashMap<String, String> getMini() {
        System.out.println("Counts of minimizer: " + mini.size());
        return mini;
    }

    public void randomMini(char[] acid, int curnum, int maxnum) {
        if (curnum == maxnum) {
            StringBuilder sb = new StringBuilder();
            for (Character str:s) {sb.append(str);}
            cnt++;
            //To do: change the "000" to the hashcode of minimizer
            if (abs(sb.toString().hashCode()) < Integer.MAX_VALUE*this.count){
                if (i < 10){
                    this.mini.put(sb.toString(), "00" + i);
                }else if (i < 100) {
                    this.mini.put(sb.toString(), "0" + i);
                }else {
                    this.mini.put(sb.toString(), "" + i);
                }
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
}
