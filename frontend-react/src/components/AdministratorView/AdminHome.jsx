import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const PAGE_COVERAGES = "/admin/readCoverages";
const PAGE_READ_USERS = "/admin/readUsers";
const PAGE_READ_REQUESTS = "/admin/readRequests";
const PAGE_READ_ROLES = "/admin/readRoles";
const PAGE_READ_STATUS = "/admin/readStatus";
const PAGE_READ_CURTAINS = "/admin/readCurtains";
const PAGE_PROFIT_MARGIN = "/admin/readProfitMargins";
const PAGE_CURTAIN_PIPES = "/admin/readCurtainPipes"
const PAGE_SOLICITUDES_ADMIN = "/admin/requestManagement"
const PAGE_QUOTES = "/admin/quotes";
const PAGE_IVA = "/admin/readIVA"

const CardContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 20px; /* Agrega un espacio entre las tarjetas */
  justify-content: center; /* Centra las tarjetas horizontalmente */
`;

const Header = styled.h1`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: -3%;

  @media (max-width: 456px) {
    font-size: x-large;
    
  }
`;


const Button = styled.a`
  background-color: #0074D9;
  color: #fff;
  padding: 10px;
  border-radius: 5px;
  text-align: center;
  text-decoration: none;
  cursor: pointer;
  &:hover {
    background-color: #004a8e;
  }
`;

const InformationItem = styled.div`
  background-color: #1976d2;
  border-radius: 3%;
  border-color: red;
  width: 30%;
  margin: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  //align-items: center;

  @media (max-width: 920px){
    width: 50%;
  }

  @media (max-width: 582px){
    width: 70%;
  }
`;

const Titulo = styled.h2`
  font-size: 1.5rem;
  margin: auto;
  margin-top: 2%;
  color: ivory;

  @media (max-width: 370px){
    font-size: larger;
  }
`;

const Boton = styled.button` 
  background-color: ivory;
  margin: auto;
  margin-top: 5%;
  margin-bottom: 5%;
  border-radius: 5px;
  border-color: black;
  color: black;
  font-size: large;
  width: 60%;
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

/* 
  @media (max-width: 540px) {
    width: 50%;
    margin: auto;
    
  } */

`;

const AdminHome = () => {
  return (
    <>
      <Header>Información de Plataforma</Header>
      <p style={{ textAlign: 'center' }}>Acceda y administre los distintos aspectos que existen en la plataforma</p>
      <CardContainer>

        <InformationItem>
          <Titulo>Usuarios Registrados</Titulo>
          <Link to={PAGE_READ_USERS} style={{ textDecoration: 'none' }}>
            <Boton>Ver Detalles</Boton>
          </Link>
        </InformationItem>

        <InformationItem>
          <Titulo>Roles Disponibles</Titulo>
          <Link to={PAGE_READ_ROLES} style={{ textDecoration: 'none' }}>
            <Boton>Ver Detalles</Boton>
          </Link>
        </InformationItem>

        <InformationItem>
          <Titulo>Cortinas Registradas</Titulo>
          <Link to={PAGE_READ_CURTAINS} style={{ textDecoration: 'none' }}>
            <Boton>Ver Detalles</Boton>
          </Link>
        </InformationItem>

        <InformationItem>
          <Titulo>Comunas de Cobertura</Titulo>
          <Link to={PAGE_COVERAGES} style={{ textDecoration: 'none' }}>
            <Boton>Ver Detalles</Boton>
          </Link>
        </InformationItem>

        <InformationItem>
          <Titulo>Estados Posibles de Solicitudes</Titulo>
          <Link to={PAGE_READ_STATUS} style={{ textDecoration: 'none' }}>
            <Boton>Ver Detalles</Boton>
          </Link>
        </InformationItem>

        <InformationItem>
          <Titulo>Ver Solicitudes de Cotización</Titulo>
          <Link to={PAGE_READ_REQUESTS} style={{ textDecoration: 'none' }}>
            <Boton>Ver Detalles</Boton>
          </Link>
        </InformationItem>

        <InformationItem>
          <Titulo>Cotizaciones Realizadas</Titulo>
          <Link to={PAGE_QUOTES} style={{ textDecoration: 'none' }}>
            <Boton>Ver Detalles</Boton>
          </Link>
        </InformationItem>

        <InformationItem>
          <Titulo>Gestionar Solicitudes de Cotización</Titulo>
          <Link to={PAGE_SOLICITUDES_ADMIN} style={{ textDecoration: 'none' }}>
            <Boton>Ver Detalles</Boton>
          </Link>
        </InformationItem>

        <InformationItem>
          <Titulo>Margen de Utilidad</Titulo>
          <Link to={PAGE_PROFIT_MARGIN} style={{ textDecoration: 'none' }}>
            <Boton>Ver Detalles</Boton>
          </Link>
        </InformationItem>

        <InformationItem>
          <Titulo>Tubos de cortinas</Titulo>
          <Link to={PAGE_CURTAIN_PIPES} style={{ textDecoration: 'none' }}>
            <Boton>Ver Detalles</Boton>
          </Link>
        </InformationItem>

        <InformationItem>
          <Titulo>IVA</Titulo>
          <Link to={PAGE_IVA} style={{ textDecoration: 'none' }}>
            <Boton>Ver Detalles</Boton>
          </Link>
        </InformationItem>

      </CardContainer>
    </>
  )
};

export default AdminHome;