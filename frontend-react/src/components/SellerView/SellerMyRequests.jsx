import React from "react";
import styled from "styled-components";
import { useState, useEffect } from "react";
import { obtenerAsignacionesVendedor } from "../../api/civilo_roller_api";

const TableContainer = styled.div`
  margin-top: 50px;
  margin: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: calc(90vh - 80px);
  //width: 100%;


`;

const Title = styled.h1`
  text-align: center;
`;

const Table = styled.table`
  border-collapse: collapse;
  margin-top: 20px;
  width: 50%;

`;



const Th = styled.th`
  border: 1px solid black;
  padding: 10px;
  text-align: left;
`;

const Tr = styled.tr`
  border: 1px solid black;
`;

const Td = styled.td`
  border: 1px solid black;
  padding: 10px;
`;



const Status = styled.td`
  color: ${({ approved }) => (approved ? "green" : "red")};
`;

const SellerMyRequests = () => {

  const [requests, setRequests] = useState([]);
  const user = JSON.parse(sessionStorage.getItem("user"));
  const id_vendedor = user.userID;
  console.log(user.userID);

  useEffect(() => {
    obtenerAsignacionesVendedor(id_vendedor)
    .then((asignaciones) => setRequests(asignaciones))
    .catch((error) => console.log("Error al obtener las asignaciones: ",error));
    console.log("las solicitudes del seller son",requests);
  },[id_vendedor])

  

  // useEffect(() => {
  //   const fetchRequests = async () => {
  //     try {
  //       const response = await fetch(`http://localhost:8080/requests/sellerRequest/${user.userID}`);
  //       const data = await response.json();
  //       console.log(data);
  //       setRequests(data);
  //     } catch (error) {
  //       console.error(error);
  //     }
  //   };
  //   fetchRequests();
  // }, [user.userID]);

  return (
    <TableContainer>
      <Title>Solicitudes Asignadas</Title>
      <Table>
        <thead>
          <Tr>
            <Th>Tipo cortina</Th>
            <Th>Descripción</Th>
            <Th>Fecha ingreso</Th>
            <Th>Fecha limite</Th>
            <Th>Cliente asociado</Th>
            <Th>E-mail</Th>
            <Th>Fono</Th>
            <Th>Estado de solicitud</Th>
          </Tr>
        </thead>
        <tbody>
          {requests.map((request) => (
            <Tr key={request.requestID}>
              <Td>{request.curtain.curtainType}</Td>
              <Td>{request.description}</Td>
              <Td>{request.admissionDate}</Td>
              <Td>{request.deadline}</Td>
              <Td>{`${request.user.name} ${request.user.surname}`}</Td>
              <Td>{request.user.email}</Td>
              <Td>{request.user.phoneNumber}</Td>
              <Status approved={request.status.statusName === "Asignada"}>{request.status.statusName}</Status>
            </Tr>
          ))}
        </tbody>
      </Table>
    </TableContainer>
  );
};

export default SellerMyRequests;