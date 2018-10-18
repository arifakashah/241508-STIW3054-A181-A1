package com.assignment1;

import sun.rmi.runtime.Log;

/**
 * Created by RJ
 *
 */
public class App {

    public static void main(String[] args) {

        MyThread myThread = new MyThread();
        myThread.start();

    }

    static class MyThread extends Thread {

        @Override
        public void run() {

            try {
                ReadNWriteFile.readData();
                sleep(Common.SLEEP_THREAD);
                ReadNWriteFile.writeData();
                sleep(Common.SLEEP_THREAD);
                ReadNWriteFile.openExcel();
                sleep(Common.SLEEP_THREAD);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}