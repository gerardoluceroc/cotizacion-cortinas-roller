import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { Container } from "@mui/material";
import { RUTA_GET_REQUESTS, URL_CIVILO } from '../../../api/civilo_roller_api';


const ListContainer = styled.ul`
  list-style-type: none;
  display: flex;
  flex-wrap: wrap;
`;

const ListItem = styled.li`
  width: 280px;
  height: 150px;
  margin: 10px;
  padding: 10px;
  border-radius: 5px;
  background-color: ${props => props.color};
`;

const Title = styled.h1`
  font-size: 2rem;
  text-align: center;
  margin-top: 30px;
  margin-left: 0px;
`;


const ReadRequests = () => {
  const [requests, setRequests] = useState([]);

  useEffect(() => {
    fetch(`${URL_CIVILO}${RUTA_GET_REQUESTS}`)
      .then((response) => response.json())
      .then((data) => setRequests(data));
  }, []);

  return (
    <ListContainer>
      <Container>
        <Title>Todas las solicitudes</Title>
      </Container>
      {requests.map(request => (
        <ListItem key={request.requestID} color={`#${Math.floor(Math.random() * 16777215).toString(16)}`}>
          <div>Identificador de solicitud: {request.requestID}</div>
          <div>Descripción: {request.description}</div>
          <div>Fecha de ingreso: {request.admissionDate}</div>
          <div>Fecha limite: {request.deadline}</div>
          <div>Fecha de cierre: {request.closingDate}</div>
          <div>Razón: {request.reason}</div>
          <div>Identificador del vendedor: {request.sellerId}</div>
        </ListItem>
      ))}
    </ListContainer>
  );
}

export default ReadRequests;
