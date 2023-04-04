package org.example.controller;

import org.example.model.MasterThread;
import org.example.model.Model;
import org.example.view.View;

public class ControllerImpl implements Controller{
    private final Model model;
    private final View view;

    public ControllerImpl(Model model, View view){
        this.model = model;
        this.view = view;
        //this.view.setController(this);
    }

    @Override
    public void start(int numberOfWorkers) {
        new MasterThread(numberOfWorkers).start();
    }
}
