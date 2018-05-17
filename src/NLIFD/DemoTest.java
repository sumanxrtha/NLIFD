package NLIFD;

import application.DependencyParserAPI;
import application.DivideList;
import application.HelperClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class DemoTest {

    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter the input...");
        String inputString = userInput.nextLine();

        userInput.close();

        // sending to the dependency parser api to generate the list
        // Collection list from parser
        Collection collection = DependencyParserAPI.DependencyGeneration(inputString);

        // type casting ie converting to LIST form.
        List lists = (List) collection;
        System.out.println("==========================================================");
        System.out.println("Dependency from Parser | Actual Dependencies of the question");
        System.out.println("==========================================================");

        for (int i=0; i<lists.size(); i++) {
            System.out.println(lists.get(i));
        }
//        Iterable it = lists.iterator();

        System.out.println("========================================================");

        System.out.println("After filtering the lists for the outputed parser lists to remvoe unwanted dependency");
        System.out.println("============================================");
        ArrayList<String> filteredList = HelperClass.WordListFilter(collection);
        for (int i =0; i<filteredList.size(); i++) {
            System.out.println(filteredList.get(i));
        }
        System.out.println("================================================");
//        System.out.println();

        // separating the conents from the lists into where and select clause
        DivideList.PopulateList(filteredList); // all rules are applied here.
        System.out.println("==========================================================");
        int selectSize = DivideList.selectList.size();
        int whereSize = DivideList.whereList.size();
        System.out.println("Contents of Select Clause (Probable)");
        for (int i =0; i<selectSize;i++) {
            System.out.println(DivideList.selectList.get(i));
        }
        System.out.println("===============================================================");
//        System.out.println("--------------------");
        System.out.println("contents of Where Clause (Probable)");
        System.out.println("==========================================================");

        for (int i=0;i<whereSize;i++) {
            System.out.println(DivideList.whereList.get(i));
        }

        System.out.println("==============================================");
        // done and combine these two combination.
        // pending;

    }
}
