import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { getAllStatus } from '../../../api/civilo_roller_api';




const TableContainer = styled.div`
  width: 90%;
  overflow-x: auto;
  margin: auto;
`;

const TableWrapper = styled.table`
  width: 100%;
  border-collapse: collapse;
`;

const TableHeader = styled.th`
  padding: 8px;
  text-align: left;
  cursor: pointer;
  background-color: #f5f5f5;
`;

const TableBody = styled.tbody``;

const TableRow = styled.tr`
  background-color: #e2e2e2;
`;

const TableCell = styled.td`
  padding: 8px;
  border-bottom: 1.5px ridge;
`;

const SortIcon = styled.span`
  margin-left: 5px;
`;

export const StatusTable = () => {

    const [status, setStatus] = useState([]);

    useEffect(() => {

        getAllStatus()
        .then((data) => {setStatus(data);})
        .catch((error) => {console.log("Error al obtener los estados", error)})
    }, []);










  const [orderBy, setOrderBy] = useState('');
  const [order, setOrder] = useState('asc');


//   // Estado para controlar la apertura y cierre del modal para cada rol
//   const [modalDeleteUserOpen, setModalDeleteUserOpen] = useState({});

//   // Función para abrir el modal de eliminación para rol
//   const handleModalDeleteUserOpen = (userID) => {
//     setModalDeleteUserOpen((prevState) => ({
//       ...prevState,
//       [userID]: true,
//     }));
//   };

//   // Función para cerrar el modal de eliminación para un rol especifico
//   const handleModalDeleteUserClose = (userID) => {
//     setModalDeleteUserOpen((prevState) => ({
//       ...prevState,
//       [userID]: false,
//     }));
//   };

  const headers = [
    {id: 'statusID', label: 'ID de Estado'},
    {id: 'statusName', label: 'Tipo de Estado'},
    // { id: 'acciones', label: 'Acciones' }
  ];

  const handleSort = (columnId) => {
    const isAsc = orderBy === columnId && order === 'asc';
    setOrder(isAsc ? 'desc' : 'asc');
    setOrderBy(columnId);
  };

  const compareValues = (a, b) => {
    if (a[orderBy] < b[orderBy]) {
      return order === 'asc' ? -1 : 1;
    }
    if (a[orderBy] > b[orderBy]) {
      return order === 'asc' ? 1 : -1;
    }
    return 0;
  };

  const sortedStatus = [...status].sort(compareValues);

  

  return (
    <TableContainer>
      <TableWrapper>
        <thead>
          <tr>
            {headers.map((header) => (
              <TableHeader
                key={header.id}
                onClick={() => handleSort(header.id)}
              >
                {header.label}
                {orderBy === header.id && (
                  <SortIcon>{order === 'asc' ? '▲' : '▼'}</SortIcon>
                )}
              </TableHeader>
            ))}
          </tr>
        </thead>
        <TableBody>
          {sortedStatus.map((status) => (
            <TableRow key={status.statusID}>
              <TableCell>{status.statusID}</TableCell>
              <TableCell>{status.statusName}</TableCell> 
              {/* <TableCell>
                <IconButton onClick={() => handleModalDeleteUserOpen(usuario.userID)}>
                  <DeleteIcon/>
                </IconButton>
                <ModalDeleteUser
                  open={modalDeleteUserOpen[usuario.userID]}
                  onClose={() => handleModalDeleteUserClose(usuario.userID)}
                  nombreUsuario={usuario.name}
                  apellidoUsuario={usuario.surname}
                  userID={usuario.userID}
                />
              </TableCell> */}
            </TableRow>
          ))}
        </TableBody>
      </TableWrapper>
    </TableContainer>
  );
};










