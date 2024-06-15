import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { AllRequestsList } from './AllRequestsList';






const Titulo = styled.h1`

  font-size: xx-large;
  display: flex;
  justify-content: center;

`;

const Container = styled.div`


    background-color: transparent;
    min-height: 700px;

`;


const ReadAllRequests= () => {


  return (
    <Container>
        <Titulo>Solicitudes Realizadas</Titulo>
        <AllRequestsList/>
    </Container>
    
  );
}

export default ReadAllRequests;