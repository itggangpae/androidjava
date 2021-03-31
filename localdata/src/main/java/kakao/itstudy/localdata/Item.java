package kakao.itstudy.localdata;

public class Item {
    private int _id;
    private String _itemname;
    private int _quantity;

    public Item(){

    }

    public Item(int id, String itemname, int quantity) {
        this._id = id;
        this._itemname = itemname;
        this._quantity = quantity;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_itemname() {
        return _itemname;
    }

    public void set_itemname(String _itemname) {
        this._itemname = _itemname;
    }

    public int get_quantity() {
        return _quantity;
    }

    public void set_quantity(int _quantity) {
        this._quantity = _quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "_id=" + _id +
                ", _itemname='" + _itemname + '\'' +
                ", _quantity=" + _quantity +
                '}';
    }
}
