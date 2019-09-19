package com.budget.spendings.controller;

import com.budget.spendings.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.budget.spendings.domain.Product;

@Controller
public class ProductController {

	private static final String PRODUCT = "product";

	@Autowired
	private ProductService productService;

	@GetMapping("product/new")
	public String newProduct(Model model) {
		model.addAttribute(PRODUCT, new Product());
		return "productform";
	}

	@PostMapping("product")
	public String saveProduct(Product product) {
		productService.save(product);
		return "redirect:/product/" + product.getId();
	}

	@GetMapping("product/{id}")
	public String showProduct(@PathVariable Integer id, Model model) {
		model.addAttribute(PRODUCT, productService.getById(id));
		return "productshow";
	}
	
	@GetMapping("/products")
	public String list(Model model) {
		model.addAttribute("products",productService.listAll());
		return "products";
	}
	
	@GetMapping("product/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute(PRODUCT, productService.getById(id));
		return "productform";
	}
	
	@GetMapping("product/delete/{id}")
	public String delete(@PathVariable Integer id) {
		productService.delete(id);
		return "redirect:/products";
	}

}
