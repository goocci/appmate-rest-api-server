package com.appmate.service.profile;

import com.appmate.model.profile.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by uujc0207 on 2017. 4. 2..
 */
public interface ImageService {

    public int uploadImage(String user_id, MultipartFile uploadedFileRef);

    public ResponseEntity<byte[]> getUserImage(String image_name) throws IOException;

    public Image getUserImageURL(String user_id);
}
