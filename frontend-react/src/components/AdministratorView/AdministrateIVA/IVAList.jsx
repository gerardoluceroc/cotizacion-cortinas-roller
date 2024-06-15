import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { IVATable } from './IVATable';

const Titulo = styled.h1`

  font-size: xx-large;
  display: flex;
  justify-content: center;

`;

const Subtitulo = styled.h1`

  font-size: small;
  display: flex;
  justify-content: center;

`;

const Container = styled.div`


    background-color: transparent;
    min-height: 700px;

`;

const IVAList = () => {

  return (
    <Container>
        <Titulo>IVA histórico</Titulo>
        <Subtitulo>El último IVA es el actual</Subtitulo>
        <IVATable/>
    </Container>
    
  );
}

export default IVAList;