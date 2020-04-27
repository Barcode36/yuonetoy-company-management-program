package com.yuonetoy.Service;

import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductDTO;
import com.yuonetoy.DTO.Product.ProductDetailDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.DTO.ProductLinkedMachineDTO;
import com.yuonetoy.Entitiy.Account.PurchaseAccount;
import com.yuonetoy.Entitiy.Machine;
import com.yuonetoy.Entitiy.Product;
import com.yuonetoy.Entitiy.ProductLinkedMachine;
import com.yuonetoy.Repository.ProductLinkedMachineRepository;
import com.yuonetoy.Repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private PurchaseAccountService purchaseAccountService;
    @Autowired
    private MachineService machineService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductLinkedMachineRepository productLinkedMachineRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ProductDetailDTO addProductDetailDTO(String name, int price, boolean isCapsuleToy, PurchaseAccountDTO purchaseAccountDTO){
        PurchaseAccount purchaseAccount = modelMapper.map(purchaseAccountDTO, PurchaseAccount.class);

        Product product = productRepository.findByNameEquals(name);

        if (product == null)
            product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setIsCapsuleToy(isCapsuleToy);
        product.setPurchaseAccount(purchaseAccount);
        productRepository.save(product);

        ProductDetailDTO productDetailDTO = modelMapper.map(product, ProductDetailDTO.class);
        productDetailDTO.setCapsuleToy(isCapsuleToy);
        return productDetailDTO;
    }

    public void deleteProduct(ProductDetailDTO productDetailDTO){
        Product product = modelMapper.map(productDetailDTO, Product.class);
        productRepository.delete(product);
    }

    public List<PurchaseAccountDTO> getPurchaseAccount() {
        return purchaseAccountService.readPurchaseAccount();
    }

    public List<MachineDTO> getMachine() {
        return machineService.readMachine();
    }

    @Transactional(readOnly = true)
    public List<ProductDetailDTO> getProductDetailDTO() {
        List<Product> products = productRepository.findAll();
        List<ProductDetailDTO> productDetailDTOList = new ArrayList<ProductDetailDTO>();

        products.forEach(product -> {
            ProductDetailDTO productDetailDTO = modelMapper.map(product, ProductDetailDTO.class);
            productDetailDTOList.add(productDetailDTO);
        });
        return productDetailDTOList;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductDTO() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<ProductDTO>();

        products.forEach(product -> {
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            productDTOS.add(productDTO);
        });
        return productDTOS;
    }
}
