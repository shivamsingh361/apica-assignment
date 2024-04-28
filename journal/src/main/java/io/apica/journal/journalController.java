package io.apica.journal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/journal")
public class journalController {
    @Autowired JournalService service;

    @PostMapping("/filter/list")
    public ResponseEntity<Page<Journal>> filterJournal(@RequestBody JournalReqDTO reqDto) {
        return ResponseEntity.ok(service.filterJournal(reqDto));
    }


}
