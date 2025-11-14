package study.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//클라이언트
public class ClientSocketEx {

    public static void main(String[] args) throws IOException {
        BufferedReader in = null;
        PrintWriter out = null;

        Scanner scanner = new Scanner(System.in);
        try (Socket socket = new Socket("127.0.0.1", 8080);) {
            // host와 port에 해당하는 소켓 생성 (host: ServerMain (localhost) , port: 8080)

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            while (true) {
                System.out.print("전송하기>>> ");
                String outputMessage = scanner.nextLine();
                out.println(outputMessage); // 클라이언트 콘솔에 출력
                out.flush(); // 서버측 콘솔로 전송
                if ("quit".equalsIgnoreCase(outputMessage)) {
                    break;
                }

                String inputMessage = in.readLine();
                System.out.println("From Server: " + inputMessage);
                if ("quit".equalsIgnoreCase(inputMessage)) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            in.close();
            out.close();
            scanner.close();
            System.out.println("서버연결종료");
        }
    }

}
