import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { CurtainsTable } from '../AdministrateCurtains/CurtainsTable';
import { CoveragesTable } from './CoveragesTable';






const Titulo = styled.h1`

  font-size: xx-large;
  display: flex;
  justify-content: center;

`;

const Container = styled.div`


    background-color: transparent;
    min-height: 700px;

`;


const CoveragesList = () => {


  return (
    <Container>
        <Titulo>Comunas de Cobertura</Titulo>
        <CoveragesTable/>
    </Container>
    
  );
}

export default CoveragesList;