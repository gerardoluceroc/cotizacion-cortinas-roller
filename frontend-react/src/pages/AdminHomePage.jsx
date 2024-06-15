import React from "react";
import NavigationBarLogged from "../components/Papelera/NavigationBarLogged";
import Footer from '../components/Footer'
import AdminHome from "../components/AdministratorView/AdminHome";
import NavbarResponsive from "../components/NavbarResponsive";
import styled from "styled-components";

const Section = styled.div`

  min-height: calc(100vh - 80px);
  margin-top: 5%;

`;
const AdminHomePage = () => {
  return (
    <>
      <NavbarResponsive/>
      <Section>
        <AdminHome />
      </Section>
      <Footer />
    </>
  )
};

export default AdminHomePage;