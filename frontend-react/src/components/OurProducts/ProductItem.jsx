import React, { useState } from 'react'
import styled from 'styled-components'

const StyledDiv = styled.div`
    background-color: #d0d3d4;
    border: solid;
    border-color:  #7b7d7d;
    display: flex; //Con display: flex y flex-direction: column, estableces que los elementos hijos de StyledDiv se ordenen en una sola columna
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 10px;
    width: 250px;

`;

const Titulo = styled.h1`
    color: black;
    font-size: large;
`;

const Imagen = styled.img`
    width: 180px; /* Reduce el tamaño de la imagen para que se ajuste dentro del espacio delimitado por el padding */
    height: 220px;
    object-fit: contain; /* Ajusta la imagen dentro de su contenedor */

`;

const Descripcion = styled.p`
    color: black;
    font-size: medium;
    max-width: 100%; /* Ajustamos el ancho máximo de la descripción al 100% */
    word-wrap: break-word; /* Permitimos que las palabras se dividan en varias líneas si exceden el ancho disponible */
    overflow: hidden; /* Ocultamos cualquier contenido que exceda el tamaño fijo del componente */
    //text-overflow: ellipsis; /* Agregamos puntos suspensivos (...) al final del texto si se recorta debido al tamaño fijo */
    //white-space: nowrap; /* Evitamos que el texto se divida en varias líneas */
`;

export const ProductItem = ({title,image,description}) => {

    

    return (
        
        <StyledDiv>
            <Titulo>{title}</Titulo>
            <Imagen src={image}/>
            <Descripcion>{description}</Descripcion>
        </StyledDiv>
    )
}
