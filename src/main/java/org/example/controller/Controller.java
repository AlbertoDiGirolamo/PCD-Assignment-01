package org.example.controller;

import org.example.model.ModelObserver;
import org.example.utils.BufferCountLines;
import org.example.utils.Pair;

import java.io.File;
import java.util.List;

public interface Controller {
    void start(int numberOfWorkers, int topN);
    List<Pair<File, Integer>> getRankingList();
    void processEvent(Runnable runnable);
    void notifyObservers(ModelObserver.Event event) throws InterruptedException;
    BufferCountLines<Pair<File, Integer>> getResult();
    void addResult(Pair<File, Integer> result) throws InterruptedException;

}
