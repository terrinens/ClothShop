package com.cloth.clothshop;

import com.querydsl.core.types.dsl.CaseBuilder;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.basic.BasicTreeUI;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStoragService {
    private final String os = System.getProperty("os.name").toLowerCase();

    /**
     * 디렉토리 체크를 진행후 존재하지 않을시 생성을 시도하는 메서드
     *
     * @param pathString - 저장해야할 디렉토리 경로값을 전달 받야야함
     */
    public void directoryCheck(String pathString) throws IOException {
        System.out.println(" { 디렉토리 체크중...." + " }");
        System.out.println("디렉토리 체크 값으로 넘어온것? { " + pathString + " }");
        String mkDirectoryPath;
        if (os.startsWith("win")) {
            mkDirectoryPath = pathString.substring(0, pathString.lastIndexOf("\\"));
        } else {
            mkDirectoryPath = pathString.substring(0, pathString.lastIndexOf("/"));
        }
        System.out.println("디렉토리 체크 컷팅 결과값? { " + mkDirectoryPath + " }");

        Path directoryPath = Paths.get(mkDirectoryPath);
        System.out.println("파일 스토리지 서비스에서 패치 위치 검색 값 { " + directoryPath + " }");
        try {
            if (!Files.exists(directoryPath)) {
                if (os.startsWith("win")) {
                    Files.createDirectories(directoryPath);
                } else  {
                    System.out.println(" { 디렉토리 존재하지 않음. 생성시작" + " }");
                    Path linuxPath = linuxRootPath(pathString);
                    String command = "sudo mkdir " + linuxPath;
                    System.out.println("리눅스 조건문 시작 { " + command + " }");
                    Process process = Runtime.getRuntime().exec(command);
                    int exitCode = process.waitFor();
                    if (exitCode != 0) {
                        throw new IOException("리눅스 환경에서 디렉토리 생성 실패....");
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException("디렉토리 추가 실패");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 디렉토리 권한이 없어 실패시 각 os 환경마다 권한 획득을 시도하는 메서드
     *
     * @param pathString - 권한을 획득해야하는 디렉토리 경로값을 전달 받야아함
     */
    public void directoryAuthorityGet(String pathString) {
        String command;
        if (os.startsWith("win")) {
            command = "icacls \"" + pathString + "\" /grant Everyone:(R,W)";
        } else {
            Path linuxPath = linuxRootPath(pathString);
            command = "chmod -R 755 \"" + linuxPath + "\"";
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

    /**
     * 오류시 디렉토리 생성, 권한 획득을 순서에 맞게 처리하는 메서드
     *
     * @param pathString - 저장할 경로를 전달 받아야함
     * @param files      - 오류시 디렉토리 생성후 파일을 추가하기 위한 전달.
     * @return files로 전달받은 파일 저장
     */
    public void directoryCheckAndHandleErrors(String pathString, MultipartFile files) {
        try {
            System.out.println(" { 에러1 체크 시작 및 디렉토리 체크 시작...." + " }");
            directoryCheck(pathString);
            files.transferTo(new File(pathString));
            System.out.println(" { 에러 체크 완료 및 파일 생성 완료!" + " }");
        } catch (IOException e1) {
            System.out.println(" { 에러2 디렉토리 생성 시작" + " }");
            directoryAuthorityGet(pathString);
        } catch (RuntimeException e2) {
            e2.printStackTrace();
        }
    }

    public Path linuxRootPath(String pathString) {
        System.out.println(" { 패치 재생성 시작" + " }");
        System.out.println("패치 재생성 값으로 들어온것 { " + pathString + " }");
        int lastIndexOfBackslash = pathString.lastIndexOf("\\");
        int testindex = pathString.lastIndexOf("/");
        if (lastIndexOfBackslash != -1) {
            System.out.println(" { 패치 재생성 케이스1 진입함" + " }");
            String mkDirectoryPath = pathString.substring(0, pathString.lastIndexOf("\\"));
            String linuxRoot = "/" + mkDirectoryPath.replace("C:\\", "").toLowerCase().trim();
            System.out.println("패치 재생성에서 출력됨 { " + linuxRoot + " }");
            return Paths.get(linuxRoot);
        } else if (testindex != -1) {
            System.out.println(" { 패치 재생성 케이스2 진입함" + " }");
            String mkDirectoryPath = pathString.substring(0, pathString.lastIndexOf("/"));
            String linuxRoot = mkDirectoryPath.replace("C:\\", "").toLowerCase().trim();
            System.out.println("패치 재생성에서 출력됨 { " + linuxRoot + " }");
            return Paths.get(linuxRoot);
        } else {
            System.out.println("패치 재생성에서 컷팅할 값 없음 본래값 리턴 { " + pathString + " }");
            return Paths.get(pathString);
        }
    }

    public String linuxRootString(String pathString) {
        int lastIndexOfBackslash = pathString.lastIndexOf("\\");
        if (lastIndexOfBackslash != -1) {
            String mkDirectoryPath = pathString.substring(0, pathString.lastIndexOf("\\"));
            System.out.println("스트링 생성에서 출력됨 { " + mkDirectoryPath + " }");
            return "/" + mkDirectoryPath.replace("C:\\", "").toLowerCase().trim();
        } else {
            System.out.println("스트링 생성에서 출력됨 { " + "/" + pathString.replace("C:\\", "").toLowerCase().trim() + " }");
            return "/" + pathString.replace("C:\\", "").toLowerCase().trim();
        }
    }
}
