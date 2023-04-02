package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class MasterThread extends Thread{

    private final int nWorkers;

    private final int NI = 5;
    private final int MAXL = 1000;
    private final int N = 10;

    IBoundedBuffer<File> bufferNameFile = new BoundedBuffer<File>(4);
    IBoundedBuffer<Pair<File, Integer>> bufferCounter = new BoundedBuffer<Pair<File, Integer>>(4);


    int nProducers = 1;
    int nConsumers = 5;

    static Map<String, Long> numFilesLines = new HashMap<>();


    public MasterThread(int nWorkers) {
        this.nWorkers = nWorkers;
        //nConsumers = nWorkers;

    }
    @Override
    public void run() {

        for (int i = 0; i < nProducers; i++){
            new ProducerNameFile(bufferNameFile).start();
        }

        for (int i = 0; i < nConsumers; i++){
            new ConsumerNameFile(bufferNameFile, bufferCounter).start();
        }

        for (int i = 0; i < nConsumers; i++){
            new Consumer2(bufferCounter).start();
        }







    /*
        String path = "./fileExample";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        try {
            countLines(listOfFiles);
           // orderByNumLines();
        } catch (Exception e) {
            System.out.println(e);
        }

     */




    }

    public static void countLines(File[] listOfFiles) throws IOException {
        if(listOfFiles != null) {
            for(File file : listOfFiles) {
                if(file.isDirectory()) {
                    countLines(file.listFiles());
                } else {
                    numFilesLines.put(file.getName(), Files.lines(Paths.get(file.getPath())).count());
                }
            }
        }
    }



}
