package Helpers.HTTP;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {
    HttpServer s;
    List<List<String>> routes = new ArrayList<>();
    List<Function<RouteContext, String>> handlers = new ArrayList<>();

    public Server(int port) throws IOException {
        s = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public void start() {
        s.setExecutor(null);
        s.start();
    }

    private boolean isMatch(List<String> enteredRoute, List<String> routeToCheck) {
        for (int i = 0; i < routeToCheck.size(); i++) {

        }
    }

    private static List<String> convertToRouteList(String method, String routeString) {
        List<String> r = Arrays.stream(routeString.split("/")).toList();
        if (r.getFirst().equals("")) r.removeFirst();
        r.addFirst(method);
        return r;
    }

    public Server get(String route, Function<RouteContext, String> handler) {
        routes.add(convertToRouteList("GET", route));
        handlers.add(handler);
        return this;
    }

    public Server post(String route, Function<RouteContext, String> handler) {
        routes.add(convertToRouteList("POST", route));
        handlers.add(handler);
        return this;
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(8000);
        server.get("/", Server::test);
    }

    private static String test(RouteContext r) {
        return "hi";
    }

    static class Router implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String requestURI = t.getRequestURI().toString();
            String[] urlParts = requestURI.split("/");
            System.out.println(requestURI);
            if (urlParts.length > 1) {
                String name = urlParts[1];
                String response = "Hello " + name;
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                String response = "Hello World!";
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    public static class RouteContext {
        HashMap<String, String> routeParams = new HashMap<>();
        HashMap<String, String> getParams = new HashMap<>();
        String postData;

        public RouteContext() {
        }
    }
}