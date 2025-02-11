package bodega2.model;

import bodega2.modelView.Producto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class FirebaseSaveObject {

    

    // save item objec to firebase.
    //new FirebaseSaveObject().save(item);
    //new FirebaseSaveObject().delete();
    //new FirebaseSaveObject().recover();
        
    

    private FirebaseDatabase firebaseDatabase;

    /**
         * inicialización de firebase.
*/
    private void initFirebase() {
        
        try {
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()   
                    .setDatabaseUrl("https://prueba-9d905-default-rtdb.firebaseio.com/")
                    //.setServiceAccount(new FileInputStream(new File("C:\\Users\\Lenovo\\Documents\\pc\\NetBeansProjects\\firebase\\prueba-esp-a7c2a-firebase-adminsdk-yd7qe-1bdb100458.json")))
                    .setServiceAccount(new FileInputStream(new File("C:\\Users\\luren\\OneDrive\\Documentos\\NetBeansProjects\\Firebase\\prueba-9d905-firebase-adminsdk-fbsvc-fddb6bab61.json")))

                   // .setDatabaseUrl("https://prueba-esp-a7c2a.firebaseio.com")
                    //.setServiceAccount(new FileInputStream(new File("C:\\Users\\Lenovo\\Documents\\pc\\NetBeansProjects\\firebase\\prueba-esp-a7c2a-firebase-adminsdk-yd7qe-1bdb100458.json")))
                    .build();

            FirebaseApp.initializeApp(firebaseOptions);
            firebaseDatabase = FirebaseDatabase.getInstance();
            System.out.println("Conexión exitosa....");
        }catch (RuntimeException ex) {
            System.out.println("Problema ejecucion ");
        }catch (FileNotFoundException ex) {
            System.out.println("Problema archivo");
        }

         
    }

    /**
     * Save item object in Firebase.
     * @param item 
     */
    
    
    public void saveMap(HashMap<String, Producto> map) throws FileNotFoundException {
        if (map != null) {
            initFirebase();
            
            /* Get database root reference */
            DatabaseReference databaseReference = firebaseDatabase.getReference("/");
            
            /* Get existing child or will be created new child. */
            DatabaseReference childReference = databaseReference.child("lista");

            /**
             * The Firebase Java client uses daemon threads, meaning it will not prevent a process from exiting.
             * So we'll wait(countDownLatch.await()) until firebase saves record. Then decrement `countDownLatch` value
             * using `countDownLatch.countDown()` and application will continues its execution.
             */
            CountDownLatch countDownLatch = new CountDownLatch(1);
            childReference.setValue(map, new DatabaseReference.CompletionListener() {

                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    System.out.println("Registro guardado!");
                    // decrement countDownLatch value and application will be continues its execution.
                    countDownLatch.countDown();
                }
            });
            try {
                //wait for firebase to saves record.
                countDownLatch.await();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Método para leer un Item por nombre
    
    
    private void deleteByName(String name) {
        // Inicializamos la referencia de la base de datos
        DatabaseReference databaseReference = firebaseDatabase.getReference("/");

        // Consultamos por el "name" del item
        databaseReference.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Si existe un item con ese nombre, lo borramos
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().removeValue().addOnSuccessListener(aVoid -> {
                            System.out.println("Item con nombre '" + name + "' ha sido eliminado.");
                        }).addOnFailureListener(e -> {
                            System.out.println("Error al eliminar el item: " + e.getMessage());
                        });
                    }
                } else {
                    System.out.println("No se encontró un item con el nombre '" + name + "' para eliminar.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error al leer los datos: " + databaseError.getMessage());
            }
        });
    }
    
    
    
    private void read(String name) {
    // Inicializamos Firebase
    initFirebase();

    // Referencia a la base de datos en la ubicación de los items
    DatabaseReference databaseReference = firebaseDatabase.getReference("/");

    // Leemos todos los objetos "item"
    databaseReference.addListenerForSingleValueEvent (new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            boolean itemFound = false; // Variable para verificar si encontramos el item

            // Iteramos sobre todos los elementos en la base de datos
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                // Convertimos cada snapshot en un objeto Item
                Item item = snapshot.getValue(Item.class);
                
                // Verificamos si el nombre del item coincide con el parámetro de entrada
                if (item != null && item.getName().equals(name)) {
                    // Si encontramos el item, mostramos sus atributos
                    System.out.println("Item encontrado: ");
                    System.out.println("ID: " + item.getId());
                    System.out.println("Nombre: " + item.getName());
                    System.out.println("Precio: " + item.getPrice());
                    itemFound = true;
                    break; // Salimos del bucle si encontramos el item
                }
            }

            // Si no encontramos ningún item con ese nombre
            if (!itemFound) {
                System.out.println("Error: No se encontró un item con el nombre '" + name + "'.");
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Si ocurre un error al leer los datos
            System.out.println("Error al leer los datos: " + databaseError.getMessage());
        }
    });
}

    private void delete(String x) {
        
            initFirebase();

            /* Get database root reference */
            DatabaseReference databaseReference = firebaseDatabase.getReference("/");

            /* Get existing child or will be created new child. */
            DatabaseReference childReference = databaseReference.child("lista");

            /**
             * The Firebase Java client uses daemon threads, meaning it will not
             * prevent a process from exiting. So we'll
             * wait(countDownLatch.await()) until firebase saves record. Then
             * decrement `countDownLatch` value using
             * `countDownLatch.countDown()` and application will continues its
             * execution.
             */
            CountDownLatch countDownLatch = new CountDownLatch(1);
            childReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        //HashMap<String, Object> itemMap = (HashMap<String, Object>) dataSnapshot.getValue();
                        //Item item = dataSnapshot.getValue(Item.class);
                        if (dataSnapshot.exists()) {
                            // Si existe un item con ese nombre, lo borramos
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                snapshot.getRef().removeValue().addOnSuccessListener(aVoid -> {
                                    System.out.println("Item ha sido eliminado.");
                                }).addOnFailureListener(e -> {
                                    System.out.println("Error al eliminar el item: " + e.getMessage());
                                });
                            }
                        } else {
                            System.out.println("No se encontró un item para eliminar.");
                        }
                        //String value = dataSnapshot.getValue(String.class);
                        //String id = itemMap.get("id").toString();
                        //String name = itemMap.get("name").toString();
                        //String price = itemMap.get("price").toString();
                        //System.out.println("valor: "+ value.getId());
                        //System.out.println("valor: "+ value);
                        //System.out.println("ID: "+ id);
                        //System.out.println("Nombre: "+ name);
                        //System.out.println("Precio: "+ price);
                        //System.out.println("ID: " + item.getId());
                        //System.out.println("Nombre: " + item.getName());
                        //System.out.println("Precio: " + item.getPrice());
                        //System.out.println("valor: "+ value.getPrice());
                        //System.out.println("valor: "+ value.getName());
                        countDownLatch.countDown();

                        //Log.d(TAG, "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                       // Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
        try {
            //wait for firebase to saves record.
            countDownLatch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        
            }
    
    private void recover() {
        
            initFirebase();

            /* Get database root reference */
            DatabaseReference databaseReference = firebaseDatabase.getReference("/");

            /* Get existing child or will be created new child. */
            DatabaseReference childReference = databaseReference.child("item5");

            /**
             * The Firebase Java client uses daemon threads, meaning it will not
             * prevent a process from exiting. So we'll
             * wait(countDownLatch.await()) until firebase saves record. Then
             * decrement `countDownLatch` value using
             * `countDownLatch.countDown()` and application will continues its
             * execution.
             */
            CountDownLatch countDownLatch = new CountDownLatch(1);
            childReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        //HashMap<String, Object> itemMap = (HashMap<String, Object>) dataSnapshot.getValue();
                        Item item = dataSnapshot.getValue(Item.class);
                        //String value = dataSnapshot.getValue(String.class);
                        //String id = itemMap.get("id").toString();
                        //String name = itemMap.get("name").toString();
                        //String price = itemMap.get("price").toString();
                        //System.out.println("valor: "+ value.getId());
                        //System.out.println("valor: "+ value);
                        //System.out.println("ID: "+ id);
                        //System.out.println("Nombre: "+ name);
                        //System.out.println("Precio: "+ price);
                        System.out.println("ID: " + item.getId());
                        System.out.println("Nombre: " + item.getName());
                        System.out.println("Precio: " + item.getPrice());
                        //System.out.println("valor: "+ value.getPrice());
                        //System.out.println("valor: "+ value.getName());
                        countDownLatch.countDown();

                        //Log.d(TAG, "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                       // Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
        try {
            //wait for firebase to saves record.
            countDownLatch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        
            }
}


//Realizar la recuperacion de informacion
//perfeccionar el metodo en que se realiza una operacion, suprimir el conteo regresivo