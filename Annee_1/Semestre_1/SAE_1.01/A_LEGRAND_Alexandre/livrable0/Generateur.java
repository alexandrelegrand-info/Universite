class Generateur extends Program {
    String fileAsString(String filename) {
        extensions.File f = new extensions.File(filename);
        String content = "";
        while (ready(f)) {
            content = content + readLine(f) + '\n';
        }
        return content;
    }
    void algorithm() {
        String texte = fileAsString("data/Zen of Python.txt");
        println("<!DOCTYPE html>");
        println("<html lang=\"fr\">");
        println("<head>");
        println("<title> Zen of Python </title>");
        println("<meta charset=\"utf-8\">");
        println("</head>");
        println("<body>");
        println("<p>");
        print(texte);
        println("</p>");
        println("</body>");
        print("</html>");
    }
}