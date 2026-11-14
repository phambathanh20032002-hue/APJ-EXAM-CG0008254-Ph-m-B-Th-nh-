package exception;

/**
 * Exception dung de validate ID dien thoai khi thuc hien chuc nang xoa.
 * Thong diep: "ID điện thoại không tồn tại."
 */
public class NotFoundProductException extends Exception {
    private static final long serialVersionUID = 1L;

    public NotFoundProductException() {
        super("ID dien thoai khong ton tai.");
    }

    public NotFoundProductException(String message) {
        super(message);
    }
}
