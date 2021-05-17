package testdemo;

import javafx.scene.control.Alert;

import java.util.*;

/**
 * 
 * */
public class QueryGeneratorFlow {
	public static ArrayList<String> select = new ArrayList<String>();
	public static ArrayList<String> where  = new ArrayList<String>();
	public static ArrayList<String> from = new ArrayList<String>();
	
	
	public static void PopulateSelect(){
		Iterator<String> itr = DivideList.selectList.iterator();
		while(itr.hasNext()){
			String[] temp = itr.next().split("-");
			
			/**
			 * If the length of the temp is greater then 1 it
			 * possibly may involve the aggregation single functions
			 * */
			if(temp.length > 1){
				//Check for the existence of the column in database
				String operator= "";
				ArrayList<String> tempHold = new ArrayList<String>();
				tempHold = SqlLibrary.GetSynonymn(temp[1].trim());
				if(tempHold.size() > 0){
//					System.out.println(tempHold.get(0)+"---"+tempHold.get(1));
					operator = SqlLibrary.GetOperator(temp[0]);
					from.add(tempHold.get(1));
					select.add(operator+"("+tempHold.get(0)+")");
				}
				else{
					tempHold = SqlLibrary.ListMatchedStems(temp[1]);
					if(tempHold.size() > 0){
						//System.out.println(tempHold.get(0)+"---"+tempHold.get(1));
						operator = SqlLibrary.GetOperator(temp[0]);
						//System.out.println(operator);
						from.add(tempHold.get(0));
						select.add(operator+"("+tempHold.get(1)+")");
					}
				}
			}
			
			/**
			 * directly go for the existence of the column in table
			 * */
			else{
				ArrayList<String> tempHold = new ArrayList<String>();
				tempHold = SqlLibrary.GetSynonymn(temp[0]);
				if(tempHold.size() > 0){
					//System.out.println(tempHold.get(0)+"---"+tempHold.get(1));
					from.add(tempHold.get(1));
					select.add(tempHold.get(0));
				}
				else{
					tempHold = SqlLibrary.ListMatchedStems(temp[0]);
					if(tempHold.size() > 0){
						//System.out.println(tempHold.get(0)+"---"+tempHold.get(1));
						from.add(tempHold.get(0));
						select.add(tempHold.get(1));
					}
				}
			}
		}
		
		QueryGeneratorFlow.filterSelect();
		System.out.println("");
		System.out.println("***************** Content on select *****************");
		for(int i = 0 ; i< select.size(); i++)
			System.out.println(select.get(i));
		System.out.println("*****************************************************");
		System.out.println("");
		
	}
	
	/**
	 * This function explicitly uses the whereList list produced by the
	 * query Generator and process them to generate the where clause
	 * It is assumed that in where clause only one condition is allowed
	 * For further the algorithm in DivideList should be modified.
	 * */
	public static void PopulateWhere(){
		ArrayList<String> left = new ArrayList<String>();
		ArrayList<String> right = new ArrayList<String>();
		/**
		 * Separate the whereList list in right and left side.
		 * */
		Iterator<String> itr = DivideList.whereList.iterator();
		while(itr.hasNext()){
			String temp = itr.next();
			/**
			 * Check for the existence of the column in database
			 * */
			if(SqlLibrary.ChkColStatus(temp)){
				left.add(temp.trim());
			}
			else{
				right.add(temp.trim());
			}
		}
		
		ArrayList<String> rightP = ProcessRight(right);
		System.out.println("");
		System.out.println("***************** Content for processed right hand side *****************");
		for(int j = 0 ; j < rightP.size(); j++){
			System.out.println(rightP.get(j));
		}
		System.out.println("*****************************************************");
		System.out.println("");
		
		
		ArrayList<String> leftP = ProcessLeft(left);
		System.out.println("");
		System.out.println("***************** Content for processed left hand side *****************");
		for(int j = 0 ; j < leftP.size(); j++){
			System.out.println(leftP.get(j));
		}
		System.out.println("*****************************************************");
		System.out.println("");
		
		processWhere(leftP, rightP);
		
	}
	
