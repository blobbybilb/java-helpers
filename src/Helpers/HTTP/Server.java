package Helpers.HTTP;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;
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
        s.createContext("/", new Router());
    }

    public void start() {
        s.setExecutor(null);
        s.start();
    }

    private static boolean isMatch(List<String> enteredRoute, List<String> routeToCheck) {
        for (int i = 0; (i < routeToCheck.size() && i < enteredRoute.size()); i++) {
            if (routeToCheck.get(i).equals("*")) return true;
            if (routeToCheck.get(i).equals(enteredRoute.get(i))) continue;
            if (routeToCheck.get(i).startsWith(":")) continue;
            return false;
        }
        return routeToCheck.size() == enteredRoute.size();
    }

    private static List<String> convertToRouteList(String method, String routeString) {
        if (routeString.equals("/")) return new ArrayList<>(List.of(method, ""));
        List<String> r = new ArrayList<>(List.of(routeString.split("\\?")[0].split("/")));
        if (r.getFirst().isEmpty()) r.removeFirst();
        if (r.getLast().isEmpty()) r.removeLast();
        if (r.isEmpty()) r.add("");
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
//        Server server = new Server(8000);
//        server.get("/", Server::test);
//        System.out.println(Server.isMatch(convertToRouteList("GET", "/users/1"), convertToRouteList("GET", "/users/:id")));
//        System.out.println(Server.isMatch(convertToRouteList("GET", "/users/1"), convertToRouteList("GET", "/*")));
//        System.out.println(Server.isMatch(convertToRouteList("GET", "/users/1"), convertToRouteList("GET", "/users/1")));
//        System.out.println(Server.isMatch(convertToRouteList("GET", "/users/1"), convertToRouteList("GET", "/users/1/posts")));
//        System.out.println(Server.isMatch(convertToRouteList("GET", "/users/1/posts"), convertToRouteList("GET", "/users/1")));
        Server server = new Server(8000);
//        server.get("/", Server::test);
        server.post("/", r -> "<b>hi</b>");
        server.get("/users/:id", r -> r.routeParams.get("id"));
        server.get("/users/:id/posts", r -> r.getParams.get("id") + " <b>hi</b>");
        server.get("/users/:id/posts/:post_id", r -> r.routeParams.get("id") + " posts " + r.routeParams.get("post_id"));
        server.start();
    }

    private static String test(RouteContext r) {
        return "<b>hi</b>";
    }

    class Router implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            List<String> route = convertToRouteList(t.getRequestMethod().toUpperCase(), t.getRequestURI().toString());
            for (int i = 0; i < routes.size(); i++) {
                if (isMatch(route, routes.get(i))) {
                    RouteContext r = new RouteContext();
                    for (int j = 0; j < routes.get(i).size(); j++) {
                        if (routes.get(i).get(j).startsWith(":")) {
                            r.routeParams.put(routes.get(i).get(j).substring(1), route.get(j));
                        }
                    }
                    r.getParams.putAll(parseQuery(t.getRequestURI().getQuery()));
                    if (t.getRequestMethod().equals("POST")) {
                        r.postData = new String(t.getRequestBody().readAllBytes());
                    }
                    String response = handlers.get(i).apply(r);
                    t.sendResponseHeaders(200, response.length());
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                    return;
                }
            }
            String response = "404 Not Found";
            t.sendResponseHeaders(404, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private HashMap<String, String> parseQuery(String query) {
            HashMap<String, String> params = new HashMap<>();
            if (query == null) return params;
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                params.put(keyValue[0], keyValue[1]);
            }
            return params;
        }
    }

    public static class RouteContext {
        HashMap<String, String> routeParams = new HashMap<>();
        HashMap<String, String> getParams = new HashMap<>();
        String postData;
    }
}