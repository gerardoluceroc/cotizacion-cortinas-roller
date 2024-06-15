import React from "react";

import Footer from '../components/Footer'
import NavbarResponsive from "../components/NavbarResponsive";
import IVAList from "../components/AdministratorView/AdministrateIVA/IVAList";

const AdminReadIVAPage = () => {
  return (
    <>
      <NavbarResponsive/>
      <IVAList />
      <Footer />
    </>
  )
};

export default AdminReadIVAPage;