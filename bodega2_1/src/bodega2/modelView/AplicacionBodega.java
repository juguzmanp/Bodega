/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bodega2.modelView;

import bodega2.modelView.Controlador;
import bodega2.view.Menu;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luren
 */
public class AplicacionBodega {
    public static void main (String args[]){
        ProductList listaProductos = new ProductList();
        Controlador controlador = new Controlador(listaProductos);
        Menu menu = new Menu();
        menu.setVisible(true);
    }
}

