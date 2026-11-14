package utils;

/**
 * Cac ham kiem tra du lieu nhap vao tu ban phim.
 * Moi ham tra ve gia tri da chuan hoa (hoac null neu du lieu khong hop le).
 */
public final class Validator {
    public static final String WARRANTY_SCOPE_NATIONAL = "Toan Quoc";
    public static final String WARRANTY_SCOPE_INTERNATIONAL = "Quoc Te";
    public static final String STATUS_REPAIRED = "Da sua chua";
    public static final String STATUS_NOT_REPAIRED = "Chua sua chua";
    public static final int MAX_WARRANTY_DAYS = 730;

    private Validator() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * File CSV phan tach cac cot bang dau phay nen cac truong text khong duoc chua dau phay.
     */
    public static boolean containsComma(String value) {
        return value != null && value.contains(",");
    }

    public static Double parsePositiveDouble(String value) {
        if (isBlank(value)) {
            return null;
        }
        try {
            double number = Double.parseDouble(value.trim());
            if (Double.isNaN(number) || Double.isInfinite(number) || number <= 0) {
                return null;
            }
            return number;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer parsePositiveInt(String value) {
        if (isBlank(value)) {
            return null;
        }
        try {
            int number = Integer.parseInt(value.trim());
            return number > 0 ? number : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static boolean isValidWarrantyDays(int days) {
        return days > 0 && days <= MAX_WARRANTY_DAYS;
    }

    /**
     * Chap nhan "Toan Quoc" / "Quoc Te" khong phan biet hoa thuong va tra ve gia tri chuan.
     */
    public static String normalizeWarrantyScope(String value) {
        if (isBlank(value)) {
            return null;
        }
        String trimmed = value.trim().replaceAll("\\s+", " ");
        if (trimmed.equalsIgnoreCase(WARRANTY_SCOPE_NATIONAL)) {
            return WARRANTY_SCOPE_NATIONAL;
        }
        if (trimmed.equalsIgnoreCase(WARRANTY_SCOPE_INTERNATIONAL)) {
            return WARRANTY_SCOPE_INTERNATIONAL;
        }
        return null;
    }

    /**
     * Quoc gia xach tay khong duoc la "Viet Nam".
     */
    public static boolean isVietNam(String country) {
        if (isBlank(country)) {
            return false;
        }
        String normalized = country.trim().replaceAll("\\s+", " ").toLowerCase();
        return normalized.equals("viet nam")
                || normalized.equals("vietnam")
                || normalized.equals("việt nam");
    }

    /**
     * Chap nhan "Da sua chua" / "Chua sua chua" khong phan biet hoa thuong va tra ve gia tri chuan.
     */
    public static String normalizeStatus(String value) {
        if (isBlank(value)) {
            return null;
        }
        String trimmed = value.trim().replaceAll("\\s+", " ");
        if (trimmed.equalsIgnoreCase(STATUS_REPAIRED)) {
            return STATUS_REPAIRED;
        }
        if (trimmed.equalsIgnoreCase(STATUS_NOT_REPAIRED)) {
            return STATUS_NOT_REPAIRED;
        }
        return null;
    }
}