	public static ArrayList<String> ProcessRight(ArrayList<String> list){
		ArrayList<String> temp = new ArrayList<String>();
		if(list.size() == 1){
			if(VerbsChk(list.get(0))){
			}
			else{
				temp.addAll(list);	
			}
		}
		else if(list.size() > 1){
			String hold ="";
			for(int i = 0; i < list.size(); i++){
				if(DivideList.selectList.contains(list.get(i).trim())){
				}
				else if(VerbsChk(list.get(i))){
				}
				
				else if(SqlLibrary.ChkOperatorStatus(list.get(i))){
					hold = SqlLibrary.GetOperator(list.get(i));
				}
				
				else{
					if(!hold.equals("")){
						temp.add(hold+" "+list.get(i));
					}
					else
						temp.add(list.get(i));
				}
			}
		}
		else{
			temp.addAll(list);
		}
		return temp;
	}
	
	
	/**
	 * Process the left side of the where clause
	 * and it must be the column of the database table
	 * that this interface is processing.
	 * */
	public static ArrayList<String> ProcessLeft(ArrayList<String> list){
		ArrayList<String> temp = new ArrayList<String>();
		if(list.size() < 1){
			
		}
		else if(list.size() > 1){
			for(int j = 0; j < list.size(); j++){
				if(DivideList.selectList.contains(list.get(j).trim())){
				}
				else if(QueryGeneratorFlow.chkselectList(list.get(j).trim())){
				}
				else{
					temp.add(list.get(j));
				}
			}
		}
		else{
			temp.addAll(list);
		}
		return temp;
	}
	
	/**
	 * Assemble the left and right side of the where clause according 
	 * the data type of the column
	 * if nothing is found in where list it is left empty.
	 * */
	public static void processWhere(ArrayList<String>left ,ArrayList<String>right){
		
		if(left.size() < 1 || right.size() < 1){
			where.add("");
		}
		
		
		else if( left.size() == 1 && right.size() == 1){
			ArrayList<String> sym = SqlLibrary.GetSynonymn(left.get(0));
			
			//if entry is in information schema then load data form it
			if(sym.size() > 0)
			{
				
				from.add(sym.get(1));
				String dataType = SqlLibrary.GetDataTypeOfColumn(sym.get(0));
				
				if(dataType.startsWith("var") || dataType.startsWith("cha")){
					String hold = " "+sym.get(0)+" LIKE '%"+right.get(0)+"%'";
					where.add(hold);
				}
				
				else if(dataType.startsWith("int") || dataType.startsWith("num")){
					if(right.get(0).startsWith(">") || right.get(0).startsWith("<")){
						String hold = " "+sym.get(0)+""+right.get(0);
						where.add(hold);
					}
					else{
						String hold = " "+sym.get(0)+" ="+right.get(0)+"";
						where.add(hold);
					}
					
				}
				
				else{
					String hold = " "+sym.get(0)+" ="+right.get(0)+"";
					where.add(hold);
				}
			}
			//if has not synonyms then go for the information schema
			else{
				ArrayList<String>lst = SqlLibrary.GetRightOfWhere(left.get(0));
				
				if(lst.size() > 0){
					from.add(lst.get(0));
					String dataType = SqlLibrary.GetDataTypeOfColumn(lst.get(1));
					//System.out.println(dataType);
					if(dataType.equals("varchar") || dataType.startsWith("cha")){
						String hold = " "+lst.get(1)+" LIKE '%"+right.get(0)+"%'";
						where.add(hold);
					}
					
					else if(dataType.equals("int") || dataType.startsWith("num")){
						if(right.get(0).startsWith(">") || right.get(0).startsWith("<")){
							String hold = " "+lst.get(1)+" "+right.get(0);
							where.add(hold);
						}
						else{
							String hold = " "+lst.get(1)+" ="+right.get(0)+"";
							where.add(hold);
						}
						
					}
					
					else{
						String hold = " "+lst.get(1)+" ="+right.get(0)+"";
						where.add(hold);
					}
				}
			}
		}
		
		else if(left.size() <= 2 || right.size() <= 2){
			
		}
		else {
			//throw exception as too much noise in the sentence.
		}
	}
	
	public static boolean chkselectList(String str){
		boolean status = false;
		for(int j = 0; j < DivideList.selectList.size(); j++){
			if(DivideList.selectList.get(j).endsWith(str)){
				status = true;
				break;
			}
		}
		return status;
	}
	
	public static boolean VerbsChk(String str){
		boolean status = false;
		String[] strs = {"what","are","is","that","this","then","those","who","where","that","list"};
		ArrayList<String> verbs = new ArrayList<String>();
		for(int j =0; j < strs.length; j++){
			verbs.add(strs[j]);
		}
		if(verbs.contains(str.trim())){
			status = true;
		}
		return status;
	}
	
