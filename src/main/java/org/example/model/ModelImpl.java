package org.example.model;

import org.example.utils.BufferCountLines;
import org.example.utils.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class ModelImpl implements Model{
    private final List<ModelObserver> observers = new LinkedList<>();
    final Comparator<Pair<String, Integer>> comparator = reverseOrder(comparing(Pair::getY));
    private BufferCountLines<Pair<File, Integer>> files = new BufferCountLines<>(comparator);
    @Override
    public void notifyObservers(ModelObserver.Event event) throws InterruptedException {
        for(ModelObserver observer : this.observers){
            switch (event){
                case RESULT_UPDATED -> observer.resultsUpdated();
                //case COMPUTATION_ENDED -> observer.computationEnded();
            }
        }
    }

    @Override
    public BufferCountLines<Pair<File, Integer>> getResult() {
        return files;
    }

    @Override
    public void addResult(Pair<File, Integer> result) throws InterruptedException {
        files.put(result);
    }

    @Override
    public List<Pair<File, Integer>> getRanking(int topN) {
        return null;
    }
}
