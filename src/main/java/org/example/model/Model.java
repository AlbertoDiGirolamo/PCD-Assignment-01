package org.example.model;

import org.example.utils.BufferCountLines;
import org.example.utils.Pair;

import java.io.File;
import java.util.List;

public interface Model {
    void notifyObservers(ModelObserver.Event event) throws InterruptedException;

    BufferCountLines<Pair<File, Integer>> getResult();
    void addResult(Pair<File, Integer> result) throws InterruptedException;
    List<Pair<File, Integer>> getRanking(int topN);

}
