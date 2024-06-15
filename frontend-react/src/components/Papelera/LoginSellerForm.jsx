import React, { useState } from "react";
import styled from "styled-components";
import { showAlert } from "../../functions/funciones";

const RUTA_HOME = "/";

const LoginContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
`;

const Column = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const FormContainer = styled.div`
  width: 400px;
  padding: 30px;
  background-color: #f5f5f5;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Input = styled.input`
  display: block;
  width: 100%;
  margin: 10px 0;
  padding: 10px;
  border: none;
  border-radius: 5px;
  background-color: #ebebeb;
  font-size: 16px;
`;

const Button = styled.button`
  display: block;
  width: 100%;
  margin-top: 20px;
  padding: 10px;
  border: none;
  border-radius: 5px;
  background-color: #2196f3;
  color: #fff;
  font-size: 16px;
  cursor: pointer;
`;

const Image = styled.img`
  width: 340px;
  height: 340px;
  margin-right: 30px;
`;


const SellerLoginForm = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/sellers/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });
      if (response.ok) {
        const email = formData.email;
        const userData = await fetch(`http://localhost:8080/users/${email}`);
        const data = await userData.json();
        sessionStorage.setItem('user', JSON.stringify(data));
        console.log(sessionStorage.getItem('user'));
        const url = `/seller?email=${encodeURIComponent(
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


  return (
    <LoginContainer>
      <Column>
        <Image src="https://fernapetcl.vtexassets.com/arquivos/ids/169337-800-auto?v=637677510279630000&width=800&height=auto&aspect=true" />
      </Column>
      <Column>
        <FormContainer>
          <h2>Iniciar Sesión</h2>
          <form onSubmit={handleSubmit}>
            <Input
              type="email"
              name="email"
              placeholder="E-mail"
              value={formData.email}
              onChange={handleInputChange}
              required
            />
            <Input
              type="password"
              name="password"
              placeholder="Contraseña"
              value={formData.password}
              onChange={handleInputChange}
              required
            />
            <Button type="submit">Iniciar sesión</Button>
          </form>
          <p>Ingresar como <a href="/login">cliente</a>, <a href="/loginExecutive">ejecutivo</a> o <a href="/loginAdmin">administrador</a></p>
        </FormContainer>
      </Column>
    </LoginContainer>
  );
};
export default SellerLoginForm;