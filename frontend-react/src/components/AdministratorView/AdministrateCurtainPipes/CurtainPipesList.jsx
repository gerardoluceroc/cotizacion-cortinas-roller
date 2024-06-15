import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { CurtainPipesTable } from './CurtainPipesTable';

const Titulo = styled.h1`

  font-size: xx-large;
  display: flex;
  justify-content: center;

`;

const Container = styled.div`


    background-color: transparent;
    min-height: 700px;

`;

const CurtainPipesList = () => {

  return (
    <Container>
        <Titulo>Tipos de tubo de cortinas</Titulo>
        <CurtainPipesTable/>
    </Container>
    
  );
}

export default CurtainPipesList;