import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { ProfitMarginTable } from './ProfitMarginTable';

const Titulo = styled.h1`

  font-size: xx-large;
  display: flex;
  justify-content: center;

`;

const Container = styled.div`


    background-color: transparent;
    min-height: 700px;

`;

const Subtitulo = styled.h1`

  font-size: small;
  display: flex;
  justify-content: center;

`;

const ProfitMarginList = () => {

  return (
    <Container>
        <Titulo>Márgenes de Utilidad</Titulo>
        <Subtitulo>El último margen de utilidad es el actual</Subtitulo>
        <ProfitMarginTable/>
    </Container>
    
  );
}

export default ProfitMarginList;