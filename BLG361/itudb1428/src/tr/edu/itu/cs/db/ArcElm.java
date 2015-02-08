package tr.edu.itu.cs.db;

public class ArcElm {
    int id;
    String name;
    String def;

    public ArcElm() {
        // TODO Auto-generated constructor stub
    }

    public ArcElm(int i, String n, String d) {
        id = i;
        name = n;
        def = d;
    }

    public ArcElm(String n, String d) {
        name = n;
        def = d;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int i) {
        id = i;
    }

    public void setName(String n) {
        name = n;
    }

    public void setDef(String d) {
        def = d;
    }

    public String getDef() {
        return def;
    }

}
