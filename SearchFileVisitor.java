package com.javarush.task.task31.task3111;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {

    private String partOfName;
    private String partOfContent;
    private int minSize = 0;
    private int maxSize = 0;
    private List<Path> foundFiles = new ArrayList<>();

    public  SearchFileVisitor() {

    }
    public SearchFileVisitor(String partOfName, String partOfContent, int minSize, int maxSize) {
        this.partOfName = partOfName;
        this.partOfContent = partOfContent;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public List<Path> getFoundFiles() {
        return foundFiles;
    }

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        byte[] content = Files.readAllBytes(file); // размер файла: content.length

        boolean containsName = true;
        if(partOfName != null && !file.getFileName().toString().contains(partOfName))
            containsName = false;

//        String content = new String(Files.readAllBytes(file));
        boolean containsContent = true;
        if(partOfContent != null && !new String(content).contains(partOfContent))
            containsContent = false;

        boolean fileIsMin = true;
        if(minSize != 0 && content.length < minSize)
            fileIsMin = false;

        boolean fileIsMax = true;
        if(maxSize != 0 && attrs.size() > maxSize)
            fileIsMax = false;

        if(containsName && containsContent && fileIsMin && fileIsMax)
            foundFiles.add(file);

//        return FileVisitResult.CONTINUE;



        return super.visitFile(file, attrs);
    }
}