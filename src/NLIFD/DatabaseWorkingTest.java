package NLIFD;

import application.QueryGeneration;
import database.DatabaseHandler;
import database.QueryParser;
import testdemo.QueryGeneratorFlow;
import testdemo.SqlLibrary;

import java.util.ArrayList;
import java.util.List;

public class DatabaseWorkingTest {
    public static void main(String[] args) {

//        System.out.println(DatabaseHandler.GetDatabaseConnection());
        System.out.println("================");

//        System.out.println(QueryParser.functionTypeSelectOrWhere("highest"));
        System.out.println("================");
//        System.out.println(QueryParser.getDataTypeForGivenColumn("employee"));
        // query was empty problem here !!

//        System.out.println(QueryParser.getFunctionOperator("highest")); // o/p=max

        System.out.println(QueryParser.ChkOperatorStatus("lower"));

        ArrayList<String> test = new ArrayList<>();
        test.add("employee");
        test.add("location");

        for (String t: test) {
            System.out.println(t);
        }

        System.out.println("===============");
        ArrayList<String> twofrom = SqlLibrary.GetForigenKey(test);
        for (int i=0; i<twofrom.size();i++) {
            System.out.println(twofrom.get(i));
        }
    }
}
