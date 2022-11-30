package com.example.firebasemascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainAgregarMascota extends AppCompatActivity {

    Button btn_ingresar;
    EditText nombre, edad, color;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_agregar_mascota);

        this.setTitle("Crear mascota");

        mFirestore = FirebaseFirestore.getInstance();

        nombre = findViewById(R.id.edit_nombre);
        edad = findViewById(R.id.edit_edad);
        color = findViewById(R.id.edit_color);
        btn_ingresar = findViewById(R.id.btn_ingresar);

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombremascota = nombre.getText().toString().trim();
                String edadmascota = edad.getText().toString().trim();
                String colormascota = color.getText().toString().trim();

                if (nombremascota.isEmpty() && edadmascota.isEmpty() && colormascota.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ingresa los datos", Toast.LENGTH_SHORT).show();
                }else{
                    postPet(nombremascota, edadmascota, colormascota);
                }
            }
        });
    }

    private void postPet(String nombremascota, String edadmascota, String colormascota) {
        Map<String, Object> data = new HashMap<>();
        data.put("Nombre", nombremascota);
        data.put("Edad", edadmascota);
        data.put("Color", colormascota);

        mFirestore.collection("Mascotas").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Creado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.print(e);
                Toast.makeText(getApplicationContext(),"Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}