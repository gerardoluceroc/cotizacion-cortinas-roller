import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { getAllCurtains } from '../../../api/civilo_roller_api';
import AddIcon from '@mui/icons-material/Add';
import ModalRegister from '../../ModalCreateCurtain';
import { Button, IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import ModalDeleteCurtain from '../../Modals/ModalDeleteCurtain';

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

const StyledDiv = styled.div`
  display: flex;
  flex-direction: row;
  margin-left: 1%;
  margin-top: -10px;
  width: 50%;
  align-items: center;

  @media (max-width: 828px) {
    width: 80%;
    
  }
`;

const BotonCrearCortina = styled.button` 
  background-color: #1010b3;
  border-radius: 5px;
  border-color: #1010b3;
  color: white;
  font-size: xx-large;
  margin-left: 1.5%;
  width: 15%;
  height: 2.3rem;
  display: flex;
  justify-content: center;
  align-items: center;
  //Animación para cuando el cursor pase por encima del botón.
  &:hover {
      box-shadow: 0px 0px 5px 2px rgba(0,0,0,0.25); /* Agrega una sombra */
      transform: scale(0.95); /* Reduzca ligeramente el tamaño */
    }

  /* sombra del botón */
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.3s ease-in-out;
  
  /* estilo cuando se presiona el botón */
  &:active {
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.4);
  }
  
  & > * {
    margin-left: 4%;
  }

  @media (max-width: 950px) {
    width: 30%;
    
  }

  @media (max-width: 540px) {
    width: 50%;
    margin: auto;
    
  }
`;

export const CurtainsTable = () => {

  const [curtains, setCurtains] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);

  const handleModalClose = () => {
    setModalOpen(false);
  };

  const createProduct = () => {
    return (
      <StyledDiv>
        <h3>Crear nuevo producto</h3>
        <BotonCrearCortina onClick={() => setModalOpen(true)}>
          Crear
          <AddIcon sx={{ fontSize: 40 }} />
        </BotonCrearCortina>
      </StyledDiv>
    )
  }


  useEffect(() => {

    getAllCurtains()
      .then((data) => { setCurtains(data); })
      .catch((error) => { console.log("Error al obtener las cortinas", error) })
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

   // Estado para controlar la apertura y cierre del modal para cada cortina
   const [modalDeleteCurtainOpen, setModalDeleteCurtainOpen] = useState({});

  // Función para abrir el modal de eliminación para un producto específico
  const handleModalDeleteCurtainOpen = (userID) => {
    setModalDeleteCurtainOpen((prevState) => ({
      ...prevState,
      [userID]: true,
    }));
  };

  // Función para cerrar el modal de eliminación para un usuario específico
  const handleModalDeleteCurtainClose = (userID) => {
    setModalDeleteCurtainOpen((prevState) => ({
      ...prevState,
      [userID]: false,
    }));
  };

  const headers = [
    { id: 'curtainID', label: 'ID de Cortina' },
    { id: 'curtainType', label: 'Tipo de Cortina' },
    { id: 'acciones', label: 'Acciones' }
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

  const sortedCurtains = [...curtains].sort(compareValues);

  return (
    <TableContainer>
      <ModalRegister open={modalOpen} onClose={handleModalClose} />
      {createProduct()}
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
          {sortedCurtains.map((curtain) => (
            <TableRow key={curtain.curtainID}>
              <TableCell>{curtain.curtainID}</TableCell>
              <TableCell>{curtain.curtainType}</TableCell>
              <TableCell>
                <IconButton onClick={() => handleModalDeleteCurtainOpen(curtain.curtainID)}>
                  <DeleteIcon />
                </IconButton>
                <ModalDeleteCurtain
                  open={modalDeleteCurtainOpen[curtain.curtainID]}
                  onClose={() => handleModalDeleteCurtainClose(curtain.curtainID)}
                  nombreCortina={curtain.curtainType}
                  cortinaID={curtain.curtainID}
                />
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </TableWrapper>
    </TableContainer>
  );
};










