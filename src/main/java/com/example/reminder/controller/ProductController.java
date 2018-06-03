package com.example.reminder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.reminder.domain.Product;
import com.example.reminder.services.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping("product/new")
	public String newProduct(Model model) {
		model.addAttribute("product", new Product());
		return "productform";
	}

	@RequestMapping(value="product", method=RequestMethod.POST)
	public String saveProduct(Product product) {
		productService.saveProduct(product);
		return "redirect:/product/" + product.getId();
	}

	@RequestMapping("product/{id}")
	public String showProduct(@PathVariable Integer id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return "productshow";
	}
	
	@RequestMapping(value="/products", method=RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("products",productService.listAllProducts());
		return "products";
	}
	
	@RequestMapping("product/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return "productform";
	}
	
	@RequestMapping(value="product/delete/{id}")
	public String delete(@PathVariable Integer id) {
		productService.deleteProduct(id);
		return "redirect:/products";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "login";
	}
}
