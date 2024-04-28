package io.apica.journal;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JournalReqDTO {
    int pageNo;
    int pageSize;
    Long userId;
    Long dateRange;
    String operationType;
}
