import React from "react";

import Footer from '../components/Footer'
import NavbarResponsive from "../components/NavbarResponsive";
import CurtainsList from "../components/AdministratorView/AdministrateCurtains/CurtainsList";

const AdminReadCurtainsPage = () => {
  return (
    <>
      <NavbarResponsive/>
      <CurtainsList />
      <Footer />
    </>
  )
};

export default AdminReadCurtainsPage;