import styled from "styled-components";
import { Link } from "react-router-dom"; // Importamos Link de React Router

const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
`;

const Button = styled.button`
  width: 200px;
  height: 50px;
  margin: 0 10px;
  border: none;
  border-radius: 5px;
  background-color: #2196f3;
  color: #fff;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.2s ease-in-out;

  &:hover {
    background-color: #1565c0;
  }
`;

const LeftButton = styled(Button)`
  background-color: #8bc34a;

  &:hover {
    background-color: #689f38;
  }
`;

const RightButton = styled(Button)`
  background-color: #ff9800;

  &:hover {
    background-color: #f57c00;
  }
`;

const ExecutiveHome = () => {
  return (
    <ButtonContainer>
      <Link to="/executive/executiveAssignment"> {/* Agregamos el Link */}
        <LeftButton>Asignar Solicitudes</LeftButton> {/* Envolvemos el botón con el Link */}
      </Link>
      <RightButton>Solicitudes Completadas</RightButton>
    </ButtonContainer>
  );
};

export default ExecutiveHome;
