package tr.edu.itu.cs.db;

public class Style {
    private int id = 0;
    private String name = "";
    private String def = "";

    public Style() {

    }

    public Style(int i, String n, String d) {
        id = i;
        name = n;
        def = d;
    }

    public Style(String n, String d) {
        name = n;
        def = d;
    }

    public void setId(int i) {
        id = i;
    }

    public void setDef(String d) {
        def = d;
    }

    public void setName(String s) {
        name = s;
    }

    public int getId() {
        return id;
    }

    public String getDef() {
        return def;
    }

    public String getName() {
        return name;
    }

}
