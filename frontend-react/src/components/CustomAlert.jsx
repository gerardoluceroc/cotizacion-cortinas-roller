import React, { useState } from 'react';
import styled from 'styled-components';
import CancelIcon from '@mui/icons-material/Cancel';

const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  z-index: 9998;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const NotificationOverlay = styled.div`
  max-width: 90%;
  width: 450px;
  background-color: #e6e5e5;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  z-index: 9999;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
`;

const NotificationText = styled.p`
  margin: 0 0 20px;
`;

const NotificationHeader = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: flex-end;
`;

const CloseButton = styled.button`
  position: absolute;
  color: #1976d2;
  border-color: red;
  border-radius: 10px;
  top: 5px;
  right: 5px;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 25px;
  margin-top: -20px;
  margin-right: -23px;
`;

const Button = styled.button`
  background-color: #107be6;
  border-radius: 5px;
  border-color: #107be6;
  color: white;
  font-size: xx-large;
  margin-top: 10px;
  width: 35%;
  height: 3rem;
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

   @media (max-width: 472px) {
    width: 45%;
    
  }
/*
  @media (max-width: 540px) {
    width: 50%;
    margin: auto;
    
  } */


  //padding: 10px 20px;
`;

const CustomAlert = ({ message }) => {
  const [showNotification, setShowNotification] = useState(true);

  const handleNotificationClose = () => {
    setShowNotification(false);
  };

  if (!showNotification) {
    return null;
  }

  return (
    <Overlay>
      <NotificationOverlay>
        <NotificationHeader>
          <CloseButton onClick={handleNotificationClose}><CancelIcon /></CloseButton>
        </NotificationHeader>
        <NotificationText>{message}</NotificationText>
        <Button onClick={handleNotificationClose}>Aceptar</Button>
      </NotificationOverlay>
    </Overlay>
  );
};

export default CustomAlert;
