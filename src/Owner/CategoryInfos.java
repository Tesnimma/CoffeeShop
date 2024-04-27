package Owner;

import java.util.ArrayList;


public class CategoryInfos {

    public ArrayList<Item> items;
    public  CategoryInfos(Item startingItem)
    {
        items = new ArrayList<Item>();
        items.add(startingItem);
    }
}
