import React from "react";
import NavigationBar from '../../components/Papelera/NavigationBar'
import Footer from '../../components/Footer'
import ExecutiveLoginForm from "../../components/Papelera/LoginExecutiveForm";
import NavbarResponsive from "../../components/NavbarResponsive";

const ExecutiveLoginFormPage = () => {
  return (
    <>
      <NavbarResponsive/>
      <ExecutiveLoginForm />
      <Footer />
    </>
  )
};

export default ExecutiveLoginFormPage;