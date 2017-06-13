package com.appmate.controller.profile;

import com.appmate.model.profile.Image;
import com.appmate.repository.profile.ImageRepository;
import com.appmate.service.profile.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by uujc0207 on 2017. 3. 20..
 */
@RestController
@RequestMapping("/profiles")
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    // PATCH - Upload image
    @RequestMapping(value = "/images/upload/{user_id}", method = RequestMethod.PATCH)
    public String uploadFile2(@PathVariable("user_id") String user_id, @RequestParam("uploadedImage") MultipartFile uploadedFileRef) {

        Image currentImage = imageRepository.findOne(user_id);

        if (currentImage == null) {
            System.out.println("User with id " + user_id + " not found");
            return "User with id " + user_id + " not found";
        }

        int totalBytes = imageService.uploadImage(user_id, uploadedFileRef);

        return "Image uploaded successfully! Total Bytes Read="+totalBytes;
    }

    // GET - Image URL
    @RequestMapping(value = "/images/getURL/{user_id}",method = RequestMethod.GET)
    public ResponseEntity<Image> getUserImageURL(@PathVariable("user_id") String user_id) throws IOException {

        Image image = imageRepository.findOne(user_id);

        if (image == null) {
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND);
        }

        Image get_image_URL = imageService.getUserImageURL(user_id);

        return new ResponseEntity<Image>(get_image_URL, HttpStatus.OK);
    }

    // GET - Image
    @RequestMapping(value = "/images/getImage",method = RequestMethod.GET)
    public ResponseEntity<byte[]> getUserImage(@RequestParam(value = "image_name", defaultValue = "") String image_name) throws IOException {

        ResponseEntity<byte[]> get_image = imageService.getUserImage(image_name);

        if (get_image == null) {
            System.out.println("User with id " + image_name + " not found");
            return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
        }

        return get_image;
    }

/*
    // GET
    @RequestMapping(value = "/image/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<Image> getImage(@PathVariable String user_id) {

        Image image = imageRepository.findOne(user_id);

        if (image == null) {
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Image>(image, HttpStatus.OK);
    }

    // PATCH
    @RequestMapping(value = "/image/{user_id}", method = RequestMethod.PATCH)
    public ResponseEntity<Image> updateImage(@PathVariable("user_id") String user_id, @RequestBody Image image) {

        Image currentImage = imageRepository.findOne(user_id);

        if (currentImage == null) {
            System.out.println("User with id " + user_id + " not found");
            return new ResponseEntity<Image>(HttpStatus.NOT_FOUND);
        }

        if(image.getImage() != null){
            currentImage.setImage(image.getImage());
        }

        imageRepository.save(currentImage);

        return new ResponseEntity<Image>(currentImage, HttpStatus.OK);
    }
    */
}
