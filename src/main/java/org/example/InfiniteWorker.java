package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InfiniteWorker extends Worker {



	public InfiniteWorker() throws FileNotFoundException {

		while (true) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}



}
