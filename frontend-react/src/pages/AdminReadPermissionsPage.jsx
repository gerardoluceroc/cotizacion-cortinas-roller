import React from "react";
import NavigationBarLogged from "../components/Papelera/NavigationBarLogged";
import Footer from '../components/Footer'
import ReadPermissions from "../components/AdministratorView/AdministratePermissions/ReadPermissions";

const AdminReadPermissionsPage = () => {
  return (
    <>
      <NavigationBarLogged />
      <ReadPermissions />
      <Footer />
    </>
  )
};

export default AdminReadPermissionsPage;