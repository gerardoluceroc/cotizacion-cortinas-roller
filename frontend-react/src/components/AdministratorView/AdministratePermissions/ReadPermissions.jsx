import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { Container } from "@mui/material";
import { RUTA_GET_PERMISSIONS, URL_CIVILO } from '../../../api/civilo_roller_api';


const ListContainer = styled.ul`
  list-style-type: none;
  display: flex;
  flex-wrap: wrap;
`;

const ListItem = styled.li`
  width: 280px;
  height: 50px;
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


const ReadPermissions = () => {
  const [permissions, setPermissions] = useState([]);

  useEffect(() => {
    fetch(`${URL_CIVILO}${RUTA_GET_PERMISSIONS}`)
      .then((response) => response.json())
      .then((data) => setPermissions(data));
  }, []);

  return (
    <ListContainer>
      <Container>
        <Title>Todos los permisos</Title>
      </Container>
      {permissions.map(permission => (
        <ListItem key={permission.permissionID} color={`#${Math.floor(Math.random() * 16777215).toString(16)}`}>
          <div>Identificador de permiso: {permission.permissionID}</div>
          <div>Permiso: {permission.permission}</div>
        </ListItem>
      ))}
    </ListContainer>
  );
}

export default ReadPermissions;