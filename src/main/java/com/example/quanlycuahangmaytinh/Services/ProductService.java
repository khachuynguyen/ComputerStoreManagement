package com.example.quanlycuahangmaytinh.Services;

import com.example.quanlycuahangmaytinh.Models.Product;
import com.example.quanlycuahangmaytinh.Repositories.ProductRepository;
import com.example.quanlycuahangmaytinh.Support.ImageProcess;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product saveProduct(Product product){
        Product product1=  productRepository.save(product);
        return product1;
    }
    public Product editProduct(Product product, @Nullable MultipartFile file){
        Product DBProduct = this.productRepository.findById(product.getId()).get();
        if(file.isEmpty()){
            product.setAvatar(DBProduct.getAvatar());
        }
        else{
            ImageProcess.deleteImage(DBProduct.getAvatar());
            try{
                product.setAvatar( ImageProcess.uploadImage(file));
            }catch (Exception exception){

            }
        }
        Product product1=  productRepository.save(product);
        return product1;
    }
    public List<Product> getAllProduct(){
        return this.productRepository.findAll();
    }
    public Optional<Product> findProductById(int id){
        return this.productRepository.findById(id);
    }
    public boolean deleteProduct(int product_id){
        try{
            Product toDelete = this.findProductById(product_id).get();
            if(toDelete == null)
                return false;
            String imageNeedToDelete =  toDelete.getAvatar();
            this.productRepository.deleteById(product_id);
            ImageProcess.deleteImage(imageNeedToDelete);
            return true;
        }
        catch (Exception e ){
            return false;
        }
    }
//    public Product addProduct(Dictionary<String,String> dictionary){
//    }
}