	//Process the from list and generate the from clause as single string
	public static String GenerateFrom(){
		QueryGeneratorFlow.filterFrom();
		String frm = "";
		if(from.size() < 1){
			//throw exception
		}
		
		else if(from.size() < 2){
			frm = "FROM "+from.get(0);
		}
		
		else if(from.size() == 2){
			ArrayList<String> temp = SqlLibrary.GetForigenKey(from);
			if(temp.size() > 0){
				frm = "FROM "+temp.get(0)+" JOIN "+temp.get(2)+" ON "+temp.get(0)+"."+temp.get(1)+
					  "="+temp.get(2)+"."+temp.get(3);
			}
			else{
				frm = "FROM "+from.get(0)+", "+from.get(1);
			}
		}
		else{
			//System.out.println(from.size());
			//frm = "nothing is done";
			// through exception too many noise in sentence
		}
		return frm;
	}
	
	//Process the select list and generate the select clause as single  string
	public static String GenerateSelect(){
		String sel ="";
		//filterSelect();
		if(select.size() < 1){
			try{
				if(select.size() < 1 )
					throw new HandleNoise();
			}
			catch(HandleNoise noise){
				System.out.println("Can't generate query due to too many noise in sentence.");
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Error");
				alert.setContentText(null);
				alert.setHeaderText("Can't generate query due to too many noise in sentence.");
				alert.show();
//				System.out.println("Can't generate query due to too many noise in sentence.");
			}
		}
		else if(select.size() < 2){
			sel = "SELECT "+select.get(0);
		}
		else if(select.size() < 3){
			sel = "SELECT "+select.get(0)+" , "+select.get(1);
		}
		else if(select.size() < 4){
			sel = "SELECT "+select.get(0)+" , "+select.get(1)+" , "+select.get(3);
		}
		else{
			try{
				if(select.size() > 3  )
					throw new HandleNoise();
			}
			catch(HandleNoise noise){
				System.out.println("Can't generate query due to too many noise in sentence.");
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Error");
				alert.setContentText(null);
				alert.setHeaderText("Can't generate query due to too many noise in sentence.");
				alert.show();
			}
		}
		return sel;
	}
	
	public static String GenerateWhere(){
		String whe = "";
		if(where.size() < 1){
			whe = "";
		}
		else if(where.get(0).equals("")){
			whe = "";
		}
		else{
			whe = "WHERE "+where.get(0);
		}
		return whe;
	}
	
	public static void GetAll(){
		PopulateSelect();
		PopulateWhere();
		
		System.out.println("");
		System.out.println("***************** Fianl Query Processed by the parser *****************");
		System.out.println(GenerateSelect().trim());
		System.out.println(GenerateFrom().trim());
		System.out.println(GenerateWhere().trim());
		System.out.println("*****************************************************");
		System.out.println("");
	}
	
	public static void filterFrom(){
		ArrayList<String> temp = new ArrayList<String>();
		for(int i = 0 ; i< from.size(); i++){
			if(temp.contains(from.get(i))){
				
			}
			else{
				temp.add(from.get(i));
			}
		}
		from = null;
		from = new ArrayList<String>();
		from.addAll(temp);
	}
	
	public static void filterSelect(){
		ArrayList<String> temp = new ArrayList<String>();
		for(int i = 0 ; i< select.size(); i++){
			String[] hold = select.get(i).split("\\(");
			
			
			if(hold.length > 1){
				System.out.println(hold[0]+" "+hold[1]);
				temp.add(select.get(i).trim());
				break;
			}
			else if(temp.contains(select.get(i).trim())){
				System.out.println("I am here!");
			}
			else{
				temp.add(select.get(i).trim());
				System.out.println("Is this fine");
			}
		}
		select = null;
		select = new ArrayList<String>();
		select.addAll(temp);
	}

//	public static void displayNoiseError() {
//		Alert alert = new Alert(Alert.AlertType.WARNING);
//		alert.setTitle("Error");
//		alert.setHeaderText("Can't generate query due to too many noise in sentence.");
//		alert.show();
//	}
}

/**
 * Exception class for handling the too much noise
 * in the sentence.
 * This exception should be raised if the select and from clause is empty
 * */
	@SuppressWarnings("serial")
	class HandleNoise extends Exception{
		public String toString() {
			return "Sorry the parser can't generate query due to too many noise in sentence.";
		}
	}