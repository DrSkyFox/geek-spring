package ru.geek.lesson4springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geek.lesson4springboot.service.ProductRepr;
import ru.geek.lesson4springboot.service.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listPage(Model model,
                           @RequestParam("productFilter") Optional<String> productFilter,
                           @RequestParam("costMinFilter") Optional<Double> costMinFilter,
                           @RequestParam("costMaxFilter") Optional<Double> costMaxFilter) {
        logger.info(String.format("List page requested. Paramets of filter: productFilter = %s, \n costMinFilter =%s, \n costMaxFilter = %s",
                productFilter, costMinFilter, costMaxFilter));
        List<ProductRepr> product = productService.findWithFilter(
                productFilter.filter(s -> !s.isBlank()).orElse(null),
                costMinFilter.orElse(null),
                costMaxFilter.orElse(null)
        );

        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        logger.info("Edit page for id {} requested", id);

        model.addAttribute("product", productService.findById(id).orElseThrow(NotFoundException::new));
        return "product_form";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("product") ProductRepr product, BindingResult result) {
        logger.info("Update endpoint requested");
        if(result.hasErrors()) {
            return "product_form";
        }

        logger.info("Updating product with id {}", product.getId());
        productService.save(product);

        return "redirect:/product";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("product", new ProductRepr());
        return "product_form";
    }

    @GetMapping("/{id}/delete")
    public String remove(@PathVariable("id") Long id) {
        productService.delete(id);
        return "redirect:/product";
    }


}
