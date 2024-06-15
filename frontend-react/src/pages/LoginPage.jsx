import React from "react";
//import LoginForm from "../Components/LoginForm";
import NavigationBar from '../components/Papelera/NavigationBar'
import Footer from '../components/Footer'
import LoginForm from "../components/LoginForm";
import NavbarResponsive from "../components/NavbarResponsive";
import SignIn from "../components/SignIn";
import styled from "styled-components";

const StyledDiv = styled.div`
  min-height: 700px;
`;

const LoginPage = () => {
  return (
    <>
      <NavbarResponsive/>
      <StyledDiv>
        <SignIn/>
      </StyledDiv>
      <Footer />

    </>
  )
};

export default LoginPage;