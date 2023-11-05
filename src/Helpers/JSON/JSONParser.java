package Helpers.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

public class JSONParser {
    String jstr;
    int jlen;
    private final Set<Character> numchars = Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '.');

    public JSONParser(String jstr) {
        this.jstr = jstr;
        this.jlen = jstr.length();
    }

    private void dw() {
        this.jstr = this.jstr.trim();
    }

    private char df() {
        char c = this.jstr.charAt(0);
        this.jstr = this.jstr.substring(1);
        return c;
    }

    private void dfw() {
        this.df();
        this.dw();
    }

    private void pr() {
        this.dw();
        if (!this.jstr.isEmpty() && Set.of('}', ']').contains(this.jstr.charAt(0))) this.dfw();
        if (!this.jstr.isEmpty() && this.jstr.charAt(0) == ',') this.dfw();
    }

    private JSONParserException e() {
        return new JSONParserException("invalid json, got to: " + this.jstr + " pos " + (this.jlen - this.jstr.length()) + " (or the parser is broken)");
    }

    private String strend() {
        this.df();
        StringBuilder s = new StringBuilder();
        char lc = '_';
        char c;
        while (true) {
            c = this.df();
            if (c == '"' && lc != '\\') {
                this.dw();
                return s.toString();
            }
            lc = c;
            s.append(c);
        }
    }

    private double numend() {
        this.dw();
        StringBuilder n = new StringBuilder();
        for (char c : this.jstr.toCharArray()) {
            if (this.numchars.contains(c)) {
                n.append(c);
                df();
            } else break;
        }
        this.dw();
        String ns = n.toString();
        return ns.contains(".") ? Double.parseDouble(ns) : Integer.parseInt(ns);
    }

    private boolean loopcond() {
        this.dw();
        return !this.jstr.isEmpty() && this.jstr.charAt(0) != ']' && this.jstr.charAt(0) != '}';
    }

    private Map<String, Object> dictend() throws JSONParserException {
        Map<String, Object> r = new HashMap<>();
        this.df();
        while (this.loopcond()) {
            this.dw();
            if (this.jstr.charAt(0) == '}') {
                this.dfw();
                return r;
            }
            if (this.jstr.charAt(0) != '"') throw this.e();
            String key = this.strend();
            if (this.jstr.charAt(0) != ':') throw this.e();
            this.df();
            r.put(key, this.parse());
        }
        return r;
    }

    private List<Object> listend() throws JSONParserException {
        List<Object> r = new ArrayList<>();
        this.df();
        while (this.loopcond()) {
            this.dw();
            if (this.jstr.charAt(0) == ']') {
                this.dfw();
                return r;
            }
            r.add(this.parse());
        }
        return r;
    }

    public Object parse() throws JSONParserException {
        this.dw();
        Object r;

        if (this.jstr.charAt(0) == '{') {
            r = this.dictend();
            this.pr();
        } else if (this.jstr.charAt(0) == '[') {
            r = this.listend();
            this.pr();
        } else {
            if (this.jstr.charAt(0) == '"') {
                r = this.strend();
            } else if (this.jstr.startsWith("null")) {
                r = null;
                this.jstr = this.jstr.substring(4);
            } else if (this.jstr.startsWith("true")) {
                r = true;
                this.jstr = this.jstr.substring(4);
            } else if (this.jstr.startsWith("false")) {
                r = false;
                this.jstr = this.jstr.substring(5);
            } else if (this.numchars.contains(jstr.charAt(0))) {
                r = this.numend();
            } else {
                throw this.e();
            }
            if (!this.jstr.isEmpty() && this.jstr.charAt(0) == ',') {
                this.dfw();
            }
        }
        return r;
    }

}

