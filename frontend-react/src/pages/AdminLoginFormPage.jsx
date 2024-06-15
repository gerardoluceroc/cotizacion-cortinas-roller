import React from "react";
import NavigationBar from '../components/Papelera/NavigationBar'
import Footer from '../components/Footer'
import AdminLoginForm from "../components/AdministratorView/LoginAdminForm";
import NavbarResponsive from "../components/NavbarResponsive";

const AdminLoginFormPage = () => {
  return (
    <>
      <NavbarResponsive/>
      <AdminLoginForm />
      <Footer />
    </>
  )
};

export default AdminLoginFormPage;