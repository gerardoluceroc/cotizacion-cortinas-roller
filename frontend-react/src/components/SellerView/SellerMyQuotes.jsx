import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import InfoIcon from '@mui/icons-material/Info';
import DownloadIcon from '@mui/icons-material/Download';
import { getAllQuotes, getAllSellerQuotes, solicitarPDF } from '../../api/civilo_roller_api';
import { showAlert } from '../../functions/funciones';
import ModalQuoteDetails from '../ModalQuoteDetails';


const Title = styled.h1`
  text-align: center;
  margin-bottom: 3%;
`;

const Div = styled.div`
    min-height: calc(100vh - 80px);
`;

const Acciones = styled.div`
  background-color: transparent;
  display: flex;
  justify-content: space-between;
  width: 45%;
`;


const BotonVerDetalles = styled.button`
  background: none;
  border: none;
  cursor: pointer;
  outline: none;
  padding: 0;
  margin: 0;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  position: relative;

  &:hover::before {
    content: 'Ver Detalles';
    position: absolute;
    top: -15px; /* Modificado: Ajusta la posición vertical del texto */
    left: 50%;
    transform: translateX(-50%);
    padding: 4px 8px;
    background-color: rgba(0, 0, 0, 0.8);
    color: #fff;
    border-radius: 4px;
    font-size: 12px;
    opacity: 0;
    transition: opacity 0.3s ease-in-out;
  }

  &:hover::after {
    content: '';
    position: absolute;
    top: -5px;
    left: 50%;
    transform: translateX(-50%);
    border-style: solid;
    border-width: 5px;
    border-color: transparent transparent rgba(0, 0, 0, 0.8) rgba(0, 0, 0, 0.8);
  }

  &:hover::before {
    opacity: 1;
  }
`;

const BotonDescargarPDF = styled.button`
  background: none;
  border: none;
  cursor: pointer;
  outline: none;
  padding: 0;
  margin: 0;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  position: relative;

  &:hover::before {
    content: 'Descargar PDF';
    position: absolute;
    top: -15px; /* Modificado: Ajusta la posición vertical del texto */
    left: 50%;
    transform: translateX(-50%);
    padding: 4px 8px;
    background-color: rgba(0, 0, 0, 0.8);
    color: #fff;
    border-radius: 4px;
    font-size: 12px;
    opacity: 0;
    transition: opacity 0.3s ease-in-out;
  }

  &:hover::after {
    content: '';
    position: absolute;
    top: -5px;
    left: 50%;
    transform: translateX(-50%);
    border-style: solid;
    border-width: 5px;
    border-color: transparent transparent rgba(0, 0, 0, 0.8) rgba(0, 0, 0, 0.8);
  }

  &:hover::before {
    opacity: 1;
  }
`;







// Define las cabeceras de las columnas y sus propiedades
const columns = [
  { id:'identificador', label: 'Identificador', minWidth: 170}, 
  { id:'identificadorSolicitud', label: 'ID Solicitud', minWidth: 170}, 
  { id:'clienteAsociado', label: 'Cliente Asociado', minWidth: 170}, 
  { id:'costoTotal', label: 'Costo de Producción', minWidth: 170}, 
  { id:'valorVenta', label: 'Valor de Venta', minWidth: 170},  
  { id:'porcentajeDescuento', label: 'Descuento (%)', minWidth: 170},  
  { id:'valorNeto', label: 'Valor Neto', minWidth: 170},  
  { id:'total', label: 'Total', minWidth: 170},  
  { id:'acciones', label: 'Acciones', minWidth: 170}, 
  
];



const StyledTableCell = styled(TableCell)`
  min-width: ${({ minWidth }) => minWidth}px;
`;

const StyledTableContainer = styled(TableContainer)`
  max-height: 440px;
  max-height: 100vh; /* Establece un tamaño máximo para la tabla */
`;

