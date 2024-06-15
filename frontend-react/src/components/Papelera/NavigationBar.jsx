import { Link } from 'react-router-dom';
import styled from 'styled-components';

const Navbar = styled.nav`
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 1rem 2rem;
  background-color: #1076d6;
  color: #fff;

  //ESTO ES PARA PROBAR EL BOTON INICIO SOLAMENTE
  //La idea es aplicarle estilo personalizado mas tarde
  button {
    background-color: inherit;
    color: #fff;
    border: none;
    cursor: pointer;
    font-size: 1.3rem;
  }

  button:hover {
  background-color: #0062cc;
  }
`;

const Logo = styled.div`
  font-size: 2rem;
`;

const NavItems = styled.ul`
  display: flex;
  gap: 1rem;
  list-style: none;
`;

const NavItem = styled.li`
  font-size: 1.3rem;
  button {
    background-color: inherit;
    color: #fff;
    border: none;
    cursor: pointer;
    font-size: 1.3rem;
  }

  button:hover {
  background-color: #0062cc;
}
`;

function NavigationBar() {
  return (
    <Navbar>
      <Link to={"/"}>
        <button>Inicio</button>
      </Link>
      <Link to={"/client/request"}>
        <button>ver diseño pagina solicitudes (componente prueba, borrar dps)</button>
      </Link>
      <NavItems>
        <NavItem>
          <Link to={"/login"}>
            <button>Iniciar Sesión</button>
          </Link>
        </NavItem>
        <NavItem>
          <Link to={"/register"}>
            <button>Registrarse</button>
          </Link>
        </NavItem>
      </NavItems>
    </Navbar>
  );
}

export default NavigationBar;

