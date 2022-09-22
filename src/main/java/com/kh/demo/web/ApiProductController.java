package com.kh.demo.web;

import com.kh.demo.dao.Product;
import com.kh.demo.svc.ProductSVC;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.api.product.AddForm;
import com.kh.demo.web.api.product.EditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/test14")
public class ApiProductController {

    private final ProductSVC productSVC;

    //상품등록
    @ResponseBody
    @PostMapping(value = "/products")
    public ApiResponse<Long> add(@RequestBody AddForm addForm, BindingResult bindingResult){
        log.info("reqMsg={}",addForm);

        if(addForm.getCount() > 100) {
            bindingResult.rejectValue("count","count.err","상품수량은 100개를 초과할 수 없습니다.");
        }

        Product product = new Product();
        BeanUtils.copyProperties(addForm,product);

        Long id = productSVC.save(product);

        ApiResponse.Header header = new ApiResponse.Header("00","등록성공");
        ApiResponse<Long> response = new ApiResponse<>(header,id);

        return response;
    }

    //상품조회
    @ResponseBody
    @GetMapping("/products/{id}")
    public ApiResponse<Product> findById(@PathVariable("id") Long id) {

        //상품조회
        Optional<Product> findProduct = productSVC.findByProductId(id);

        ApiResponse<Product> response = null;
        if(findProduct.isPresent()) {
            Product product =findProduct.get();

            ApiResponse.Header header = new ApiResponse.Header("00","조회성공");
            response = new ApiResponse<>(header,product);

        }else{
            ApiResponse.Header header = new ApiResponse.Header("99","조회실패");
            response = new ApiResponse<>(header,null);
        }
        return response;
    }

    //수정
    @ResponseBody
    @PatchMapping("/products/{id}")
    public ApiResponse<Product> edit(@PathVariable("id") Long id, @RequestBody EditForm editForm) {

        ApiResponse<Product> response = null;
        Optional<Product> findProduct = productSVC.findByProductId(id);
        if(findProduct.isEmpty()) {
            ApiResponse.Header header = new ApiResponse.Header("10","상품을 찾을 수 없습니다.");
            response = new ApiResponse<>(header, null);
            return response;
        }

        Product product = new Product();
        BeanUtils.copyProperties(editForm,product);

        productSVC.update(id,product);

        ApiResponse.Header header = new ApiResponse.Header("00","수정성공");
        response = new ApiResponse<>(header,productSVC.findByProductId(id).get());

        return response;

    }

    //삭제
    @ResponseBody
    @DeleteMapping("/products/{id}")
    public ApiResponse<Product> del(@PathVariable("id") Long id) {

        ApiResponse<Product> response = null;
        Optional<Product> findProduct = productSVC.findByProductId(id);
        if(findProduct.isEmpty()) {
            ApiResponse.Header header = new ApiResponse.Header("10","상품을 찾을 수 없습니다.");
            response = new ApiResponse<>(header,null);

            return response;
        }else {
            productSVC.deleteByProductId(id);

            ApiResponse.Header header = new ApiResponse.Header("00","삭제성공");
            response = new ApiResponse<>(header,null);

            return response;
        }
    }

    //목록
    @ResponseBody
    @GetMapping("/products")
    public ApiResponse<List<Product>> findAll(){

        List<Product> products = productSVC.findAll();

        ApiResponse.Header header = new ApiResponse.Header("00","목록불러오기 성공");
        ApiResponse<List<Product>> response =new ApiResponse<>(header,products);

        return response;

    }


}
