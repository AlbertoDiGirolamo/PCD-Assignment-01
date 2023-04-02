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

public class Main {

    public static final int NUMBEROFWORKERS = 5;
    public static void main(String[] args) throws IOException {
        MasterThread masterThread = new MasterThread(NUMBEROFWORKERS);
        masterThread.start();
    }

}