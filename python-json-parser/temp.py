import json


class JSONParser:
    jstr: str
    jlen: int
    _numchars = {str(i) for i in range(10)}.union({".", "-"})

    def __init__(self, json_data: str):
        self.jstr = json_data
        self.jlen = len(json_data)

    def dw(self):  # discard whitespace
        self.jstr = self.jstr.strip()

    def df(self):  # discard first
        c = self.jstr[0]
        self.jstr = self.jstr[1:]
        return c

    def dfw(self):  # discard first then whitespace
        self.df()
        self.dw()

    def strend(self):  # get string to end and discard
        self.df()
        s = ""
        lc = ""
        while ...:
            c = self.df()
            if c == '"' and lc != '\\':
                self.dw()
                return s
            lc = c
            s += c

    def numend(self):  # get number to end and discard
        self.dw()
        n = ""
        for c in self.jstr:
            if c in self._numchars:
                n += c
                self.df()
            else:
                break
        self.dw()
        return int(n) if "." not in n else float(n)

    def e(self):  # error
        return ValueError(
            "invalid json, got to: ", self.jstr, f"pos {self.jlen - len(self.jstr)} (or the parser is broken)")

    def pr(self):  # prepare return
        self.dw()
        if self.jstr != "" and self.jstr[0] in {"}", "]"}:
            self.dfw()
        if self.jstr != "" and self.jstr[0] == ",":
            self.dfw()

    def dictend(self):  # get dict to end and discard
        r = {}
        self.df()
        while self.jstr != "" and self.jstr[0] != "]" and self.jstr[0] != "}":
            # print(r, "|||", self.jstr)
            # handle empty object
            self.dw()
            if self.jstr[0] == "}":
                self.dfw()
                return r

            # extract key
            if self.jstr[0] != '"':
                raise self.e()
            key = self.strend()
            if self.jstr[0] != ":":
                raise self.e()
            self.dfw()

            if self.jstr[0] == "{":
                r[key] = self.dictend()
                self.pr()
            elif self.jstr[0] == "[":
                r[key] = self.listend()
                self.pr()
            else:
                if self.jstr[0] == '"':
                    r[key] = self.strend()
                elif self.jstr.startswith("null"):
                    r[key] = None
                    self.jstr = self.jstr[4:]
                elif self.jstr.startswith("true"):
                    r[key] = True
                    self.jstr = self.jstr[4:]
                elif self.jstr.startswith("false"):
                    r[key] = False
                    self.jstr = self.jstr[5:]
                elif self.jstr[0] in self._numchars:
                    r[key] = self.numend()
                else:
                    raise self.e()

            # if self.jstr[0] == "}":
            # self.dfw()
            # return r
            if self.jstr[0] == ",":
                self.dfw()

        return r

    def listend(self):  # get list to end and discard
        r = []
        self.df()
        while self.jstr != "" and self.jstr[0] != "}" and self.jstr[0] != "]":
            self.dw()
            if self.jstr[0] == "]":
                self.dfw()
                return r

            if self.jstr[0] == "{":
                r.append(self.dictend())
                self.pr()
            elif self.jstr[0] == "[":
                r.append(self.listend())
                self.pr()
            else:
                if self.jstr[0] == '"':
                    r.append(self.strend())
                elif self.jstr.startswith("null"):
                    r.append(None)
                    self.jstr = self.jstr[4:]
                elif self.jstr.startswith("true"):
                    r.append(True)
                    self.jstr = self.jstr[4:]
                elif self.jstr.startswith("false"):
                    r.append(False)
                    self.jstr = self.jstr[5:]
                elif self.jstr[0] in self._numchars:
                    r.append(self.numend())
                else:
                    raise self.e()

                # if self.jstr[0] == "]":
                # self.dfw()
                # return r
                ...
            if self.jstr[0] == ",":
                self.dfw()

        return r

    # def np(self):  # next part

    def parse(self):  # start parsing
        self.dw()
        if self.jstr[0] == "{":
            res = self.dictend()
        elif self.jstr[0] == "[":
            res = self.listend()
        else:
            raise self.e()
        return res


