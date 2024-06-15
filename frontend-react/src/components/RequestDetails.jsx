import styled from "styled-components"
import PersonIcon from '@mui/icons-material/Person';
import EmailIcon from '@mui/icons-material/Email';
import LocalPhoneIcon from '@mui/icons-material/LocalPhone';
import PlaceIcon from '@mui/icons-material/Place';
import { AccessTime, CalendarMonth } from "@mui/icons-material";
import EventBusyIcon from '@mui/icons-material/EventBusy';
import BlindsClosedIcon from '@mui/icons-material/BlindsClosed';
import DescriptionIcon from '@mui/icons-material/Description';
import PendingIcon from '@mui/icons-material/Pending';
import BusinessIcon from '@mui/icons-material/Business';
import { useState } from "react";
import { useEffect } from "react";
import { obtenerVendedor } from "../api/civilo_roller_api";

const ContainerRequestDetails = styled.div`
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

`;

const Label = styled.label`
    font-weight: 700;
    margin-right: 0.5%;
`;

const InformacionUsuario = styled.div`
    background-color: transparent;
    display: flex;
    flex-direction: column;
`;

const InformacionCotización = styled.div`
    background-color: transparent;
    display: flex;
    flex-direction: column;

`;

const ItemInfoCotizacion = styled.div`
    font-size: large;
    display: flex; /* Añade "display: flex" para alinear los elementos verticalmente */
    align-items: center; /* Añade "align-items: center" para centrar verticalmente los elementos */
    margin-top: 0; /* Ajusta el margen superior del texto para que esté alineado con el ícono */
    padding: 0.5%;

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

const ItemInfoUsuario = styled.div`
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

export const RequestDetails = ({ requestDetails }) => {

    const { requestID,
        sellerId,
        description: descripcion,
        deadline: fechaVencimiento,
        admissionDate: fechaRealizacion,
        coverage: cobertura,
        curtain: cortina,
        status: estado,
        user: usuario
    } = requestDetails;
    const [vendedor, setVendedor] = useState({});

    useEffect(() => {
        //obtenerSolicitudes().then((solicitudes) => setSolicitudes(solicitudes));
        obtenerVendedor(sellerId)
            .then(data => setVendedor(data))
            .catch((error) => console.log("Error al obtener el vendedor: ", error));
    }, []);


    return (
        <ContainerRequestDetails>
            <Titulo>Solicitud #{requestID}</Titulo>
            <H2>Solicitud De Cotización de Cortina(s)</H2>
            <H2>Realizada Por:</H2>
            <InformacionUsuario>
                <ItemInfoUsuario>
                    <PersonIcon />
                    <Label>Nombre:</Label>
                    {`${usuario.name} ${usuario.surname}`}
                </ItemInfoUsuario>
                <ItemInfoUsuario>
                    <EmailIcon />
                    <Label>Correo Electrónico: </Label>
                    {usuario.email}
                </ItemInfoUsuario>
                <ItemInfoUsuario>
                    <LocalPhoneIcon />
                    <Label>Número Telefónico: </Label>
                    {usuario.phoneNumber}
                </ItemInfoUsuario>
                <ItemInfoUsuario>
                    <PlaceIcon />
                    <Label>Comuna: </Label>
                    {usuario.commune}
                </ItemInfoUsuario>
                <ItemInfoUsuario>
                    <AccessTime />
                    <Label>Horario de atención: </Label>
                    {usuario.startTime === null || usuario.endTime === null ? (
                        <p style={{ fontStyle: 'italic' }}>No Informado</p>
                    ) : (
                        <p>
                            {usuario.startTime.substring(0, 5)} - {usuario.endTime.substring(0, 5)}
                        </p>
                    )}
                </ItemInfoUsuario>

            </InformacionUsuario>

            <H2>Detalles de Cotizacion Solicitada: </H2>
            <InformacionCotización>
                <ItemInfoCotizacion>
                    <Label>ID de Solicitud: </Label>
                    {requestID}
                </ItemInfoCotizacion>
                <ItemInfoCotizacion>
                    <CalendarMonth />
                    <Label>Fecha de Realización: </Label>
                    {fechaRealizacion}
                </ItemInfoCotizacion>

                <ItemInfoCotizacion>
                    <PlaceIcon />
                    <Label>Comuna Solicitada: </Label>
                    {cobertura.commune}
                </ItemInfoCotizacion>

                <ItemInfoCotizacion>
                    <EventBusyIcon />
                    <Label>Fecha de Expiración: </Label>
                    {fechaVencimiento}
                </ItemInfoCotizacion>

                <ItemInfoCotizacion>
                    <BlindsClosedIcon />
                    <Label>Tipo de Cortina: </Label>
                    {cortina.curtainType}
                </ItemInfoCotizacion>

                <ItemInfoCotizacion>
                    <DescriptionIcon />
                    <Label>Descripción: </Label>
                </ItemInfoCotizacion>

                <DescripcionCotización>
                    {descripcion}
                </DescripcionCotización>

                <ItemInfoCotizacion>
                    <PendingIcon />
                    <Label>Estado De Solicitud: </Label>
                    {estado.statusName}

                </ItemInfoCotizacion>
            </InformacionCotización>

            <H2>Vendedor Asignado:</H2>
            {estado.statusName.toLowerCase() !== 'sin asignar' ?
                <InformacionUsuario>
                    <ItemInfoUsuario>
                        <PersonIcon />
                        <Label>Nombre: </Label>
                        {`${vendedor.name} ${vendedor.surname}`}
                    </ItemInfoUsuario>
                    <ItemInfoUsuario>
                        <BusinessIcon />
                        <Label>Empresa: </Label>
                        {vendedor.companyName === null ? 'No Informada' : vendedor.companyName}
                    </ItemInfoUsuario>
                    <ItemInfoUsuario>
                        <EmailIcon />
                        <Label>Correo Electrónico: </Label>
                        {vendedor.email}
                    </ItemInfoUsuario>
                    <ItemInfoUsuario>
                        <LocalPhoneIcon />
                        <Label>Número Telefónico: </Label>
                        {vendedor.phoneNumber}
                    </ItemInfoUsuario>
                    <ItemInfoUsuario>
                        <PlaceIcon />
                        <Label>Comuna: </Label>
                        {vendedor.commune}
                    </ItemInfoUsuario>
                </InformacionUsuario>

                :
                <NoSellerAvailable>No tiene asignado un vendedor en estos momentos</NoSellerAvailable>
            }
        </ContainerRequestDetails>
    )

}
