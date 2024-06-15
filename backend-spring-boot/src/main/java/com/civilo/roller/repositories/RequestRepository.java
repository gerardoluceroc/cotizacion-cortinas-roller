package com.civilo.roller.repositories;

import com.civilo.roller.Entities.RequestEntity;

import java.util.ArrayList;
import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<RequestEntity, Long> {

    //Se consulta a la base de datos por el sueldo fijo mensual de un empleado de acuerdo a su id
    //@Query(value = "select c.sueldo_fijo_mensual from categorias_empleados c, empleados e where e.id_categoria = c.id_categoria and e.id_empleado = :id", nativeQuery = true)
    //int consultarSueldoFijoMensual(@Param("id") Long id);

    //Se consulta por las solicitudes hechas por un cliente en especifico
    @Query(value = "SELECT r FROM RequestEntity r JOIN FETCH r.user WHERE r.user.id = :id_cliente")
    ArrayList<RequestEntity> findRequestByUserId(@Param("id_cliente") Long id_cliente);
    //La siguiente consulta no me funcionó, la dejo porsiacaso nomas.
    //@Query(value = "select req from requests req where req.users = :id_cliente", nativeQuery = true)
    //ArrayList<RequestEntity> findRequestByUserId(@Param("id_cliente") Long id_cliente);
}
