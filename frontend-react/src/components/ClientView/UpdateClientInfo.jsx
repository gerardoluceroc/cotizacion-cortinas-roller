import styled from 'styled-components'
import { useState, useEffect } from 'react';
import { RUTA_COBERTURAS, URL_CIVILO, registrarUsuario } from '../../api/civilo_roller_api';
import { showAlert } from '../../functions/funciones';

const Formulario = styled.form`
    background-color: #f8f8f8;
    border-radius: 3px;
    border: solid;
    border-color: gray;
    display: flex;
    flex-direction: column;
    margin: auto; //Se centra el formulario
    margin-top: 10%;
    margin-bottom: 15px;
    width: 43%;
    word-wrap: break-word; //Hace que el texto se ajuste de forma automática para evitar que se salga del botón.

    

    @media (max-width: 950px){
      width: 70%;
    }
    @media (max-width: 500px){
      width: 90%;
    }


`;

const EspacioVertical = styled.div`
  margin-bottom: 10px;
`;

const Titulo = styled.div`
  font-size: xx-large;
  font-family: Arial, Helvetica, sans-serif;
  font-weight: bold;
  margin-left: 10%;
`;

const Subtitulo = styled.p`
width: 90%;
color: #a09e9e;
font-size: medium;
border-bottom: 2px solid #a09e9e; /* Línea separadora */
`;

const Nombres = styled.input`
  background-color: #ccd0d5;
  border-radius: 6px;
  width: 50%;
  margin-right: 5px;
`;

const Apellidos = styled.input`
  background-color: #ccd0d5;
  width: 50%;
  border-radius: 6px;
`;

const NombreApellido = styled.div`
  background-color: transparent;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  margin: auto;
  width: 80%;

  // Selecciona todos los elementos hijos directos de NombreApellido y les aplica un ancho inicial del 40% del ancho del contenedor padre
  & > * {
    flex-basis: 40%;
  }

  @media (max-width: 600px) {
    flex-direction: column; // Los elementos se comportan como columnas
    align-items: center; // Centra los elementos en el eje X
    & > * {
    width: 98%;
    margin: auto;
  }
  }
`;

//
const Input = styled.input`
  background-color: #ccd0d5;
  border-radius: 6px;
  display: flex;
  width: 79%;
  margin: auto;
`;

const NumeroTelefonico = styled.div`
display: flex;
  margin: auto;
  width: 80%;
`;

const CodigosInternacionales = styled.select`
  align-items: center;
  margin-right: 3px;
  width: 14%;

  @media (max-width: 900px){
    width: 30%;
    
  }
`;

const InputTelefono = styled.input`
background-color: #ccd0d5;
border-radius: 6px;
width: 90%;


`;

const RegionComuna = styled.div`
  border-color: transparent;
  margin: auto;
  width: 80%;
  
  & > * {
    width: 51%;  
  }

  @media (max-width: 600px) {
    & > * {
      margin-left:0;
      width: 100%;
      }
    
  }

`;

const RolUsuario = styled.div`
  border-color: transparent;
  display: flex;
  flex-direction: column;
  gap: 10%; // Agrega un espacio de 10px entre los hijos del div
  margin: auto;
  width: 80%;
`;


const Birthday = styled.div`
  border-color: transparent;
  display: flex;
  flex-direction: column;
  margin: auto;
  width: 80%;
  label {
    margin-bottom: 6px;
  }
  ${EspacioVertical} {
    margin-bottom: 20px;
  }
`;

const StartTime = styled.div`
border-color: transparent;
display: flex;
flex-direction: column;
margin: auto;
width: 80%;
label {
  margin-bottom: 6px;
}
${EspacioVertical} {
  margin-bottom: 20px;
}
`;

const EndTime = styled.div`
border-color: transparent;
display: flex;
flex-direction: column;
margin: auto;
width: 80%;
label {
  margin-bottom: 6px;
}
${EspacioVertical} {
  margin-bottom: 20px;
}
`;

const SubmitButton = styled.button`
  background-color: #1f618d;
  border-radius: 10px;
  border-color: #1f618d;
  color: #eae1e1;
  height: 80%;
  font-size: x-large;
  margin: auto;
  margin-bottom: 3%;
  width: 40%;

  //Animación para cuando el cursor pase por encima del botón.
  &:hover {
      box-shadow: 0px 0px 5px 2px rgba(0,0,0,0.25); /* Agrega una sombra */
      transform: scale(0.95); /* Reduzca ligeramente el tamaño */
    }

  // Agrega una sombra cuando el botón es presionado
  &:active {
      box-shadow: 0px 0px 10px 2px rgba(0,0,0,0.25); /* Agrega una sombra más grande */
      transform: scale(0.9); /* Reduce el tamaño aún más */
  }

  @media (max-width: 590px){
    width: 70%;
    
  }

`;

