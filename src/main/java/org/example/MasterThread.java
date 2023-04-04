package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class MasterThread extends Thread{

    private final int nWorkers;

    private final int NI = 5;
    private final int MAXL = 1000;
    private final int N = 10;

    IBufferFileFind<File> bufferNameFile;
    IBufferCountLines<Pair<File, Integer>> bufferCounter;
    int nConsumers = 5;

    static Map<Pair<Integer, Integer>, List<Integer>> filesInRange = new HashMap<>();


    public MasterThread(int nWorkers) {
        this.nWorkers = nWorkers;
    }

    @Override
    public void run() {

        List<Object> filesPath;
        try (Stream<Path> walk = Files.walk(Paths.get("./fileExample"))) {
            filesPath = walk
                    .filter(p -> !Files.isDirectory(p))   // not a directory
                    .map(p -> p.toString().toLowerCase()) // convert path to string
                    .filter(f -> f.endsWith("java"))       // check end with
                    .collect(Collectors.toList());        // collect all matched to a List
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        bufferNameFile = new BufferFileFind<>();

        final Comparator<Pair<String, Integer>> comparator = reverseOrder(comparing(Pair::getY));
        bufferCounter = new BufferCountLines<>(comparator, filesPath.size());

        for(Object o : filesPath){
            try {
                bufferNameFile.put(new File(o.toString()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < nConsumers; i++){
            new WorkerCountLines(bufferNameFile, bufferCounter).start();
        }


        try {
            Thread.sleep(5000);

            List<Pair<File, Integer>> pair = bufferCounter.getTopN(10);



            for(Pair<File, Integer> p : pair){
                System.out.println(p.getX()+" "+p.getY());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int range = MAXL / (NI - 1);
        int indexRange=0;
        for(int i = 0; i < NI -1; i++){
            filesInRange.put(new Pair<>(indexRange, indexRange+range -1), new ArrayList<>());
            indexRange += range;
        }
        filesInRange.put(new Pair<>(indexRange, Integer.MAX_VALUE), new ArrayList<>());

        for(int i = 0; i < filesPath.size(); i++){
            Integer countLinesFile = bufferCounter.getItem().getY();
            for(Pair<Integer, Integer> p : filesInRange.keySet()){
                if(countLinesFile > p.getX() && countLinesFile < p.getY()){
                    filesInRange.get(p).add(countLinesFile);
                }
            }
        }




    }




}
