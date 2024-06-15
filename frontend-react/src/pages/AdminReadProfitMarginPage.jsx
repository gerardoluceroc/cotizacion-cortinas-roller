import React from "react";
import Footer from '../components/Footer'
import NavbarResponsive from "../components/NavbarResponsive";
import ProfitMarginList from "../components/AdministratorView/AdministrateProfitMargin/ProfitMarginList";

const AdminReadProfitMarginPage = () => {
  return (
    <>
      <NavbarResponsive/>
      <ProfitMarginList />
      <Footer />
    </>
  )
};

export default AdminReadProfitMarginPage;