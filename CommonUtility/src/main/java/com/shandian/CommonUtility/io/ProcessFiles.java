package com.shandian.CommonUtility.io;

import java.io.File;
import java.io.IOException;

public class ProcessFiles {
    public interface Strategy {
        void process(File file);
    }

    private final Strategy strategy;
    private final String ext;

    public ProcessFiles(Strategy strategy, String ext) {
        super();
        this.strategy = strategy;
        this.ext = ext;
    }

    public void start(String[] args) {
        try {
            if (args.length == 0) {
                // processDirectoryTree(new File("."));
            } else {
                for (String arg : args) {
                    File fileArg = new File(arg);
                    if (fileArg.isDirectory()) {
                        // processDirectoryTree(new File("."));
                    } else {
                        if (!arg.endsWith("." + ext)) {
                            arg += "." + ext;
                        }
                        strategy.process(new File(arg).getCanonicalFile());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // ??????
    // public void processDirectoryTree(File root) throws IOException {
    // for (File file : Directory.walk(root.getAbsolutePath(), ".*\\." + ext)) {
    // strategy.process(file.getCanonicalFile());
    // }
    // }
}
