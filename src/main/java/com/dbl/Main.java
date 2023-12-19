package com.dbl;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {
    public static void main(String[] args) throws Exception {
        FileAlterationObserver observer =
                new FileAlterationObserver("arquivos");
        FileAlterationMonitor monitor = new FileAlterationMonitor();
        FileAlterationListenerAdaptor listener = new FileAlterationListenerAdaptor() {
            @Override
            public void onFileChange(File file) {
                try {
                    BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class); //line that throws exception
                    attr.lastModifiedTime();
                    attr.creationTime();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Arquivo criado " + file.getName());
            }

            @Override
            public void onFileCreate(File file) {
                System.out.println("Arquivo modificado " + file.getName());
            }

            @Override
            public void onFileDelete(File file) {
                System.out.println("Arquivo deletado " + file.getName());
            }
        };

        observer.addListener(listener);
        monitor.addObserver(observer);
        monitor.start();

    }
}