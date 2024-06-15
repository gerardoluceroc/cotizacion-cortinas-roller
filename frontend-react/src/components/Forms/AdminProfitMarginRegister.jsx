import styled from 'styled-components'
import { registrarMargenUtilidad } from '../../api/civilo_roller_api';
import { useState, useEffect } from 'react';

const Formulario = styled.form`
    background-color: #f8f8f8;
    border-radius: 3px;
    border: solid;
    border-color: gray;
    display: flex;
    flex-direction: column;
    margin: auto; //Se centra el formulario
    margin-top: 15px;
    margin-bottom: 15px;
    width: 43%;
    word-wrap: break-word; //Hace que el texto se ajuste de forma automática para evitar que se salga del botón.

    @media (max-width: 950px){
      width: 85%;
    }
    @media (max-width: 500px){
      width: 90%;
    }


`;

const EspacioVertical = styled.div`
  margin-bottom: 10px;
`;

const Titulo = styled.div`
  font-size: xx-large;
  font-family: Arial, Helvetica, sans-serif;
  font-weight: bold;
  margin-left: 10%;
`;

const Subtitulo = styled.p`
width: 90%;
color: #a09e9e;
font-size: medium;
border-bottom: 2px solid #a09e9e; /* Línea separadora */
`;

const Input = styled.input`
  background-color: #ccd0d5;
  border-radius: 6px;
  display: flex;
  width: 79%;
  margin: auto;
`;

const SubmitButton = styled.button`
  background-color: #1f618d;
  border-radius: 10px;
  border-color: #1f618d;
  color: #eae1e1;
  height: 80%;
  font-size: 150%;
  margin: auto;
  margin-bottom: 3%;
  width: 80%;

  //Animación para cuando el cursor pase por encima del botón.
  &:hover {
      box-shadow: 0px 0px 5px 2px rgba(0,0,0,0.25); /* Agrega una sombra */
      transform: scale(0.95); /* Reduzca ligeramente el tamaño */
    }

  // Agrega una sombra cuando el botón es presionado
  &:active {
      box-shadow: 0px 0px 10px 2px rgba(0,0,0,0.25); /* Agrega una sombra más grande */
      transform: scale(0.9); /* Reduce el tamaño aún más */
  }

  @media (max-width: 590px){
    width: 70%;
    
  }

`;

export const AdminProfitMarginRegister = () => {
  // Define los estados para cada campo del formulario
  const [porcentajeMargen, setPorcentajeMargen] = useState('');
  const [decimalMargen, setdDecimalMargen] = useState('');

  const handleClick = (event) => {

    //Se llama al metodo para evitar que haga su comportamiento por defecto, en este caso enviar el formulario
    event.preventDefault();

    //Se guardan los datos del nuevo usuario para enviarlos al servidor
    const margenNuevo = {
      "profitMarginPercentaje": porcentajeMargen,
      "decimalProfitMargin": decimalMargen
    }

    registrarMargenUtilidad(margenNuevo);
  }
  return (



    <Formulario>
      <EspacioVertical />
      <Titulo>Nuevo Margen de Utilidad
        <Subtitulo>Rellena los datos</Subtitulo>
      </Titulo>

      

      {/*Espacio creado para separar los elementos*/}
      <EspacioVertical />

      <Input type="text" name="porcentajeMargen" placeholder='Margen de utilidad (porcentual)' onChange={(e) => setPorcentajeMargen(e.target.value)} />
      <EspacioVertical />
      <Input type="text" name="decimalMargen" placeholder='Margen de utilidad (décimal)' onChange={(e) => setdDecimalMargen(e.target.value)} />
      <EspacioVertical />


      <EspacioVertical />
      <SubmitButton onClick={handleClick}>Registrar</SubmitButton>
      {/* lo ultimo que hice fue configurar que al presionar el boton no se haga nada, pq antes se hacia eso de enviar la info al swervidor
    pero ahora no hace nada, por lo que yo puedo configurar lo que se hace*/}
    </Formulario>




  )
}
