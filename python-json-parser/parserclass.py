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

    def pr(self):  # prepare return
        self.dw()
        if self.jstr != "" and self.jstr[0] in {"}", "]"}:
            self.dfw()
        if self.jstr != "" and self.jstr[0] == ",":
            self.dfw()

    def e(self):  # error
        return ValueError(
            "invalid json, got to: ", self.jstr, f"pos {self.jlen - len(self.jstr)} (or the parser is broken)")

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

    def loopcond(self):
        # do dw to handle edge case: empty list in list, in object, with a key after, with whitespace ( ["hi", [ ] ] )
        # (example: '{"hi": [[ ] ], "hi2": { }}') (also for similar edge case but for empty object)
        self.dw()
        return self.jstr != "" and self.jstr[0] != "]" and self.jstr[0] != "}"

    def dictend(self):  # get dict to end and discard
        r = {}
        self.df()
        while self.loopcond():
            self.dw()
            if self.jstr[0] == "}":
                self.dfw()
                return r
            if self.jstr[0] != '"':
                raise self.e()
            key = self.strend()
            if self.jstr[0] != ":":
                raise self.e()
            self.dfw()
            r[key] = self.parse()
        return r

    def listend(self):  # get list to end and discard
        r = []
        self.df()
        while self.loopcond():
            self.dw()
            if self.jstr[0] == "]":
                self.dfw()
                return r
            r.append(self.parse())
        return r

    def parse(self):  # next part
        self.dw()
        if self.jstr[0] == "{":
            r = self.dictend()
            self.pr()
        elif self.jstr[0] == "[":
            r = self.listend()
            self.pr()
        else:
            if self.jstr[0] == '"':
                r = self.strend()
            elif self.jstr.startswith("null"):
                r = None
                self.jstr = self.jstr[4:]
            elif self.jstr.startswith("true"):
                r = True
                self.jstr = self.jstr[4:]
            elif self.jstr.startswith("false"):
                r = False
                self.jstr = self.jstr[5:]
            elif self.jstr[0] in self._numchars:
                r = self.numend()
            else:
                raise self.e()
            if self.jstr != "" and self.jstr[0] == ",":
                self.dfw()
        return r
