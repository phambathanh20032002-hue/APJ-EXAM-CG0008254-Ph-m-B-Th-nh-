package service;

import exception.NotFoundProductException;
import model.Mobile;
import repository.MobileCsvRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Cài đặt các nghiệp vụ quản lý điện thoại, làm việc với file CSV qua repository.
 */
public class MobileServiceImpl implements MobileService {
    private final MobileCsvRepository repository;

    public MobileServiceImpl() {
        this(new MobileCsvRepository());
    }

    public MobileServiceImpl(MobileCsvRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Mobile> getAll() {
        return repository.findAll();
    }

    @Override
    public Mobile add(Mobile mobile) {
        mobile.setId(getNextId());
        repository.append(mobile);
        return mobile;
    }

    @Override
    public int getNextId() {
        int maxId = 0;
        for (Mobile mobile : repository.findAll()) {
            if (mobile.getId() > maxId) {
                maxId = mobile.getId();
            }
        }
        return maxId + 1;
    }

    @Override
    public Mobile findById(int id) throws NotFoundProductException {
        for (Mobile mobile : repository.findAll()) {
            if (mobile.getId() == id) {
                return mobile;
            }
        }
        throw new NotFoundProductException();
    }

    @Override
    public void deleteById(int id) throws NotFoundProductException {
        List<Mobile> mobiles = repository.findAll();
        boolean removed = false;
        for (int i = 0; i < mobiles.size(); i++) {
            if (mobiles.get(i).getId() == id) {
                mobiles.remove(i);
                removed = true;
                break;
            }
        }
        if (!removed) {
            throw new NotFoundProductException();
        }
        repository.saveAll(mobiles);
    }

    @Override
    public List<Mobile> search(String keyword) {
        List<Mobile> result = new ArrayList<>();
        String normalizedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();
        for (Mobile mobile : repository.findAll()) {
            boolean matchId = String.valueOf(mobile.getId()).contains(normalizedKeyword);
            boolean matchName = mobile.getName().toLowerCase().contains(normalizedKeyword);
            if (matchId || matchName) {
                result.add(mobile);
            }
        }
        return result;
    }
}
