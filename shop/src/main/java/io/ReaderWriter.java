package io;
import java.io.*;
import java.util.*;
import java.util.Map.*;
import java.util.Iterator;
import model.*;
public class ReaderWriter{

public void read(String filename, TreeMap<String, String> users){
try{
   FileInputStream fstream = new FileInputStream(filename);
   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
   String str;
   while ((str = br.readLine()) != null){
	String[] arr = str.split(":");
	if(arr[1].equals("-")){arr[1] = null;}
	users.put(arr[0], arr[1]);
   }
	}catch (IOException e){
	   System.out.println("Error");
	}
}

public void write(String filename, TreeMap<String, String> users) {
        try(FileWriter writer = new FileWriter(filename, false))
        {
	for(Entry entry: users.entrySet()) {
	    String key = (String)entry.getKey();
	     String value = (String)entry.getValue();
	    writer.write(key + ":"+  value);
	    writer.append('\n');
	    }    
        writer.flush();
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        } 
    } 
}
