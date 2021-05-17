package se.melsom.warehouse.data.vo;

public class StockOnHandVO {
    private String number;
    private String name;
    private String packaging;
    private int nominalQuantity;
    private int actualQuantity;
    private String identity;
    private String annotation;

    public StockOnHandVO() {}

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%d|%d|%s|%s", number, name, packaging, nominalQuantity, actualQuantity, identity, annotation);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public int getNominalQuantity() {
        return nominalQuantity;
    }

    public void setNominalQuantity(int nominalQuantity) {
        this.nominalQuantity = nominalQuantity;
    }

    public int getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(int actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    // Means for ordering a list of StockOnHandVO.
    public int compareByIdentity(StockOnHandVO other) {
        if (getIdentity() != null && other.getIdentity() != null) {
            if (getIdentity().length() > 0 && other.getIdentity().length() > 0) {
                return getIdentity().compareTo(other.getIdentity());
            } else if (getIdentity().length() > 0 && other.getIdentity().length() == 0) {
                return -1;
            } else if (getIdentity().length() == 0 && other.getIdentity().length() > 0) {
                return 1;
            }
        } else if (getIdentity() == null && other.getIdentity() != null) {
            return 1;
        } else if (getIdentity() != null && other.getIdentity() == null) {
            return -1;
        }

        return 0;
    }
}
