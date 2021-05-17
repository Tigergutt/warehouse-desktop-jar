package se.melsom.warehouse.data.vo;

public class InventoryId {
    private String number;
    private String identity;

    public InventoryId(String number, String identity) {
        this.number = number;
        this.identity = identity;
    }

    @Override
    public String toString() {
        return String.format("%s|%s", number, identity);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Override
    public int hashCode() {
        return number.hashCode() * identity.hashCode();
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof InventoryId)) {
            return false;
        }

        InventoryId other = (InventoryId) object;

        if (!number.equals(other.number)) {
            return false;
        }

        return identity.equals(other.identity);
    }
}
