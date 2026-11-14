import repository.MobileCsvRepository;
import service.MobileService;
import service.MobileServiceImpl;
import view.MobileView;

/**
 * Diem khoi dong cua chuong trinh quan ly dien thoai.
 */
public class Main {
    public static void main(String[] args) {
        MobileService mobileService = new MobileServiceImpl(new MobileCsvRepository());
        MobileView mobileView = new MobileView(mobileService);
        mobileView.start();
    }
}
