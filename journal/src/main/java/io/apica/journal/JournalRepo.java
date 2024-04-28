package io.apica.journal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepo extends JpaRepository<Journal, Long>, JpaSpecificationExecutor<Journal> {
}
