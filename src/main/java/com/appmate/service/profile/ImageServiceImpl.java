package com.appmate.service.profile;

import com.appmate.model.profile.Image;
import com.appmate.repository.profile.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by uujc0207 on 2017. 4. 2..
 */
@Service("imageService")
@Transactional
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepository imageRepository;

    // 이미지 업로드
    public int uploadImage(String user_id, MultipartFile uploadedFileRef){

        Image currentImage = imageRepository.findOne(user_id);

        // Get name of uploaded file.
        String fileName = uploadedFileRef.getOriginalFilename();

        // Path where the uploaded file will be stored.
        String path = "images/" + user_id + "_" + fileName;

        // This buffer will store the data read from 'uploadedFileRef'
        byte[] buffer = new byte[1000];

        // Now create the output file on the server.
        File outputFile = new File(path);

        FileInputStream reader = null;
        FileOutputStream writer = null;
        int totalBytes = 0;
        try {
            outputFile.createNewFile();

            // Create the input stream to uploaded file to read data from it.
            reader = (FileInputStream) uploadedFileRef.getInputStream();

            // Create writer for 'outputFile' to write data read from
            // 'uploadedFileRef'
            writer = new FileOutputStream(outputFile);

            // Iteratively read data from 'uploadedFileRef' and write to
            // 'outputFile';
            int bytesRead = 0;
            while ((bytesRead = reader.read(buffer)) != -1) {
                writer.write(buffer);
                totalBytes += bytesRead;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!uploadedFileRef.isEmpty()){
            currentImage.setImage(user_id + "_" + fileName);
        }

        imageRepository.save(currentImage);

        return totalBytes;
    }

    // 이미지 URL GET
    public Image getUserImageURL(String user_id){

        Image image = imageRepository.findOne(user_id);

        try{
            URL facebook_image_url = new URL(image.getImage());

            return image;
        } catch (MalformedURLException e){
            // 페이스북 이미지 URL 없음
        }

        String image_name = image.getImage();
//        String get_image_URL = "http://localhost:8080/profiles/images/getImage?image_name="+image_name;
        String get_image_URL = "http://ec2-52-39-83-45.us-west-2.compute.amazonaws.com:8080/profiles/images/getImage?image_name="+image_name;

        Image myImage = new Image();

        myImage.setUser_id(user_id);
        myImage.setImage(get_image_URL);

        return myImage;

    }

    // GET 이미지 (서버에 저장된 이미지에 해당)
    public ResponseEntity<byte[]> getUserImage(String image_name) throws IOException{

        System.out.println(image_name);
        String get_image_name = "images/" + image_name;
        RandomAccessFile f = new RandomAccessFile(get_image_name, "r");
        byte[] b = new byte[(int)f.length()];
        f.readFully(b);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(b, headers, HttpStatus.OK);
    }
}
