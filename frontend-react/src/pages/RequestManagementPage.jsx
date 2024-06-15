import styled from "styled-components";
import Footer from "../components/Footer"
import NavbarResponsive from "../components/NavbarResponsive";
import RequestManagementUI from "../components/RequestManegementUI";


const RequestManagementPage = () => {
    return (
        <>
            <NavbarResponsive/>
            <RequestManagementUI/>
            <Footer />
        </>
    )
};

export default RequestManagementPage;