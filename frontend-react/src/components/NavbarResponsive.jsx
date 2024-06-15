import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import BlindsIcon from '@mui/icons-material/Blinds';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { Link, redirect, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useEffect } from 'react';
import { cerrarSesionUsuario } from '../api/civilo_roller_api';

const RUTA_PAGE_LOGIN = "/login";
const RUTA_PAGE_HOME = "/";
const RUTA_PAGE_HOME_ADMIN = "/admin";
const RUTA_PAGE_REGISTER = "/register";
const RUTA_PAGE_SOLICITUDES_CLIENTE = "/client/request";
const RUTA_PAGE_ASIGNACIONES_VENDEDOR = "/seller/assignnedRequest";
const RUTA_PAGE_COTIZAR_VENDEDOR = "/seller/quote";
const RUTA_PAGE_SOLICITUDES_EJECUTIVO = "/executive/requestManagement";
const RUTA_PAGE_UPDATE_INFO_CLIENTE = "/client/updateInfo";
const RUTA_PAGE_ADMIN_READUSERS = "/admin/readUsers";
const RUTA_PAGE_QUOTES = "/admin/quotes";
const RUTA_PAGE_READ_CURTAINS = "/admin/readCurtains";
const RUTA_PAGE_SOLICITUDES_ADMIN = "/admin/requestManagement"
const RUTA_PAGE_UPDATE_INFO_VENDEDOR = "/seller/sellerInformation"
const RUTA_PAGE_SELLER_MY_QUOTES = "/seller/myQuotes";


