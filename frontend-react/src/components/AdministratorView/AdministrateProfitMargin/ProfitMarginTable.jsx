import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { getAllProfitMargins } from '../../../api/civilo_roller_api';
import AddIcon from '@mui/icons-material/Add';
import ModalRegister from '../../ModalCreateProfitMargin';
import { Button, IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import ModalDeleteProfitMargin from '../../Modals/ModalDeleteProfitMargin';

const BotonCrearMargen = styled.button` 
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

export const ProfitMarginTable = () => {
  const [modalOpen, setModalOpen] = useState(false);

  const handleModalClose = () => {
    setModalOpen(false);
  };

  const crearMargen = () => {
    return (
      <StyledDiv>
        <h3>Crear Margen de Utilidad</h3>
        <BotonCrearMargen onClick={() => setModalOpen(true)}>
          Crear
          <AddIcon sx={{ fontSize: 40 }} />
        </BotonCrearMargen>
      </StyledDiv>
    )

  }

  const [profitMargins, setProfitMargins] = useState([]);
  useEffect(() => {
    getAllProfitMargins()
      .then((data) => { setProfitMargins(data); })
      .catch((error) => { console.log("Error al obtener los margenes de utilidad", error) })
  }, []);

  const [orderBy, setOrderBy] = useState('');
  const [order, setOrder] = useState('asc');

  // Estado para controlar la apertura y cierre del modal para cada margen
  const [modalDeleteProfitMarginOpen, setModalDeleteProfitMarginOpen] = useState({});

  // Función para abrir el modal de eliminación para un margen específico
  const handleModalDeleteProfitMarginOpen = (userID) => {
    setModalDeleteProfitMarginOpen((prevState) => ({
      ...prevState,
      [userID]: true,
    }));
  };

  // Función para cerrar el modal de eliminación para un margen específico
  const handleModalDeleteProfitMarginClose = (userID) => {
    setModalDeleteProfitMarginOpen((prevState) => ({
      ...prevState,
      [userID]: false,
    }));
  };


  const headers = [
    { id: 'profitMarginID', label: 'ID de Margen de Utilidad' },
    { id: 'profitMarginPercentaje', label: 'Margen de Utilidad (Porcentaje)' },
    { id: 'decimalProfitMargin', label: 'Margen de Utilidad (Decimal)' },
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

  const sortedProfitMargins = [...profitMargins].sort(compareValues);

  return (
    <TableContainer>
      <ModalRegister open={modalOpen} onClose={handleModalClose} />
      {crearMargen()}
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
          {sortedProfitMargins.map((profitMargin) => (
            <TableRow key={profitMargin.profitMargin}>
              <TableCell>{profitMargin.profitMarginID}</TableCell>
              <TableCell>{profitMargin.profitMarginPercentaje}</TableCell>
              <TableCell>{profitMargin.decimalProfitMargin}</TableCell>
              <TableCell>
                <IconButton onClick={() => handleModalDeleteProfitMarginOpen(profitMargin.profitMarginID)}>
                  <DeleteIcon />
                </IconButton>
                <ModalDeleteProfitMargin
                  open={modalDeleteProfitMarginOpen[profitMargin.profitMarginID]}
                  onClose={() => handleModalDeleteProfitMarginClose(profitMargin.profitMarginID)}
                  porcentajeMargen={profitMargin.profitMarginPercentaje}
                  decimalMargen={profitMargin.decimalProfitMargin}
                  margenID={profitMargin.profitMarginID}
                />
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </TableWrapper>
    </TableContainer>
  );
};