export default function SellerMyQuotes() {
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);
////////////////////Codigo relacionado con los datos a colocar en la tabla /////////////////////////////////////////


    
    //Funcion que recibe un numero y retorna el mismo en formato CLP 
    function formatToCLP(value) {
      const number = parseFloat(value);
      if (isNaN(number)) {
        return "Valor inválido";
      }
      return number.toLocaleString('es-CL', { style: 'currency', currency: 'CLP' });
    }
    
    const [cotizaciones, setCotizaciones] = useState([]);
    const [cotizacionSeleccionada, setCotizacionSeleccionada] = useState([]);
    useEffect(() => {
      const usuario = JSON.parse(sessionStorage.getItem("user"));
      const tipo_usuario = usuario.role.accountType.toLowerCase();

      if(tipo_usuario === "vendedor"){ 
        const id_vendedor = usuario.userID;
        //Se obtienen las cotizaciones asociadas al vendedor logueado
        getAllSellerQuotes(id_vendedor)
        .then((data) => {setCotizaciones(data);})
        .catch((error) => console.log("error al obtener cotizaciones: ",error));
      }

      else if( tipo_usuario === "ejecutivo" || tipo_usuario === "administrador"){
        //Se obtienen todas las cotizaciones realizadas
        getAllQuotes()
        .then((data) => {setCotizaciones(data);})
        .catch((error) => console.log("error al obtener cotizaciones: ",error));
      }
          
    }, [])
    

    //modal que muestra los detalles de la solicitud
    const [modalOpen, setModalOpen] = useState(false);

    //maneja el cierre del modal
    const handleModalClose = () => {
      setModalOpen(false);
    };

    //funcion que crea un objeto con la fila de la tabla
    function createData(identificador, identificadorSolicitud, clienteAsociado, costoTotal,valorVenta, porcentajeDescuento, valorNeto, total, acciones){
        return {identificador, identificadorSolicitud, clienteAsociado, costoTotal, valorVenta, porcentajeDescuento, valorNeto, total, acciones};
    }
    //funcion que transforma una fecha del tipo "2023-16-06" al formato "16/06/2023"
    function formatDateToSpanish(dateString) {
        const [year, month, day] = dateString.split('-');
        const formattedDate = `${day}/${month}/${year}`;
        return formattedDate;
      }
      

    //Se definen las filas de la tabla
    const rows = cotizaciones.map((cotizacion) => 
                    createData(cotizacion.quoteID,
                                `${cotizacion.requestEntity.requestID}`,
                                `${cotizacion.requestEntity.user.name} ${cotizacion.requestEntity.user.surname}`,
                                formatToCLP(cotizacion.quoteSummary.totalCostOfProduction),
                                formatToCLP(cotizacion.quoteSummary.totalSaleValue),
                                `% ${cotizacion.quoteSummary.percentageDiscount}`,
                                formatToCLP(cotizacion.quoteSummary.netTotal),
                                formatToCLP(cotizacion.quoteSummary.total),
                                )
                                );
    //Funcion que se activa al presionar el boton Ver Detalles
    const handleVerDetalles = (event, id_cotizacion) => {
      //se busca la cotizacion con la id de entrada
      const cotizacion = cotizaciones.find((cotizacion) => cotizacion.quoteID === id_cotizacion);
      setCotizacionSeleccionada(cotizacion);

      //se abre el modal con los detalles de la solicitud
      setModalOpen(true);

    }

    //Funcion que se activa al presionar el boton Descargar PDF
    const handleDescargarPDF = (event, id_cotizacion) => {

      const cotizacionSeleccionada = cotizaciones.find((cotizacion) => cotizacion.quoteID === id_cotizacion);

      //id de la solicitud asociada a la cotizacion seleccionada
      const id_solicitud = cotizacionSeleccionada.requestEntity.requestID;

      //vendedor asociado a la cotizacion seleccionada
      const vendedor = cotizacionSeleccionada.seller;

      //se solicita la descarga del pdf de la cotizacion
      solicitarPDF(vendedor, id_solicitud);
      showAlert("PDF Descargado");

    }

    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  return (
    <Div>
      <ModalQuoteDetails open={modalOpen} onClose={handleModalClose} quoteDetails={cotizacionSeleccionada} />
      <Paper>
        <StyledTableContainer>
          <Title>Cotizaciones Realizadas</Title>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                {/* Itera sobre las columnas y muestra las cabeceras */}
                {/* La condicion de ModalOpen === false se hace para que cuando el modal se abra, las cabeceras de la tabla
                 no se vean, ya que por alguna razon esta cabecera se ve por encima del modal, tapandolo. */}
                {modalOpen === false ? columns.map((column) => (
                  <StyledTableCell
                    key={column.id}
                    align={column.align}
                    minWidth={column.minWidth}
                  >
                    {column.label}
                  </StyledTableCell>
                ))
                  : null }
              </TableRow>
            </TableHead>
            <TableBody>
              {/* Itera sobre las filas y muestra los datos */}
              {rows
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((row) => (
                  <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
                    {/* Itera sobre las columnas y muestra los valores */}
                    {columns.map((column) => {
                      const id_cotizacion = row.identificador;
                      //const id_solicitud = row.identificador; //BORRAR
                      const value = row[column.id];
                      return (
                        <StyledTableCell key={column.id} align={column.align}>
                          
                          {column.format && typeof value === 'number'
                            ? column.format(value)
                            : column.id === "acciones" ? <Acciones> 
                                                            <BotonVerDetalles onClick={(e) => handleVerDetalles(e, id_cotizacion)}> <InfoIcon/> </BotonVerDetalles> 
                                                            <BotonDescargarPDF onClick={(e) => handleDescargarPDF(e, id_cotizacion)}> <DownloadIcon/> </BotonDescargarPDF> 
                                                          </Acciones>
                            : value}
                        </StyledTableCell>
                      );
                    })}
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </StyledTableContainer>
        <TablePagination
          rowsPerPageOptions={[10, 25, 100]}
          component="div"
          count={rows.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
          labelRowsPerPage="Cantidad de filas"
        />
      </Paper>
    </Div>
  );
}
