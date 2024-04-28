package io.apica.journal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@Slf4j
public class KafkaConsumerService {

    @Autowired
    JournalService journalService;
    @KafkaListener(topics = {"create-user-events","update-user-events","delete-user-events","fetch_all-user-events","fetch-user-events"}, groupId = "user-events")
    public void consume(UserEvent userEvent) {

        log.info(String.format("Task status is updated : " + userEvent));
        journalService.add(userEvent);
    }

}
