/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bodega2.modelView;

import bodega2.view.Menu;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author luren
 */
public class Controlador {
    public ProductList lista;
    public Controlador (ProductList lista){
        this.lista = lista;
    }
    public void agregarProducto (Producto producto) throws FileNotFoundException{
        lista.añadirProducto(producto);
    }
    public List<Producto> recuperarLista (){
        return lista.getList();
    }
    public HashMap<String, Producto> recuperarMapa (){
        return lista.getMap();
    }
    public HashMap<String, Producto> ordenarPorNombre(){
        return lista.ordenarNombre();
    }
    public HashMap<String, Producto> ordenarPorPrecio(){
        return lista.ordenarPrecio();
    }
    public HashMap<String, Producto> ordenarPorCantidad(){
        return lista.ordenarCantidad();
    }
}
