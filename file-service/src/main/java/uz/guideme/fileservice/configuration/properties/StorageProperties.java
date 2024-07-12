package uz.guideme.fileservice.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("storage")
@Component
public class StorageProperties {

    private String location;

}
