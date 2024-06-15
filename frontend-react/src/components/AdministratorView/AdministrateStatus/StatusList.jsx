import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { StatusTable } from './StatusTable';






const Titulo = styled.h1`

  font-size: xx-large;
  display: flex;
  justify-content: center;

`;

const Container = styled.div`


    background-color: transparent;
    min-height: 700px;
    //display: flex;
    //flex-direction: column;
    //align-items: center;

`;


const StatusList = () => {


  return (
    <Container>
        <Titulo>Estados Posibles de Solicitudes</Titulo>
        <p style={{ textAlign: 'center' }}>Estados en los que puede estar una solictud de cotización realizada por un cliente</p>
        <StatusTable/>
        
    </Container>
    
  );
}

export default StatusList;