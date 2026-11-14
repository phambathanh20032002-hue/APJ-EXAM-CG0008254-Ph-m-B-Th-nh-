# Chuong trinh quan ly dien thoai (Java Console + CSV)

Bai lam quan ly dien thoai cua cua hang dien thoai, viet bang Java (tuong thich Java 8+),
luu du lieu trong file CSV `data/mobiles.csv`.

## 1. Cau truc project

```
thanh/
├── data/
│   └── mobiles.csv                      # Du lieu dien thoai (UTF-8)
└── src/
    ├── Main.java                        # Diem khoi dong
    ├── exception/
    │   └── NotFoundProductException.java# Exception khi ID khong ton tai
    ├── model/
    │   ├── Mobile.java                  # Abstract class (thong tin chung)
    │   ├── GenuineMobile.java           # Dien thoai chinh hang
    │   └── ImportedMobile.java          # Dien thoai xach tay
    ├── repository/
    │   └── MobileCsvRepository.java     # Doc/ghi file CSV
    ├── service/
    │   ├── MobileService.java           # Interface nghiep vu
    │   └── MobileServiceImpl.java       # Cai dat nghiep vu
    ├── utils/
    │   └── Validator.java               # Kiem tra du lieu nhap
    └── view/
        └── MobileView.java              # Menu console
```

## 2. Thiet ke huong doi tuong

- `Mobile` la **abstract class** chua cac thuoc tinh chung: `id`, `name`, `price`,
  `quantity`, `manufacturer`; co 2 **phuong thuc truu tuong**:
  - `getType()`: tra ve loai dien thoai.
  - `toCsvRow()`: tra ve mot dong CSV de ghi file.
- `GenuineMobile extends Mobile`: them `warrantyDays`, `warrantyScope`;
  override `getType()`, `toCsvRow()`, `toString()`.
- `ImportedMobile extends Mobile`: them `importCountry`, `status`;
  override `getType()`, `toCsvRow()`, `toString()`.
- `Mobile.toString()` hien thi cac cot chung, lop con override va bo sung thong tin rieng
  (dung cho chuc nang "Xem danh sach" va "Tim kiem").
- Tang `service` chi lam nghiep vu, tang `repository` chi lam viec voi file CSV.

## 3. Dinh dang file CSV

File `data/mobiles.csv` gom 10 cot:

```
id,name,price,quantity,manufacturer,type,warrantyDays,warrantyScope,importCountry,status
1,Samsung Galaxy S24 Ultra,31990000,10,Samsung,Chinh hang,365,Toan Quoc,,
2,iPhone 15 Pro Max,34990000,5,Apple,Chinh hang,730,Quoc Te,,
3,Xiaomi 14 Ultra,25990000,7,Xiaomi,Xach tay,,,Trung Quoc,Chua sua chua
4,Google Pixel 8 Pro,21990000,3,Google,Xach tay,,,My,Da sua chua
```

- `type = Chinh hang`: dien `warrantyDays`, `warrantyScope`; 2 cot cuoi de trong.
- `type = Xach tay`: 2 cot bao hanh de trong; dien `importCountry`, `status`.
- Neu file chua ton tai, chuong trinh tu tao thu muc `data` va ghi dong tieu de.
- ID tu dong tang: `ID moi = max(ID) + 1`. File mau dang co ID 1..4 nen dien thoai
  them moi se co ID = 5, 6, ...

## 4. Chuc nang

1. **Them moi**: nhap loai dien thoai va cac thong tin, validate tung truong
   (bat buoc, so duong, bao hanh <= 730 ngay, pham vi `Toan Quoc`/`Quoc Te`,
   quoc gia khac `Viet Nam`, trang thai `Da sua chua`/`Chua sua chua`),
   sau do ghi them vao file CSV. ID do chuong trinh tu sinh.
2. **Xoa**: nhap ID; neu ID khong ton tai se nem `NotFoundProductException`
   voi thong diep "ID dien thoai khong ton tai." va yeu cau nhap lai.
   Neu ton tai, hien thi dien thoai va hoi `Yes/No`:
   - `Yes`: xoa trong file CSV va hien thi lai danh sach.
   - `No`: quay ve menu chinh.
3. **Xem danh sach**: hien thi tat ca dien thoai trong file CSV kem thong tin rieng
   cua tung loai.
4. **Tim kiem**: tim gan dung theo ID hoac ten (khong phan biet hoa thuong).
5. **Thoat**: ket thuc chuong trinh.

## 5. Cach chay

### Bang IntelliJ IDEA
1. Mo project `thanh` (JDK 20 hoac moi hon).
2. Chay file `src/Main.java` (Run 'Main.main()').
3. Thu muc lam viec phai la thu muc goc project de tim thay `data/mobiles.csv`.
   Project da cau hinh san encoding UTF-8 (`.idea/encodings.xml`).

### Bang dong lenh
```bash
cd thanh
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out Main
```

## 6. Ghi chu

- Du lieu va thong bao tren console dung tieng Viet khong dau de hien thi dung
  tren moi console (cmd/PowerShell/IntelliJ), dung voi yeu cau de bai.
- Ten dien thoai / nha san xuat khong duoc chua dau phay vi CSV phan tach cot bang dau phay.
- Gia ban duoc ghi duoi dang so nguyen/thap phan thong thuong (khong dung E-notation),
  hien thi tren console co phan cach hang nghin.
