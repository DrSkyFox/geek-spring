package com.lessons_two.client_test;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ClienAppOne {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8888);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("-auth l1 p2");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(50);
                            String message = in.readUTF();
                            System.out.println(message);
                            if (message.contains("Incorrect credentials")) {
                                out.writeUTF("-auth l1 p1");
                            }
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }

                    }
                }
            }).start();

            out.writeUTF("-exit");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
