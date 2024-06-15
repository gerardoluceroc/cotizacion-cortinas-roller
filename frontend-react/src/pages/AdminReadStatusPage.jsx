import React from "react";
import Footer from '../components/Footer'
import NavbarResponsive from "../components/NavbarResponsive";
import StatusList from "../components/AdministratorView/AdministrateStatus/StatusList";

const AdminReadStatusPage = () => {
  return (
    <>
      <NavbarResponsive/>
      <StatusList/>
      <Footer />
    </>
  )
};

export default AdminReadStatusPage;