package testdemo;

import application.DependencyParserAPI;
import application.HelperClass;

import java.util.*;
public class DemoTestRun {

	public static String getData = null;

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the Question:");
		String text = in.nextLine();
		runTask(text);
		in.close();

	}

	public static void runTask(String question) {

		Collection coll = DependencyParserAPI.DependencyGeneration(question);

		List lists = (List)coll;
		System.out.println("");
		System.out.println("********************Actual dependeincies.**********************");
		for(int i = 0; i < lists.size() ;i++){
			System.out.println(lists.get(i));
		}



		System.out.println("");
		System.out.println("*********Contents of the filtered list of dependencies ********");
		ArrayList<String>list  = HelperClass.WordListFilter(coll);
		for(int i = 0; i < list.size(); i++){
			System.out.println(list.get(i));
		}
		System.out.println("*************************************************************");
		System.out.println("");

		DivideList.PopulateList(list);

		System.out.println("");
		System.out.println("*********Contents of the selectList list ********");
		for(int i = 0; i < DivideList.selectList.size(); i++){
			System.out.println(DivideList.selectList.get(i));
		}
		System.out.println("*************************************************************");
		System.out.println("");

		System.out.println("");
		System.out.println("*********Contents of the whereList list ********");
		for(int i = 0; i < DivideList.whereList.size(); i++){
			System.out.println(DivideList.whereList.get(i));
		}
		System.out.println("*************************************************************");
		System.out.println("");

		QueryGeneratorFlow.GetAll();

		// this code is for displaying resultant output in table form
		String output = QueryGeneratorFlow.GenerateSelect()+"\n";
		output += QueryGeneratorFlow.GenerateFrom()+"\n";
		output += QueryGeneratorFlow.GenerateWhere();

		String[] heading = new String[QueryGeneratorFlow.select.size()];
		QueryGeneratorFlow.select.toArray(heading);

		ArrayList<ArrayList<String>> test = SqlLibrary.GetQueryResult(output, QueryGeneratorFlow.select);
		Object[][] result = new Object[test.size()][];

		System.out.println("============ Resultant Output ==========");

		for (int k=0; k<test.size(); k++) {
			ArrayList<String> temp = test.get(k);
			result[k] = new Object[test.size()];
			for (int l=0; l<temp.size(); l++) {
				result[k][l]  = temp.get(l);
				System.out.println(temp.get(l));
			}
		}

	}

}
