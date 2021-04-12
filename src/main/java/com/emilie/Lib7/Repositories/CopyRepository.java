package com.emilie.Lib7.Repositories;

import com.emilie.Lib7.Models.Entities.Copy;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CopyRepository {

    Optional<Copy> findById(Long id);

    Copy save(Copy copy);

    Copy update(Copy copy);

    void deleteById(Long id);
}
