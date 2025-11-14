package study.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerSocketEx {

    public static void main(String[] args) throws IOException {
        BufferedReader receiveMessageReader = null;
        Scanner scanner = new  Scanner(System.in);
        PrintWriter out = null;
        Socket acceptSocket =  null;
        try(// 1. socket() :준비용 소켓 생성
            ServerSocket serverSocket = new ServerSocket(8080)
        ) {

            // 2.  bind() : ip, port 할당
//            serverSocket.bind(InetSocketAddress.createUnresolved("127.0.0.1", 8080));
            System.out.println("연결 대기중 ..");
            acceptSocket = serverSocket.accept(); // 연결을 위한 소켓 생성
            System.out.println("클라이언트 연결");
            receiveMessageReader = new BufferedReader(new InputStreamReader(acceptSocket.getInputStream()));
            out = new PrintWriter(acceptSocket.getOutputStream());
            while(true){
                String receiveMessage = receiveMessageReader.readLine();
                if(receiveMessage.equalsIgnoreCase("exit")){
                    break;
                }
                System.out.println("receive message: " + receiveMessage);
                System.out.println("[Server] 전송 > ");
                String sendMessage = scanner.nextLine();
                out.println(sendMessage);
                out.flush();
                if ("quit".equalsIgnoreCase(sendMessage)) break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            receiveMessageReader.close();
            scanner.close();
            out.close();
            acceptSocket.close();
        }
    }

}
