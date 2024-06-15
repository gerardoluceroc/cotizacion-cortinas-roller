import React from "react";
import styled from "styled-components";
import ClientRequestForm from "../components/ClientView/ClientRequestForm";
import DisabledByDefaultIcon from '@mui/icons-material/DisabledByDefault';

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

  @media (max-width: 760px) {
    width: 80%;
    
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

const FormularioSolicitud = styled.div`
    background-color: #e6e5e5;
    display: flex;
    justify-content: center;
    flex-direction: column;
`;

const CamposFormulario = styled.div`

`;

const Titulo = styled.h1`
    font-size: x-large;
    margin: auto;

`;
 


// Creamos el componente Modal que acepta una prop "open" que indica si debe mostrarse o no
const Modal = ({ open, onClose }) => {
  return (
    // Usamos un ternario para determinar si el Modal debe mostrarse o no
    open ? (
      <Overlay>
        <ModalContainer>
          <CloseButton onClick={onClose}><DisabledByDefaultIcon style={{ fontSize: 40 }}/></CloseButton>
          <FormularioSolicitud>
            <ClientRequestForm/>
          </FormularioSolicitud>
        </ModalContainer>
      </Overlay>
    ) : null
  );
};

export default Modal;
