import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { getAllCurtainPipes } from '../../../api/civilo_roller_api';
import AddIcon from '@mui/icons-material/Add';
import ModalRegister from '../../ModalCreateCurtainPipe';
import { Button, IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import ModalDeleteCurtainPipe from '../../Modals/ModalDeleteCurtainPipe';

const BotonCrearTubo = styled.button` 
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

export const CurtainPipesTable = () => {
  const [modalOpen, setModalOpen] = useState(false);

  const handleModalClose = () => {
    setModalOpen(false);
  };

  const crearTubo = () => {
    return (
      <StyledDiv>
        <h3>Crear tubo de cortina roller</h3>
        <BotonCrearTubo onClick={() => setModalOpen(true)}>
          Crear
          <AddIcon sx={{ fontSize: 40 }} />
        </BotonCrearTubo>
      </StyledDiv>
    )

  }

  const [curtainPipes, setCurtainPipes] = useState([]);
  useEffect(() => {
    getAllCurtainPipes()
      .then((data) => { setCurtainPipes(data); })
      .catch((error) => { console.log("Error al obtener los tubos de cortinas roller", error) })
  }, []);

  const [orderBy, setOrderBy] = useState('');
  const [order, setOrder] = useState('asc');

  // Estado para controlar la apertura y cierre del modal para cada tubo
  const [modalDeleteCurtainPipeOpen, setModalDeleteCurtainPipeOpen] = useState({});

  // Función para abrir el modal de eliminación para un tubo específico
  const handleModalDeleteCurtainPipeOpen = (userID) => {
    setModalDeleteCurtainPipeOpen((prevState) => ({
      ...prevState,
      [userID]: true,
    }));
  };

  // Función para cerrar el modal de eliminación para un tubo específico
  const handleModalDeleteCurtainPipeClose = (userID) => {
    setModalDeleteCurtainPipeOpen((prevState) => ({
      ...prevState,
      [userID]: false,
    }));
  };


  const headers = [
    { id: 'pipeID', label: 'ID de tubo' },
    { id: 'pipeName', label: 'Nombre de tubo' },
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

  const sortedCurtainPipes = [...curtainPipes].sort(compareValues);

  return (
    <TableContainer>
      <ModalRegister open={modalOpen} onClose={handleModalClose} />
      {crearTubo()}
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
          {sortedCurtainPipes.map((curtainPipe) => (
            <TableRow key={curtainPipe.curtainPipe}>
              <TableCell>{curtainPipe.pipeID}</TableCell>
              <TableCell>{curtainPipe.pipeName}</TableCell>
              <TableCell>
                <IconButton onClick={() => handleModalDeleteCurtainPipeOpen(curtainPipe.pipeID)}>
                  <DeleteIcon />
                </IconButton>
                <ModalDeleteCurtainPipe
                  open={modalDeleteCurtainPipeOpen[curtainPipe.pipeID]}
                  onClose={() => handleModalDeleteCurtainPipeClose(curtainPipe.pipeID)}
                  nombreTubo={curtainPipe.pipeName}
                  tuboID={curtainPipe.pipeID}
                />
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </TableWrapper>
    </TableContainer>
  );
};










