import React from 'react'
import styled from 'styled-components';
import PersonIcon from '@mui/icons-material/Person';
import EmailIcon from '@mui/icons-material/Email';
import PlaceIcon from '@mui/icons-material/Place';
import BlindsClosedIcon from '@mui/icons-material/BlindsClosed';
import DescriptionIcon from '@mui/icons-material/Description';
import FiberManualRecordIcon from '@mui/icons-material/FiberManualRecord';
import { CalendarMonth } from '@mui/icons-material';


const ContainerQuoteDetails = styled.div`
    background-color: transparent;
    display: flex;
    flex-direction: column;
    justify-content: center;
    //align-items: center;
`;


const Titulo = styled.h1`
    font-size: x-large;
    margin: auto;
    color: #525151;
`;

const H2 = styled.h2`
    color: #525151;
    margin-top: 8%;

`;

const Label = styled.label`
    font-weight: 700;
    margin-right: 1.5%;
`;


const DescripcionCotización = styled.div`
    display: flex;
    flex-direction: column;
    font-size: medium;
    margin-bottom: 3%;
    margin-top: 0.5%;
    margin-left: 4%;
    width: 70%;
`;

const Item = styled.div`
    font-size: large;
    display: flex; /* Añade "display: flex" para alinear los elementos verticalmente */
    align-items: center; /* Añade "align-items: center" para centrar verticalmente los elementos */
    margin-top: 0; /* Ajusta el margen superior del texto para que esté alineado con el ícono */
    padding: 0.5%;

`;

const NoSellerAvailable = styled.h2`
    color: gray;
    margin-top: -2%;
    font-size: large;
`;

export const SellerQuoteDetails = ({quoteDetails}) => {
    
    //Funcion que recibe un numero y retorna el mismo en formato CLP 
    function formatToCLP(value) {
        const number = parseFloat(value);
        if (isNaN(number)) {
          return "Valor inválido";
        }
        return number.toLocaleString('es-CL', { style: 'currency', currency: 'CLP' });
      }

      //funcion que transforma una fecha del tipo "2023-16-06" al formato "16/06/2023"
    function formatDateToSpanish(dateString) {
        const [year, month, day] = dateString.split('-');
        const formattedDate = `${day}/${month}/${year}`;
        return formattedDate;
      }

    const solicitudCotizacion = quoteDetails.requestEntity;
  return (
    <ContainerQuoteDetails>
        <Titulo>Cotización #{quoteDetails.quoteID}</Titulo>
        <H2>Resumen de Solicitud</H2>
        <Item>
            <Label>ID de solicitud:</Label>
            {solicitudCotizacion.requestID}
        </Item>
        <Item>
            <PersonIcon/>
            <Label>Nombre de Cliente:</Label>
            {`${solicitudCotizacion.user.name} ${solicitudCotizacion.user.surname}`}
        </Item>
        <Item>
            <EmailIcon />
            <Label>Correo Electrónico: </Label>
            {solicitudCotizacion.user.email}
        </Item>
        <Item>
            <CalendarMonth />
            <Label>Fecha de Realización: </Label>
            {formatDateToSpanish(solicitudCotizacion.admissionDate)}
        </Item>

        <Item>
            <PlaceIcon />
            <Label>Comuna Solicitada: </Label>
            {solicitudCotizacion.coverage.commune}
        </Item>
        <Item>
            <BlindsClosedIcon />
            <Label>Tipo de Cortina: </Label>
            {solicitudCotizacion.curtain.curtainType}
        </Item>

        <Item>
            <DescriptionIcon />
            <Label>Descripción: </Label>
        </Item>

        <DescripcionCotización>
            {solicitudCotizacion.description}
        </DescripcionCotización>

        <Item>
            <PersonIcon />
            <Label>Vendedor Asignado: </Label>
            {solicitudCotizacion.status.statusName.toLowerCase() !== "sin asignar" 
                ? `${quoteDetails.seller.name} ${quoteDetails.seller.surname}`
                : <NoSellerAvailable>No existe un vendedor asignado en estos momentos</NoSellerAvailable>
            }
        </Item>

        <H2>Resumen de cotización</H2>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Costo de producción: </Label>
            {` ${formatToCLP(quoteDetails.quoteSummary.totalCostOfProduction)}`}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Costo de venta: </Label>
            {formatToCLP(quoteDetails.quoteSummary.totalSaleValue)}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Descuento: </Label>
            {` ${quoteDetails.quoteSummary.percentageDiscount} %`}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Valor neto: </Label>
            {formatToCLP(quoteDetails.quoteSummary.netTotal)}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Valor después de descuento: </Label>
            {formatToCLP(quoteDetails.quoteSummary.valueAfterDiscount)}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Valor total:</Label>
            {formatToCLP(quoteDetails.quoteSummary.total)}
        </Item>

        <H2>Detalles de cotización</H2>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Cantidad de cortinas:</Label>
            {quoteDetails.amount}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Valor por metro cuadrado:</Label>
            {formatToCLP(quoteDetails.valueSquareMeters)}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Ancho:</Label>
            {`${quoteDetails.width} [m]`}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Alto:</Label>
            {`${quoteDetails.height} [m]`}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Valor por bracket:</Label>
            {formatToCLP(quoteDetails.bracketValue)}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Valor por tapa:</Label>
            {formatToCLP(quoteDetails.capValue)}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Valor por contrapeso:</Label>
            {formatToCLP(quoteDetails.counterweightValue)}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Valor por zuncho:</Label>
            {formatToCLP(quoteDetails.bandValue)}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Valor por cadena:</Label>
            {formatToCLP(quoteDetails.chainValue)}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Tipo de tubo:</Label>
            {quoteDetails.pipe.pipeName}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Valor por tubo:</Label>
            {formatToCLP(quoteDetails.pipeValue)}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Valor del armado:</Label>
            {formatToCLP(quoteDetails.assemblyValue)}
        </Item>
        <Item>
            <FiberManualRecordIcon style={{fontSize: '16px' , color:'#27208d', marginRight: '5px'}} />
            <Label>Valor de instalación:</Label>
            {formatToCLP(quoteDetails.installationValue)}
        </Item>
    </ContainerQuoteDetails>


  )
}
