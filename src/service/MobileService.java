package service;

import exception.NotFoundProductException;
import model.Mobile;

import java.util.List;

/**
 * Cac nghiep vu quan ly dien thoai.
 */
public interface MobileService {

    /**
     * Lay toan bo dien thoai trong file CSV.
     */
    List<Mobile> getAll();

    /**
     * Them moi dien thoai. ID duoc tu dong sinh theo quy tac: max(ID) + 1.
     *
     * @return dien thoai da duoc gan ID va ghi xuong file.
     */
    Mobile add(Mobile mobile);

    /**
     * Sinh ID tiep theo cho dien thoai moi (bat dau tu 1).
     */
    int getNextId();

    /**
     * Tim dien thoai theo ID.
     *
     * @throws NotFoundProductException neu khong tim thay ID trong file CSV.
     */
    Mobile findById(int id) throws NotFoundProductException;

    /**
     * Xoa dien thoai theo ID.
     *
     * @throws NotFoundProductException neu khong tim thay ID trong file CSV.
     */
    void deleteById(int id) throws NotFoundProductException;

    /**
     * Tim gan dung theo ID hoac ten dien thoai (khong phan biet hoa thuong).
     */
    List<Mobile> search(String keyword);
}
