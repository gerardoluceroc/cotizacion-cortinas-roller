import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { CurtainsTable } from './CurtainsTable';






const Titulo = styled.h1`

  font-size: xx-large;
  display: flex;
  justify-content: center;

`;

const Container = styled.div`


    background-color: transparent;
    min-height: 700px;

`;


const CurtainsList = () => {


  return (
    <Container>
        <Titulo>Cortinas Registradas</Titulo>
        <CurtainsTable/>
    </Container>
    
  );
}

export default CurtainsList;