function NavbarResponsive() {

  //const pages = ['Inicio','otra cosa', 'xd'];
  //const settings = ['Iniciar Sesion', 'Registrarse'];

  //console.log("acabo de entrar a NavbarResponsive");
  //console.log("LA SESION ES ",JSON.parse(sessionStorage.getItem('user')));
  const pagesHome = ['Inicio'];
  const settingsHome = ['Iniciar Sesion', 'Registrarse'];

  const pagesCLiente = ['Inicio', 'Mis Solicitudes'];
  const settingsCliente = ['Cerrar Sesión', 'Actualizar Información'];

  const pagesVendedor = ['Inicio', 'Mis Asignaciones', 'Cotizar', 'Mis Cotizaciones'];
  const settingsVendedor = ['Cerrar Sesión', 'Actualizar Información', 'Actualizar Cobertura'];

  const pagesEjecutivo = ['Inicio', 'Solicitudes'];
  const settingsEjecutivo = ['Cerrar Sesión'];

  const pagesAdmin = ['Inicio', 'Administrar', 'Cotizaciones', 'Usuarios', 'Productos', 'Solicitudes'];
  const settingsAdmin = ['Cerrar Sesión'];

  let userLocalStorage = JSON.parse(sessionStorage.getItem('user'));
  const [pages, setPages] = useState(pagesHome);
  const [settings, setSettings] = useState(settingsHome);
  const [sesionUsuario, setSesionUsuario] = useState(userLocalStorage);

  
  //En caso de cambiar el tipo de sesion, se actualizara la barra de navegacion
  useEffect(() => {
    //Si no hay ninguna sesion activa, se muestran las siguientes opciones en la barra de navegacion
    if (sesionUsuario === null) {
      setPages(pagesHome);
      setSettings(settingsHome);
    }
    //Si existe una sesion activa y es de tipo cliente
    else if(userLocalStorage.role.accountType.toLowerCase() === 'cliente'){
      //se muestran las siguientes opciones en la barra de navegacion
      setSesionUsuario(userLocalStorage.role.accountType);
      setPages(pagesCLiente);
      setSettings(settingsCliente);
    }
    //Si existe una sesion activa y es de tipo vendedor
    else if(userLocalStorage.role.accountType.toLowerCase() === 'vendedor'){
      //se muestran las siguientes opciones en la barra de navegacion
      setSesionUsuario(userLocalStorage.role.accountType);
      setPages(pagesVendedor);
      setSettings(settingsVendedor);
    }
    //Si existe una sesion activa y es de tipo ejecutivo
    else if(userLocalStorage.role.accountType.toLowerCase() === 'ejecutivo'){
      //se muestran las siguientes opciones en la barra de navegacion
      setSesionUsuario(userLocalStorage.role.accountType);
      setPages(pagesEjecutivo);
      setSettings(settingsEjecutivo);
    }
    //Si existe una sesion activa y es de tipo administrador
    else if(userLocalStorage.role.accountType.toLowerCase() === 'administrador'){
      //se muestran las siguientes opciones en la barra de navegacion
      setSesionUsuario(userLocalStorage.role.accountType);
      setPages(pagesAdmin);
      setSettings(settingsAdmin);
    }

  }, [sesionUsuario]);


  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const [anchorElUser, setAnchorElUser] = React.useState(null);

  //Evento cuando es presionado el icono de hamburguesa disponible en pantallas pequeñas
  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };

  //Evento cuando es presionado el menu con el logo de usuario
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };



  //Evento cuando alguno de los item de la barra de navegacion es presionado
  const handleCloseNavMenu = (event) => {
    setAnchorElNav(null);
    const itemSeleccionado = event.target.textContent.toLowerCase();   
  };

  //Evento cuando es presionado alguno de los items del menu de usuario
  //AL FINAL NO CACHO QUE HACE ESTA FUNCION ya que cambie la funcion del onCLick del item del menu usuario
  //la voy a dejar ahi por si acaso mas que nada,
  //ya que venia en la plantilla de la navbar
  const handleCloseUserMenu = (event) => {
    setAnchorElUser(null);
    const itemSeleccionado = event.target.textContent.toLowerCase();
  };

  //Funcion que se activa al presionar algun item del menu del usuario
  const itemUserPresionado = (event) => {
    const itemSeleccionado = event.target.textContent.toLowerCase();
    
    //si el item seleccionado es cerrar sesion
    if(itemSeleccionado === 'cerrar sesión'){
      //Se cierra la sesion del cliente y se actualiza el valor de sesion usuario
      //para actualizar las opciones de la barra de navegacion;
      setAnchorElUser(null);
      setSesionUsuario(null);
      cerrarSesionUsuario();
    }
  }


  // //funcion para cerrar la sesion de un usuario tipo cliente
  // const cerrarSesionCliente = () => {
  //   sessionStorage.removeItem('user');
  //   fetch("http://localhost:8080/users/logout", {
  //     method: "POST",
  //     headers: {
  //       "Content-Type": "application/json",
  //     },
  //     body: JSON.stringify({}),
  //   })
  //     .then((res) => {
  //       if (res.ok) {
  //         window.location.href = "/";
  //       } else {
  //         throw new Error("Error en la solicitud de logout");
  //       }
  //     })
  //     .catch((err) => console.error(err));
  // };

  //Funcion que evalua el item del menu y asigna la ruta que se debe seguir cuando este boton sea presionado
  const definirRuta = (item) => {

    const itemSeleccionado = item.toLowerCase();

    if(itemSeleccionado === 'inicio'){
      return RUTA_PAGE_HOME;
    }
    else if(itemSeleccionado === 'iniciar sesion'){
      return RUTA_PAGE_LOGIN;
    }
    else if(itemSeleccionado === 'registrarse'){
      return RUTA_PAGE_REGISTER;
    }
    else if(itemSeleccionado === 'actualizar información'){
      return RUTA_PAGE_UPDATE_INFO_CLIENTE;
    }
    else if(itemSeleccionado === 'actualizar cobertura'){
      return RUTA_PAGE_UPDATE_INFO_VENDEDOR;
    }

    else if(itemSeleccionado === 'mis solicitudes'){
      //Si existe una sesion activa
      if(JSON.parse(sessionStorage.getItem('user')) !== null){
        //Si es de tipo cliente
        if(JSON.parse(sessionStorage.getItem('user')).role.accountType.toLowerCase() === 'cliente'){
          return RUTA_PAGE_SOLICITUDES_CLIENTE;
        }

      }
      
    }
    else if(itemSeleccionado === 'mis asignaciones'){
      //Si existe una sesion activa
      if(JSON.parse(sessionStorage.getItem('user')) !== null){
        //Si es de tipo vendedor
        if(JSON.parse(sessionStorage.getItem('user')).role.accountType.toLowerCase() === 'vendedor'){
          return RUTA_PAGE_ASIGNACIONES_VENDEDOR;
        }
      }
    }
    else if(itemSeleccionado === 'cotizar'){
      //Si existe una sesion activa
      if(JSON.parse(sessionStorage.getItem('user')) !== null){
        //Si es de tipo vendedor
        if(JSON.parse(sessionStorage.getItem('user')).role.accountType.toLowerCase() === 'vendedor'){
          return RUTA_PAGE_COTIZAR_VENDEDOR;
        }
      }
    }
    else if(itemSeleccionado === 'mis cotizaciones'){
      //Si existe una sesion activa
      if(JSON.parse(sessionStorage.getItem('user')) !== null){
        //Si es de tipo vendedor
        if(JSON.parse(sessionStorage.getItem('user')).role.accountType.toLowerCase() === 'vendedor'){
          return RUTA_PAGE_SELLER_MY_QUOTES;
        }
      }
    }
    else if(itemSeleccionado === 'cotizaciones'){
      //Si existe una sesion activa
      if(JSON.parse(sessionStorage.getItem('user')) !== null){
        //Si es de tipo vendedor
        if(JSON.parse(sessionStorage.getItem('user')).role.accountType.toLowerCase() === 'administrador'){
          return RUTA_PAGE_QUOTES;
        }
      }
    }
    else if(itemSeleccionado === 'solicitudes'){
      //Si existe una sesion activa
      if(JSON.parse(sessionStorage.getItem('user')) !== null){
        //Si es de tipo ejecutivo
        if(JSON.parse(sessionStorage.getItem('user')).role.accountType.toLowerCase() === 'ejecutivo'){
          return RUTA_PAGE_SOLICITUDES_EJECUTIVO;
        }
        //Si es de tipo administrador
        else if(JSON.parse(sessionStorage.getItem('user')).role.accountType.toLowerCase() === 'administrador'){
          return RUTA_PAGE_SOLICITUDES_ADMIN;
        }
      }
    }
    else if(itemSeleccionado === 'administrar'){
      //Si existe una sesion activa
      if(JSON.parse(sessionStorage.getItem('user')) !== null){
        //Si es de tipo administrador
        if(JSON.parse(sessionStorage.getItem('user')).role.accountType.toLowerCase() === 'administrador'){
          return RUTA_PAGE_HOME_ADMIN;
        }
      }
    }
    else if(itemSeleccionado === 'usuarios'){
      //Si existe una sesion activa
      if(JSON.parse(sessionStorage.getItem('user')) !== null){
        //Si es de tipo ejecutivo
        if(JSON.parse(sessionStorage.getItem('user')).role.accountType.toLowerCase() === 'administrador'){
          return RUTA_PAGE_ADMIN_READUSERS;
        }
      }
    }

    else if(itemSeleccionado === 'productos'){
      //Si existe una sesion activa
      if(JSON.parse(sessionStorage.getItem('user')) !== null){
        //Si es de tipo ejecutivo
        if(JSON.parse(sessionStorage.getItem('user')).role.accountType.toLowerCase() === 'administrador'){
          return RUTA_PAGE_READ_CURTAINS;
        }
      }
    }



    else return RUTA_PAGE_HOME;
  }

  //Configuracion del menu de hamburguesa disponibles en pantallas moviles
  const mostrarOpcionesMovil = (pages) => {
    let ruta;
    return pages.map((page) => { 
      ruta = definirRuta(page);
      return(
      <Link to={ruta} style={{ textDecoration: 'none' }}> 
      <MenuItem key={page} onClick={handleCloseNavMenu}>
        <Typography textAlign="center" color={"black"}>{page}</Typography>
      </MenuItem>
      </Link>
    )})
  }

  //Configuracion del menu de la barra de navegacion disponible en pantallas de escritorio
  const mostrarOpcionesDesktop = (pages) => {
    let ruta;
    return pages.map((page) => {
      ruta = definirRuta(page);
      return(
        <Link to={ruta} style={{ textDecoration: 'none' }}>
          <Button
          key={page}
          onClick={handleCloseNavMenu}
          sx={{ my: 2, color: 'white', display: 'block' }}
          >
            {page}
          </Button>
        </Link>
      )
    })}

    //Configuracion de menu desplegable con las opciones de usuario
    const mostrarSettingsUsuario = (settings) => { 
      let ruta;
      return settings.map((setting) => {
        ruta = definirRuta(setting);
        return(
          <Link to={ruta} style={{ textDecoration: 'none' }}>
            <MenuItem key={setting} onClick={itemUserPresionado}>
              <Typography textAlign="center" color={"black"}>{setting}</Typography>
            </MenuItem>
          </Link>
        )
      })

    };



  return (
    <AppBar position="static">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <BlindsIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }} />
          <Typography
            variant="h6"
            noWrap
            component={Link}
            to={RUTA_PAGE_HOME}
            sx={{
              mr: 2,
              display: { xs: 'none', md: 'flex' },
              fontFamily: 'monospace',
              fontWeight: 800,
              letterSpacing: '0rem',
              color: 'white',
              textDecoration: 'none',
              marginRight: '30px'
            }}
          >
            ROLLER DECO
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'left',
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{
                display: { xs: 'block', md: 'none' },
              }}
            >
              {mostrarOpcionesMovil(pages)}
            </Menu>
          </Box>

            <BlindsIcon sx={{ display: { xs: 'flex', md: 'none' }, mr: 1 }} />
            <Typography
              variant="h5"
              noWrap
              component={Link}
              to={RUTA_PAGE_HOME}
              sx={{
                mr: 2,
                display: { xs: 'flex', md: 'none' },
                flexGrow: 1,
                fontFamily: 'monospace',
                fontWeight: 700,
                letterSpacing: '.3rem',
                color: 'inherit',
                textDecoration: 'none',
              }}
            >
              ROLLER DECO
            </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
            {mostrarOpcionesDesktop(pages)}
          </Box>

          <Box sx={{ flexGrow: 0 }}>
            <Tooltip title="Open settings">
              <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }} fontSize="large">
                <AccountCircleIcon fontSize="large" sx={{ color: "white" }} />
              </IconButton>
            </Tooltip>
            <Menu
              sx={{ mt: '45px' }}
              id="menu-appbar"
              anchorEl={anchorElUser}
              anchorOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              open={Boolean(anchorElUser)}
              onClose={handleCloseUserMenu}
            >
              {mostrarSettingsUsuario(settings)}
            </Menu>
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  );
}
export default NavbarResponsive;