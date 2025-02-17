package com.github.raphaelfontoura.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class WorkLoadSplitter extends RecursiveAction {

    private final long workLoad;
    
    public WorkLoadSplitter(long workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {
        if (workLoad > 16) {
            System.out.println("Work Load too big! Thus splitting : " + workLoad);
            long firstWorkload = workLoad / 2;
            long secondWorkload = workLoad - firstWorkload;

            WorkLoadSplitter firstSplitter = new WorkLoadSplitter(firstWorkload);
            WorkLoadSplitter secondSplitter = new WorkLoadSplitter(secondWorkload);

            firstSplitter.fork();
            secondSplitter.fork();
        } else {
            System.out.println("Work Load within limits! Task being executed for workload : " + workLoad);
        }
    }
    
}

class WorkLoadSplitterDemo {
    public static void main(String[] args) {
        try (ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors())) {
            WorkLoadSplitter workLoadSplitter = new WorkLoadSplitter(128);
            pool.invoke(workLoadSplitter);
        }
    }
}
