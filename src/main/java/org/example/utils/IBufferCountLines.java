package org.example.utils;

import java.io.File;
import java.util.Collection;
import java.util.List;

public interface IBufferCountLines<Item> {

    void put(Item item) throws InterruptedException;
    
    List<Item> getTopN(int limit) throws InterruptedException;
    Item getItem();


}
