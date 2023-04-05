package org.example.controller;

import org.example.model.MasterThread;
import org.example.model.Model;
import org.example.utils.Pair;
import org.example.view.View;

import java.io.File;
import java.util.List;

public class ControllerImpl implements Controller{
    private final Model model;
    private final View view;
    private MasterThread masterThread;


    public ControllerImpl(Model model, View view){
        this.model = model;
        this.view = view;
        this.view.setController(this);
    }

    @Override
    public void start(int numberOfWorkers, int topN) {
        masterThread = new MasterThread(numberOfWorkers, topN);
        masterThread.start();
    }

    @Override
    public List<Pair<File, Integer>> getRankingList() {
        return masterThread.getRankingList();
    }


}
