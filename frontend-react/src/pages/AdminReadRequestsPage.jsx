import React from "react";
import Footer from '../components/Footer'
import ReadAllRequests from "../components/AdministratorView/AdministrateRequests/ReadAllRequests";
import NavbarResponsive from "../components/NavbarResponsive";

const AdminReadRequestsPage = () => {
  return (
    <>
      <NavbarResponsive/>
      <ReadAllRequests/>
      <Footer />
    </>
  )
};

export default AdminReadRequestsPage;