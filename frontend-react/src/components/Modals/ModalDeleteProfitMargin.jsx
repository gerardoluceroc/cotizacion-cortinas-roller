import React, { useState } from "react";
import styled from "styled-components";
import DisabledByDefaultIcon from '@mui/icons-material/DisabledByDefault';
import { deleteProfitMargin } from "../../api/civilo_roller_api";


// Creamos un overlay para oscurecer el fondo detrás del Modal
const Overlay = styled.div`
  background-color: rgba(0, 0, 0, 0.7);
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

// Creamos el contenedor del Modal en sí
const ModalContainer = styled.div`
  background-color: #e6e5e5;
  border-radius: 5px;
  padding: 20px;
  position: relative;
  width: 50%;
  height: 70%;
  overflow-y: auto; /* añade esta línea para permitir el desplazamiento vertical */


  @media (max-width: 760px) {
    width: 80%;
    
  }

  @media (min-height: 934px) {
    height: 50%;
    
  }

  @media (max-height: 668px) {
    height: 90%;
    
  }

`;

// Creamos un botón para cerrar el Modal
const CloseButton = styled.button`
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 1.5rem;
  background-color: transparent;
  border: none;
  cursor: pointer;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: auto;
  text-align: center;
`;

const Opciones = styled.div`
  display: flex;
  justify-content: space-evenly;
`;

const Boton = styled.button`
  background-color: #1010b3;
  border-radius: 5px;
  border-color: #1010b3;
  color: white;
  font-size: xx-large;
  margin-left: 1.5%;
  width: 15%;
  height: 2.3rem;
  display: flex;
  justify-content: center;
  align-items: center;
  //Animación para cuando el cursor pase por encima del botón.
  &:hover {
      box-shadow: 0px 0px 5px 2px rgba(0,0,0,0.25); /* Agrega una sombra */
      transform: scale(0.95); /* Reduzca ligeramente el tamaño */
    }

  /* sombra del botón */
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.3s ease-in-out;
  
  /* estilo cuando se presiona el botón */
  &:active {
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.4);
  }
  
  & > * {
    margin-left: 4%;
  }

  @media (max-width: 950px) {
    width: 30%;
    
  }

  @media (max-width: 540px) {
    width: 50%;

    
  }
`;








// Creamos el componente Modal que acepta una prop "open" que indica si debe mostrarse o no
const ModalDeleteProfitMargin = ({ open, onClose, porcentajeMargen, decimalMargen, margenID}) => {

  return (
    // Usamos un ternario para determinar si el Modal debe mostrarse o no
    open ? (
      <Overlay>
        <ModalContainer>
          <CloseButton onClick={onClose}> <DisabledByDefaultIcon style={{ fontSize: 40 }}/> </CloseButton>
          <Container>
            <h1>Eliminar Margen de Utilidad</h1>
            <h3>{`Margen de utilidad (porcentaje): ${porcentajeMargen}%`}</h3>
            <h3>{`Margen de utilidad (décimal)   : ${decimalMargen}`}</h3>
            <p>¿Estas seguro que deseas eliminar este margen de utilidad? Se eliminará toda su información de manera permanente</p>
            <Opciones>
              <Boton onClick={() => {deleteProfitMargin(margenID)}} style={{background:"red", borderColor:"red"}}>Eliminar</Boton>
              <Boton onClick={onClose}>Cancelar</Boton>
            </Opciones>
          </Container>
        </ModalContainer>
      </Overlay>
    ) : null
  );
};

export default ModalDeleteProfitMargin;