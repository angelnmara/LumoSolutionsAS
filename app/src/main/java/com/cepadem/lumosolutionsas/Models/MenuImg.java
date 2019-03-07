package com.cepadem.lumosolutionsas.Models;

public class MenuImg {
    public MenuImg(int id, int imagen){
        Id = id;
        Imagen = imagen;
    }
    public int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }

    public int Imagen;
}
