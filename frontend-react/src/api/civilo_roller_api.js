//Archivo donde se definirán todas las conexiones a la api de civilo_roller

import { showAlert } from "../functions/funciones"

export const URL_CIVILO = "http://134.122.88.187:8080"
export const URL_HOME = "http://localhost:3000"

const RUTA_ASIGNACIONES_VENDEDOR = "/requests/sellerRequest" // +`${ID DEL VENDEDOR}`
export const RUTA_COBERTURAS = "/coverages"
export const RUTA_CORTINAS = "/curtains"
const RUTA_COVERAGES = "/coverages"
const RUTA_PROFIT_MARGINS = "/profitMargins"
const RUTA_DELETE_USERS = "/users"
export const RUTA_GET_IVA = "/iva";
const RUTA_GET_USERS = "/users"
const RUTA_GET_CURTAIN_PIPES = "/pipes"
export const RUTA_GET_PERMISSIONS = "/permissions";
export const RUTA_GET_REQUESTS = "/requests";
const RUTA_GET_SELLERS = "/sellers";
const RUTA_GET_STATUS = "/status";
const RUTA_HOME = "/"
const RUTA_LOGIN = "/users/login"
export const RUTA_LOGIN_ADMIN = "/users/loginAdmin";
const RUTA_LOGOUT_CLIENTE = "/users/logout"
const RUTA_MIS_SOLICITUDES_CLIENTE = "/requests/clientRequest/";
export const RUTA_POST_SOLICITUDES_CLIENTE = "/requests/clientRequest"; //Peticion POST para crear solicitud del cliente
const RUTA_REGISTER = "/users/register"
const RUTA_ROLES = "/roles";
const RUTA_STATUS = "/status";
export const RUTA_UPDATE_COBERTURAS_VENDEDOR = "/sellers/sellerInformation" //PETICION TIPO POST
const RUTA_VENDEDORES = "/sellers"
export const RUTA_TUBOS = "/pipes"
export const RUTA_COTIZACIONES = "/quotes"
export const RUTA_PDF = "/quotes/:id/pdf" // :id porque el id es dinámico
export const RUTA_REQUESTS = "/requests"
export const RUTA_UPDATE_REQUEST_ASSIGNMENT = "/updateRequest";
export const RUTA_QUOTES = "/quotes";
export const RUTA_SELLER_QUOTES = "/sellerQuotes";
export const RUTA_POST_CORTINA = "/curtains/register"; //Peticion POST para crear cortina
export const RUTA_POST_TUBO = "/pipes"; //Peticion POST para crear cortina
const RUTA_GET_ALL_IVA = "/iva/all"

//Funcion para pedirle al servidor que elimine un usuario especifico
export const deleteUser = async (userID) => {

  let usuario = sessionStorage.getItem('user');
  usuario = JSON.parse(usuario);

  //SI el usuario a eliminar es el mismo que se encuentra con la cuenta activa
  if (usuario.userID === userID) {
    showAlert("Error: No puede eliminar su propia cuenta");
  }

  else {
    const respuesta = fetch(`${URL_CIVILO}${RUTA_DELETE_USERS}/${userID}`, {
      method: 'DELETE'
    })
      .then(response => {
        if (response.ok) {
          showAlert("Usuario eliminado con exito");
          setTimeout(() => {
            //Se recarga la pagina luego de 1 segundo
            window.location.reload();
          }, 1000);
        } else {
          showAlert("Error: Ha ocurrido un problema el eliminar a este usuario");
          setTimeout(() => {
            //Se recarga la pagina luego de 1 segundo
            window.location.reload();
          }, 1000);
        }
      })
      .catch(error => {
        console.error('Error al eliminar el usuario:', error);
        showAlert("Error: Ha ocurrido un problema el eliminar a este usuario");
        setTimeout(() => {
          //Se recarga la pagina luego de 1 segundo
          window.location.reload();
        }, 1000);
        // Hacer algo en caso de que ocurra un error en la solicitud
      });



  }
}

//Funcion para pedirle al servidor que elimine un producto especifico
export const deleteCurtain = async (curtainID) => {
  const respuesta = fetch(`${URL_CIVILO}${RUTA_CORTINAS}/${curtainID}`, {
    method: 'DELETE'
  })
    .then(response => {
      if (response.ok) {
        showAlert("Producto eliminado con exito");
        setTimeout(() => {
          //Se recarga la pagina luego de 1 segundo
          window.location.reload();
        }, 1000);
      } else {
        showAlert("Error: Ha ocurrido un problema el eliminar este producto");
        setTimeout(() => {
          //Se recarga la pagina luego de 1 segundo
          window.location.reload();
        }, 1000);
      }
    })
    .catch(error => {
      console.error('Error al eliminar el producto:', error);
      showAlert("Error: Ha ocurrido un problema el eliminar este producto");
      setTimeout(() => {
        //Se recarga la pagina luego de 1 segundo
        window.location.reload();
      }, 1000);
      // Hacer algo en caso de que ocurra un error en la solicitud
    });
}

