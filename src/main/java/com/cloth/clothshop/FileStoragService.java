package com.cloth.clothshop;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStoragService {
    private final String os = System.getProperty("os.name").toLowerCase();
    public void directoryCheck(String pathString) throws IOException {
        String mkDirectoryPath = pathString.substring(0, pathString.lastIndexOf("\\"));
        Path directoryPath = Paths.get(mkDirectoryPath);

        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
        } catch (IOException e) {
            throw new IOException("디렉토리 추가 실패");
        }
    }

    public void directoryAuthorityGet(String pathString) {
        String command;

        if (os.startsWith("win")) {
            command = "icacls \"" + pathString + "\" /grant Everyone:(R,W)";
        } else if (os.startsWith("linux")) {
            command = "chmod -R 755 \"" + pathString + "\"";
        } else {
            throw new RuntimeException("알수없는 환경입니다. 메서드를 수정 해야합니다.");
        }

        try {
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("권한 수정 실패 혹은 폴더 존재하지 않음");
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void directoryCheckAndHandleErrors(String pathString, MultipartFile files) {
        try {
            directoryCheck(pathString);
            files.transferTo(new File(pathString));
        } catch (IOException e1) {
            directoryAuthorityGet(pathString);
        } catch (RuntimeException e2) {
            e2.printStackTrace();
        }
    }
}
