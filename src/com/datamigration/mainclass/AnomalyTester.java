package com.datamigration.mainclass;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AnomalyTester {

	private static HashMap<String,String> employeeeTracker = new HashMap<String,String>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("Entry or Exit?");
                String line = scanner.nextLine();
               if("ENTRY".equalsIgnoreCase(line)) {
            	   System.out.println("Who is entering?");
            	   String whoEntry = scanner.nextLine();
            	   if(employeeeTracker.containsKey(whoEntry.toUpperCase()) && "ENTRY".equalsIgnoreCase(employeeeTracker.get(whoEntry.toUpperCase()))) {
            		   System.out.println("ANomalye.........................");
            	   }
            	   employeeeTracker.put(whoEntry.toUpperCase(), "ENTRY");    
            	   
               }else if("Exit".equalsIgnoreCase(line)) {
            	   System.out.println("Who is exiting?");
            	   String whoExist = scanner.nextLine();
            	   if(employeeeTracker.containsKey(whoExist.toUpperCase()) && "EXIT".equalsIgnoreCase(employeeeTracker.get(whoExist.toUpperCase()))) {
            		   System.out.println("ANomalye.........................");
            	   }else if(!employeeeTracker.containsKey(whoExist.toUpperCase())) {
            		   System.out.println("ANomalye.........................");
            	   }
            	   employeeeTracker.put(whoExist.toUpperCase(), "EXIT")    ;    
               }
               System.out.println(employeeeTracker);
               System.out.flush();
            }
        } catch(IllegalStateException | NoSuchElementException e) {
            // System.in has been closed
            System.out.println("System.in was closed; exiting");
        }
    }
}