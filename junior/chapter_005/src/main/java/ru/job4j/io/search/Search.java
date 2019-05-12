package ru.job4j.io.search;

import java.io.File;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class for searching files with transmitted extensions.
 */
public class Search {
    private final Queue<File> directories = new ConcurrentLinkedQueue<>();
    private final BlockingQueue<File> files = new LinkedBlockingQueue<>();
    private final List<File> foundFiles = new CopyOnWriteArrayList<>();


    public Search() {

    }

    /**
     * Method for searching files.
     *
     * @param parent String path to folder.
     * @param exts   List of files extensions for searching.
     * @return List of found files.
     */
    public List<File> files(String parent, List<String> exts) {
        File dir = new File(parent);
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Parent must be directory!");
        }
        if (dir.listFiles() == null) {
            throw new NullPointerException("Directory is empty!");
        }
        this.parseFiles(dir);
        Task task = new Task(this.files, exts, foundFiles);
        Thread thread = new Thread(task);
        thread.start();
        while (!this.directories.isEmpty()) {
            File file = this.directories.poll();
            this.parseFiles(file);
        }
        while (!this.files.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread.interrupt();
        return this.foundFiles;
    }

    /**
     * Helper method for parsing files.
     *
     * @param file File file.
     */
    private void parseFiles(File file) {
        Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .forEach(f -> {
                    if (f.isDirectory()) {
                        this.directories.add(f);
                    } else {
                        this.files.add(f);
                    }
                });
    }

    /**
     * Class for searching files with extensions in the queue.
     * It will run in another thread.
     */
    private final class Task implements Runnable {
        private final BlockingQueue<File> files;
        private final List<String> exts;
        private final List<File> foundFiles;

        public Task(BlockingQueue<File> files, List<String> exts, List<File> foundFiles) {
            this.files = files;
            this.exts = exts;
            this.foundFiles = foundFiles;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                File file = null;
                try {
                    file = this.files.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (file != null) {
                    for (String ext : this.exts) {
                        if (file.getName().contains(ext)) {
                            this.foundFiles.add(file);
                        }
                    }
                }
            }
        }
    }
}
