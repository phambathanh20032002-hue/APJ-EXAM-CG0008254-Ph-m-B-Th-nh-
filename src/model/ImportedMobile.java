package model;

/**
 * Dien thoai xach tay.
 * Thong tin rieng: quoc gia xach tay va trang thai
 * ("Da sua chua" hoac "Chua sua chua").
 */
public class ImportedMobile extends Mobile {
    private String importCountry;
    private String status;

    public ImportedMobile() {
        super();
    }

    public ImportedMobile(int id, String name, double price, int quantity, String manufacturer,
                          String importCountry, String status) {
        super(id, name, price, quantity, manufacturer);
        this.importCountry = importCountry;
        this.status = status;
    }

    public String getImportCountry() {
        return importCountry;
    }

    public void setImportCountry(String importCountry) {
        this.importCountry = importCountry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getType() {
        return "Xach tay";
    }

    /**
     * Cac cot rieng cua dien thoai xach tay nam o vi tri importCountry, status,
     * hai cot cua dien thoai chinh hang de trong.
     */
    @Override
    public String toCsvRow() {
        return getId() + "," + getName() + "," + formatPriceForCsv() + "," + getQuantity() + ","
                + getManufacturer() + "," + getType() + ",,," + importCountry + "," + status;
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Quoc gia xach tay: " + importCountry + " - Trang thai: " + status;
    }
}
