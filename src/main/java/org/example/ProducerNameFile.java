package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ProducerNameFile extends Thread {

	private IBoundedBuffer<File> buffer;
	
	public ProducerNameFile(IBoundedBuffer<File> buffer){
		this.buffer = buffer;
	}

	public void run(){
		/*
		String path = "./fileExample";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();*/
		File[] listOfFiles = new File[0];

		List filesPath;
		try (Stream<Path> walk = Files.walk(Paths.get("./fileExample"))) {
			filesPath = walk
					.filter(p -> !Files.isDirectory(p))   // not a directory
					.map(p -> p.toString().toLowerCase()) // convert path to string
					.filter(f -> f.endsWith("java"))       // check end with
					.collect(Collectors.toList());        // collect all matched to a List
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		for(Object o : filesPath){
			try {
				buffer.put(new File(o.toString()));
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private File produce(){
		return null;
	}
	
	private void log(String st){
		synchronized(System.out){
			System.out.println("["+this.getName()+"] "+st);
		}
	}
}
