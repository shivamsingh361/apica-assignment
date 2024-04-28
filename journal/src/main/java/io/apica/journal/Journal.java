package io.apica.journal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Journal {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Date dateTime;
    Long userId;
    String operation;
}
