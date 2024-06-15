import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { RUTA_CORTINAS, RUTA_GET_IVA, URL_CIVILO, RUTA_TUBOS, RUTA_COTIZACIONES, URL_HOME, RUTA_PDF, RUTA_REQUESTS, obtenerAsignacionesVendedor, getRequestById, solicitarPDF } from '../../api/civilo_roller_api';
import { RequestResume } from '../ClientView/RequestResume';
import { showAlert } from '../../functions/funciones';

const Container = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    //height: 100vh;
    margin-bottom: 3%;
    `;

const Title = styled.h1`
    font-size: xx-large;
    `;

const Subtitle = styled.h2`
    font-size: x-large;
    `;

const Table = styled.table`
    border-collapse: collapse;
    margin-top: 20px;
    
    
    `;

const TableRow = styled.tr``;

const TableHeader = styled.th`
    padding: 8px;
    border: 6px inset #073088;
    background-color: #073088;
    color: white;
    
    `;

const TableCell = styled.td`
    padding: 8px;
    border: 6px inset #073088;
    background-color: #1976d265;
    
    `;

const Button = styled.button`
    margin: 0 10px;
    padding: 10px 20px;
    border-radius: 5px;
    color: #fff;
    font-size: 16px;
    font-weight: bold;
    cursor: pointer;
    border: none;
    `;

const ButtonContainer = styled.div`
    display: flex;
    justify-content: center;
    margin-top: 20px;
    `;

const RedButton = styled(Button)`
    background-color: #ff5a5f;
    `;

const GreenButton = styled(Button)`
    background-color: #5fa463;
    `;

const DescriptionInput = styled.textarea`
    width: 30%; /* Ajusta el ancho según tus necesidades */
    height: 70%; /* Ajusta el alto según tus necesidades */
    font-size: x-large; /* Ajusta el tamaño de la letra según tus necesidades */
`;

const Input = styled.input`
  width: 100%;
`;

// const Select = styled.select`
//     width: 100%;
//   `;

const Select = styled.select`
  /* Estilos para el select */
  padding: 8px;
  font-size: large;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 200px;
  text-align: center; /* Centrar el texto */

  /* Estilos para las opciones del select */
  option {
    padding: 8px;
  }
`;

const SelectSolicitud = styled.select`
  /* Estilos para el select */
  padding: 8px;
  font-size: x-large;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 200px;
  text-align: center; /* Centrar el texto */

  /* Estilos para las opciones del select */
  option {
    padding: 8px;
  }
`;

const Textarea = styled.textarea`
  width: 49%;
  height: 50px;
`;