//Funcion para pedirle al servidor que elimine un IVA especifico
export const deleteIVA = async (ivaID) => {
  console.log(ivaID)
  const respuesta = fetch(`${URL_CIVILO}${RUTA_GET_IVA}/${ivaID}`, {
    method: 'DELETE'
  })
    .then(response => {
      if (response.ok) {
        showAlert("IVA eliminado con exito");
        setTimeout(() => {
          //Se recarga la pagina luego de 1 segundo
          window.location.reload();
        }, 1000);
      } else {
        showAlert("Error: Ha ocurrido un problema el eliminar este IVA");
        setTimeout(() => {
          //Se recarga la pagina luego de 1 segundo
          window.location.reload();
        }, 1000);
      }
    })
    .catch(error => {
      console.error('Error al eliminar el IVA:', error);
      showAlert("Error: Ha ocurrido un problema el eliminar este IVA");
      setTimeout(() => {
        //Se recarga la pagina luego de 1 segundo
        window.location.reload();
      }, 1000);
      // Hacer algo en caso de que ocurra un error en la solicitud
    });
}

//Funcion para pedirle al servidor que elimine un producto especifico
export const deleteCurtainPipe = async (pipeID) => {
  const respuesta = fetch(`${URL_CIVILO}${RUTA_GET_CURTAIN_PIPES}/${pipeID}`, {
    method: 'DELETE'
  })
    .then(response => {
      if (response.ok) {
        showAlert("Tubo eliminado con exito");
        setTimeout(() => {
          //Se recarga la pagina luego de 1 segundo
          window.location.reload();
        }, 1000);
      } else {
        showAlert("Error: Ha ocurrido un problema el eliminar este tubo");
        setTimeout(() => {
          //Se recarga la pagina luego de 1 segundo
          window.location.reload();
        }, 1000);
      }
    })
    .catch(error => {
      console.error('Error al eliminar el tubo:', error);
      showAlert("Error: Ha ocurrido un problema el eliminar este tubo");
      setTimeout(() => {
        //Se recarga la pagina luego de 1 segundo
        window.location.reload();
      }, 1000);
      // Hacer algo en caso de que ocurra un error en la solicitud
    });
}

//Funcion para pedirle al servidor que elimine un margen de utilidad especifico
export const deleteProfitMargin = async (profitMarginID) => {
  const respuesta = fetch(`${URL_CIVILO}${RUTA_PROFIT_MARGINS}/${profitMarginID}`, {
    method: 'DELETE'
  })
    .then(response => {
      if (response.ok) {
        showAlert("Margen eliminado con exito");
        setTimeout(() => {
          //Se recarga la pagina luego de 1 segundo
          window.location.reload();
        }, 1000);
      } else {
        showAlert("Error: Ha ocurrido un problema el eliminar este margen");
        setTimeout(() => {
          //Se recarga la pagina luego de 1 segundo
          window.location.reload();
        }, 1000);
      }
    })
    .catch(error => {
      console.error('Error al eliminar el margen:', error);
      showAlert("Error: Ha ocurrido un problema el eliminar este margen");
      setTimeout(() => {
        //Se recarga la pagina luego de 1 segundo
        window.location.reload();
      }, 1000);
      // Hacer algo en caso de que ocurra un error en la solicitud
    });
}

//Funcion que asigna a un vendedor a una solicitud realizada por un cliente
export const asignarVendedor = async (id_solicitud, id_vendedor) => {

  try {
    const response = await fetch(`${URL_CIVILO}${RUTA_REQUESTS}${RUTA_UPDATE_REQUEST_ASSIGNMENT}/${id_solicitud}/${id_vendedor}`, {
      method: 'POST',
    });

    if (response.ok) {
      return 'Asignación realizada con éxito';
    } else {
      throw new Error('Lo sentimos, asignación fallida');
    }
  } catch (error) {
    console.log('Error:', error);
    throw new Error('Lo sentimos, asignación fallida');
  }
};



//Funcion que permite obtener todos los usuarios
export const getAllUsers = async () => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_GET_USERS}`);
  const usuarios = await respuesta.json();
  return usuarios;
}

//Funcion que permite obtener todos los IVA utilizados
export const getAllIVA = async () => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_GET_ALL_IVA}`);
  const iva = await respuesta.json();
  return iva;
}

//Funcion que permite obtener todos los tubos
export const getAllCurtainPipes = async () => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_GET_CURTAIN_PIPES}`);
  const tubos = await respuesta.json();
  return tubos;
}

export const obtenerVendedor = async (idVendedor) => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_VENDEDORES}/${idVendedor}`);
  const vendedor = await respuesta.json();
  return vendedor;
}

