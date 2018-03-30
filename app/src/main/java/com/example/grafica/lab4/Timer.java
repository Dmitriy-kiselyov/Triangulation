package com.example.grafica.lab4;

class Timer {

    private Long start, finish;
    private Long time;

    public void start() {
        if (start != null && finish == null)
            stop();

        start = getTime();
    }

    private long getTime() {
        return System.nanoTime() / 1_000_000;
    }

    public long stop() {
        finish = getTime();
        time = finish - start;

        start = null;
        finish = null;

        return time;
    }

    public long time() {
        return System.nanoTime() / 1000000 - start;
    }
}