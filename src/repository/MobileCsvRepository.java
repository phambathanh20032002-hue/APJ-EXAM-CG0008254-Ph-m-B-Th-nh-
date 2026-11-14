package repository;

import model.GenuineMobile;
import model.ImportedMobile;
import model.Mobile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Doc/ghi danh sach dien thoai tu file CSV "data/mobiles.csv".
 *
 * Cau truc file (10 cot):
 * id,name,price,quantity,manufacturer,type,warrantyDays,warrantyScope,importCountry,status
 *
 * - Dien thoai chinh hang: dien warrantyDays, warrantyScope; importCountry, status de trong.
 * - Dien thoai xach tay: warrantyDays, warrantyScope de trong; dien importCountry, status.
 */
public class MobileCsvRepository {
    private static final String FILE_PATH = "data/mobiles.csv";
    private static final String HEADER =
            "id,name,price,quantity,manufacturer,type,warrantyDays,warrantyScope,importCountry,status";
    private static final int COLUMN_COUNT = 10;

    /**
     * Doc toan bo dien thoai dang co trong file CSV.
     */
    public List<Mobile> findAll() {
        Path path = prepareFile();
        List<Mobile> mobiles = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String rawLine : lines) {
                String line = rawLine.trim();
                if (line.isEmpty() || line.toLowerCase().startsWith("id,")) {
                    continue;
                }
                Mobile mobile = parseLine(line);
                if (mobile != null) {
                    mobiles.add(mobile);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Khong the doc file du lieu: " + FILE_PATH, e);
        }
        return mobiles;
    }

    /**
     * Ghi de toan bo danh sach xuong file (dung khi xoa dien thoai).
     */
    public void saveAll(List<Mobile> mobiles) {
        Path path = prepareFile();
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        for (Mobile mobile : mobiles) {
            lines.add(mobile.toCsvRow());
        }
        try {
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Khong the ghi file du lieu: " + FILE_PATH, e);
        }
    }

    /**
     * Them mot dong moi vao cuoi file CSV.
     */
    public void append(Mobile mobile) {
        Path path = prepareFile();
        try {
            Files.write(path, Collections.singletonList(mobile.toCsvRow()),
                    StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalStateException("Khong the ghi file du lieu: " + FILE_PATH, e);
        }
    }

    /**
     * Tao thu muc "data" va file CSV kem dong tieu de neu file chua ton tai.
     */
    private Path prepareFile() {
        Path path = Paths.get(FILE_PATH);
        try {
            Path parent = path.toAbsolutePath().getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(path) || Files.size(path) == 0) {
                Files.write(path, Collections.singletonList(HEADER), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Khong the khoi tao file du lieu: " + FILE_PATH, e);
        }
        return path;
    }

    /**
     * Chuyen mot dong CSV thanh doi tuong Mobile tuong ung.
     * Tra ve null neu dong du lieu bi loi (thieu cot, sai dinh dang so...).
     */
    private Mobile parseLine(String line) {
        String[] columns = line.split(",", -1);
        if (columns.length < COLUMN_COUNT) {
            System.out.println("Bo qua dong du lieu khong hop le trong file CSV: " + line);
            return null;
        }
        try {
            int id = Integer.parseInt(columns[0].trim());
            String name = columns[1].trim();
            double price = Double.parseDouble(columns[2].trim());
            int quantity = Integer.parseInt(columns[3].trim());
            String manufacturer = columns[4].trim();
            String type = columns[5].trim();

            if ("Chinh hang".equalsIgnoreCase(type)) {
                int warrantyDays = Integer.parseInt(columns[6].trim());
                String warrantyScope = columns[7].trim();
                return new GenuineMobile(id, name, price, quantity, manufacturer,
                        warrantyDays, warrantyScope);
            }
            String importCountry = columns[8].trim();
            String status = columns[9].trim();
            return new ImportedMobile(id, name, price, quantity, manufacturer,
                    importCountry, status);
        } catch (NumberFormatException e) {
            System.out.println("Bo qua dong du lieu khong hop le trong file CSV: " + line);
            return null;
        }
    }
}
