import * as React from 'react';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import { URL_CIVILO, asignarVendedor, obtenerVendedor } from '../api/civilo_roller_api';
import { useEffect } from 'react';
import { useState } from 'react';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { showAlert } from '../functions/funciones';

export default function SelectSellerUI({ tipoSelect ,listado, IdSolicitud, solicitudes, vendedorAsignado: sellerID}) {

  const [vendedorSeleccionado, setVendedorSeleccionado] = useState();
  const [requests, setRequests] = useState(solicitudes);
  const [open, setOpen] = useState(false);

  
  //Funcion que se activa a seleccionar una opcion en el select
  //En este caso, cuando se asigna un vendedor a una solicitud 
  const handleSellerChange = async (event) => {

    const opcionSeleccionada = event.target.value;

    // Buscar el vendedor seleccionado en el listado de todos los vendedores
    const vendedorSeleccionado = listado.find((item) => `${item.name} ${item.surname}` === opcionSeleccionada);
    
    //si se encuentra el vendedor
    if (vendedorSeleccionado) {
      try {
        const mensaje = await asignarVendedor(IdSolicitud, vendedorSeleccionado.userID);
        setVendedorSeleccionado(opcionSeleccionada); // Cambiar el valor del select
        showAlert(mensaje);
      } catch (error) {
        showAlert(error.message);
      }
      } 
    
    else {
      showAlert("Error: Vendedor no encontrado");
    }


  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleOpen = () => {
    setOpen(true);
  };


    return (
        <div>
          <FormControl sx={{ m: 1, minWidth: 120}}>
          <InputLabel id="demo-simple-select-label" sx={{ marginTop: '-5px' }}>Asignar</InputLabel>
            <Select
              labelId="demo-controlled-open-select-label"
              id="demo-controlled-open-select"
              open={open}
              onClose={handleClose}
              onOpen={handleOpen}
              value={vendedorSeleccionado}
              onChange={handleSellerChange}
            >
              {listado.map((item) => (
                <MenuItem key={item.userID} value={`${item.name} ${item.surname}`}>
                  {`${item.name} ${item.surname}`}
                  {item.userID === sellerID ? <CheckCircleIcon sx={{ marginLeft: '8px' }} /> : null}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </div>
      );

}
