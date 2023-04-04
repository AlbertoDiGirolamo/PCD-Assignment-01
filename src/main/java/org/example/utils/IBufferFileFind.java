package org.example.utils;

public interface IBufferFileFind<Item> {
    void put(Item item) throws InterruptedException;

    Item get() throws InterruptedException;

}
