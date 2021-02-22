package ru.geek.lesson4springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
                           @RequestParam("costMaxFilter") Optional<Double> costMaxFilter,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sortField") Optional<String> sortField) {
        logger.info("List page requested. Parameters of filter: productFilter {}, costMinFilter {}, costMaxFilter {}, page {}, size {}, sortField {}", productFilter, costMinFilter,costMaxFilter, page,size,sortField);

        Page<ProductRepr> product = productService.findWithFilter(
                productFilter.filter(s -> !s.isBlank()).orElse(null),
                costMinFilter.orElse(null),
                costMaxFilter.orElse(null),
                page.orElse(1) - 1,
                size.orElse(3),
                sortField.orElse("id"));
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

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView mav = new ModelAndView("not_found");
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }


}
