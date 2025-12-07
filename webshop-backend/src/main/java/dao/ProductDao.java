package dao;
import model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao <T>{
    Optional<T> findById(long id);
    List<Product>findAll();
    void save (Product product);
    void delete (long id);

}
