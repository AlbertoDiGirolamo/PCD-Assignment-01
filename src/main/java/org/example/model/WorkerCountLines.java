package org.example.model;


import org.example.utils.IBufferCountLines;
import org.example.utils.IBufferFileFind;
import org.example.utils.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class WorkerCountLines extends Thread {

	private final IBufferFileFind<File> bufferFindFile;
	private final IBufferCountLines<Pair<File, Integer>> bufferCounter;
	
	public WorkerCountLines(IBufferFileFind<File> bufferFindFile, IBufferCountLines<Pair<File, Integer>> bufferCounter){
		this.bufferFindFile = bufferFindFile;
		this.bufferCounter = bufferCounter;
	}

	public void run(){
		while (true){
			try {

				File item = bufferFindFile.get();
				if(bufferFindFile == null){
					break;
				}
				consume(item);
			} catch (InterruptedException ex){
				ex.printStackTrace();
			}
		}
	}
	
	private void consume(File file){
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		int count = 0;
		while(sc.hasNextLine()) {
			sc.nextLine();
			count++;
		}
		sc.close();
		try {
			bufferCounter.put(new Pair<>(file, count));
			//System.out.println(file.getName()+" : "+count);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

	}
	
	private void log(String st){
		synchronized(System.out){
			System.out.println("["+this.getName()+"] "+st);
		}
	}
}
