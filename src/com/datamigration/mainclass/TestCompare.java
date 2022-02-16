package com.datamigration.mainclass;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestCompare {
static List<String> masterListDB = new ArrayList<String>();
static List<String> masterListDB2 = new ArrayList<String>();
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("D:\\dataMigration\\ks_catalog\\srcCompare.txt");
		 Scanner myReader = new Scanner(file);
		 while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        data = data.trim();
		        if(!masterListDB.contains(data)) {
		        	masterListDB.add(data)	;
		        }
		 }
		  myReader.close();
		  
		  File file2 = new File("D:\\dataMigration\\ks_catalog\\tesCompare.txt");
			 Scanner myReader2 = new Scanner(file2);
			 while (myReader2.hasNextLine()) {
			        String data = myReader2.nextLine();
			        data = data.trim();
			        if(!masterListDB2.contains(data)) {
			        	masterListDB2.add(data)	;
			        }
			 }
			  myReader2.close();
		  int count = 0;
			  for(String str : masterListDB2) {
				  if(masterListDB.contains(str) ) {
					  count++;
				  }
			  }
			  
			  System.out.println(count);

	}

}
