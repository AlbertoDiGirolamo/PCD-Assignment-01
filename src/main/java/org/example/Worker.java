package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Worker extends Thread {
	
	private Random rand;
	private Map<File, Integer> fileAndLines = new HashMap<>();
	
	public Worker(List<File> files) throws FileNotFoundException {
		for(File f : files){
			Scanner sc = new Scanner(f);
			int count = 0;
			while(sc.hasNextLine()) {
				sc.nextLine();
				count++;
			}
			sc.close();
			fileAndLines.put(f, count);
		}

	}

	public Map<File, Integer> getFileAndLines(){
		return fileAndLines;
	}
	public String printFileAndLine(){
		String s = "";
		for(File f : fileAndLines.keySet()){
			s+=f.getName()+" Line:"+ fileAndLines.get(f)+"\n";
		}
		return s;
	}
	protected void println(String msg){
		synchronized (System.out){
			System.out.println(msg);
		}
	}

}
