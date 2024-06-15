import Footer from "../components/Footer"
import NavbarResponsive from "../components/NavbarResponsive";
import styled from "styled-components";
import SellerMyQuotes from "../components/SellerView/SellerMyQuotes";



const Div = styled.div`
    min-height: calc(85vh - 80px);
`;

const SellerMyQuotesPage = () => {
    return (
        <>
            <NavbarResponsive />
            <Div>
                <SellerMyQuotes/>
            </Div>

            <Footer />
        </>
    )
};

export default SellerMyQuotesPage;
