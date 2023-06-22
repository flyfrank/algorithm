/*
 * Copyright (c) 2022年 by XuanWu Wireless Technology Co.Ltd.
 *             All rights reserved
 */
package com.mpoom.algorithm.thread;

import java.util.concurrent.Semaphore;

/**
 * A, B, C线程循环打印
 * 这里主要考察的是一个多线程的调度，多线程调度常用工具有
 * 1.信号量
 * 2.锁
 * 3.阻塞队列
 * @author huyaoke
 * @date 2023-06-22
 */
public class MultiThreadLoop {

    private Semaphore semaphoreA = new Semaphore(1);
    private Semaphore semaphoreB = new Semaphore(1);
    private Semaphore semaphoreC = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {
        MultiThreadLoop obj = new MultiThreadLoop();
        obj.initSemaphore();
        obj.startThread();
        obj.startPrint();
    }

    public void initSemaphore() throws InterruptedException {
        semaphoreA.acquire();
        semaphoreB.acquire();
        semaphoreC.acquire();
    }

    public void startPrint() {
        semaphoreA.release();
    }

    public void startThread() {
        Thread threadA = new Thread(() -> {
            try {
                while (true) {
                    semaphoreA.acquire();
                    System.out.println("A");
                    semaphoreB.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadA.start();

        Thread threadB = new Thread(() -> {
            try {
                while (true) {
                    semaphoreB.acquire();
                    System.out.println("B");
                    semaphoreC.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadB.start();

        Thread threadC = new Thread(() -> {
            try {
                while (true) {
                    semaphoreC.acquire();
                    System.out.println("C");
                    semaphoreA.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadC.start();
    }
}
