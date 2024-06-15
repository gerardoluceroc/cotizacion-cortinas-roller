import React from 'react'
import styled from 'styled-components'
import { UsersTable } from '../../UsersTable';



const Titulo = styled.h1`

  font-size: xx-large;
  display: flex;
  justify-content: center;

`;

const TipoUsuario = styled.h2`
  font-size: x-large;
  display: flex;
  margin-left: 3%;
  margin-top: 3%;
  width: 90%;
  margin: auto;
  padding: 1%;

`;



const Container = styled.div`


    background-color: transparent;

`;

export const UsersList = () => {
  return (
    <Container>
        <Titulo>Usuarios Registrados</Titulo>
        <TipoUsuario>Clientes</TipoUsuario>
        <UsersTable tipoUsuario={"cliente"}/>
        <TipoUsuario>Vendedores</TipoUsuario>
        <UsersTable tipoUsuario={"vendedor"}/>
        <TipoUsuario>Ejecutivos</TipoUsuario>
        <UsersTable tipoUsuario={"ejecutivo"}/>
        <TipoUsuario>Administradores</TipoUsuario>
        <UsersTable tipoUsuario={"administrador"}/>
    </Container>
  )
}
