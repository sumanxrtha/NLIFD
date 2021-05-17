package application;

import com.sun.org.apache.xpath.internal.operations.Div;
import database.QueryParser;
import javafx.scene.control.Alert;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class QueryGeneration {

    public static ArrayList<String> select = new ArrayList<String>();
    public static ArrayList<String> where = new ArrayList<>();
    public static ArrayList<String> from = new ArrayList<>();

    public static void populateSelect() {
        Iterator<String> it = DivideList.selectList.iterator();

        while (it.hasNext()) {
            String[] temp = it.next().split("-");

            // if length grater than 1 it may have aggregation single function
            if (temp.length >1) {
                // checking existence for the column in database
                String operator = "";
                ArrayList<String> holdData = new ArrayList<>();
                holdData = QueryParser.GetSynonyms(temp[1].trim());
                if (holdData.size()>0) {
                    System.out.println(holdData.get(0) + " and " +holdData.get(1));
                    operator = QueryParser.getFunctionOperator(temp[0]);
//                    System.out.println("nothing got");
                    from.add(holdData.get(1));
                    select.add(operator+"("+holdData.get(0)+")");
                } else {
                    holdData = QueryParser.ListMatchedStems(temp[1]);
                    if (holdData.size() >0) {
                        operator = QueryParser.getFunctionOperator(temp[0]);
//                        System.out.println(operator);
                        from.add(holdData.get(0));
                        select.add(operator+"("+holdData.get(1)+")");
                    }
                }
            } else {
                ArrayList<String> hold = new ArrayList<>();
                hold = QueryParser.GetSynonyms(temp[0]);
                if (hold.size()>0) {
                    from.add(hold.get(1));
                    select.add(hold.get(0));
                } else {
                    hold = QueryParser.ListMatchedStems(temp[0]);
                    if (hold.size()>0) {
                        from.add(hold.get(0));
                        select.add(hold.get(1));
                    }
                }
            }
        }
        QueryGeneration.fiteringSelectClause();
//        System.out.println("-----Testing--");
        System.out.println("--------------------");
        System.out.println("content of select");
        System.out.println("-------------------");
        for (int i =0; i<select.size(); i++) {
            System.out.println(select.get(i));
        }
        System.out.println("=================");
    }


    public static void fiteringSelectClause() {
        ArrayList<String> temp = new ArrayList<>();
        for (int i=0; i<select.size(); i++) {
            String[] hold = select.get(i).split("\\(");
            if (hold.length > 1) {
                System.out.println(hold[0]+" "+hold[1]);
//                System.out.println(hold[1]);
                temp.add(select.get(i).trim());
                break;
            } else if (temp.contains(select.get(i).trim())) {
                System.out.println("fine");
            } else {
                temp.add(select.get(i).trim());
            }
        }
        select = null; // creating new instance for the select variable
        select = new ArrayList<String>();
//        select.add("salary");
        select.addAll(temp);
    }

    // processing the select list and generate the select clause as a single string
    public static String generateSelectString() {
        String sel = "";
        if (select.size() < 1) {
            try {
                if (select.size() <1)
//                    handleDialogBox();
                    throw new HandleNoise();

            } catch (HandleNoise noise) {
//                System.out.println("Noise Error");
//                handleDialogBox();
                System.out.println("Sorry the parser cannot generate query due to too many noise");
//                throw new HandleNoise();
            }
//            System.out.println("Error !!");
        } else if (select.size()<2) {
            sel = "SELECT "+select.get(0);
        } else if (select.size()<3) {
            sel = "SELECT "+select.get(0) + " , " +select.get(1);

        } else if (select.size()<4) {
            sel = "Select " +select.get(0) + ","+select.get(1) +"," +select.get(3);
        } else {
            try {
                if (select.size()>3)
//                    handleDialogBox();
                    throw new HandleNoise();
            } catch (HandleNoise noise) {
//                System.out.println("Noise Error");
//               handleDialogBox();
                System.out.println("Sorry the parser cannot generate query due to too many noise");
            }
        }
        return sel;
    }

    // for from clause
    // filtering from clause
    public static void filteringFromClause() {
        ArrayList<String> temp = new ArrayList<>();
        for (int i=0; i<from.size(); i++) {
            if (temp.contains(from.get(i))) {

            } else {
                temp.add(from.get(i));
            }
        }
        from = null;
        from = new ArrayList<>();
        from.addAll(temp);
    }

    // generating from as a string form
    public static String generateFromString() {
        QueryGeneration.filteringFromClause();
        String fr = "";
        if (from.size()<1) {
            // exception faalxa
        } else if (from.size() <2) {
            fr = "FROM " +from.get(0);
        }
        else if (from.size() == 2) {

            ArrayList<String> temp = QueryParser.GetForignKey(from);
            if (temp.size() > 0) {
                fr = "FROM " +temp.get(0) + " JOIN " +temp.get(2) + " ON " + temp.get(0) + "." +temp.get(1) + "=" +temp.get(2)+"."+temp.get(3);
            } else {
                fr = "FROM " +from.get(0) +", " +from.get(1);
            }

        }
        else {
            // nothing done
        }
        return fr;
    }

    public static void populateWhere() {
//        System.out.println("This section for where clause");
        // left ra right side ma separate gareko
        ArrayList<String> left = new ArrayList<>();
        ArrayList<String> right = new ArrayList<>();
    Iterator<String> itr = DivideList.whereList.iterator();
    while (itr.hasNext()) {
        String temp = itr.next(); // get each colummn name

        // check, database ma column xa ki xaina vaneera
        if (QueryParser.checkColumnStatus(temp)) {
            left.add(temp.trim());
        } else {
            right.add(temp.trim());
        }
    }

    // aba right lai ooperation garne or call the function right
    ArrayList<String> contentRight = processedRight(right);
//        System.out.println("This is righ hand side content of where clause");

        // display contents of right
        System.out.println("------------------");
        System.out.println("content of Right of WHere clause");
        System.out.println("----------------------");
        for (int i=0; i<contentRight.size(); i++) {
            System.out.println(contentRight.get(i));
        }

        // testai left ko laagi pani same process
        System.out.println("-------------------");
        System.out.println("content of Left of where clause");
        System.out.println("---------------------");
        ArrayList<String> contentLeft = processedLeft(left);
        System.out.println("Left hand side content of where");

        for (int i=0; i<contentLeft.size(); i++) {
            System.out.println(contentLeft.get(i));
        }

        // finally, combine garnney duitai left and right content lai
        processedWhereClause(contentLeft,contentRight);
    }

    // process right ko laagi yo functiion call garney !!
    public static ArrayList<String> processedRight(ArrayList<String> list) {
        ArrayList<String> temp = new ArrayList<>();
        if (list.size() ==1) {
            if (verbsChecking(list.get(0))) {

            } else {
                temp.addAll(list);
            }
        } else if(list.size() > 1) {
            String hold = "";
            for (int i=0; i<list.size(); i++) {
                if (DivideList.selectList.contains(list.get(i).trim())) {

                } else if (verbsChecking(list.get(i))) {

                } else if (QueryParser.ChkOperatorStatus(list.get(i))) {
                    hold = QueryParser.getFunctionOperator(list.get(i));
                } else {
                    if (!hold.equals("")) {
                        temp.add(hold +" "+list.get(i));
                    } else {
                        temp.add(list.get(i));
                    }
                }
            }
        } else {
            temp.addAll(list);
        }
        return temp;
    }

    // process for left ko laagi method
    // yo chai sadai database table ko column name return garnu parxa.
    public static ArrayList<String> processedLeft(ArrayList<String> list) {

        ArrayList<String> temp = new ArrayList<>();
        if (list.size() < 1) {

        } else if (list.size() >1) {
            for (int j=0; j<list.size(); j++) {
                if (DivideList.selectList.contains(list.get(j).trim())) {

                } else
                    if (QueryGeneration.checkSelectList(list.get(j).trim())) {
                } else {
                    temp.add(list.get(j));
                    }
            }
        } else {
            temp.addAll(list);
        }
        return temp;

    }

    // combining both left and right where clauses
    // data type column anusaar
    public static void processedWhereClause(ArrayList<String> left, ArrayList<String> right) {

        if (left.size()<1 || right.size() <1) {
            where.add("");
        } else if (left.size()==1 && right.size() ==1) {
            ArrayList<String> sym = QueryParser.GetSynonyms(left.get(0));
            if (sym.size()>0) { // synonym exists vanyeko case ma
                from.add(sym.get(1));

                String dataType = QueryParser.getDataTypeForGivenColumn(sym.get(0));

                if (dataType.startsWith("var") || dataType.startsWith("cha")) {
                    String hold = " " +sym.get(0)+ " like '%"+right.get(0)+"%'";
                    where.add(hold);
                } else if (dataType.startsWith("int") || dataType.startsWith("num")) {
                    if (right.get(0).startsWith(">") || right.get(0).startsWith("<")) {
                        String hold = " "+sym.get(0)+ ""+right.get(0);
                        where.add(hold);
                    } else {
                        String hold = " "+sym.get(0)+" = "+right.get(0)+"";
                        where.add(hold);
                    }
                } else {
                    String hold = " "+sym.get(0)+ " = "+right.get(0)+"";
                    where.add(hold);
                }
            }
            // yedi synonyms xaina vaney in the IS
            else {
                ArrayList<String> list1 = QueryParser.getRightOfWhere(left.get(0));
                if (list1.size()>0) {
                    from.add(list1.get(0));
                    String dataType =QueryParser.getDataTypeForGivenColumn(list1.get(1));

                    if (dataType.equals("varchar") || dataType.startsWith("cha")) {
                        String hold = " "+list1.get(1)+" like'%"+right.get(0)+"%'";
                        where.add(hold);
                    } else if (dataType.equals("int") || dataType.startsWith("num")) {
                        if (right.get(0).startsWith(">") || right.get(0).startsWith("<")) {
                            String hold = " "+list1.get(1)+" "+right.get(0);
                            where.add(hold);
                        } else {
                            String hold = " "+list1.get(1)+" ="+right.get(0)+"";
                            where.add(hold);
                        }
                    } else {
                        String hold = " "+list1.get(1)+"="+right.get(0)+"";
                        where.add(hold);
                    }
                }
            }
        } else if (left.size()<=2 || right.size()<=2) {
            // nothing
        } else {
//            handleDialogBox();
            // throw exception as too much noise in the sentence
        }
    }

    public static String generateWhereString() {
        String whereString = "";
        if (where.size() <1) {
            whereString = "";
        } else if (where.get(0).equals("")) {
            whereString ="";
        } else {
            whereString = "WHERE "+where.get(0);
        }
        return whereString;
    }




    // getting all required expression here
    public static void GetAllQuery() {
        populateSelect();
        populateWhere();
        System.out.println("------------------");
        System.out.println("Final query is");
        System.out.println("============");
        System.out.println(generateSelectString().trim());
//        System.out.println("======================");
        System.out.println(generateFromString().trim());
//        System.out.println("=======================");
        System.out.println(generateWhereString());

        // sending for output result
        System.out.println("-------------------");
//        System.out.println("Resultant Output of given query");
    }

// verb check function

    public static boolean verbsChecking(String s) {
        boolean status = false;
//        String[] strings = {"what","are", "is", "that", "this", "then"}; // problem
        String[] strings = {"what","are","is","that","this","then","those","who","where","that","list"};
        ArrayList<String> verbs = new ArrayList<>();
        for (int j=0; j<strings.length; j++) {
            verbs.add(strings[j]);
        }
        if (verbs.contains(s.trim())) {
            status = true;

        }
        return status;
    }

    public static boolean checkSelectList(String str) {
        boolean status = false;
        for (int j=0; j<DivideList.selectList.size(); j++) {
            if (DivideList.selectList.get(j).endsWith(str)) {
                status = true;
                break;
            }
        }
        return status;
    }

    public void handleDialogBox() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Warning");
        alert.setContentText("Sorry the parser cannot generate query due to too many noise");
        alert.show();
    }
}


class HandleNoise extends Exception {
    public String toString() {
        return "Sorry the parser cannot generate query due to too many noise";
        }
        }