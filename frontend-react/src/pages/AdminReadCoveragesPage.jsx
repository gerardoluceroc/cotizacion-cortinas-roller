import React from "react";
import Footer from '../components/Footer'
import NavbarResponsive from "../components/NavbarResponsive";
import CoveragesList from "../components/AdministratorView/AdministrateCoverages/CoveragesList";

const AdminReadCoveragesPage = () => {
  return (
    <>
      <NavbarResponsive/>
      <CoveragesList />
      <Footer />
    </>
  )
};

export default AdminReadCoveragesPage;