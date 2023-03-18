package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions) throws Exception{
        //add an image to the blog
        Blog blog;
        try{
            blog = blogRepository2.findById(blogId).get();
        }catch(Exception e){
            throw new Exception("Blog does not exists");
        }

        Image image = new Image(description,dimensions,blog);
        blog.getImageList().add(image);
        blogRepository2.save(blog);

        return image;

    }

    public void deleteImage(Integer id){
        imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) throws Exception{
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        Image image;
        try{
            image = imageRepository2.findById(id).get();
        }catch(Exception e){
            throw new Exception("Image does not exists");
        }

        String[] screenDim = screenDimensions.split("X");
        String[] imageDim = image.getDimensions().split("X");

        int screenArea = Integer.parseInt(screenDim[0]) * Integer.parseInt(screenDim[1]);
        int imageArea = Integer.parseInt(imageDim[0]) * Integer.parseInt(imageDim[1]);

        return screenArea/imageArea;
    }
}
