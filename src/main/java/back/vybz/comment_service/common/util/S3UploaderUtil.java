package back.vybz.comment_service.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3UploaderUtil {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file, String dirName){
        try {
            return upload(file.getInputStream(), dirName, file.getOriginalFilename(), file.getContentType(), file.getSize());
        } catch (IOException e) {
            throw new IllegalArgumentException("S3 업로드 실패", e);
        }
    }

    public String upload(InputStream inputStream, String dirName, String originalFileName, String contentType, long size) {
        String fileName = dirName + "/" + UUID.randomUUID() + "_" + originalFileName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(size);
        metadata.setContentType(contentType);

        PutObjectRequest request = new PutObjectRequest(bucket, fileName, inputStream, metadata);


        amazonS3.putObject(request);
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public void delete(String fileUrl) throws URISyntaxException {
        URI uri = new URI(fileUrl);
        String key = URLDecoder.decode(uri.getPath().substring(1), StandardCharsets.UTF_8);
        amazonS3.deleteObject(bucket, key);
    }
}
