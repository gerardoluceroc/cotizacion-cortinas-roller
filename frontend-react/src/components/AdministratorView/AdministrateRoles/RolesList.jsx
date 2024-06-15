import React, { useState, useEffect } from 'react';
import styled from 'styled-components';

import { getAllRoles } from '../../../api/civilo_roller_api';
import { RolesTable } from './RolesTable';







const Titulo = styled.h1`

  font-size: xx-large;
  display: flex;
  justify-content: center;

`;

const Container = styled.div`


    background-color: transparent;
    min-height: 700px;

`;


const RolesList = () => {


  return (
    <Container>
        <Titulo>Roles Disponibles</Titulo>
        <RolesTable/>
    </Container>
    
  );
}

export default RolesList;
