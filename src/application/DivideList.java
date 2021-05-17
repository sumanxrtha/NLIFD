package application;

import database.QueryParser;
//import sun.plugin.javascript.navig.Array;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class DivideList {

    public static ArrayList<String> whereList; // for where clause
    public static ArrayList<String> selectList; // for select clause

    // processed arraylist and divide them into two list
    @SuppressWarnings("unused")
    public static void PopulateList(ArrayList<String> processedList) {

        int size = processedList.size();

        selectList = new ArrayList<>(); // list for collecting select clause
        whereList = new ArrayList<>(); // lsit for collecting where clause

        // flag to test if root exists or not
        boolean hasRoot = false; // ROOT xa ki xaina vanney status check garna
        boolean hasSubj = false; // nSubj ko laagi
        int i = 0;

//        System.out.println(size);
        while (size > 0) {
            Iterator<String> it = processedList.iterator();
            while (it.hasNext()) {
                String[] split = Split(it.next(), "(");

                // rule 1
                // ROOT(gov,dep) => dep sadai main verb hunxa which is question focus
                // so root exist and add wherelist
                if (split[0].equals("root")) {
                    String[] temp = DivideList.GetGovDep(split[1]);
                    whereList.add(temp[1].trim());
                    hasRoot = true;
                    it.remove();
                }

                //rule 2
                //nsubj(gov,dep) vannale subject lai focus garxa !!

                else if (split[0].equals("nsubj")) {
                    hasSubj = true;
                    String[] temp = DivideList.GetGovDep(split[1]);

                    // sub rule
                    // 2.1
                    if (HasElement(whereList, temp[0].trim())) {
                        selectList.add(temp[1].trim());
                        it.remove();
                    }

                    // sub RUle for 2,
                    // 2.1 rule
                    // hasRoot flag lai check nagareko,
                    else if (!HasElement(whereList, temp[0].trim()) && hasRoot == false) {

                        whereList.add(temp[0].trim());
                        selectList.add(temp[1].trim());

                        it.remove();
                    }

                    // sub rule 3
                    // 2.3
                    else {
                        // just do nothing
                    }
                }

                // rule no 3
                else if (split[0].startsWith("prep") || split[0].endsWith("obj")) {
                    String[] temp = DivideList.GetGovDep(split[1].trim());

                    // sub rule for 3
                    // 3.1
                    // this step is for aggregation from database if exists on database


                    if (QueryParser.ChkOperatorStatus(temp[0])) {
                        String test = QueryParser.SelectWhere(temp[0]);
                        if (test.equals("SELECT")) {
                            selectList.add(temp[0].trim()+"-"+temp[1].trim());
                            it.remove();
                        } else if (test.equals("WHERE")) {
                            whereList.add(temp[0].trim() +"-"+temp[1].trim());
                            it.remove();
                        }
                    }

                    // sub rule for 3
                    // 3.2
                    // if
                    else if (HasElement(selectList, temp[0].trim())) {
                        if (!QueryParser.ChkStatus(temp[0].trim(),temp[1].trim())) {
                            whereList.add(temp[1].trim());
                        } else {
                            whereList.add(temp[1].trim());
                            selectList.add(temp[1].trim());
                        }
                        it.remove();
                    }
                    // 3.3
                    else if (!HasElement(selectList, temp[0].trim())) {
                        whereList.add(temp[1].trim());
                        selectList.add(temp[0].trim());
                    } else {
                        // do noting
                    }

                }

                // rule no. 4
                else if (split[0].endsWith("mod")) {
                    String[] temp = DivideList.GetGovDep(split[1]);
                    //sub rule i
                    if (HasElement(whereList, temp[0].trim())) {
                        whereList.add(temp[1].trim());
                        it.remove();
                        //System.out.println("1 added: mod: selection:"+temp[1]);
                    }

                    //sub rule ii
                    else if (HasElement(selectList, temp[0].trim())) {
                        selectList.add(temp[1].trim());
                        //System.out.println("2 added: prep or obj: projection:"+temp[1]);
                        it.remove();
                    } else {
                        /**
                         * Check for aggregation functions and operators
                         * */
                        String test = QueryParser.SelectWhere(temp[1]);
                        if (test.equals("SELECT")) {
                            selectList.add(temp[1].trim() + "-" + temp[0].trim());
                            it.remove();
                        } else if (test.equals("WHERE")) {
                            whereList.add(temp[1].trim() + "-" + temp[0].trim());
                            it.remove();
                        } else {
                            whereList.add(temp[1].trim());
                            it.remove();
                        }
                    }
                }

                //rule 5
                else if (split[0].equals("nn")) {
                    String[] temp = DivideList.GetGovDep(split[1]);
                    whereList.add(temp[1].trim());
                    //System.out.println("1 added: nn: selection:"+temp[1]);
                    it.remove();
                }

                //rule 6
                else if (split[0].startsWith("conj")) {
                    String[] temp = DivideList.GetGovDep(split[1]);
                    selectList.add(temp[1].trim());
                    //System.out.println("1 added: conj: selection:"+temp[1]);
                    it.remove();
                }

                //rule 7
                else if (split[0].startsWith("dep")) {
                    String[] temp = DivideList.GetGovDep(split[1]);
                    if (selectList.contains(temp[1].trim())) {
                        it.remove();
                    } else if (hasRoot == true && hasSubj == true) {
                        whereList.add(temp[1].trim());
                        it.remove();
                    } else {
                        //whereList.add(temp[1].trim());
                        it.remove();
                    }
                }
            }
            i++;
//                System.out.println(selectList);
            if (i>5)
                break;
    }
//       return processedList;
    }

    // split functions (regular expression use garney yeha)
    public static String[] Split(String s, String splitter) {
        // regex exp.
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

    public static boolean HasElement(ArrayList<String> list, String value) {
        boolean status = false;
        if (list.contains(value)) {
            status = true;
        }
        return status;
    }
}
