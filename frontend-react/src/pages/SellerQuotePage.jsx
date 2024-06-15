import Footer from "../components/Footer"
import NavbarResponsive from "../components/NavbarResponsive";
import styled from "styled-components";
import SellerQuote from "../components/SellerView/SellerQuote";


const Div = styled.div`
    min-height: calc(85vh - 80px);
`;

const SellerQuotePage = () => {
    return (
        <>
            <NavbarResponsive />
            <Div>
                <SellerQuote />
            </Div>

            <Footer />
        </>
    )
};

export default SellerQuotePage;

