import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import InfoIcon from '@mui/icons-material/Info';
import { Button, IconButton } from '@mui/material';
import { getAllUsers } from '../api/civilo_roller_api';
import AddIcon from '@mui/icons-material/Add';
import ModalRegister from './ModalRegister';
import DeleteIcon from '@mui/icons-material/Delete';
import ModalDeleteUser from './Modals/ModalDeleteUser';


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

const BotonCrearUsuario = styled.button` 
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

const LogoAdd = styled(AddIcon)`
  width: 100px;
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

export const UsersTable = ({ tipoUsuario }) => {

  //Para manejar el modal para registrar un ejecutivo o admin nuevo
  const [modalOpen, setModalOpen] = useState(false);
  const handleModalClose = () => {
    setModalOpen(false);
  };

  const crearUsuario = (tipoUsuario) => {

    if(tipoUsuario.toLowerCase() === "ejecutivo"){
      return(
        <StyledDiv>
          <h3>Crear Nuevo Ejecutivo</h3>
          <BotonCrearUsuario onClick={() => setModalOpen(true)}>
            Crear
            <AddIcon sx={{ fontSize: 40 }}/>
          </BotonCrearUsuario> 
        </StyledDiv>
      )
    }
    if(tipoUsuario.toLowerCase() === "administrador"){
      return(
        <StyledDiv>
          <h3>Crear Nuevo Administrador</h3>
          <BotonCrearUsuario onClick={() => setModalOpen(true)}>
            Crear
            <AddIcon sx={{ fontSize: 40 }}/>
          </BotonCrearUsuario> 
        </StyledDiv>
      )
    }

  }











  const [usuarios, setUsuarios] = useState([]);
  const [usuariosEspecificos, setUsuariosEspecificos] = useState([]);
  const [orderBy, setOrderBy] = useState('');
  const [order, setOrder] = useState('asc');


  // Estado para controlar la apertura y cierre del modal para cada usuario
  const [modalDeleteUserOpen, setModalDeleteUserOpen] = useState({});

  // Función para abrir el modal de eliminación para un usuario específico
  const handleModalDeleteUserOpen = (userID) => {
    setModalDeleteUserOpen((prevState) => ({
      ...prevState,
      [userID]: true,
    }));
  };

  // Función para cerrar el modal de eliminación para un usuario específico
  const handleModalDeleteUserClose = (userID) => {
    setModalDeleteUserOpen((prevState) => ({
      ...prevState,
      [userID]: false,
    }));
  };

  useEffect(() => {
    //Se hace la peticion al servidor de todos los usuarios
    getAllUsers()
      .then((data) => {
        setUsuarios(data);
        //se guarda en listado, los usuarios con un rol especifico, el cual se recibe por props
        let listado = data.filter(
          (usuario) =>
            usuario.role.accountType.toLowerCase() === tipoUsuario
        );
        setUsuariosEspecificos(listado);
      })
      .catch((error) => console.log("Error al obtener usuarios", error));
  }, [tipoUsuario]);

  const headers = [
    {id: 'id user', label: 'ID de Usuario'},
    { id: 'name', label: 'Nombre' },
    { id: 'surname', label: 'Apellido' },
    {id: 'Fecha de Nacimiento', label: 'Fecha de Nacimiento'},
    { id: 'email', label: 'Email' },
    { id: 'phoneNumber', label: 'Contacto' },
    { id: 'commune', label: 'Comuna' },
    {id: 'empresa', label: 'Empresa'},
    { id: 'acciones', label: 'Acciones' }
  ];

  const handleSort = (columnId) => {
    const isAsc = orderBy === columnId && order === 'asc';
    setOrder(isAsc ? 'desc' : 'asc');
    setOrderBy(columnId);
  };

  const compareValues = (a, b) => {
    if (orderBy === 'Fecha de Nacimiento') {
      const dateA = new Date(a.birthDate);
      const dateB = new Date(b.birthDate);
      if (dateA < dateB) {
        return order === 'asc' ? -1 : 1;
      }
      if (dateA > dateB) {
        return order === 'asc' ? 1 : -1;
      }
      return 0;
    } else if (orderBy === 'id user') {
      if (a.userID < b.userID) {
        return order === 'asc' ? -1 : 1;
      }
      if (a.userID > b.userID) {
        return order === 'asc' ? 1 : -1;
      }
      return 0;
    } else if (orderBy === 'empresa') {
      if (a.empresa < b.empresa) {
        return order === 'asc' ? -1 : 1;
      }
      if (a.empresa > b.empresa) {
        return order === 'asc' ? 1 : -1;
      }
      return 0;
    } else {
      if (a[orderBy] < b[orderBy]) {
        return order === 'asc' ? -1 : 1;
      }
      if (a[orderBy] > b[orderBy]) {
        return order === 'asc' ? 1 : -1;
      }
      return 0;
    }
  };

  const sortedUsuarios = [...usuariosEspecificos].sort(compareValues);

  return (
    <TableContainer>
      <ModalRegister open={modalOpen} onClose={handleModalClose} />
      {crearUsuario(tipoUsuario)}
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
          {sortedUsuarios.map((usuario) => (
            <TableRow key={usuario.userID}>
              <TableCell>{usuario.userID}</TableCell>
              <TableCell>{usuario.name}</TableCell>
              <TableCell>{usuario.surname}</TableCell>
              <TableCell>{usuario.birthDate}</TableCell>
              <TableCell>{usuario.email}</TableCell>
              <TableCell>{usuario.phoneNumber}</TableCell>
              <TableCell>{usuario.commune}</TableCell>
              {usuario.role.accountType.toLowerCase() === 'vendedor' ? 
              <TableCell>{usuario.companyName}</TableCell>
              : <TableCell><p style={{ fontStyle: 'italic' }}>No Aplica</p></TableCell>
              }
              <TableCell>
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
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </TableWrapper>
    </TableContainer>
  );
};










































































// import React, { useEffect, useState } from 'react';
// import styled from 'styled-components';
// import InfoIcon from '@mui/icons-material/Info';
// import { IconButton } from '@mui/material';
// import { getAllUsers } from '../api/civilo_roller_api';




// const TableContainer = styled.div`
//   width: 100%;
//   overflow-x: auto; /* Habilita el desplazamiento horizontal */
// `;

// const TableWrapper = styled.table`
//   width: 100%;
//   border-collapse: collapse;
// `;

// const TableHeader = styled.th`
//   padding: 8px;
//   text-align: left;
//   cursor: pointer;
//   background-color: #f5f5f5;
// `;

// const TableBody = styled.tbody``;

// const TableRow = styled.tr``;

// const TableCell = styled.td`
//   padding: 8px;
//   border-bottom: 1px solid #ddd;
// `;

// const SortIcon = styled.span`
//   margin-left: 5px;
// `;

// export const UsersTable = ({tipoUsuario}) => {

//     //Define los datos de ejemplo
//   const rows = [
//     { nombre: 'John', apellido: 'Doe', email: 'john.doe@example.com', contacto: '123456789', comuna: 'Puente Alto' },
//     { nombre: 'Jane', apellido: 'Smith', email: 'jane.smith@example.com', contacto: '987654321', comuna: 'La Florida'},
//     //Agrega más filas aquí...
//   ];

//   // Define los nombres de las cabeceras y los datos a ordenar
//   const headers = [
//     { id: 'nombre', label: 'Nombre' },
//     { id: 'apellido', label: 'Apellido' },
//     { id: 'email', label: 'Email' },
//     { id: 'contacto', label: 'Contacto' },
//     {id: 'comuna', label: 'Comuna'}, 
//     {id: 'acciones', label: 'Acciones'}
//   ];

//   const [usuarios, setUsuarios] = useState([]);
//   const [usuariosEspecificos, setUsuariosEspecificos] = useState([]);

//   useEffect(() => {
//     //Se obtienen los usuarios de la plataforma
//     getAllUsers()
//     .then((data) => {
//       setUsuarios(data)
//       console.log(data);
//       let listado = [];
      
//       //se recorre el listado de usuarios
//       data.map((usuario) => {
//         //si el rol de usuario coincide con el ingresado como prop
//         if(usuario.role.accountType.toLowerCase() === tipoUsuario){
//           //Se agrega al listado que luego sera mostrado en la tabla
//           listado.push(usuario);
//         }
//       })
//       //Se ajusta el listado de usuarios con el rol especifico
//       setUsuariosEspecificos(listado);
//     })
//     .catch((error) => console.log("Error al obtener usuarios", error));
//   }, [])


//   const [orderBy, setOrderBy] = useState('');
//   const [order, setOrder] = useState('asc');

//   // Función para manejar el ordenamiento al hacer clic en una cabecera
//   const handleSort = (columnId) => {
//     const isAsc = orderBy === columnId && order === 'asc';
//     setOrder(isAsc ? 'desc' : 'asc');
//     setOrderBy(columnId);
//   };

//   // Función para comparar los valores y ordenar la tabla
//   const compareValues = (a, b) => {
//     if (a[orderBy] < b[orderBy]) {
//       return order === 'asc' ? -1 : 1;
//     }
//     if (a[orderBy] > b[orderBy]) {
//       return order === 'asc' ? 1 : -1;
//     }
//     return 0;
//   };

//   // Ordena los datos en base al orden seleccionado
//   const usuariosOrdenados = [...usuariosEspecificos].sort(compareValues);

//   return (
//     <TableContainer>
//       <TableWrapper>
//         <thead>
//           <tr>
//             {headers.map((header) => (
//               <TableHeader key={header.id} onClick={() => handleSort(header.id)}>
//                 {header.label}
//                 {orderBy === header.id && (
//                   <SortIcon>{order === 'asc' ? '▲' : '▼'}</SortIcon>
//                 )}
//               </TableHeader>
//             ))}
//           </tr>
//         </thead>
//         <TableBody>
//           {usuariosOrdenadoss.map((usuario) => (
//             <TableRow key={usuario.userID}>
//               <TableCell>{usuario.name}</TableCell>
//               <TableCell>{usuario.surname}</TableCell>
//               <TableCell>{usuario.email}</TableCell>
//               <TableCell>{usuario.phoneNumber}</TableCell>
//               <TableCell>{usuario.commune}</TableCell>
//               <TableCell>
//                 <IconButton>
//                   <InfoIcon/>
//                 </IconButton>
//               </TableCell>
//             </TableRow>
//           ))}
//         </TableBody>
//       </TableWrapper>
//     </TableContainer>
//   );
// };
