package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MasterThread extends Thread{

    private final int nWorkers;

    private final int NI = 5;
    private final int MAXL = 1000;
    private final int N = 10;
    private final Map<File, Integer> files = new HashMap<>();

    public MasterThread(int nWorkers) {
        this.nWorkers = nWorkers;
    }
    @Override
    public void run() {
        findFile();

        int countIndexFile = 0;
        List<CounterWorker> counterWorkers = new ArrayList<>();

        for (int i = 0; i < nWorkers; i++){
            List<File> fileForThread = new ArrayList<>();
            for(int f = 0; f < files.size() / nWorkers; f++){
                fileForThread.add(files.keySet().stream().collect(Collectors.toList()).get(countIndexFile));
                countIndexFile++;
            }
            counterWorkers.add(new CounterWorker(fileForThread));
            counterWorkers.get(i).start();
        }



        for (CounterWorker w : counterWorkers){
            for(File f : w.getFileAndLines().keySet()){
                files.replace(f, w.getFileAndLines().get(f));
            }
            //System.out.println(w.printFileAndLine());
        }

        System.out.println(files.keySet().size());

        Map<Pair<Integer, Integer>, List<File>> fileInRange = new HashMap<>();
        int range = (MAXL / (NI - 1)) - 1;
        int index = 0;
        for(int i = 0; i <  NI - 1; i++){
            fileInRange.put(new Pair<>(index, index + range), new ArrayList<>());
            index+=range;
            index++;
        }
        fileInRange.put(new Pair<>(index, Integer.MAX_VALUE), new ArrayList<>());

        for(File f : files.keySet()){
            for(int i = 0; i < NI; i++){
                Pair<Integer, Integer> singleRange = new Pair<>( fileInRange.keySet().stream().collect(Collectors.toList()).get(i).getX(),
                        fileInRange.keySet().stream().collect(Collectors.toList()).get(i).getY());

                if(files.get(f) > singleRange.getX() &&
                        files.get(f) < singleRange.getY()){
                    fileInRange.get(singleRange).add(f);
                }
            }

        }
        int c = 0;
        for(Pair<Integer,Integer> p : fileInRange.keySet()){
            c+=fileInRange.get(p).size();
        }
        System.out.println(c);

    }

    private void findFile(){

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
        System.out.println(filesPath.size());


        for(Object o : filesPath){
            files.put(new File(o.toString()), 0);
        }
    }

}
