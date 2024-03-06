class JSONParser:
    jstr: str

    def __init__(self, json_data: str):
        self.jstr = json_data

    def dw(self):
        try:
            self.jstr = self.jstr.strip()
        except:
            raise ValueError("invalid json (in self.dw) (or the parser is broken)")

    def df(self):
        try:
            char = self.jstr[0]
            self.jstr = self.jstr[1:]
            return char
        except:
            raise ValueError("invalid json (in self.df) (or the parser is broken)")

    def strend(self):
        try:
            string = ""
            last_char = ""
            while ...:
                char = self.df()
                if char == '"' and last_char != "\\":
                    return string
                last_char = char
                string += char
        except:
            raise ValueError("invalid json (in strend) (or the parser is broken)")

    def e(self):
        raise ValueError("invalid json", self.jstr)

    # def dfw(self, c=""):
    #     if c=="" or self.jstr[0] == c:
    #         self.df()
    #         self.dw()
    #         return True
    #     return False

    def dictend(self):
        r = {}
        self.df()
        while (not (self.jstr == "")) and (self.jstr[0] != "]"):
            self.dw()
            if self.jstr[0] == "}":
                self.df()
                self.dw()
                return r

            # extract key
            elif self.jstr[0] != '"':
                raise ValueError("invalid json")
            self.df()
            key = self.strend()
            self.dw()
            if self.jstr[0] != ":":
                self.e()
            self.df()
            self.dw()

            if self.jstr[0] == '"':
                self.df()
                value = self.strend()
                self.dw()
                if self.jstr[0] == "}":
                    self.df()
                    self.dw()

                    r[key] = value
                    return r  # added during rewrite
                if self.jstr[0] == ",":
                    self.df()
                    self.dw()
                r[key] = value

            elif self.jstr[0] == "{":
                r[key] = self.dictend()
                self.dw()
                if (not (self.jstr == "")) and (self.jstr[0] == "}"):
                    self.df()
                    self.dw()
                elif (not (self.jstr == "")) and (self.jstr[0] == "]"):
                    self.df()
                    self.dw()
                if (not (self.jstr == "")) and (self.jstr[0] == ","):
                    self.df()
                    self.dw()
            elif self.jstr[0] == "[":
                r[key] = self.listend()
                self.dw()
                if (not (self.jstr == "")) and (self.jstr[0] == "}"):
                    self.df()
                    self.dw()
                elif (not (self.jstr == "")) and (self.jstr[0] == "]"):
                    self.df()
                    self.dw()
                if (not (self.jstr == "")) and (self.jstr[0] == ","):
                    self.df()
                    self.dw()
        return r

    def listend(self):
        r = []
        self.df()
        while (not (self.jstr == "")) and (self.jstr[0] != "}" and self.jstr[0] != "]"):
            self.dw()
            if self.jstr[0] == "]":
                self.df()
                self.dw()
            elif self.jstr[0] == '"':
                self.df()
                r.append(self.strend())
                self.dw()
                if self.jstr[0] == "]":
                    self.df()
                    self.dw()
                    return r  # added during rewrite
                if self.jstr[0] == ",":
                    self.df()
                    self.dw()
            elif self.jstr[0] == "{":
                r.append(self.dictend())
                self.dw()
                if self.jstr[0] == "]":
                    self.df()
                    self.dw()
                elif self.jstr[0] == ",":
                    self.df()
                    self.dw()
            elif self.jstr[0] == "[":
                r.append(self.listend())
                self.dw()
                if (not (self.jstr == "")) and (self.jstr[0] == "]"):
                    self.df()
                    self.dw()
                if (not (self.jstr == "")) and (self.jstr[0] == ","):
                    self.df()
                    self.dw()
            else:
                raise ValueError("invalid json", self.e())
        return r

    def parse(self):
        self.dw()
        if self.jstr[0] == "{":
            json_res = self.dictend()
        elif self.jstr[0] == "[":
            json_res = self.listend()
        else:
            raise ValueError("invalid json")

        return json_res


x = """{"hi": ["sdf", [  ]], "hi2": "hihihi"}"""
print(JSONParser(x).parse())
