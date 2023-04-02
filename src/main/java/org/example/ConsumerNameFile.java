package org.example;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class ConsumerNameFile extends Thread {

	private IBoundedBuffer<File> buffer;
	private IBoundedBuffer<Pair<File, Integer>> bufferCounter;
	
	public ConsumerNameFile(IBoundedBuffer<File> buffer, IBoundedBuffer<Pair<File, Integer>> bufferCounter){
		this.buffer = buffer;
		this.bufferCounter = bufferCounter;
	}

	public void run(){
		while (true){
			try {
				File item = buffer.get();
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