export const UpdateClientInfo = () => {


    // Define los estados para cada campo del formulario
    const [nombre, setNombre] = useState('');
    const [apellido, setApellido] = useState('');
    const [telefono, setTelefono] = useState('');
    const [codigoInternacional, setCodigoInternacional] = useState('+56');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [communes, setCommunes] = useState([]);
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');
    const [coverage, setCoverage] = useState({
        coverageID: "",
        commune: "",
    });

    const handleCommuneChange = (event) => {
        const commune = event.target.value;
        
        //Si la cadena es vacía (esto es para corregir el error de que si se vuelve a "seleccionar comuna" la pagina se cae)
        if(commune === ""){
          setCoverage({
            coverageID: "",
            commune: "",
        })
        }
        else{
          const selectedCoverage = communes.find(
            (c) => c.commune === commune
          );
          setCoverage(selectedCoverage);
        }
    };

    useEffect(() => {
        fetch(`${URL_CIVILO}${RUTA_COBERTURAS}`)
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                setCommunes(data);
            })
            .catch((error) => {
                console.error("Error al obtener comunas: ", error);
            });
    }, []);

    const handleClick = (event) => {

        //Se llama al metodo para evitar que haga su comportamiento por defecto, en este caso enviar el formulario
        event.preventDefault();

        //Se guardan los datos del nuevo usuario para enviarlos al servidor
        const usuarioActualizado = {
            "name": nombre,
            "surname": apellido,
            "email": email,
            "password": password,
            "phoneNumber": `${codigoInternacional}${telefono}`,
            "commune": coverage.commune,
            "startTime": startTime,
            "endTime": endTime,
        }

        //borrar estos print despues
        // console.log("El objeto usuario esss:");
        // console.log(usuarioActualizado);

        const user = JSON.parse(sessionStorage.getItem('user'));
        const id = user.userID;
        //console.log(user.userID);

         const {name,surname,commune} = usuarioActualizado;

        //Si alguno de los campos está vacío, se muestra una alerta indicando que no se ha podido realizar la actualización
        if(name === "" || surname === "" || usuarioActualizado.email === "" || usuarioActualizado.password === "" || telefono === "" || commune === ""){
          showAlert("Error: Debe completar todos los campos");

        }

        else{ 

          fetch(`${URL_CIVILO}/users/${id}`, {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify(usuarioActualizado)
          })
              .then(response => {
                  if (!response.ok) {
                      throw new Error('Error al actualizar usuario');
                  }
                  const url = "/";
                  window.location.replace(url);
                  return response.json();
              })
              .then(data => {
                  console.log(data);
              })
              .catch(error => {
                  console.error(error);
              });
          }
    }
    return (
        <Formulario>
            <EspacioVertical />
            <Titulo>Actualizar información
            </Titulo>

            <NombreApellido>
                <Nombres type="text" name="nombres" placeholder='Nombre(s)' onChange={(e) => setNombre(e.target.value)} />
                <Apellidos type="text" name="nombres" placeholder='Apellido(s)' onChange={(e) => setApellido(e.target.value)} />
            </NombreApellido>

            {/*Espacio creado para separar los elementos*/}
            <EspacioVertical />

            <Input type="email" name="email" placeholder='Correo Electrónico' onChange={(e) => setEmail(e.target.value)} />
            <EspacioVertical />
            <Input type="password" name="password" placeholder='Contraseña Nueva' onChange={(e) => setPassword(e.target.value)} />
            <EspacioVertical />

            <NumeroTelefonico>
                <CodigosInternacionales onChange={(e) => setCodigoInternacional(e.target.value)}>
                    <option selected>+56</option>
                    <option>+54</option>
                </CodigosInternacionales>
                <InputTelefono type='tel' name='telefono' placeholder='Número Telefónico (Ejemplo: 9 1234 5678)' onChange={(e) => setTelefono(e.target.value)}></InputTelefono>
            </NumeroTelefonico>

            <EspacioVertical />

            <RegionComuna>
                <select
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
                </select>
            </RegionComuna>

            <EspacioVertical />

            <StartTime>
              <label>Horario disponible:</label> 
              <label>Horario inicio:</label>
              <input type="time" onChange={(e) => setStartTime(e.target.value)} />
            </StartTime>   

            <EspacioVertical />   

            <EndTime>
              <label>Horario fin:</label>
              <input type="time" onChange={(e) => setEndTime(e.target.value)} />
            </EndTime>      

            <EspacioVertical />
            <SubmitButton onClick={handleClick}>Actualizar</SubmitButton>
            {/* lo ultimo que hice fue configurar que al presionar el boton no se haga nada, pq antes se hacia eso de enviar la info al swervidor
    pero ahora no hace nada, por lo que yo puedo configurar lo que se hace*/}
        </Formulario>




    )
};
