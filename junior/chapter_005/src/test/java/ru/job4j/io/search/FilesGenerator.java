package ru.job4j.io.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FilesGenerator {
    private final File testDirectory = new File(System.getProperty("java.io.tmpdir"));
    private final Random random = new Random();
    private final List<String> dirs = new ArrayList<>();
    private final int depth = 3;
    private static final int FILES_NUMBER = 10;
    private File root;


    public FilesGenerator() {
        this.init();

    }

    private void init() {
        for (int i = 0; i < FILES_NUMBER; i++) {
            this.dirs.add("test" + i);
        }
        this.root = new File(this.testDirectory, "root");
        this.root.mkdir();
    }

    public void generate() throws IOException {
        this.mkDirs(this.root, this.dirs, this.depth);
    }

    public void delete() {
        if (this.root == null) {
            throw new NullPointerException("root is null");
        }
        deleteFile(this.root);
    }

    private void deleteFile(File dir) {
        File[] allContents = dir.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteFile(file);
            }
        }
        dir.delete();
    }

    private void mkDirs(File root, List<String> dirs, int depth) throws IOException {
        if (depth == 0) {
            return;
        }
        for (String s : dirs) {
            File subdir = new File(root, s);
            subdir.mkdir();
            File jpg = new File(root, this.random.nextInt() + ".jpg");
            jpg.createNewFile();
            File txt = new File(root, this.random.nextInt() + ".txt");
            txt.createNewFile();
            File doc = new File(root, this.random.nextInt() + ".doc");
            doc.createNewFile();
            mkDirs(subdir, dirs, depth - 1);
        }
    }
}

