package org.example;


import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Scanner;

class Consumer2 extends Thread {


	private IBoundedBuffer<Pair<File, Integer>> bufferCounter;

	public Consumer2(IBoundedBuffer<Pair<File, Integer>> bufferCounter){
		this.bufferCounter = bufferCounter;
	}

	public void run(){
		while (true){
			try {
				Pair<File, Integer> item = bufferCounter.get();
				consume(item);
			} catch (InterruptedException ex){
				ex.printStackTrace();
			}
		}
	}
	
	private void consume(Pair<File, Integer> file){
		System.out.println(file.getX().getName()+" "+ file.getY());

	}
	
	private void log(String st){
		synchronized(System.out){
			System.out.println("["+this.getName()+"] "+st);
		}
	}
}
