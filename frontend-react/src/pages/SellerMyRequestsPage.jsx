import SellerMyRequests from "../components/SellerView/SellerMyRequests"; 
import Footer from "../components/Footer"
import NavigationBarLogged from "../components/Papelera/NavigationBarLogged";
import NavbarResponsive from "../components/NavbarResponsive";
import { SellerMyRequestsList } from "../components/SellerView/SellerMyRequestsList";
import styled from "styled-components";


const MyRequestSection = styled.div`
  display: flex;
  flex-direction: column;
  gap: 30px;
  width: 100%;
  min-height: calc(100vh - 80px);
  margin-top: 14px;

`;


const SellerMyRequestsPage = () => {
    return (
        <>
            <NavbarResponsive/>
            <MyRequestSection>
                <SellerMyRequestsList/>
            </MyRequestSection>
            <Footer />
        </>
    )
};

export default SellerMyRequestsPage;

