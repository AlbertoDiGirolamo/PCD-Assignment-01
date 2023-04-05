package org.example.utils;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferFileFind<Item> implements IBufferFileFind<Item> {

    private LinkedList<Item> buffer;
    private Lock mutex;
    private Condition notEmpty;
    public BufferFileFind() {
        buffer = new LinkedList<Item>();
        mutex = new ReentrantLock();
        notEmpty = mutex.newCondition();

    }

    public void put(Item item) throws InterruptedException {
        try {
            mutex.lock();
            buffer.addLast(item);
            notEmpty.signal();
        } finally {
            mutex.unlock();
        }
    }

    public Item get() throws InterruptedException {
        try {
            mutex.lock();
            if(isEmpty()){
                notEmpty.await();
            }
            if (buffer.isEmpty()){
                return null;
            }

            Item item = buffer.removeFirst();

            return item;
        } finally {
            mutex.unlock();
        }
    }
    private boolean isEmpty(){
        try{
            mutex.lock();
            return this.buffer.isEmpty();
        }finally {
            mutex.unlock();
        }

    }
}