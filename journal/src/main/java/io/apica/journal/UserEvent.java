package io.apica.journal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent implements Serializable {
    String eventName;
    Long id;

}
