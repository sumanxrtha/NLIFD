package application;

import java.util.ArrayList;
import java.util.Iterator;

public class DivideList {

    public static ArrayList<String> whereList; // for where clause
    public static ArrayList<String> selectList; // for select clause

    // processed arraylist and divide them into two list
    public static ArrayList<String> PopulateList(ArrayList<String> processedList) {

        int size = processedList.size();

        selectList = new ArrayList<>();
        whereList = new ArrayList<>();

        // flag to test if root exists or not
        boolean hasRooj = false;
        boolean hasSubj = false;
        int i = 0;

//        System.out.println(size);
        while (size > 0) {
            Iterator<String> it = processedList.iterator();
            while (it.hasNext()) {
                String[] split = Split(it.next(), "(");
                // rule 1
                if (split[0].equals("root")) {
                    String[] temp = GetGovDep(split[1]);
                    whereList.add(temp[1].trim());
                    hasRooj = true;
                    it.remove();
                }
                //rule 2
                else if (split[0].equals("nsubj")) {
                    hasSubj = true;
                    String[] temp = GetGovDep(split[1]);
                    selectList.add(temp[1].trim());
                    it.remove();
                }
            }
        }


        return processedList;
    }

    // split functions (regular expression use garney yeha)
    public static String[] Split(String s, String splitter) {
        String[] split = s.split("\\" + splitter);
        return split;
    }

    // returns governor and dependents ====>  relation(gov,dep) format ma hunxa !!
    public static String[] GetGovDep(String string) {
        String[] GovDep = new String[2];
        // divide the string into governor and dependents
        String[] process = string.split(",");
        // extract the governor from the above code
        String[] gov = process[0].split("-");
        // extract the dependent from the process
        String[] dep = process[1].split("-");

        // add the gov and dep to the array and return
        GovDep[0] = gov[0];
        GovDep[1] = dep[0];

        return GovDep;
    }

}
