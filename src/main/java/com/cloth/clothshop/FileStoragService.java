package com.cloth.clothshop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStoragService {
    private final String os = System.getProperty("os.name").toLowerCase();

    /**
     * 디렉토리 체크를 진행후 존재하지 않을시 생성을 시도하는 메서드
     * @param pathString - 저장해야할 디렉토리 경로값을 전달 받야야함
     */
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

    /**
     * 디렉토리 권한이 없어 실패시 각 os 환경마다 권한 획득을 시도하는 메서드
     * @param pathString - 권한을 획득해야하는 디렉토리 경로값을 전달 받야아함
     */
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
}
