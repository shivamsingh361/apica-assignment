package io.apica.journal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class JournalServiceImpl implements  JournalService{

    @Autowired
    JournalRepo repo;

    @Override
    public void add(UserEvent userEvent) {
        Journal journal = new Journal();
        journal.setDateTime(new Date(System.currentTimeMillis()));
        journal.setOperation(userEvent.getEventName());
        journal.setUserId(userEvent.getId());
        repo.save(journal);
    }

    @Override
    public Page<Journal> filterJournal(JournalReqDTO reqDTO) {
        return repo.findAll(journalSpecification(reqDTO), getPageable(reqDTO.getPageNo(), reqDTO.getPageSize()));
    }
    private Pageable getPageable(final int pageNo, final int pageSize) {
        return PageRequest.of(pageNo, pageSize!=0?pageSize:20);
    }
    private Specification<Journal> journalSpecification(JournalReqDTO reqDTO) {
        Specification<Journal> spec = (journal, cq, cb) -> cb.equal(cb.literal(1), 1);
        if (Objects.nonNull(reqDTO.getUserId())) {
            spec = spec.and((journal, cq, cb) -> cb.equal(journal.get("userId"), reqDTO.getUserId()));
        }
        if (Objects.nonNull(reqDTO.getOperationType())) {
            spec = spec.and((journal, cq, cb) -> cb.like(journal.get("operation"), "%" +reqDTO.getOperationType() + "%"));
        }
        return spec;
    }
}
