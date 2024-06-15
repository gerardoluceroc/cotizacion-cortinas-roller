import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { Container } from "@mui/material";


const ListContainer = styled.ul`
  list-style-type: none;
  display: flex;
  flex-wrap: wrap;
`;

const ListItem = styled.li`
  width: 280px;
  height: 200px;
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


const ReadUsers = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/users")
      .then((response) => response.json())
      .then((data) => setUsers(data));
  }, []);

  return (
    <ListContainer>
      <Container>
        <Title>Todos los usuarios</Title>
      </Container>
      {users.map(user => (
        <ListItem key={user.curtainID} color={`#${Math.floor(Math.random() * 16777215).toString(16)}`}>
          <div>Identificador de usuario: {user.userID}</div>
          <div>Nombre: {user.name}</div>
          <div>Nombre: {user.surname}</div>
          <div>Nombre: {user.email}</div>
          <div>Nombre: {user.password}</div>
          <div>Nombre: {user.phoneNumber}</div>
          <div>Nombre: {user.commune}</div>
          <div>Nombre: {user.birthDate}</div>
          <div>Nombre: {user.age}</div>
        </ListItem>
      ))}
    </ListContainer>
  );
}

export default ReadUsers;
