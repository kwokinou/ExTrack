package umbf16cs443.extrack.db.models;


public class Category {

    int id;
    String catName;

        // Constructors
    public Category() {
    }

    public Category(String name) {
        this.catName = name;
    }

    public Category(int id, String name) {
        this.id = id;
        this.catName = name;
    }

    //getters, setters, movers and shakers
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String toString() {
        return this.catName;
    }

}
