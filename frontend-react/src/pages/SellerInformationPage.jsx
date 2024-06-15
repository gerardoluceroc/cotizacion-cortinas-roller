import React from "react";
import NavigationBar from '../components/Papelera/NavigationBar'
import Footer from '../components/Footer'
import SellerInformation from "../components/SellerView/SellerInformation";
import NavbarResponsive from "../components/NavbarResponsive";

const SellerInformationPage = () => {
  return (
    <>
      <NavbarResponsive/>
      <SellerInformation />
      <Footer />
    </>
  )
};

export default SellerInformationPage;