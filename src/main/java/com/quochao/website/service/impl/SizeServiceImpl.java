package com.quochao.website.service.impl;

import com.quochao.website.entity.Size;
import com.quochao.website.repository.SizeRepository;
import com.quochao.website.service.SizeService;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@Data
@Transactional
public class SizeServiceImpl implements SizeService {
    private final SizeRepository sizeRepository;

    @Override
    public Page<Size> findAll(Integer page, Integer size, String field, String dir) {

        return (dir.equals("asc")) ? sizeRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field))) :
                sizeRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
    }

    @Override
    public Size save(Size size) {
        if (sizeRepository.getSizeByName(size.getName()) != null) throw new IllegalStateException("Size was existed");
        size.setCode(size.getName().trim().toLowerCase().replaceAll(" ", "-"));
        size.setState(true);
        size.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return sizeRepository.save(size);
    }

    @Override
    public Size update(Size size) {
        if (size == null || size.getId() == null) throw new IllegalStateException("NULL");
        Size updated = sizeRepository.getById(size.getId());
        updated.setName(size.getName());
        updated.setCode(updated.getName().trim().toLowerCase().replaceAll(" ", "-"));
        updated.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return updated;
    }

    @Override
    public Size delete(Long id) {
        if (id == null) throw new IllegalStateException("NULL");
        Size size = sizeRepository.getById(id);
        size.setState(false);
        size.setDeletedAt(new Timestamp(System.currentTimeMillis()));
        return size;
    }
}
