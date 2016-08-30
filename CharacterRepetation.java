package regx;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bharathan on 25/10/15.
 * Created on 25/10/15 1:45 AM
 */
public class CharacterRepetation {
    public static void main(String[] args) {
//        String companyname_String = " tcs ,  cts ,  wipro ,  infosys , wipro ,  infosys, cts ,  wipro ,  infosys, wipro ,  infosys , tcs , cts , tcs ,  cts ,  wipro ,  infosys wipro ,  infosys cts ,  wipro , infosys wipro ,  infosys , tcs ,  cts";

        String[] comanyname = {"tcs", "cts", "wipro", "infosys"
                , "wipro", "infosys"
                , "cts", "wipro", "infosys"
                , "wipro", "infosys"
                ,"tcs", "cts"
                ,"tcs", "cts", "wipro", "infosys"
                , "wipro", "infosys"
                , "cts", "wipro", "infosys"
                , "wipro", "infosys"
                ,"tcs", "cts"};


        Map<String, Integer> wordCounter = new HashMap<String, Integer>();
        for (String word : comanyname) {
            Integer count = wordCounter.get(word);
            if (count == null) {
                wordCounter.put(word, 1);
            } else {
                wordCounter.put(word, count + 1);
            }
        }
        System.out.println(wordCounter.toString());
    }
    
    
    
    
    
    
    
    
}
