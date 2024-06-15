import styled from "styled-components"
import { ClientRequestList } from "../components/ClientView/ClientRequestList"
import Footer from "../components/Footer"
import NavbarResponsive from "../components/NavbarResponsive"
import NavigationBar from "../components/Papelera/NavigationBar"
import ClientRequestForm from "../components/ClientView/ClientRequestForm";
import { Button } from "@mui/material"
import AddIcon from '@mui/icons-material/Add';
import Modal from "../components/Modal"
import { useState } from "react"



const MyRequestSection = styled.div`
  display: flex;
  flex-direction: column;
  gap: 30px;
  width: 100%;
  min-height: calc(100vh - 80px);
  margin-bottom: 14px;

`;

const NewRequestItem = styled.div`
  background-color: transparent;

`;

const NewRequestTitle = styled.h1`
  color: black; // Establece el color del texto del título
  font-size: x-large; // Establece el tamaño de fuente del título
  margin-left: 1%; // Establece el margen izquierdo del título
  display: flex; // Establece el modo de visualización en "flex"
  align-items: center; // Establece el alineamiento vertical de los elementos en el centro
  &::after { // Agrega una pseudoclase "::after" al elemento principal "Titulo"
    content: ''; // Agrega un contenido vacío para la pseudoclase "::after"
    flex-grow: 1; // Hace que la línea horizontal se extienda para llenar todo el espacio disponible a la derecha del texto
    height: 2px; // Establece la altura de la línea horizontal en 2 píxeles
    background-color: gray; // Establece el color de fondo de la línea horizontal en gris
    margin-left: 10px;
  }

  @media (max-width: 890px) {
    margin-left: 5%;
    
  }
  @media (max-width: 540px) {
    margin-left: 10%;
    
  }
`;

const NewRequestButton = styled.button` 
  background-color: #1010b3;
  border-radius: 5px;
  border-color: #1010b3;
  color: white;
  font-size: xx-large;
  margin-left: 4%;
  width: 15%;
  height: 3.3rem;
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
    margin: auto;
    
  }

`;

const LogoAdd = styled(AddIcon)`
  width: 100px;
`;


export const ClientRequestPage = () => {


  const [modalOpen, setModalOpen] = useState(false);

  const handleModalClose = () => {
    setModalOpen(false);
  };



  return (
    <>
      <NavbarResponsive/>
      <MyRequestSection>
        <NewRequestItem>

          <NewRequestTitle>Nueva Solicitud De Cotización</NewRequestTitle>
          <NewRequestButton onClick={() => setModalOpen(true)}>
            Crear
            <AddIcon sx={{ fontSize: 40 }}/>
          </NewRequestButton> 

        </NewRequestItem>
        {/*<ClientRequestForm/>*/}
        <ClientRequestList/>
      <Modal open={modalOpen} onClose={handleModalClose} />
      </MyRequestSection>
    
    {/*<ClientRequestList/>*/}
      <Footer/>
    </>



  )
}
