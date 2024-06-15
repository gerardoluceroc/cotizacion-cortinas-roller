import React from "react";

import Footer from '../components/Footer'
import NavbarResponsive from "../components/NavbarResponsive";
import CurtainPipesList from "../components/AdministratorView/AdministrateCurtainPipes/CurtainPipesList";

const AdminReadCurtainPipesPage = () => {
  return (
    <>
      <NavbarResponsive/>
      <CurtainPipesList />
      <Footer />
    </>
  )
};

export default AdminReadCurtainPipesPage;