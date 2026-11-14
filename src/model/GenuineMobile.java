package model;

/**
 * Dien thoai chinh hang.
 * Thong tin rieng: thoi gian bao hanh (so ngay) va pham vi bao hanh
 * ("Toan Quoc" hoac "Quoc Te").
 */
public class GenuineMobile extends Mobile {
    private int warrantyDays;
    private String warrantyScope;

    public GenuineMobile() {
        super();
    }

    public GenuineMobile(int id, String name, double price, int quantity, String manufacturer,
                         int warrantyDays, String warrantyScope) {
        super(id, name, price, quantity, manufacturer);
        this.warrantyDays = warrantyDays;
        this.warrantyScope = warrantyScope;
    }

    public int getWarrantyDays() {
        return warrantyDays;
    }

    public void setWarrantyDays(int warrantyDays) {
        this.warrantyDays = warrantyDays;
    }

    public String getWarrantyScope() {
        return warrantyScope;
    }

    public void setWarrantyScope(String warrantyScope) {
        this.warrantyScope = warrantyScope;
    }

    @Override
    public String getType() {
        return "Chinh hang";
    }

    /**
     * Cac cot rieng cua dien thoai chinh hang nam o vi tri warrantyDays, warrantyScope,
     * hai cot cua dien thoai xach tay de trong.
     */
    @Override
    public String toCsvRow() {
        return getId() + "," + getName() + "," + formatPriceForCsv() + "," + getQuantity() + ","
                + getManufacturer() + "," + getType() + "," + warrantyDays + "," + warrantyScope + ",,";
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Bao hanh: " + warrantyDays + " ngay - Pham vi: " + warrantyScope;
    }
}