const SellerQuote = () => {
  const [numColumns, setNumColumns] = useState(1);
  const [iva, setIva] = useState(0);
  const [curtains, setCurtains] = useState([]);
  const [pipes, setPipes] = useState([]);
  const [description, setDescription] = useState('');
  const [quoteData, setQuoteData] = useState([]);
  const [discount, setDiscount] = useState(0); // Nuevo estado para el descuento
  const [cost, setCost] = useState(null);
  const [saleValue, setSaleValue] = useState(null);
  const [total, setTotal] = useState(null);
  const [requests, setRequests] = useState([]);
  const [solicitudSeleccionada, setSolicitudSeleccionada] = useState([]);
  const [solicitudVisible, setSolicitudVisible] = useState(null);
  const user = JSON.parse(sessionStorage.getItem("user"));
  const id_vendedor = user.userID;

  // Extracción de datos desde BE
  useEffect(() => {
    // Extracción de IVA
    const fetchIva = async () => {
      try {
        const response = await fetch(`${URL_CIVILO}${RUTA_GET_IVA}`);
        const data = await response.json();
        setIva(data);
      } catch (error) {
        console.log("Error al obtener IVA:", error);
      }
    };

    // Extracción de cortinas
    const fetchCurtains = async () => {
      try {
        const response = await fetch(`${URL_CIVILO}${RUTA_CORTINAS}`);
        const data = await response.json();
        setCurtains(data);
        setQuoteData(createInitialQuoteData(data.length));
      } catch (error) {
        console.log("Error al obtener las cortinas: ", error);
      }
    };

    // Extracción de tubos
    const fetchPipes = async () => {
      try {
        const response = await fetch(`${URL_CIVILO}${RUTA_TUBOS}`);
        const data = await response.json();
        setPipes(data)
        console.log(pipes)
      } catch (error) {
        console.log("Error al obtener las tuberías:", error);
      }
    };

    console.log("id vendedor", id_vendedor);
    obtenerAsignacionesVendedor(id_vendedor)
      .then((asignaciones) => setRequests(asignaciones))
      .catch((error) => console.log("Error al obtener las asignaciones: ", error));
    console.log("las solicitudes del seller son", requests);

    fetchIva();
    fetchCurtains();
    fetchPipes();
  }, [id_vendedor]);

  // Creación del quoteData a enviar a BE
  useEffect(() => {
    setQuoteData((prevQuoteData) => {
      if (prevQuoteData.length < numColumns) {
        return createInitialQuoteData(numColumns, curtains);
      } else if (prevQuoteData.length > numColumns) {
        return prevQuoteData.slice(0, numColumns);
      } else {
        return prevQuoteData;
      }
    });
  }, [numColumns, curtains]);

  // Función para crear el quoteData a enviar a BE
  const createInitialQuoteData = (numColumns, curtainsData) => {
    const updatedQuoteData = [...quoteData];
    for (let i = quoteData.length; i < numColumns; i++) {
      updatedQuoteData.push({
        curtain: null,
        amount: 0,
        valueSquareMeters: 0,
        width: 1,
        height: 1,
        bracketValue: 0,
        capValue: 0,
        counterweightValue: 0,
        bandValue: 0,
        chainValue: 0,
        pipe: null,
        pipeValue: 0,
        assemblyValue: 0,
        installationValue: 0,
        description: "",
        totalSquareMeters: null,
        totalFabrics: null,
        totalMaterials: null,
        totalLabor: null,
        productionCost: null,
        saleValue: null,
        percentageDiscount: discount,
        iva: iva,
        total: null,
        date: null,
        seller: JSON.parse(sessionStorage.getItem('user')),
        currentIVA: null,
        requestEntity: solicitudSeleccionada
      });
    }
    return updatedQuoteData;
  };

  // Función para manejar el número de columnas
  const handleNumColumnsChange = (event) => {
    const newNumColumns = parseInt(event.target.value);
    setNumColumns(newNumColumns >= 0 ? newNumColumns : 0);
  };

  // Función para manejar la selección de cortinas
  const handleCurtainSelection = (index, value) => {
    const updatedQuoteData = [...quoteData];
    updatedQuoteData[index] = {
      ...updatedQuoteData[index],
      curtain: curtains.find((curtain) => curtain.curtainID === parseInt(value)),
    };
    setQuoteData(updatedQuoteData);
  };

  // Función para manejar el número de cortinas
  const handleAmountChange = (index, value) => {
    const updatedQuoteData = [...quoteData];
    updatedQuoteData[index] = {
      ...updatedQuoteData[index],
      amount: parseInt(value),
    };
    setQuoteData(updatedQuoteData);
  };

  // Función para manejar el valor por metro cuadrado
  const handleValueSquareMetersChange = (index, value) => {
    const updatedQuoteData = [...quoteData];
    updatedQuoteData[index] = {
      ...updatedQuoteData[index],
      valueSquareMeters: parseInt(value),
    };
    setQuoteData(updatedQuoteData);
  };

  // Función para manejar el ancho en metros
  const handleWidthChange = (index, value) => {
    const updatedQuoteData = [...quoteData];
    updatedQuoteData[index] = {
      ...updatedQuoteData[index],
      width: parseFloat(value),
    };
    setQuoteData(updatedQuoteData);
  };

  // Función para manejar el alto en metros
  const handleHeightChange = (index, value) => {
    const updatedQuoteData = [...quoteData];
    updatedQuoteData[index] = { ...updatedQuoteData[index], height: parseFloat(value) };
    setQuoteData(updatedQuoteData);
  };

  // Función para manejar el valor por bracket
  const handleValueBracketChange = (index, value) => {
    const updatedQuoteData = [...quoteData];
    updatedQuoteData[index] = {
      ...updatedQuoteData[index],
      bracketValue: parseInt(value),
    };
    setQuoteData(updatedQuoteData);
  };

  // Función para manejar el valor por tapa
  const handleValueCapChange = (index, value) => {
    const updatedQuoteData = [...quoteData];
    updatedQuoteData[index] = {
      ...updatedQuoteData[index],
      capValue: parseInt(value),
    };
    setQuoteData(updatedQuoteData);
  };

  // Función para manejar el valor por contrapeso
  const handleValueCounterWeightChange = (index, value) => {
    const updatedQuoteData = [...quoteData];
    updatedQuoteData[index] = {
      ...updatedQuoteData[index],
      counterweightValue: parseInt(value),
    };
    setQuoteData(updatedQuoteData);
  };

  // Función para manejar el valor por zuncho
  const handleValueBandChange = (index, value) => {
    const updatedQuoteData = [...quoteData];
    updatedQuoteData[index] = {
      ...updatedQuoteData[index],
      bandValue: parseInt(value),
    };
    setQuoteData(updatedQuoteData);
  };

  // Función para manejar el valor por cadena
  const handleValueChainChange = (index, value) => {
    const updatedQuoteData = [...quoteData];
    updatedQuoteData[index] = {
      ...updatedQuoteData[index],
      chainValue: parseInt(value),
    };
    setQuoteData(updatedQuoteData);
  };

  // Función para manejar la selección de tubos
  const handlePipeSelection = (index, value) => {
    const updatedQuoteData = [...quoteData];
    updatedQuoteData[index] = {
      ...updatedQuoteData[index],
      pipe: pipes.find((pipe) => pipe.pipeID === parseInt(value)),
    };
    setQuoteData(updatedQuoteData);
  };

  // Función para manejar el valor por tubo
  const handleValuePipeChange = (index, value) => {
    const updatedQuoteData = [...quoteData];
    updatedQuoteData[index] = {
      ...updatedQuoteData[index],
      pipeValue: parseInt(value),
    };
    setQuoteData(updatedQuoteData);
  };

  // Función para manejar el valor de armado
  const handleValueAssemblyChange = (index, value) => {
    const updatedQuoteData = [...quoteData];
    updatedQuoteData[index] = {
      ...updatedQuoteData[index],
      assemblyValue: parseInt(value),
    };
    setQuoteData(updatedQuoteData);
  };

  // Función para manejar el valor de instalación
  const handleValueInstalationChange = (index, value) => {
    const updatedQuoteData = [...quoteData];
    updatedQuoteData[index] = {
      ...updatedQuoteData[index],
      installationValue: parseInt(value),
    };
    setQuoteData(updatedQuoteData);
  };

  // Función para manejar el texto de descripción
  const handleDescriptionChange = (event) => {
    const { value } = event.target;
    setDescription(value);
    const updatedQuoteData = quoteData.map((item) => ({ ...item, description: value }));
    setQuoteData(updatedQuoteData);
  };

  // Función que envía los datos al BE
  const handleQuote = async () => {
    const quoteDataValid = quoteData.filter((item) => item.curtain !== null);
    if (quoteDataValid.length > 0) {
      const updatedQuoteData = quoteDataValid.map((data) => ({
        ...data,
        requestEntity: solicitudSeleccionada
      }));
      const response = await fetch(`${URL_CIVILO}${RUTA_COTIZACIONES}?description=${encodeURIComponent(description)}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedQuoteData),
      });
      if (response.ok) {
        const quoteSummary = await response.json(); // Esperar la respuesta como JSON
        console.log(quoteSummary);
        setCost(quoteSummary.totalCostOfProduction);
        setSaleValue(quoteSummary.totalSaleValue);
        setTotal(quoteSummary.total);
        showAlert('Cotización enviada');
      } else {
        showAlert('Error al enviar la cotización');
      }
    }
  };

  // Función que renderiza las columnas de la fila: Tipo de cortina
  const renderCurtainColumns = () => {
    return quoteData.map((item, index) => (
      <TableCell key={index}>
        <Select
          value={item?.curtain?.curtainID || ''}
          onChange={(event) => handleCurtainSelection(index, event.target.value)}
        >
          <option value="">Seleccionar</option>
          {curtains.map((curtain) => (
            <option key={curtain.curtainID} value={curtain.curtainID}>
              {curtain.curtainType}
            </option>
          ))}
        </Select>
      </TableCell>
    ));
  };

  // Función que renderiza las columnas de la fila: Cantidad de cortinas
  const renderAmountColumns = () => {
    return quoteData.map((item, index) => (
      <TableCell key={index}>
        <Input
          type="number"
          value={item?.amount || 0}
          onChange={(event) => handleAmountChange(index, event.target.value)}
        />
      </TableCell>
    ));
  };

  // Función que renderiza las columnas de la fila: Valor por metro cuadrado
  const renderValueSquareMetersColumns = () => {
    return quoteData.map((item, index) => (
      <TableCell key={index}>
        <Input
          type="number"
          value={item?.valueSquareMeters || 0}
          onChange={(event) => handleValueSquareMetersChange(index, event.target.value)}
        />
      </TableCell>
    ));
  };

  // Función que renderiza las columnas de la fila: Ancho
  const renderWidthColumns = () => {
    return quoteData.map((item, index) => (
      <TableCell key={index}>
        <Input
          type="number"
          step="0.01"
          value={item?.width || 1}
          onChange={(event) => handleWidthChange(index, event.target.value)}
        />
      </TableCell>
    ));
  };

  // Función que renderiza las columnas de la fila: Alto
  const renderHeightColumns = () => {
    return quoteData.map((item, index) => (
      <TableCell key={index}>
        <Input
          type="number"
          step="0.01"
          value={item?.height || 1}
          onChange={(event) => handleHeightChange(index, event.target.value)}
        />
      </TableCell>
    ));
  };

  // Función que renderiza las columnas de la fila: Valor por bracket
  const renderValueBracketColumns = () => {
    return quoteData.map((item, index) => (
      <TableCell key={index}>
        <Input
          type="number"
          value={item?.bracketValue || 0}
          onChange={(event) => handleValueBracketChange(index, event.target.value)}
        />
      </TableCell>
    ));
  };

  // Función que renderiza las columnas de la fila: Valor por tapa
  const renderValueCapColumns = () => {
    return quoteData.map((item, index) => (
      <TableCell key={index}>
        <Input
          type="number"
          value={item?.capValue || 0}
          onChange={(event) => handleValueCapChange(index, event.target.value)}
        />
      </TableCell>
    ));
  };

  // Función que renderiza las columnas de la fila: Valor por contrapeso
  const renderValueCounterWeightColumns = () => {
    return quoteData.map((item, index) => (
      <TableCell key={index}>
        <Input
          type="number"
          value={item?.counterweightValue || 0}
          onChange={(event) => handleValueCounterWeightChange(index, event.target.value)}
        />
      </TableCell>
    ));
  };

  // Función que renderiza las columnas de la fila: Valor por zuncho
  const renderValueBandColumns = () => {
    return quoteData.map((item, index) => (
      <TableCell key={index}>
        <Input
          type="number"
          value={item?.bandValue || 0}
          onChange={(event) => handleValueBandChange(index, event.target.value)}
        />
      </TableCell>
    ));
  };

  // Función que renderiza las columnas de la fila: Valor por cadena
  const renderValueChainColumns = () => {
    return quoteData.map((item, index) => (
      <TableCell key={index}>
        <Input
          type="number"
          value={item?.chainValue || 0}
          onChange={(event) => handleValueChainChange(index, event.target.value)}
        />
      </TableCell>
    ));
  };

  // Función que renderiza las columnas de la fila: Tipo de tubo
  const renderPipeColumns = () => {
    return quoteData.map((item, index) => (
      <TableCell key={index}>
        <Select
          value={item?.pipe?.pipeID || ''}
          onChange={(event) => handlePipeSelection(index, event.target.value)}
        >
          <option value="">Seleccionar</option>
          {pipes.map((pipe) => (
            <option key={pipe.pipeID} value={pipe.pipeID}>
              {pipe.pipeName}
            </option>
          ))}
        </Select>
      </TableCell>
    ));
  };

  // Función que renderiza las columnas de la fila: Valor por tubo
  const renderValuePipeColumns = () => {
    return quoteData.map((item, index) => (
      <TableCell key={index}>
        <Input
          type="number"
          value={item?.pipeValue || 0}
          onChange={(event) => handleValuePipeChange(index, event.target.value)}
        />
      </TableCell>
    ));
  };

  // Función que renderiza las columnas de la fila: Valor de ensamblaje
  const renderValueAssemblyColumns = () => {
    return quoteData.map((item, index) => (
      <TableCell key={index}>
        <Input
          type="number"
          value={item?.assemblyValue || 0}
          onChange={(event) => handleValueAssemblyChange(index, event.target.value)}
        />
      </TableCell>
    ));
  };

  // Función que renderiza las columnas de la fila: Valor de instalación
  const renderValueInstalationColumns = () => {
    return quoteData.map((item, index) => (
      <TableCell key={index}>
        <Input
          type="number"
          value={item?.installationValue || 0}
          onChange={(event) => handleValueInstalationChange(index, event.target.value)}
        />
      </TableCell>
    ));
  };

  const handleDiscountChange = (e) => {
    const parsedValue = parseFloat(e.target.value);
    setDiscount(parsedValue >= 0 ? parsedValue : 0); // Actualizar el descuento
  };

  //Funcion que se activa al cambiar lo seleccionado en select
  const handleUserChange = (index, value) => {
    if (value) {
      const updatedData = [...quoteData];
      updatedData[index][14] = JSON.parse(value);
      setQuoteData(updatedData);
    }

    const id_solicitud = index.target.value;

    if (id_solicitud !== "") {
      getRequestById(id_solicitud)
        .then((solicitud) => {
          console.log("la solicitud es: ", solicitud);
          setSolicitudSeleccionada(solicitud);
        })
        .catch((error) => console.log("Error al obtener la solicitud especificada: ", error))
    }
    else {
      setSolicitudSeleccionada([]);
    }

    console.log("index es: ", index.target.value);
    console.log("value es: ", value);
  };

  //Este useEffect es para que cuando cambie la solicitud seleccionada se renderize nuevamente el componente 
  //Y se muestre el resumen de otra solicitud
  useEffect(() => {
    let componenteRequestResume = null;

    if (solicitudSeleccionada.length !== 0) {
      if (solicitudSeleccionada.status.statusName.toLowerCase() === "sin asignar") {
        componenteRequestResume =
          <RequestResume
            key={solicitudSeleccionada.requestID}
            fecha={solicitudSeleccionada.admissionDate}
            IdSolicitud={solicitudSeleccionada.requestID}
            estado={"Enviada"}
            colorLetraTag="white"
            colorFondoTag="#1f618d"
            requestDetails={solicitudSeleccionada}
          />
          ;
      } else if (solicitudSeleccionada.status.statusName.toLowerCase() === "asignada") {
        componenteRequestResume =
          <RequestResume
            key={solicitudSeleccionada.requestID}
            fecha={solicitudSeleccionada.admissionDate}
            IdSolicitud={solicitudSeleccionada.requestID}
            estado={solicitudSeleccionada.status.statusName}
            colorLetraTag="#134c2b"
            colorFondoTag="#53cfb6"
            requestDetails={solicitudSeleccionada}
          />
          ;
      } else if (solicitudSeleccionada.status.statusName.toLowerCase() === "finalizada y fallida") {
        componenteRequestResume =
          <RequestResume
            key={solicitudSeleccionada.requestID}
            fecha={solicitudSeleccionada.admissionDate}
            IdSolicitud={solicitudSeleccionada.requestID}
            estado={"Sin Éxito"}
            colorLetraTag="#9e1919"
            colorFondoTag="#f5b7b1"
            requestDetails={solicitudSeleccionada}
          />
          ;
      } else if (solicitudSeleccionada.status.statusName.toLowerCase() === "finalizada y exitosa") {
        componenteRequestResume =
          <RequestResume
            key={solicitudSeleccionada.requestID}
            fecha={solicitudSeleccionada.admissionDate}
            IdSolicitud={solicitudSeleccionada.requestID}
            estado={"Completada"}
            colorLetraTag="white"
            colorFondoTag=" #22653f"
            requestDetails={solicitudSeleccionada}
          />
          ;
      }
    }
    setSolicitudVisible(componenteRequestResume);

  }, [solicitudSeleccionada])

  //Funcion que se activa cuando se presiona el boton descargar PDF
  const handleDownloadPDF = () => {

    const vendedor = JSON.parse(sessionStorage.getItem("user"));
    let id_solicitud = [];

    //Si existe alguna solicitud seleccionada
    if (solicitudSeleccionada.length !== 0) {
      id_solicitud = solicitudSeleccionada.requestID;
      console.log(id_solicitud, vendedor);
      //se envia al servidor la entidad seller y la id de la cotizacion para realizar el descargo del PDF
      solicitarPDF(vendedor, id_solicitud);
    } else {
      showAlert('Debe seleccionar una solicitud');
    }

  }

  return (
    <Container>
      <Title>Cotización</Title>
      <Subtitle>Vista Resumen</Subtitle>
      <Table>
        <tbody>
          <TableRow>
            <TableHeader>Costo</TableHeader>
            <TableHeader>Valor venta</TableHeader>
            <TableHeader>Descuento</TableHeader>
            <TableHeader>IVA</TableHeader>
            <TableHeader>Total</TableHeader>
          </TableRow>
          <TableRow>
            <TableCell>{cost !== null ? `CLP $${cost.toLocaleString()}` : '-'}</TableCell>
            <TableCell>{saleValue !== null ? `CLP $${saleValue.toLocaleString()}` : '-'}</TableCell>
            <TableCell>
              <input
                type="number"
                step="0.1"
                min="0"
                value={discount}
                onChange={handleDiscountChange}
              />
            </TableCell>
            <TableCell>{iva}%</TableCell>
            <TableCell>{total !== null ? `CLP $${total.toLocaleString()}` : '-'}</TableCell>
          </TableRow>
        </tbody>
      </Table>
      <Subtitle>Descripción</Subtitle>
      <Textarea
        id="description"
        value={description}
        onChange={handleDescriptionChange}
      />
      <Subtitle>Seleccione solicitud:</Subtitle>
      <SelectSolicitud onChange={(e) => handleUserChange(e)}>
        <option value="">Seleccionar</option>
        {requests
          .filter((request) => request.status.statusName === "Asignada")
          .map((request, index) => (
            <option key={request.requestID} value={request.requestID} index={index}>
              {request.requestID}
            </option>
          ))}
      </SelectSolicitud>
      {solicitudVisible}

      <Subtitle>Ingreso de costos y variables</Subtitle>
      <label htmlFor="numColumns">Columnas de cotización: </label>
      <input type="number" id="numColumns" value={numColumns} onChange={handleNumColumnsChange} />
      
      <Table>
        <tbody>
          <TableRow>
            <TableHeader>Tipo de cortina</TableHeader>
            {renderCurtainColumns()}
          </TableRow>
          <TableRow>
            <TableHeader>Cantidad de cortinas</TableHeader>
            {renderAmountColumns()}
          </TableRow>
          <TableRow>
            <TableHeader>Valor por metro cuadrado (CLP)</TableHeader>
            {renderValueSquareMetersColumns()}
          </TableRow>
          <TableRow>
            <TableHeader>Ancho (metros)</TableHeader>
            {renderWidthColumns()}
          </TableRow>
          <TableRow>
            <TableHeader>Alto (metros)</TableHeader>
            {renderHeightColumns()}
          </TableRow>
          <TableRow>
            <TableHeader>Valor por bracket (CLP)</TableHeader>
            {renderValueBracketColumns()}
          </TableRow>
          <TableRow>
            <TableHeader>Valor por tapa (CLP)</TableHeader>
            {renderValueCapColumns()}
          </TableRow>
          <TableRow>
            <TableHeader>Valor por contrapeso (CLP)</TableHeader>
            {renderValueCounterWeightColumns()}
          </TableRow>
          <TableRow>
            <TableHeader>Valor por zuncho (CLP)</TableHeader>
            {renderValueBandColumns()}
          </TableRow>
          <TableRow>
            <TableHeader>Valor por cadena (CLP)</TableHeader>
            {renderValueChainColumns()}
          </TableRow>
          <TableRow>
            <TableHeader>Tipo de tubo</TableHeader>
            {renderPipeColumns()}
          </TableRow>
          <TableRow>
            <TableHeader>Valor por tubo (CLP)</TableHeader>
            {renderValuePipeColumns()}
          </TableRow>
          <TableRow>
            <TableHeader>Valor del armado (CLP)</TableHeader>
            {renderValueAssemblyColumns()}
          </TableRow>
          <TableRow>
            <TableHeader>Valor de la instalación (CLP)</TableHeader>
            {renderValueInstalationColumns()}
          </TableRow>
        </tbody>
      </Table>
      <ButtonContainer>
        <RedButton onClick={() => window.location.href = `${URL_HOME}`}>Regresa</RedButton>
        <GreenButton onClick={handleQuote}>Cotizar</GreenButton>
        <GreenButton onClick={handleDownloadPDF}>Descargar en PDF</GreenButton>
      </ButtonContainer>

    </Container>
  );
};

export default SellerQuote;