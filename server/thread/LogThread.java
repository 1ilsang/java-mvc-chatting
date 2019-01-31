package thread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Thread that stores all logs - ./resources/chatLog.txt
 */
public class LogThread extends Thread {
    private static String DIR_PATH = "./resources/";
    private static String FILE_NAME = "chatLog.txt";
    private static LogThread logThread = new LogThread();
    private static BufferedWriter bw, bwFile;
    private static Calendar date;
    private Queue<String> queue;
    private boolean flag = true;

    private LogThread() {
        try {
            bwFile = new BufferedWriter(new FileWriter(DIR_PATH + FILE_NAME));
            date = Calendar.getInstance();
            bw = new BufferedWriter(new OutputStreamWriter(System.out));
            queue = new LinkedList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LogThread getInstance() {
        return logThread;
    }

    public void stopThis() {
        log("Server is down...");
        this.flag = false;
    }

    public void log(String str) {
        queue.add(str);
    }

    @Override
    public void run() {
        // TODO 하루를 기준으로 저장 파일 자동으로 변하게
        String str;
        while (flag) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!queue.isEmpty()) {
                try {
                    str = "[" + date.getTime() + "] " + queue.peek() + "\n";
                    bw.write(str);
                    saveLogFile(str);
                    bw.flush();
                    queue.poll();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            bwFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLogFile(String str) {
        try {
            bwFile.write(str);
            bwFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
