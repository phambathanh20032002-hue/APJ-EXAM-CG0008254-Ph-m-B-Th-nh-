package model;

import java.math.BigDecimal;

/**
 * Lop truu tuong dai dien cho mot chiec dien thoai trong cua hang.
 * Cac thong tin chung: ID, ten, gia ban, so luong, nha san xuat.
 */
public abstract class Mobile {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String manufacturer;

    protected Mobile() {
    }

    protected Mobile(int id, String name, double price, int quantity, String manufacturer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Loai dien thoai: "Chinh hang" hoac "Xach tay".
     * Day la phuong thuc truu tuong, moi lop con bat buoc override.
     */
    public abstract String getType();

    /**
     * Tra ve mot dong du lieu theo dinh dang CSV de ghi xuong file.
     * Moi lop con tu quyet dinh cac cot rieng cua minh.
     */
    public abstract String toCsvRow();

    /**
     * Gia ban hien thi tren man hinh console (co phan cach hang nghin).
     */
    protected String formatPriceForDisplay() {
        return String.format("%,.0f", price);
    }

    /**
     * Gia ban ghi xuong file CSV (khong chua dau phay, khong bi chuyen sang dang E-notation).
     */
    protected String formatPriceForCsv() {
        return BigDecimal.valueOf(price).stripTrailingZeros().toPlainString();
    }

    @Override
    public String toString() {
        return String.format("%-5d | %-25s | %14s | %7d | %-15s | %-10s",
                id, name, formatPriceForDisplay(), quantity, manufacturer, getType());
    }
}
