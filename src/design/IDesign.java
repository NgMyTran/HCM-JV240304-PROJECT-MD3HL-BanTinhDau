package design;

import java.util.List;

public interface IDesign <T,E>{
    List<T> getAll();
    void save(T t);
    T findById(E e);
    void delete(E e);

}
