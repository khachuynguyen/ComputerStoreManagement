package com.example.quanlycuahangmaytinh.Controllers.Mvc;

import com.example.quanlycuahangmaytinh.DTO.UserDTO;
import com.example.quanlycuahangmaytinh.Models.Product;
import com.example.quanlycuahangmaytinh.Services.ProductService;
import com.example.quanlycuahangmaytinh.Services.UserService;
import com.example.quanlycuahangmaytinh.Support.ImageProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @RequestMapping(value = "/",name = "homePage",method = RequestMethod.GET)
    public String index(Model model){
        List<Product> listProducts = productService.getAllProduct();
        model.addAttribute("list",listProducts);
        return "index";
    }
    @PostMapping("/addProduct")
    public String addProduct( @ModelAttribute("product") Product product, Model model,@RequestParam("image") MultipartFile file, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("auth");
        if(userDTO == null || !userService.checkInfoAuth(userDTO.getEmail(),userDTO.getName()))
            return "redirect:/login";
        Product savedProduct =null;
        try {
                product.setAvatar(ImageProcess.uploadImage(file));
                savedProduct = productService.saveProduct(product);
            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        if(savedProduct!=null)
        {
            model.addAttribute("saved",savedProduct);
            return  "redirect:/";
        }
        else return "fail";

    }
    @GetMapping("/empty")
    public String viewNotFound(){
        return "empty";
    }
    @GetMapping("/productDetail")
    public String viewProductDetail(@PathParam("id") int id,Model model){
        Optional<Product>  product = this.productService.findProductById(id);
        if(product.isPresent()){
            model.addAttribute("product",product.get());
            return "productDetail";
        }
        else{
            return "redirect:/empty";
        }
    }
    @GetMapping("/management")
    public String productManagement(Model model, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("auth");
        if(userDTO == null || !userService.checkInfoAuth(userDTO.getEmail(),userDTO.getName()))
            return "redirect:/login";
        Product product = new Product();
        model.addAttribute("product",product);
        List<Product> list = this.productService.getAllProduct();
        model.addAttribute("list",list);
        return "ProductManagement";
    }
    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("id") int id,HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("auth");
        if(userDTO == null || !userService.checkInfoAuth(userDTO.getEmail(),userDTO.getName()))
            return "redirect:/login";
        if(this.productService.deleteProduct(id))
            return "redirect:/management";
        return "fail";
    }
    @GetMapping("/success")
    public String getSuccessResult(){
        return "success";
    }
    @GetMapping("/edit")
    public String editProductForm(@PathParam("id") int id ,Model model,HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("auth");
        if(userDTO == null || !userService.checkInfoAuth(userDTO.getEmail(),userDTO.getName()))
            return "redirect:/login";
        Optional<Product> productToEdit = this.productService.findProductById(id);
        if(productToEdit.isPresent()){
            model.addAttribute("editProduct",productToEdit.get());
            return "editProduct";
        }
        else{
            return "redirect:/management";
        }
    }
    @PostMapping("/edit")
    public String saveForm(@ModelAttribute("editProduct") Product product, Model model, @Nullable MultipartFile file, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("auth");
        if(userDTO == null || !userService.checkInfoAuth(userDTO.getEmail(),userDTO.getName()))
            return "redirect:/login";
        Product saved = this.productService.editProduct(product,file);
        return "redirect:/productDetail/?id="+saved.getId();
    }
//    @PostMapping("/edit")
//    public String saveForm(@ModelAttribute("editProduct") Product product, Model model, @Nullable MultipartFile file){
//        Product saved = this.productService.editProduct(product);
//        return "redirect:/productDetail/?id="+saved.getId();
//    }
}
