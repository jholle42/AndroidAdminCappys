package jackal.org.adminandroid;

/**
 * Created by jholle42 on 6/15/18.
 */

public class hold {
    public String name;
    public String number;
    public String itemName;
    public String quantity;
    public String message;
    public Integer approved;

    //ADD TImestamp

    public hold(){

    }

    public hold(String name, String number, String itemName, String quantity, String message, Integer approved) {
        this.name = name;
        this.number = number;
        this.itemName = itemName;
        this.quantity = quantity;
        this.message = message;
        this.approved = approved;
    }

    public Integer getApproved() {
        return approved;
    }

    public String getMessage(){ return message;}

    public void setMessage(String message){ this.message = message;}

    public void setApproved(Integer approved) { this.approved = approved;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
