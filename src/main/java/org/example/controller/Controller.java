package org.example.controller;

import org.example.utils.Pair;

import java.io.File;
import java.util.List;

public interface Controller {
    void start(int numberOfWorkers, int topN);
    List<Pair<File, Integer>> getRankingList();
}
