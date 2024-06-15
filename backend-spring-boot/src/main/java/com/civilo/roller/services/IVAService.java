package com.civilo.roller.services;

import com.civilo.roller.Entities.IVAEntity;
import com.civilo.roller.repositories.IVARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.civilo.roller.exceptions.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class IVAService {
    @Autowired
    IVARepository ivaRepository;

    // Get all
    // El siguiente método retorna un listado el cual contiene TODA la información asociada al IVA
    public float getIVAPercentage(){
        List<IVAEntity> ivaEntityList = (List<IVAEntity>) ivaRepository.findAll();
        Integer size = ivaEntityList.size();
        float percentage;

        //ESTE IF LO HICE PARA CORREGIR EL PROBLEMA DE QUE SI SIZE ES CERO HAY VIOLACIÓN DE SEGMENTO
        //nose que es lo que hace esta funcion y cual es su objetivo, el arreglo que hice fue momentaneo
        //el que hizo esto que lo vea porfa para que vea que entregue la respuesta que se busca en caso de tener un size de 0
        if(size > 0){
            percentage = ivaEntityList.get(size - 1).getIvaPercentage();
        }
        else{
            percentage = 0;
        }
        return percentage;
    }

    // Get the last
    public IVAEntity getLastIVA(){
        List<IVAEntity> ivaEntityList = (List<IVAEntity>) ivaRepository.findAll();
        Integer size = ivaEntityList.size();
        return ivaEntityList.get(size - 1);
    }

    // Se obtiene iva por porcentaje
    public IVAEntity getIVAByPercentage(float percentage){
        List<IVAEntity> ivaEntities = (List<IVAEntity>) ivaRepository.findAll();
        for (int i = 0; i < ivaEntities.size(); i++){
            if (percentage == ivaEntities.get(i).getIvaPercentage()){
                return ivaEntities.get(i);
            }
        }
        return null;
    }

    //----

    // Get all
    // El siguiente método retorna un listado el cual contiene TODA la información asociada a los iva
    public List<IVAEntity> getIVAs(){
        return (List<IVAEntity>) ivaRepository.findAll();
    }

    // Get by id
    // Permite obtener la informacion de un iva en especifico.
    public Optional<IVAEntity> getIVAById(Long id){
        return ivaRepository.findById(id);
    }

    // Create
    // Permite guardar un objeto del tipo "IVAEntity" en la base de datos.
    public IVAEntity createIVA(IVAEntity iva){   
        return ivaRepository.save(iva);  
    }

    // Update
    // Permite actualizar los datos de un objeto del tipo "IVAEntity" en la base de datos.
    public IVAEntity updateIVA(Long ivaID, IVAEntity iva){
        IVAEntity existingIVA = ivaRepository.findById(ivaID)
            .orElseThrow(() -> new EntityNotFoundException("IVA no encontrado con el ID: " + ivaID));

        existingIVA.setIvaPercentage(iva.getIvaPercentage());
        
        IVAEntity updatedIVA = ivaRepository.save(existingIVA);
        return updatedIVA;
    }

    // Delete all
    // Permite eliminar todos los IVAs del sistema.
    public void deleteIVAs() {
        ivaRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar un iva en especifico del sistema.
    public void deleteIVAById(Long id){
        ivaRepository.deleteById(id);
    }
    
    // Permite verificar si existe un usario en el sistema, segun el id ingresado.
    public boolean existsIVAById(Long id){
        return ivaRepository.findById(id).isPresent();
    }
}
