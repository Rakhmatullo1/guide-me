package uz.guideme.fileservice.service;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.fileservice.configuration.StorageConfiguration;
import uz.guideme.fileservice.configuration.properties.StorageProperties;
import uz.guideme.fileservice.service.exception.CustomException;
import uz.guideme.fileservice.service.exception.InvalidArgumentException;
import uz.guideme.fileservice.service.exception.NotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class FileSystemService {

    private final Map<String, String> supportedMimeTypes;
    private final Path storageLocation;
    private final StorageProperties properties;

    public FileSystemService(StorageConfiguration storageConfiguration, StorageProperties properties) {
        this.supportedMimeTypes = storageConfiguration.getSupportedMimeTypes();
        this.storageLocation = Paths.get(storageConfiguration.getStorageLocation());
        this.properties = properties;
    }

    public Optional<Resource> store(MultipartFile file) {
        log.debug("Start store file to system directory");

        if (Objects.isNull(file) || file.isEmpty()) {
            log.warn("Invalid argument is passed! File must not be empty!");
            throw new InvalidArgumentException("Invalid argument is passed! File must not be empty!");
        }

        Path fileLocation = generateFileLocation(file.getContentType());

        try {

            long size = Files.copy(file.getInputStream(), fileLocation, StandardCopyOption.REPLACE_EXISTING);

            log.debug("File is stored successfully. Size: {}", size);
            return Optional.of(new UrlResource(fileLocation.toUri()));
        } catch (IOException e) {
            log.error("Error while storing file to our system directory! ", e);
            throw new CustomException("Error while storing file to our system directory! " + e.getMessage());
        }
    }

    public Optional<Resource> fetch(String path) {
        log.debug("Start fetch stored file by path! Path: {}", path);
        path = properties.getLocation() + "/" + path;

        if (StringUtils.isBlank(path)) {
            log.warn("Invalid argument is passed! File path must not be blank!");
            throw new InvalidArgumentException("Invalid argument is passed! File path must not be blank!");
        }

        try {
            Path filePath = Paths.get(path);

            Resource resource = new UrlResource(filePath.toUri());

            log.debug("Stored file is fetch successfully");
            return Optional.of(resource);
        } catch (MalformedURLException e) {
            log.error("Error while fetching stored file! ", e);
            throw new NotFoundException("Error while fetching stored file! " + e.getMessage());
        }
    }

    private Path generateFileLocation(String contentType) {

        String fileName = String.valueOf(UUID.randomUUID());
        if (StringUtils.isBlank(contentType)) {
            log.warn("Invalid argument is passed! Content type of file must not be blank!");
            throw new InvalidArgumentException("Invalid argument is passed! Content type of file must not be blank!");
        }

        if (!supportedMimeTypes.containsKey(contentType)) {
            log.warn("Unsupported content type! Given content type: {} | Supported content-types: {}", contentType, supportedMimeTypes);
            throw new InvalidArgumentException("Unsupported content type! Supported content-types: " + supportedMimeTypes);
        }

        return storageLocation.resolve(fileName + supportedMimeTypes.get(contentType));
    }
}
