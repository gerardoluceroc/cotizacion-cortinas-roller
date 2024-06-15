import React from "react";
import NavigationBar from '../../components/Papelera/NavigationBar'
import Footer from '../../components/Footer'
import SellerLoginForm from "../../components/Papelera/LoginSellerForm";
import NavbarResponsive from "../../components/NavbarResponsive";

const SellerLoginFormPage = () => {
  return (
    <>
      <NavbarResponsive/>
      <SellerLoginForm />
      <Footer />
    </>
  )
};

export default SellerLoginFormPage;