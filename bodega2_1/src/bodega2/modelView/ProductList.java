/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bodega2.modelView;
import bodega2.model.FirebaseSaveObject;
import bodega2.modelView.Producto;
import java.io.FileNotFoundException;
import java.util.*;
import static java.util.Collections.list;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Collections;
/**
 *
 * @author luren
 */
public class ProductList {
    
    LinkedHashMap<String, Producto> sortedMap = new LinkedHashMap<>();
    HashMap<String, Producto> mapaProductos = new HashMap<>();
    List<Producto> listaProductos = new ArrayList<>();
    List<String> listaEnteros = new ArrayList<>();
    List<String> listaStrings = new ArrayList<>();
    FirebaseSaveObject firebaseSave = new FirebaseSaveObject();
    //HashMap<String, Producto> mapaProductos = firebaseSave.recoverMap();
    
    
    public void crearLista(){
        
    }
    
    public void añadirProducto(String x, int y, int z) throws FileNotFoundException{
        Producto producto = new Producto(x,y,z);
        mapaProductos.put(x,producto);
        firebaseSave.saveMap(mapaProductos);
    }
    
    public void añadirProducto(Producto producto) throws FileNotFoundException{
        mapaProductos.put(producto.getNombre(),producto);
        firebaseSave.saveMap(mapaProductos);
    }
    
    public String toString(){
        return listaProductos.toString();
    }
    
    public List<Producto> getList(){
        return listaProductos;
    }
    
    public HashMap<String,Producto> getMap(){
        return mapaProductos;
    }
    
    public HashMap<String,Producto> ordenarNombre(){
        sortedMap.clear();
        listaStrings.removeAll(listaStrings);
        for (Map.Entry<String, Producto> entry : mapaProductos.entrySet()) {
            listaStrings.add(entry.getKey());
        }
        Collections.sort(listaStrings, new Comparator<String>() {
            public int compare(String str, String str1) {
                return (str).compareTo(str1);
            }
        });
        for (String str : listaStrings) {
            for (Entry<String, Producto> entry : mapaProductos.entrySet()) {
                if (entry.getKey().equals(str)) {
                    sortedMap.put(str, entry.getValue());
                }
            }
        }
        System.out.println(sortedMap);
        return sortedMap;
    }
    
    public HashMap<String,Producto> ordenarPrecio(){
        sortedMap.clear();
        listaProductos.removeAll(listaProductos);
        listaEnteros.removeAll(listaEnteros);
        for (Map.Entry<String, Producto> entry : mapaProductos.entrySet()) {
            listaProductos.add(entry.getValue());
            listaEnteros.add(Integer.toString(entry.getValue().getPrecio()));
        }
        Collections.sort(listaEnteros, new Comparator<String>() {
            public int compare(String str, String str1) {
                return (str).compareTo(str1);
            }
        });
        for (String str : listaEnteros) {
            Integer x = Integer.valueOf(str);
            for (Entry<String, Producto> entry : mapaProductos.entrySet()) {
                if (entry.getValue().getPrecio()== x) {
                    sortedMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        System.out.println(sortedMap);
        return sortedMap;
    }
    
    public HashMap<String,Producto> ordenarCantidad(){
        sortedMap.clear();
        listaProductos.removeAll(listaProductos);
        listaEnteros.removeAll(listaEnteros);
        for (Map.Entry<String, Producto> entry : mapaProductos.entrySet()) {
            listaProductos.add(entry.getValue());
            listaEnteros.add(Integer.toString(entry.getValue().getCantidad()));
        }
        Collections.sort(listaEnteros, new Comparator<String>() {
            public int compare(String str, String str1) {
                return (str).compareTo(str1);
            }
        });
        for (String str : listaEnteros) {
            Integer x = Integer.valueOf(str);
            for (Entry<String, Producto> entry : mapaProductos.entrySet()) {
                if (entry.getValue().getCantidad()== x) {
                    sortedMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        System.out.println(sortedMap);
        return sortedMap;
    }
}



