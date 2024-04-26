package ClientSide;

public class Item {
    public  String name;
    public  double price;
    public  int ID;
    public  Item(String n,double p,int ID)
    {
        name = n;
        price = p;
        this.ID = ID;
    }

    @Override
    public String toString() {
        return name + ": "+price+" dt";
    }
}
