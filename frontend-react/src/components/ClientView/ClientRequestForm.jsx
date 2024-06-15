import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { RUTA_COBERTURAS, RUTA_CORTINAS, RUTA_POST_SOLICITUDES_CLIENTE, URL_CIVILO } from "../../api/civilo_roller_api";
import { showAlert } from "../../functions/funciones";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 9px;
  background-color: transparent;
`;

const Form = styled.form`
    display: flex;
    flex-direction: column;
    width: 85%;
`;

const Input = styled.input`
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;

`;

const Label = styled.label`
    font-family: 'Times New Roman', Times, serif;
    font-size: large;
    margin-bottom: 5px;
`;

const TextArea = styled.textarea`
    margin-bottom: 10px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    max-width: 97%;
`;

const Select = styled.select`
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
`;

const Button = styled.button`
    padding: 10px 20px;
    border-radius: 5px;
    background-color: #1010b3;
    border-color: #1010b3;
    margin-bottom: 15px;
    color: #fff;
    font-size: x-large;
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

`;



const ClientRequestForm = () => {
    const [description, setDescription] = useState("");
    const [deadline, setDeadline] = useState("");
    const [admissionDate, setAdmissionDate] = useState("");
    const [user, setUser] = useState(null);

    const [curtains, setCurtains] = useState([]);
    const [curtain, setCurtain] = useState({
        curtainID: "",
        curtainType: "",
    });

    const [communes, setCommunes] = useState([]);
    const [coverage, setCoverage] = useState({
        coverageID: "",
        commune: "",
    });

    useEffect(() => {
        fetch(`${URL_CIVILO}${RUTA_CORTINAS}`)
        .then((responseCurtain) => responseCurtain.json())
        .then((dataCurtain) => {
            console.log(dataCurtain);
            setCurtains(dataCurtain);
        })
        .catch((error) => {
            console.error("Error fetching curtains: ", error);
        });
    }, []);

    useEffect(() => {
        fetch(`${URL_CIVILO}${RUTA_COBERTURAS}`)
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                setCommunes(data);
            })
            .catch((error) => {
                console.error("Error fetching communes: ", error);
            });
    }, []);

    useEffect(() => {
        setUser(JSON.parse(sessionStorage.getItem("user")));
    }, []);

    const handleCurtainChange = (eventCurtain) => {
        const curtainType = eventCurtain.target.value;
        const selectedCurtain = curtains.find(
            (a) => a.curtainType === curtainType
        );
        setCurtain(selectedCurtain);
    };

    const handleCommuneChange = (event) => {
        const commune = event.target.value;
        const selectedCoverage = communes.find(
            (c) => c.commune === commune
        );
        setCoverage(selectedCoverage);
    };


    const handleSubmit = async (event) => {
        event.preventDefault();

        const data = {
            description: description,
            deadline: deadline,
            admissionDate: admissionDate,
            closingDate: null,
            reason: null,
            user: user,
            coverage: coverage,
            curtain: curtain,
            status: null,
            seller: null,
        };
        console.log("sesion storage es ",sessionStorage.getItem('user'));
        console.log("data: ", data);
        try {
            const response = await fetch(`${URL_CIVILO}${RUTA_POST_SOLICITUDES_CLIENTE}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${sessionStorage.getItem('user')}`,
                },
                body: JSON.stringify(data),
            });

            if (response.ok) {
                showAlert("Solicitud creada con éxito");
                setTimeout(() => {
                    window.location.reload();
                  }, 3000);
            } else {
                showAlert("Ha ocurrido un error al crear la solicitud");
            }
        } catch (error) {
            console.error(error);
            showAlert("Ha ocurrido un error al crear la solicitud");
        }
    };

    return (
        <Container>
            <h2>Nueva solicitud</h2>
            <Form onSubmit={handleSubmit}>
                <Select
                    value={curtain.curtainType}
                    onChange={handleCurtainChange}
                    required
                >
                    <option value="">Seleccione un tipo de cortina</option>
                    {curtains.map((a) => (
                        <option key={a.curtainID} value={a.curtainType}>
                            {a.curtainType}
                        </option>
                    ))}
                </Select>

                <Select
                    value={coverage.commune}
                    onChange={handleCommuneChange}
                    required
                >
                    <option value="">Seleccione una comuna</option>
                    {communes.map((c) => (
                        <option key={c.coverageID} value={c.commune}>
                            {c.commune}
                        </option>
                    ))}
                </Select>

                <Label>Fecha de Inicio</Label>
                <Input
                    type="date"
                    placeholder="Fecha de admisión"
                    value={admissionDate}
                    onChange={(event) => setAdmissionDate(event.target.value)}
                    required
                />
                
                <Label>Fecha de expiración</Label>
                <Input
                    type="date"
                    placeholder="Fecha límite"
                    value={deadline}
                    onChange={(event) => setDeadline(event.target.value)}
                    required
                />

                <TextArea
                    type="text"
                    placeholder="Descripción de solicitud"
                    value={description}
                    onChange={(event) => setDescription(event.target.value)}
                    
                />

                

                
                
                <Button type="submit">Enviar solicitud</Button>
            </Form>
        </Container>
    );
};

export default ClientRequestForm;