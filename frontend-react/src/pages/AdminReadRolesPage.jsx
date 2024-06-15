import React from "react";
import Footer from '../components/Footer'
import NavbarResponsive from "../components/NavbarResponsive";
import RolesList from "../components/AdministratorView/AdministrateRoles/RolesList";

const AdminReadRolesPage = () => {
  return (
    <>
      <NavbarResponsive/>
      <RolesList/>
      <Footer />
    </>
  )
};

export default AdminReadRolesPage;