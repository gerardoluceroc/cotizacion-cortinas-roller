import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { RUTA_COBERTURAS, RUTA_UPDATE_COBERTURAS_VENDEDOR, URL_CIVILO, URL_HOME } from "../../api/civilo_roller_api";
import { showAlert } from "../../functions/funciones";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Title = styled.h1`
  font-size: x-large;
  margin-top: 3%;
`;

const TableContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 30px;
`;

const TableWrapper = styled.div`
  margin: 0 10px;
`;

const Table = styled.table`
  border-collapse: collapse;
  background-color: #eaf2f8; /* Color azul más intenso */
  border-radius: 5px;
`;

const TableHeader = styled.th`
  border: 1px solid #d1d9e6;
  padding: 10px;
  text-align: left;
  background-color: #1c4a80; /* Color azul más intenso */
  color: white;
`;

const TableRow = styled.tr`
  border: 1px solid #d1d9e6;
`;

const TableCell = styled.td`
  border: 1px solid #d1d9e6;
  padding: 10px;
`;

const Checkbox = styled.input`
  margin-right: 5px;
`;

const Input = styled.input`
  margin-top: 20px;
  padding: 10px;
  width: 200px;
`;

const Button = styled.button`
  background-color: #1010b3;
  border-radius: 5px;
  border-color: #1010b3;
  color: white;
  font-size: xx-large;
  margin-top: 1%;
  margin-bottom: 2%;
  width: 15%;
  height: 2.3rem;
  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    box-shadow: 0px 0px 5px 2px rgba(0, 0, 0, 0.25);
    transform: scale(0.95);
  }

  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.3s ease-in-out;

  &:active {
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.4);
  }

  @media (max-width: 950px) {
    width: 30%;
  }

  @media (max-width: 540px) {
    width: 50%;
  }

  @media (max-width: 312px) {
    width: 80%;
  }
`;

const Card = styled.div`
  border: 2px solid blue;
  padding: 16px;
  border-radius: 8px;
  width: 300px;
  margin: 20px auto;
`;

const RUTA_PAGE_LOGIN = "/login";

const SellerInformation = () => {
  const [coverages, setCoverages] = useState([]);
  const [selectedCoverages, setSelectedCoverages] = useState([]);
  const [companyName, setCompanyName] = useState("");
  const [bank, setBank] = useState("");
  const [bankAccountType, setBankAccountType] = useState("");
  const [bankAccountNumber, setBankAccountNumber] = useState("");

  useEffect(() => {
    fetch(`${URL_CIVILO}${RUTA_COBERTURAS}`)
      .then((response) => response.json())
      .then((data) => {
        setCoverages(data);
      })
      .catch((error) => {
        console.error("Error al obtener las coberturas:", error);
      });
  }, []);

  const handleCheckboxChange = (event) => {
    const coverageId = parseInt(event.target.value);
    if (event.target.checked) {
      setSelectedCoverages([...selectedCoverages, coverageId]);
    } else {
      setSelectedCoverages(selectedCoverages.filter((id) => id !== coverageId));
    }
  };

  const handleCompanyNameChange = (event) => {
    setCompanyName(event.target.value);
  };

  const handleBankChange = (event) => {
    setBank(event.target.value);
  };

  const handleBankAccountTypeChange = (event) => {
    setBankAccountType(event.target.value);
  };

  const handleBankAccountNumberChange = (event) => {
    setBankAccountNumber(event.target.value);
  };

  const handleSubmit = () => {
    const data = {
      email: localStorage.getItem('email'),
      coverageID: selectedCoverages,
      companyName: companyName,
      bank: bank,
      bankAccountType: bankAccountType,
      bankAccountNumber: bankAccountNumber,
    };

    try {
      if (JSON.parse(sessionStorage.getItem('user')).email != null) {
        data.email = JSON.parse(sessionStorage.getItem('user')).email;
      }
    } catch (error) {
      console.log("ERROR: ", error);
    }

    fetch(`${URL_CIVILO}${RUTA_UPDATE_COBERTURAS_VENDEDOR}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((response) => {
        if (response.ok) {
          try {
            if (JSON.parse(sessionStorage.getItem('user')).email != null) {
              showAlert("Información actualizada con éxito");
              window.location.replace(URL_HOME);
            }
          } catch (error) {
            console.log("ERROR: ", error);
            showAlert("Información actualizada con éxito");
            window.location.replace(RUTA_PAGE_LOGIN);
          }
        } else {
          showAlert("Complete todos los campos");
        }
      })
      .catch((error) => {
        console.error("Error al enviar la petición de actualización:", error);
      });
  };

  const renderTables = () => {
    const minRowsPerTable = 85;
    const numTables = Math.ceil(coverages.length / minRowsPerTable);
    const coverageChunks = chunkArray(coverages, minRowsPerTable);

    return coverageChunks.map((chunk, index) => (
      <TableWrapper key={index}>
        <Table>
          <thead>
            <tr>
              <TableHeader>Comuna</TableHeader>
              <TableHeader>Seleccionar</TableHeader>
            </tr>
          </thead>
          <tbody>
            {chunk.map((coverage) => (
              <TableRow key={coverage.coverageID}>
                <TableCell>{coverage.commune}</TableCell>
                <TableCell>
                  <Checkbox
                    type="checkbox"
                    value={coverage.coverageID}
                    onChange={handleCheckboxChange}
                  />
                </TableCell>
              </TableRow>
            ))}
          </tbody>
        </Table>
      </TableWrapper>
    ));
  };

  const chunkArray = (arr, size) => {
    const chunkedArray = [];
    let index = 0;
    while (index < arr.length) {
      chunkedArray.push(arr.slice(index, size + index));
      index += size;
    }
    return chunkedArray;
  };

  return (
    <Container>
      <Card>
        <Title>Datos de empresa</Title>
        <Input
          type="text"
          placeholder="Nombre empresa"
          value={companyName}
          onChange={handleCompanyNameChange}
        />
        <Input
          type="text"
          placeholder="Banco"
          value={bank}
          onChange={handleBankChange}
        />
        <Input
          type="text"
          placeholder="Tipo de cuenta"
          value={bankAccountType}
          onChange={handleBankAccountTypeChange}
        />
        <Input
          type="int"
          placeholder="Número de cuenta"
          value={bankAccountNumber}
          onChange={handleBankAccountNumberChange}
        />
      </Card>
      <Title>Indique las comunas a las cuales le realiza cobertura</Title>
      <TableContainer>
        {renderTables()}
      </TableContainer>
      <Button onClick={handleSubmit}>Confirmar</Button>
    </Container>
  );
};

export default SellerInformation;
