package com.mproduits.web.controller;

import com.mproduits.configurations.ApplicationPropertiesConfiguration;
import com.mproduits.dao.ProductDao;
import com.mproduits.model.Product;
import com.mproduits.web.exceptions.ImpossibleAjouterProductException;
import com.mproduits.web.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ProductController {

    @Autowired
    ProductDao productDao;

   /* // Affiche la liste de tous les produits disponibles
    @GetMapping(value = "/Produits")
    public List<Product> listeDesProduits(){

        List<Product> products = productDao.findAll();

        if(products.isEmpty()) throw new ProductNotFoundException("Aucun produit n'est disponible à la vente");

        return products;

    }*/
   @Autowired
   ApplicationPropertiesConfiguration appProperties;

    // Affiche la liste de tous les produits disponibles
    @GetMapping(value = "/Produits")
    public List<Product> listeDesProduits(){

        List<Product> products = productDao.findAll();

        if(products.isEmpty()) throw new ProductNotFoundException("Aucun produit n'est disponible à la vente");

        List<Product> listeLimitee = products.subList(0, appProperties.getLimitDeProduits());

        return listeLimitee;

    }

    //Récuperer un produit par son id
    @GetMapping( value = "/Produits/{id}")
    public Optional<Product> recupererUnProduit(@PathVariable int id) {

        Optional<Product> product = productDao.findById(id);

        if(!product.isPresent())  throw new ProductNotFoundException("Le produit correspondant à l'id " + id + " n'existe pas");

        return product;
    }
    @PostMapping(value = "/Produits")
    public ResponseEntity<Product> ajouterCommande(@RequestBody Product product){

        Product nouvelproduit = productDao.save(product);

        if(nouvelproduit == null) throw new ImpossibleAjouterProductException("Impossible d'ajouter ce Produit");

        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }
    private static final String LOCAL_SERVER_PORT = "local.server.port";
    @Autowired
    private Environment environment;

    @RequestMapping(method = GET)
    public ResponseEntity getProduct(){
        return ResponseEntity.ok("Product Controller, Port:  " + environment.getProperty(LOCAL_SERVER_PORT));
    }

}