export const obtenerCoberturas = async () => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_COBERTURAS}`)
  const coberturasJSON = await respuesta.json();
  return coberturasJSON;

}

export const obtenerAsignacionesVendedor = async (id_vendedor) => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_ASIGNACIONES_VENDEDOR}/${id_vendedor}`);
  const asignaciones = await respuesta.json();
  return asignaciones;
}

//Funcion que pide los datos al servidor de las solicitudes de un cliente especifico
export const solicitarMisSolicitudes = async (id_usuario) => {
  const response = await fetch(`${URL_CIVILO}${RUTA_MIS_SOLICITUDES_CLIENTE}${id_usuario}`);
  const solicitudes = await response.json();
  return solicitudes;
}

//Funcion para obtener los roles disponibles en la pagina
export const getAllRoles = async () => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_ROLES}`);
  const roles = await respuesta.json();
  return roles;

}

//Funcion para obtener las cortinas disponibles en la pagina
export const getAllCurtains = async () => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_CORTINAS}`);
  const cortinas = await respuesta.json();
  return cortinas;

}

//Funcion para obtener las comunas de cobertura disponibles en la pagina
export const getAllCoverages = async () => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_COVERAGES}`);
  const comunas = await respuesta.json();
  return comunas;

}

//Funcion para obtener todos los margenes de utilidad utilizados en la pagina
export const getAllProfitMargins = async () => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_PROFIT_MARGINS}`);
  const profitMargins = await respuesta.json();
  return profitMargins;
}

//Funcion para obtener los estados posibles de las solicitudes disponibles en la pagina
export const getAllStatus = async () => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_STATUS}`);
  const status = await respuesta.json();
  return status;

}

//Función que permite actualizar el estado de una solicitud
export const updateRequestStatus = async (id_solicitud, solicitudActualizada) => {
  try {
    const url = `${URL_CIVILO}${RUTA_REQUESTS}/${id_solicitud}`; 

    await fetch(url, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(solicitudActualizada),
    });

    // No necesitamos esperar la respuesta del servidor, ya que solo se espera un código de estado 200 OK.
  } catch (error) {
    console.error('Error al enviar la solicitud:', error);
  }
};

//Funcion para obtener todas las solicitudes realizadas en la pagina
export const getAllRequests = async () => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_GET_REQUESTS}`);
  const solicitudes = await respuesta.json();
  return solicitudes;
}

//Función para obtener todas las cotizaciones del sistema
export const getAllQuotes = async () => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_QUOTES}`);
  const cotizaciones = await respuesta.json();
  return cotizaciones;
}

//Función para obtener todas las cotizaciones realizadas por un vendedor especifico
export const getAllSellerQuotes = async (id_vendedor) => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_QUOTES}${RUTA_SELLER_QUOTES}/${id_vendedor}`);
  const cotizaciones = await respuesta.json();
  return cotizaciones;
}

//Funcion para obtener todas los solicitudes registradas en la pagina
export const getAllSellers = async () => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_GET_SELLERS}`);
  const vendedores = await respuesta.json();
  return vendedores;
}

//Funcion en la cual se obtiene una solicitud de cortina de acuerdo a su id
export const getRequestById = async (id_solicitud) => {
  const respuesta = await fetch(`${URL_CIVILO}${RUTA_GET_REQUESTS}/${id_solicitud}`);
  const solicitud = await respuesta.json();
  return solicitud;
}

//Funcion para realizar la solicitud del pdf de la cotizacion para descargar
export const solicitarPDF = (sellerEntity, id_solicitud) => {
  //Se envia la peticion POST al servidor
  fetch(`${URL_CIVILO}${RUTA_COTIZACIONES}/${id_solicitud}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(sellerEntity),
  })
    .then((response) => {
      if (response.ok) {
        return response.json(); // Parsear el cuerpo de la respuesta como un objeto JSON   
      } else {
        console.log("Fallo al enviar la solicitud del PDF");
      }
    })
    .then((data) => {
      const fileName = data.fileName; // Obtener el nombre del archivo del objeto JSON
      const pdfData = data.pdfData; // Obtener los datos del PDF codificados en Base64

      console.log("Nombre del archivo:", fileName);

      // Decodificar la cadena Base64 a bytes
      const pdfBytes = Uint8Array.from(atob(pdfData), c => c.charCodeAt(0));

      // Crear un objeto Blob a partir de los bytes del PDF
      const blob = new Blob([pdfBytes], { type: "application/pdf" });

      // Crear un objeto URL para el blob
      const url = URL.createObjectURL(blob);

      // Crear un enlace temporal para descargar el archivo
      const link = document.createElement("a");
      link.href = url;
      link.download = fileName; // Nombre del archivo
      link.click();

      // Liberar el objeto URL
      URL.revokeObjectURL(url);

    })
    .catch((error) => {
      console.error("Fallo al enviar la solicitud del PDF: ", error);
    });

}

