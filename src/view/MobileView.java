package view;

import exception.NotFoundProductException;
import model.GenuineMobile;
import model.ImportedMobile;
import model.Mobile;
import service.MobileService;
import utils.Validator;

import java.util.List;
import java.util.Scanner;

/**
 * Giao dien Console cua chuong trinh quan ly dien thoai.
 * Noi dung hien thi su dung tieng Viet khong dau de hien thi dung
 * tren moi console (cmd, PowerShell, IntelliJ...).
 */
public class MobileView {
    private static final String YES = "Yes";
    private static final String NO = "No";

    private final MobileService mobileService;
    private final Scanner scanner;

    public MobileView(MobileService mobileService) {
        this.mobileService = mobileService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Vong lap hien thi menu chinh cho den khi nguoi dung chon "Thoat".
     */
    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            showMenu();
            int choice = readInt("Chon chuc nang: ");
            try {
                switch (choice) {
                    case 1:
                        addMobile();
                        break;
                    case 2:
                        deleteMobile();
                        break;
                    case 3:
                        showMobileList();
                        pause();
                        break;
                    case 4:
                        searchMobile();
                        pause();
                        break;
                    case 5:
                        isRunning = false;
                        System.out.println("Da thoat chuong trinh. Tam biet!");
                        break;
                    default:
                        System.out.println("Lua chon khong hop le, vui long chon tu 1 den 5.");
                }
            } catch (RuntimeException e) {
                System.out.println("Da xay ra loi khi xu ly du lieu: " + e.getMessage());
                pause();
            }
        }
        scanner.close();
    }

    private void showMenu() {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("         CHUONG TRINH QUAN LY DIEN THOAI");
        System.out.println("==================================================");
        System.out.println("1. Them moi dien thoai");
        System.out.println("2. Xoa dien thoai");
        System.out.println("3. Xem danh sach dien thoai");
        System.out.println("4. Tim kiem dien thoai");
        System.out.println("5. Thoat");
        System.out.println("--------------------------------------------------");
    }

    // ==================== CHUC NANG 1: THEM MOI ====================

    private void addMobile() {
        System.out.println();
        System.out.println("--- THEM MOI DIEN THOAI ---");
        int type = readPhoneType();

        String name = readRequiredText("Ten dien thoai: ");
        double price = readPositiveDouble("Gia ban: ");
        int quantity = readPositiveInt("So luong: ",
                "So luong phai la so nguyen duong. Vui long nhap lai.");
        String manufacturer = readRequiredText("Nha san xuat: ");

        Mobile mobile;
        if (type == 1) {
            int warrantyDays = readWarrantyDays();
            String warrantyScope = readWarrantyScope();
            mobile = new GenuineMobile(0, name, price, quantity, manufacturer,
                    warrantyDays, warrantyScope);
        } else {
            String importCountry = readImportCountry();
            String status = readImportStatus();
            mobile = new ImportedMobile(0, name, price, quantity, manufacturer,
                    importCountry, status);
        }

        Mobile savedMobile = mobileService.add(mobile);
        System.out.println("Them moi dien thoai thanh cong!");
        System.out.println("ID dien thoai duoc cap: " + savedMobile.getId());
        pause();
    }

    private int readPhoneType() {
        while (true) {
            System.out.println("Chon loai dien thoai:");
            System.out.println("  1. Dien thoai chinh hang");
            System.out.println("  2. Dien thoai xach tay");
            int type = readInt("Loai dien thoai: ");
            if (type == 1 || type == 2) {
                return type;
            }
            System.out.println("Lua chon khong hop le, vui long chon 1 hoac 2.");
        }
    }

    private double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            Double value = Validator.parsePositiveDouble(input);
            if (value != null) {
                return value;
            }
            System.out.println("Gia ban phai la so duong. Vui long nhap lai.");
        }
    }

    private int readPositiveInt(String prompt, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            Integer value = Validator.parsePositiveInt(input);
            if (value != null) {
                return value;
            }
            System.out.println(errorMessage);
        }
    }

    private int readWarrantyDays() {
        while (true) {
            int days = readInt("Thoi gian bao hanh (so ngay, toi da 730): ");
            if (Validator.isValidWarrantyDays(days)) {
                return days;
            }
            System.out.println("Thoi gian bao hanh phai la so duong va khong qua 730 ngay. "
                    + "Vui long nhap lai.");
        }
    }

    private String readWarrantyScope() {
        while (true) {
            System.out.print("Pham vi bao hanh (Toan Quoc / Quoc Te): ");
            String scope = Validator.normalizeWarrantyScope(scanner.nextLine());
            if (scope != null) {
                return scope;
            }
            System.out.println("Pham vi bao hanh chi co 2 gia tri: 'Toan Quoc' va 'Quoc Te'. "
                    + "Vui long nhap lai.");
        }
    }

    private String readImportCountry() {
        while (true) {
            String country = readRequiredText("Quoc gia xach tay: ");
            if (!Validator.isVietNam(country)) {
                return country;
            }
            System.out.println("Quoc gia xach tay khong duoc la 'Viet Nam'. Vui long nhap lai.");
        }
    }

    private String readImportStatus() {
        while (true) {
            System.out.print("Trang thai (Da sua chua / Chua sua chua): ");
            String status = Validator.normalizeStatus(scanner.nextLine());
            if (status != null) {
                return status;
            }
            System.out.println("Trang thai chi co 2 gia tri: 'Da sua chua' va 'Chua sua chua'. "
                    + "Vui long nhap lai.");
        }
    }

    // ==================== CHUC NANG 2: XOA ====================

    private void deleteMobile() {
        System.out.println();
        System.out.println("--- XOA DIEN THOAI ---");
        while (true) {
            int id = readInt("Nhap ID dien thoai can xoa: ");
            try {
                Mobile mobile = mobileService.findById(id);
                System.out.println("Dien thoai ban muon xoa:");
                printTableHeader();
                System.out.println(mobile);

                String confirm = readYesNo("Ban co chac chan muon xoa? (Yes/No): ");
                if (NO.equals(confirm)) {
                    System.out.println("Da huy viec xoa. Quay lai menu chinh.");
                    pause();
                    return;
                }

                mobileService.deleteById(id);
                System.out.println("Da xoa dien thoai co ID = " + id + ".");
                System.out.println("Danh sach dien thoai sau khi xoa:");
                showMobileList();
                pause();
                return;
            } catch (NotFoundProductException e) {
                System.out.println(e.getMessage());
                System.out.println("Vui long nhap lai ID dien thoai.");
            }
        }
    }

    // ==================== CHUC NANG 3: XEM DANH SACH ====================

    private void showMobileList() {
        List<Mobile> mobiles = mobileService.getAll();
        if (mobiles.isEmpty()) {
            System.out.println("Danh sach dien thoai dang trong.");
            return;
        }
        printTableHeader();
        for (Mobile mobile : mobiles) {
            System.out.println(mobile);
        }
        System.out.println("Tong so dien thoai: " + mobiles.size());
    }

    private void printTableHeader() {
        System.out.printf("%-5s | %-25s | %14s | %7s | %-15s | %-10s | %s%n",
                "ID", "Ten dien thoai", "Gia ban", "So luong", "Nha san xuat",
                "Loai", "Thong tin them");
        System.out.println("------------------------------------------------------------------"
                + "--------------------------------------------------------------");
    }

    // ==================== CHUC NANG 4: TIM KIEM ====================

    private void searchMobile() {
        System.out.println();
        System.out.println("--- TIM KIEM DIEN THOAI ---");
        String keyword = readRequiredText("Nhap ID hoac ten dien thoai can tim: ");
        List<Mobile> result = mobileService.search(keyword);
        if (result.isEmpty()) {
            System.out.println("Khong tim thay dien thoai nao phu hop voi tu khoa: " + keyword);
            return;
        }
        System.out.println("Ket qua tim kiem cho tu khoa \"" + keyword + "\":");
        printTableHeader();
        for (Mobile mobile : result) {
            System.out.println(mobile);
        }
        System.out.println("Tim thay " + result.size() + " dien thoai.");
    }

    // ==================== CAC HAM NHAP LIEU DUNG CHUNG ====================

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap mot so nguyen hop le.");
            }
        }
    }

    /**
     * Doc mot chuoi bat buoc: khong duoc de trong va khong duoc chua dau phay
     * (vi file CSV phan tach cot bang dau phay).
     */
    private String readRequiredText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Truong nay la bat buoc, vui long nhap lai.");
                continue;
            }
            if (Validator.containsComma(input)) {
                System.out.println("Thong tin khong duoc chua dau phay (dinh dang CSV). "
                        + "Vui long nhap lai.");
                continue;
            }
            return input;
        }
    }

    private String readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (YES.equalsIgnoreCase(input)) {
                return YES;
            }
            if (NO.equalsIgnoreCase(input)) {
                return NO;
            }
            System.out.println("Vui long nhap 'Yes' hoac 'No'.");
        }
    }

    private void pause() {
        System.out.println("Nhan Enter de quay lai menu chinh.");
        scanner.nextLine();
    }
}
