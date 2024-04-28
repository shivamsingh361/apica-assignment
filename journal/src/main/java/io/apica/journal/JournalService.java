package io.apica.journal;

import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface JournalService {
    @Async
    void add(UserEvent userEvent);
    Page<Journal> filterJournal(JournalReqDTO reqDTO);
}