export const iniciarSesion = (usuario) => {
  //Se envia la peticion POST al servidor
  fetch(`${URL_CIVILO}${RUTA_LOGIN}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(usuario),
  })
    .then((response) => {
      if (response.ok) {
        console.log("Login successful");
      } else {
        console.log("Login failed");
      }
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}


export const registrarUsuario = (usuario) => {
  fetch(`${URL_CIVILO}${RUTA_REGISTER}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(usuario),
  })
    .then((response) => {
      if (response.ok) {
        console.log("Registro exitoso");
        console.log(usuario.role.accountType);
        if (usuario.role.accountType === "Cliente") {
          window.location.href = '/login';
        } else if (usuario.role.accountType === "Vendedor") {
          localStorage.setItem('email', JSON.stringify(usuario.email));
          console.log(usuario);
          window.location.href = '/seller/sellerInformation';
        }
        //si el usuario registrado es de tipo ejecutivo o administrador
        else if ((usuario.role.accountType.toLowerCase() === "ejecutivo") || (usuario.role.accountType.toLowerCase() === "administrador")) {
          showAlert("Usuario agregado con exito");
          setTimeout(() => {
            //Se recarga la pagina luego de 1 segundo
            window.location.reload();

          }, 1000);
        }
      } else {
        console.log("Registro fallido");
      }
    })
    .catch((error) => {
      showAlert("Error al registrar usuario");
      console.error("Error:", error);
    });
}

// Función para crear un iva como administrador
export const registrarIVA = (iva) => {
  fetch(`${URL_CIVILO}${RUTA_GET_IVA}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(iva),
  })
    .then((response) => {
      if (response.ok) {
        console.log("Registro exitoso");
      } else {
        console.log("Registro fallido");
      }
      window.location.reload();
    })
    .catch((error) => {
      showAlert("Error al registrar el IVA");
      console.error("Error:", error);
    });
}

// Función para crear una cortina como administrador
export const registrarCortina = (cortina) => {
  fetch(`${URL_CIVILO}${RUTA_POST_CORTINA}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(cortina),
  })
    .then((response) => {
      if (response.ok) {
        console.log("Registro exitoso");
      } else {
        console.log("Registro fallido");
      }
      window.location.reload();
    })
    .catch((error) => {
      showAlert("Error al registrar tipo de corina");
      console.error("Error:", error);
    });
}

// Función para crear un tubo como administrador
export const registrarTubo = (tubo) => {
  fetch(`${URL_CIVILO}${RUTA_POST_TUBO}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(tubo),
  })
    .then((response) => {
      if (response.ok) {
        console.log("Registro exitoso");
      } else {
        console.log("Registro fallido");
      }
      window.location.reload();
    })
    .catch((error) => {
      showAlert("Error al registrar tipo de corina");
      console.error("Error:", error);
    });
}

// Función para crear un margen de utilidad como administrador
export const registrarMargenUtilidad = (margenUtilidad) => {
  fetch(`${URL_CIVILO}${RUTA_PROFIT_MARGINS}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(margenUtilidad),
  })
    .then((response) => {
      if (response.ok) {
        console.log("Registro exitoso");
      } else {
        console.log("Registro fallido");
      }
      window.location.reload();
    })
    .catch((error) => {
      showAlert("Error al registrar tipo de corina");
      console.error("Error:", error);
    });
}

//funcion para cerrar la sesion de un usuario tipo cliente
export const cerrarSesionUsuario = () => {
  sessionStorage.removeItem('user');
  fetch(`${URL_CIVILO}${RUTA_LOGOUT_CLIENTE}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({}),
  })
    .then((res) => {
      if (res.ok) {
        window.location.href = RUTA_HOME;
      } else {
        throw new Error("Error en la solicitud de logout");
      }
    })
    .catch((err) => console.error(err));
};


export const iniciarSesionCliente = async (event, formData) => {
  event.preventDefault();
  try {
    const response = await fetch(`${URL_CIVILO}${RUTA_LOGIN}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(formData),
    });
    if (response.ok) {
      const email = formData.email;
      const userData = await fetch(`${URL_CIVILO}/users/${email}`);
      const data = await userData.json();
      sessionStorage.setItem('user', JSON.stringify(data));
      console.log(sessionStorage.getItem('user'));
      const url = `/client?email=${encodeURIComponent(
        formData.email
      )}&password=${encodeURIComponent(formData.password)}`;
      window.location.replace(RUTA_HOME);
    } else {
      showAlert("Email o contraseña incorrectos");
    }
  } catch (error) {
    console.error(error);
    showAlert("Error al iniciar sesión");
  }
};







