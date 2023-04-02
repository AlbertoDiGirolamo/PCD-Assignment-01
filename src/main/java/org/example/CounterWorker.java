package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CounterWorker extends Worker {
	
	private Random rand;
	private Map<File, Integer> fileAndLines = new HashMap<>();
	private List<File> files = new ArrayList<>();
	
	public CounterWorker(List<File> files){
		this.files = files;
		/*try {
			Random rnd = new Random();
			sleep(rnd.nextInt(10000));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}*/

	}
	public void run(){
		for(File f : files){
			Scanner sc = null;
			try {
				sc = new Scanner(f);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
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
