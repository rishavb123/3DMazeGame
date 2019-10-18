import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;

public class HttpServer {

    public static final String newLine="\r\n";

    private ServerSocket serverSocket;
    private HashMap<String, Receiver> receivers;
    private int port;

    public static interface Receiver {
        public String call(String params);
    }

    public HttpServer(int port, HashMap<String, Receiver> receivers) {
        this.receivers = receivers;
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Thread start() {
        Thread t = new Thread(new Runnable(){
        
            @Override
            public void run() {
                System.out.println("Server started at port " + port);
                while(true) {
                    try {
                        Socket connection = serverSocket.accept();

                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                        PrintStream pout=new PrintStream(out);

                        String request=in.readLine();
                        if (request==null) continue;

                        while (true)
                        {
                            String ignore=in.readLine();
                            if (ignore==null || ignore.length()==0) break;
                        }

                        if (!request.startsWith("GET ") || !(request.endsWith(" HTTP/1.0") || request.endsWith(" HTTP/1.1"))) {
                            pout.print("HTTP/1.0 400 Bad Request"+newLine+newLine);
                        } else {
                            String routing = request.split(" ")[1];

                            String path = routing.split("\\?")[0].substring(1);
                            String params = "";
                            if(routing.split("\\?").length > 1)
                                params = routing.split("\\?")[1];

                            String response = receivers.get(path) == null? "Receiver Not Found": receivers.get(path).call(params);

                            pout.print(
                                "HTTP/1.0 200 OK"+newLine+
                                "Content-Type: text/html"+newLine+
                                "Date: "+new Date()+newLine+
                                "Content-length: "+response.length()+newLine+newLine+
                                response
                            );
                            pout.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }      
            }
        });
        t.start();
        return t;
    }

    public int getPort() {
        return port;
    }

}